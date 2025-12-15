package com.exam.service;

import com.exam.DTO.GPTMessage;
import com.exam.DTO.GPTRequest;
import com.exam.DTO.GPTResponse;
import com.exam.model.User;
import com.exam.model.exam.*;
import com.exam.repository.AnswerRepository;
import com.exam.repository.TheoryQuestionsRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class QuizGPTService {
    private static final Logger logger = LoggerFactory.getLogger(QuizGPTService.class);

    @Value("${openai.api.url}")
    private String apiURL;

    @Value("${openai.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 2000L;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TheoryQuestionsRepository theoryQuestionsRepository;

    // 🔍 Add this to verify configuration on startup
    @PostConstruct
    public void init() {
        logger.info("=== OpenAI Configuration ===");
        logger.info("API URL: {}", apiURL);
        logger.info("API Key configured: {}", apiKey != null && !apiKey.isEmpty() ? "YES" : "NO");
        if (apiKey != null && apiKey.length() > 10) {
            logger.info("API Key starts with: {}...", apiKey.substring(0, 10));
        } else {
            logger.error("API Key is MISSING or INVALID!");
        }
        logger.info("============================");
    }




    @Transactional
    public List<QuestionEvaluationResult> evaluateQuiz(GeminiRequest request, User user) {
        List<QuestionSubmission> submissions = parseSubmissions(request);

        List<QuestionEvaluationResult> results = submissions.stream()
                .map(this::evaluateSingleQuestion)
                .collect(Collectors.toList());

        // ✅ FIX: Only save successful evaluations to database
        List<QuestionEvaluationResult> successfulResults = new ArrayList<>();

        for (QuestionEvaluationResult result : results) {
            // Skip saving if evaluation failed (score is 0 and has error message)
            if (result.getFeedback().startsWith("Evaluation failed")) {
                logger.warn("Skipping database save for failed evaluation: Question {} - {}",
                        result.getQuestionNumber(), result.getFeedback());
                successfulResults.add(result);
                continue;
            }

            try {
                TheoryQuestions theoryQuestions = theoryQuestionsRepository.findById(Long.valueOf(result.getTqid()))
                        .orElseThrow(() -> new RuntimeException("Question not found with tqid: " + result.getTqid()));

                Long quizId = theoryQuestions.getQuiz().getqId();
                Answer answer = mapToAnswer(result, user, theoryQuestions, quizId);
                answerRepository.save(answer);

                logger.info("Successfully saved evaluation for question {}", result.getQuestionNumber());
                successfulResults.add(result);
                logger.info( "This is the results {}", result);

            } catch (Exception e) {
                logger.error("Failed to save evaluation for tqid {}: {}", result.getTqid(), e.getMessage());
                // Still include the result but mark it as having a save error
                result.setFeedback(result.getFeedback() + " [Note: Failed to save to database]");
                successfulResults.add(result);
            }
        }

        return successfulResults;
    }





    private QuestionEvaluationResult evaluateSingleQuestion(QuestionSubmission submission) {
        int attempts = 0;

        while (attempts < MAX_RETRIES) {
            try {
                // Validate API key before making request
                if (apiKey == null || apiKey.trim().isEmpty() || apiKey.contains("${")) {
                    throw new IllegalStateException("OpenAI API key is not configured properly. Check application.properties");
                }

                GPTRequest evaluationRequest = createEvaluationRequest(submission);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(apiKey);

                // 🔍 Debug logging
                logger.debug("Making request to: {}", apiURL);
                logger.debug("Authorization header present: {}", headers.containsKey("Authorization"));

                HttpEntity<GPTRequest> entity = new HttpEntity<>(evaluationRequest, headers);

                GPTResponse response = restTemplate.postForObject(
                        apiURL,
                        entity,
                        GPTResponse.class
                );
                return parseEvaluationResponse(response, submission);

            } catch (HttpClientErrorException e) {
                attempts++;
                logger.warn("Attempt {} failed for question {}: {} {}",
                        attempts, submission.getQuestionNumber(),
                        e.getStatusCode(), e.getMessage());

                // 🔍 Detailed error logging for 401
                if (e.getStatusCode().value() == 401) {
                    logger.error("❌ AUTHENTICATION FAILED - Check your OpenAI API key!");
                    logger.error("Status: {}", e.getStatusCode());
                    logger.error("Response: {}", e.getResponseBodyAsString());
                    logger.error("API Key starts with: {}",
                            apiKey != null && apiKey.length() > 10 ? apiKey.substring(0, 10) + "..." : "MISSING");
                }

                if (attempts >= MAX_RETRIES) {
                    return createFailedEvaluation(submission, e);
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return createFailedEvaluation(submission, ie);
                }

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

    private GPTRequest createEvaluationRequest(QuestionSubmission submission) {
        String prompt = String.format(
                "ACT AS A STRICT EXAMINER. Evaluate this answer and respond ONLY with this JSON format:\n" +
                        "{\n" +
                        "  \"score\": decimal (0-%.2f),\n" +
                        "  \"feedback\": string,\n" +
                        "  \"keyMissed\": string[]\n" +
                        "}\n\n" +
                        "QUESTION: %s\n" +
                        "CRITERIA: %s\n" +
                        "MAX MARKS: %.2f\n" +
                        "STUDENT ANSWER: %s",
                submission.getMaxMarks(),
                submission.getQuestion(),
                submission.getCriteria(),
                submission.getMaxMarks(),
                submission.getStudentAnswer()
        );

        GPTRequest request = new GPTRequest();
        request.setModel("gpt-3.5-turbo"); // Using 3.5 for cost-effectiveness, change to "gpt-4-turbo-preview" for better accuracy
        request.setTemperature(0.3);
        request.setMaxTokens(500);

        GPTMessage message = new GPTMessage();
        message.setRole("user");
        message.setContent(prompt);

        request.setMessages(Collections.singletonList(message));

        return request;
    }

    private QuestionEvaluationResult parseEvaluationResponse(GPTResponse response, QuestionSubmission submission) {
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            throw new IllegalArgumentException("Empty API response");
        }

        String responseText = response.getChoices().get(0)
                .getMessage()
                .getContent();

        try {
            // Extract JSON part (remove markdown code fences if present)
            responseText = responseText.replaceAll("^```json\\s*|\\s*```$", "").trim();

            double score = extractDoubleValue(responseText, "score");
            String feedback = extractStringValue(responseText, "feedback");
            List<String> keyMissed = extractStringArray(responseText, "keyMissed");

            // Ensure score is within bounds
            score = Math.max(0, Math.min(score, submission.getMaxMarks()));

            return new QuestionEvaluationResult(
                    submission.getQuizId(),
                    submission.getTqid(),
                    submission.getQuestionNumber(),
                    submission.getQuestion(),
                    submission.getStudentAnswer(),
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
        if (numberStr.endsWith(".")) {
            numberStr = numberStr.substring(0, numberStr.length() - 1);
        }
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

    private List<QuestionSubmission> parseSubmissions(GeminiRequest request) {
        return request.getContents().stream()
                .flatMap(content -> content.getParts().stream())
                .map(part -> {
                    String text = part.getText();
                    return new QuestionSubmission(
                            extractField(text, "quizId ", ":"),
                            extractField(text, "tqid ", ":"),
                            extractField(text, "Question Number ", ":"),
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
        String errorMessage = "Evaluation failed after " + MAX_RETRIES + " attempts: " + e.getMessage();

        // Add specific guidance for 401 errors
        if (e instanceof HttpClientErrorException) {
            HttpClientErrorException httpError = (HttpClientErrorException) e;
            if (httpError.getStatusCode().value() == 401) {
                errorMessage = "Authentication failed: Invalid or missing OpenAI API key. Please check your configuration.";
            }
        }

        return new QuestionEvaluationResult(
                submission.getQuizId(),
                submission.getTqid(),
                submission.getQuestionNumber(),
                submission.getQuestion(),
                submission.getStudentAnswer(),
                0,
                submission.getMaxMarks(),
                errorMessage,
                Collections.emptyList()
        );
    }

    private Answer mapToAnswer(QuestionEvaluationResult result, User user, TheoryQuestions theoryQuestions, Long quizId) {
        Answer answer = new Answer();
        answer.setStudentAnswer(result.getStudentAnswer());
        answer.setScore(result.getScore());
        answer.setMaxMarks(result.getMaxMarks());
        answer.setFeedback(result.getFeedback());
        answer.setKeyMissed(result.getKeyMissed());
        answer.setUser(user);
        answer.setTheoryQuestion(theoryQuestions);

        Quiz quiz = new Quiz();
        quiz.setqId(quizId);
        answer.setQuiz(quiz);

        return answer;
    }
}