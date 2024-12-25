package com.exam.service;

import com.exam.model.exam.GeminiRequest;
import com.exam.model.exam.GeminiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.util.stream.Collectors;

@Service
public class QuizGeminiService {

    private static final Logger logger = LoggerFactory.getLogger(QuizGeminiService.class);

    @Value("${google.gemini.api.url}")
    private String apiURL;

    @Value("${google.gemini.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    private static final int MAX_RETRIES = 3; // Maximum retries for response validation
    private static final long RETRY_DELAY = 2000L; // Delay between retries in milliseconds

    public List<String> evaluateQuiz(GeminiRequest geminiRequest) throws InterruptedException {
        String fullApiUrl = apiURL + "?key=" + apiKey;

        // Log the full URL and request body for debugging
        logger.debug("Full API URL: " + fullApiUrl);
        logger.debug("Request Body: " + geminiRequest);

        int attempts = 0;
        while (attempts < MAX_RETRIES) {
            try {
                // Perform POST request
                GeminiResponse geminiResponse = restTemplate.postForObject(fullApiUrl, geminiRequest, GeminiResponse.class);

                // Log the response body
                logger.debug("Response Body: " + geminiResponse);

                if (geminiResponse == null || geminiResponse.getCandidates() == null || geminiResponse.getCandidates().isEmpty()) {
                    throw new IllegalStateException("Received null or empty response from the API");
                }

                List<String> responseParts = geminiResponse.getCandidates().stream()
                        .flatMap(candidate -> candidate.getContent().getParts().stream())
                        .map(GeminiResponse.Part::getText)
                        .collect(Collectors.toList());
                System.out.println(responseParts);
                // Validate response format for each part
                if (responseParts.stream().allMatch(this::validateResponseFormat)) {
                    System.out.println("This is the response Parts: " + responseParts);
                    return processResponseParts(responseParts);
                } else {
                    logger.warn("Response format invalid. Retrying... Attempt {}/{}", attempts + 1, MAX_RETRIES);
                    Thread.sleep(RETRY_DELAY);
                }
            } catch (HttpClientErrorException e) {
                logger.error("Error response from API: Status code: " + e.getStatusCode() + ", Response body: " + e.getResponseBodyAsString());
                throw e;
            } catch (Exception e) {
                logger.error("Unexpected error: ", e);
                throw e;
            }
            attempts++;
        }

        throw new IllegalStateException("Failed to retrieve a valid response after " + MAX_RETRIES + " attempts.");
    }

    private boolean validateResponseFormat(String responseText) {
        System.out.println("Validating Response Format...");
        System.out.println("Response Text: " + responseText);
        // Regex patterns
        String questionPattern = "(?s).*Q\\d+[a-z]*(?:i{1,3})?:.*?Answer:.*";
//        String answerPattern = "(?s).*Answer:.*"; // Sort of Flexible

        String answerPattern = "(?s)Answer:.*?(?=\\n|$)\n";

        String marksPattern = "(?s).*\\d+/\\d+.*";
        // Validate question format
        boolean hasQuestions = Pattern.compile(questionPattern).matcher(responseText).find();
        // Validate if "Answer" exists with proper structure
        boolean hasAnswers = Pattern.compile(answerPattern).matcher(responseText).find();
        // Validate if marks in "x/y" format exist
        boolean hasMarks = Pattern.compile(marksPattern).matcher(responseText).find();
        // Log validation results
        System.out.println("Has Questions: " + hasQuestions);
        System.out.println("Has Answers: " + hasAnswers);
        System.out.println("Has Marks: " + hasMarks);
        return hasQuestions && hasAnswers && hasMarks;
    }


    private List<String> processResponseParts(List<String> responseParts) {
        List<String> results = new ArrayList<>();
        for (String part : responseParts) {
            // Extract questions with labels
            List<String> questions = extractQuestions(part);
            // Extract answers
            List<String> answers = extractAnswers(part);
            // Extract marks
            List<String> marks = extractMarks(part);
            // Combine questions, answers, and marks
            for (int i = 0; i < questions.size(); i++) {
                String question = questions.get(i);
                String answer = (i < answers.size()) ? answers.get(i) : "N/A";
                String mark = (i < marks.size()) ? marks.get(i) : "N/A";
                results.add(String.format("%s, Answer: %s, Marks: %s", question, answer, mark));
            }
        }
        return results;
    }

    // Helper method to extract questions with labels
    private static List<String> extractQuestions(String text) {
        List<String> questions = new ArrayList<>();
        String questionPattern = "(Q\\d+[a-z]*(?:i{1,3})?):\\s*(.+?)\\s*Answer:";
        Pattern pattern = Pattern.compile(questionPattern, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String label = matcher.group(1).trim();     // Capture the question label (e.g., Q1a)
            String question = matcher.group(2).trim(); // Capture the question text
            questions.add(String.format("%s: %s", label, question));
        }
        if (questions.isEmpty()) {
            logger.warn("No questions were extracted. Please verify the input text format.");
        }
        return questions;
    }

    // Helper method to extract answers
    private static List<String> extractAnswers(String text) {
        List<String> answers = new ArrayList<>();
        String answerPattern = "Answer:\\s*(.+?)(?=\\n|\\*|$)";
        Pattern pattern = Pattern.compile(answerPattern, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String answer = matcher.group(1).trim();
            answers.add(answer);
        }
        return answers;
    }

    // Helper method to extract marks
    private static List<String> extractMarks(String questionText) {
        List<String> marks = new ArrayList<>();
        String regex = "(\\d+/\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(questionText);

        while (matcher.find()) {
            marks.add(matcher.group(1));
        }
        return marks;
    }
}
