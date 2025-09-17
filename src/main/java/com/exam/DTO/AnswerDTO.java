package com.exam.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AnswerDTO {
    private Long answerId;
    private String studentAnswer;
    private double score;
    private Integer maxMarks;
    private String feedback;
    private List<String> keyMissed;
    private Long theoryQuestionId;
    private Long quizId;


    // extra fields from TheoryQuestions
    private String question;
    private String quesO;
}
