package com.exam.DTO;

import lombok.Data;
import java.util.List;

/**
 * Student-facing DTO for objective questions.
 *
 * IMPORTANT: correct_answer is intentionally EXCLUDED here.
 * Sending correct answers to the client is a security risk —
 * students can inspect network responses and cheat.
 *
 * correct_answer should only be used server-side during grading.
 */
@Data
public class QuestionResponseDTO {

    private Long quesId;

    /** Assigned after shuffle — reflects actual display order */
    private int count;

    /** Question text (may contain HTML) */
    private String content;

    /** Optional image URL */
    private String image;

    // ── Answer options ──
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    /**
     * Student's current selected answers.
     * Empty list = unanswered.
     * List because multi-select questions use .includes() in the frontend.
     */
    private List<String> givenAnswer;

    // ── NOTE: correct_answer is NOT included ──
    // It is only used server-side in the grading/submission logic.
}