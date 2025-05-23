package com.exam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Value("${google.gemini.api.key}")
    private String apiKey;

    @Bean
    public String geminiApiKey() {
        return apiKey;
    }
}
