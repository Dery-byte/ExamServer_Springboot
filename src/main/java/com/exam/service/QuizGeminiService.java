package com.exam.service;

import com.exam.model.exam.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class QuizGeminiService {
    private static final Logger logger = LoggerFactory.getLogger(QuizGeminiService.class);

    @Value("${google.gemini.api.url}")
    private String apiURL;

    @Value("${google.gemini.api.key}")
    private String apiKey;



//    @Value("${deepseek.api.url}")
//    private String apiURL;
//
//    @Value("deepseek.api.key")
//    private String apiKey;




    @Autowired
    private RestTemplate restTemplate;

    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 2000L;

    public List<QuestionEvaluationResult> evaluateQuiz(GeminiRequest request) {
        List<QuestionSubmission> submissions = parseSubmissions(request);
        return submissions.stream()
                .map(this::evaluateSingleQuestion)
                .collect(Collectors.toList());
    }

    private QuestionEvaluationResult evaluateSingleQuestion(QuestionSubmission submission) {
        String fullApiUrl = apiURL + "?key=" + apiKey;
        int attempts = 0;

        while (attempts < MAX_RETRIES) {
            try {
                GeminiRequest evaluationRequest = createEvaluationRequest(submission);
                GeminiResponse response = restTemplate.postForObject(
                        fullApiUrl,
                        evaluationRequest,
                        GeminiResponse.class
                );
                return parseEvaluationResponse(response, submission);
            } catch (Exception e) {
                attempts++;
                logger.warn("Attempt {} failed for question {}: {}",
                        attempts, submission.getQuestionNumber(), e.getMessage());

                if (attempts >= MAX_RETRIES) {
                    return createFailedEvaluation(submission, e);
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return createFailedEvaluation(submission, ie);
                }
            }
        }
        return createFailedEvaluation(submission, new Exception("Max retries exceeded"));
    }

    private GeminiRequest createEvaluationRequest(QuestionSubmission submission) {
        String prompt = String.format(
                "ACT AS A STRICT EXAMINER. Evaluate this answer and respond ONLY with this JSON format:\n" +
                        "{\n" +
                        "  \"score\": number (0-%d),\n" +
                        "  \"feedback\": string,\n" +
                        "  \"keyMissed\": string[]\n" +
                        "}\n\n" +
                        "QUESTION: %s\n" +
                        "CRITERIA: %s\n" +
                        "MAX MARKS: %d\n" +
                        "STUDENT ANSWER: %s",
                submission.getMaxMarks(),
                submission.getQuestion(),
                submission.getCriteria(),
                submission.getMaxMarks(),
                submission.getStudentAnswer()
        );

        return new GeminiRequest(prompt);
    }

    private QuestionEvaluationResult parseEvaluationResponse(GeminiResponse response, QuestionSubmission submission) {
        if (response == null || response.getCandidates() == null || response.getCandidates().isEmpty()) {
            throw new IllegalArgumentException("Empty API response");
        }

        String responseText = response.getCandidates().get(0)
                .getContent()
                .getParts()
                .get(0)
                .getText();

        try {
            // Extract JSON part (remove markdown code fences if present)
            responseText = responseText.replaceAll("^```json|```$", "").trim();

            double score = extractDoubleValue(responseText, "score");
            String feedback = extractStringValue(responseText, "feedback");
            List<String> keyMissed = extractStringArray(responseText, "keyMissed");

            // Ensure score is within bounds
            score = Math.max(0, Math.min(score, submission.getMaxMarks()));

            return new QuestionEvaluationResult(
                    submission.getQuestionNumber(),
                    submission.getQuestion(),  // Include original question
                    submission.getStudentAnswer(),  // Include student's answer
                    score,
                    submission.getMaxMarks(),
                    feedback,
                    keyMissed
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse evaluation: " + responseText, e);
        }
    }




    // Helper methods for JSON parsing

    private double extractDoubleValue(String json, String key) {
        try {
            int start = json.indexOf("\"" + key + "\":") + key.length() + 3;
            int end = findNextDelimiter(json, start);
            String numberStr = cleanNumberString(json.substring(start, end).trim());
            return Double.parseDouble(numberStr);
        } catch (Exception e) {
            logger.error("Failed to parse {} from JSON: {}", key, json);
            throw new IllegalArgumentException("Invalid " + key + " format", e);
        }
    }

    private String cleanNumberString(String numberStr) {
        // Remove trailing decimal point
        if (numberStr.endsWith(".")) {
            numberStr = numberStr.substring(0, numberStr.length() - 1);
        }
        // Remove any non-numeric characters except decimal point and minus
        return numberStr.replaceAll("[^\\d.-]", "");
    }

    private int findNextDelimiter(String json, int start) {
        int commaPos = json.indexOf(",", start);
        int bracePos = json.indexOf("}", start);
        return commaPos == -1 ? bracePos : Math.min(commaPos, bracePos);
    }




    private String extractStringValue(String json, String key) {
        int start = json.indexOf("\"" + key + "\":\"") + key.length() + 4;
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private List<QuestionSubmission> parseSubmissions(GeminiRequest request) {
        return request.getContents().stream()
                .flatMap(content -> content.getParts().stream())
                .map(part -> {
                    String text = part.getText();
                    return new QuestionSubmission(
                            extractField(text, "Question Number:", ":"),
                            extractField(text, ":", "Answer:"),
                            extractField(text, "Answer:", "Marks:"),
                            Integer.parseInt(extractField(text, "Marks:", "Criteria:").trim()),
                            extractField(text, "Criteria:", null)
                    );
                })
                .collect(Collectors.toList());
    }

    private String extractField(String text, String startDelimiter, String endDelimiter) {
        int start = text.indexOf(startDelimiter) + startDelimiter.length();
        int end = endDelimiter != null ? text.indexOf(endDelimiter, start) : text.length();
        return text.substring(start, end).trim();
    }

    private QuestionEvaluationResult createFailedEvaluation(QuestionSubmission submission, Exception e) {
        return new QuestionEvaluationResult(
                submission.getQuestionNumber(),
                submission.getQuestion(), // Add the question text
                submission.getStudentAnswer(), // Add student's answer
                0, // score
                submission.getMaxMarks(),
                "Evaluation failed after " + MAX_RETRIES + " attempts: " + e.getMessage(),
                Collections.emptyList() // empty list for keyMissed
        );
    }


    // Add this helper method to extract string arrays
    private List<String> extractStringArray(String json, String key) {
        try {
            int start = json.indexOf("\"" + key + "\":") + key.length() + 3;
            int end = json.indexOf("]", start) + 1;
            String arrayStr = json.substring(start, end).trim();
            return Arrays.asList(arrayStr.replaceAll("[\\[\\]\"]", "").split(",\\s*"));
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}