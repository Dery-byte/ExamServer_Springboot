package com.exam.DTO;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class QuizProgressRequest{
    private Long questionId;
    private String option;
    private boolean checked;
    private Long quizId; // Optional

    // Getters and Setters
}





