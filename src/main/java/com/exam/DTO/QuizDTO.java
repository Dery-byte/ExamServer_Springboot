package com.exam.DTO;

import com.exam.model.QuizStatus;
import com.exam.model.QuizType;
import com.exam.model.exam.Quiz;

import java.time.LocalDate;
import java.time.LocalTime;

public class QuizDTO {

    private Long qId;
    private String title;
    private String description;
    private String maxMarks;
    private String quizTime;
    private String numberOfQuestions;
    private boolean active;
    private boolean attempted;
    private QuizStatus status;
    private QuizType quizType;
    private LocalTime startTime;
    private LocalDate quizDate;
    private CategoryDTO category;

    // Constructors
    public QuizDTO() {
    }

    public QuizDTO(Quiz quiz) {
        this.qId = quiz.getqId();
        this.title = quiz.getTitle();
        this.description = quiz.getDescription();
        this.maxMarks = quiz.getMaxMarks();
        this.quizTime = quiz.getQuizTime();
        this.numberOfQuestions = quiz.getNumberOfQuestions();
        this.active = quiz.isActive();
        this.attempted = quiz.isAttempted();
        this.status = quiz.getStatus();
        this.quizType = quiz.getQuizType();
        this.startTime = quiz.getStartTime();
        this.quizDate = quiz.getQuizDate();

        // Convert category to DTO (avoid sending user info)
        if (quiz.getCategory() != null) {
            this.category = new CategoryDTO(quiz.getCategory());
        }
    }

    // Getters and Setters
    public Long getqId() {
        return qId;
    }

    public void setqId(Long qId) {
        this.qId = qId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(String maxMarks) {
        this.maxMarks = maxMarks;
    }

    public String getQuizTime() {
        return quizTime;
    }

    public void setQuizTime(String quizTime) {
        this.quizTime = quizTime;
    }

    public String getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(String numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAttempted() {
        return attempted;
    }

    public void setAttempted(boolean attempted) {
        this.attempted = attempted;
    }

    public QuizStatus getStatus() {
        return status;
    }

    public void setStatus(QuizStatus status) {
        this.status = status;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getQuizDate() {
        return quizDate;
    }

    public void setQuizDate(LocalDate quizDate) {
        this.quizDate = quizDate;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }
}