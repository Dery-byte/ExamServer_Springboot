package com.exam.service;


import com.exam.model.exam.GeminiRequest;
import com.exam.model.exam.GeminiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

//import static QuizGeminiService.logger;
//import static QuizGeminiService.logger;

@Service
public class QuizGeminiService {

    private static final Logger logger = LoggerFactory.getLogger(QuizGeminiService.class);

    @Value("${google.gemini.api.url}")
    private String apiURL;

    @Value("${google.gemini.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public List<String> evaluateQuiz(GeminiRequest geminiRequest) {
        String fullApiUrl = UriComponentsBuilder.fromHttpUrl(apiURL)
                .queryParam("key", apiKey)
                .toUriString();

        // Log the full URL and request body for debugging
        logger.debug("Full API URL: " + fullApiUrl);
        logger.debug("Request Body: " + geminiRequest);

        // Create headers and set Content-Type
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an HttpEntity with headers and body
        HttpEntity<GeminiRequest> requestEntity = new HttpEntity<>(geminiRequest, headers);

        System.out.println("This is the requestEntity: " + requestEntity);

        try {
            // Perform POST request
            GeminiResponse geminiResponse = restTemplate.exchange(fullApiUrl, HttpMethod.POST, requestEntity, GeminiResponse.class)
                    .getBody();
            System.out.println("This is the response body: " + geminiResponse);
            if (geminiResponse == null || geminiResponse.getCandidates() == null || geminiResponse.getCandidates().isEmpty()) {
                throw new IllegalStateException("Received null or empty response from the API");
            }


            // Extract and return only the text content
            return geminiResponse.getCandidates().stream()
                    .flatMap(candidate -> candidate.getContent().getParts().stream())
                    .map(GeminiResponse.Part::getText)
                    .collect(Collectors.toList());

        } catch (HttpClientErrorException e) {
            logger.error("Error response from API: Status code: " + e.getStatusCode() + ", Response body: " + e.getResponseBodyAsString());
            throw e; // Or handle the error as needed
        } catch (Exception e) {
            logger.error("Unexpected error: ", e);
            throw e;
        }
    }
}