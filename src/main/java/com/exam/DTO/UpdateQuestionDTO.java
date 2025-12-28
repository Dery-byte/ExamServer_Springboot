package com.exam.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UpdateQuestionDTO {

    private Long quesId;
    private String content;
    private String image;

    private String option1;
    private String option2;
    private String option3;
    private String option4;

    private String[] correct_answer;

    // getters & setters
}
