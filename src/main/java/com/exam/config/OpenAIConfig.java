package com.exam.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class OpenAIConfig {
    @Value("${OPENAI_API_KEY}")
    private String apiKey;
}