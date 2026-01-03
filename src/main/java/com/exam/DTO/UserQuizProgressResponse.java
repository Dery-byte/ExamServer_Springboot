package com.exam.DTO;

import lombok.Data;

import java.util.List;
import java.util.Map;

// Bulk response DTO

@Data
public class UserQuizProgressResponse {
    private Map<Long, List<String>> answers;

    public UserQuizProgressResponse(Map<Long, List<String>> answers) {
        this.answers = answers;
    }

}