package com.exam.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class GeminiAIService {
    private final String apiKey;
    private final RestTemplate restTemplate;
    @Autowired
    public GeminiAIService(String apiKey, RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }
    public String getSomeDataFromGemini() {
        String url = "https://api.gemini.com/v1/order/status";
        return restTemplate.getForObject(url + "?apiKey=" + apiKey, String.class);
    }
}
