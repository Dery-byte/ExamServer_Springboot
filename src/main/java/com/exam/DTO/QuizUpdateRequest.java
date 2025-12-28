package com.exam.DTO;
import com.exam.model.QuizStatus;
import com.exam.model.QuizType;

import java.time.LocalDate;
import java.time.LocalTime;

public class QuizUpdateRequest {

    private Long qId;
    private String title;
    private String description;
    private String maxMarks;
    private String quizTime;
    private String numberOfQuestions;
    private boolean active;
    private String quizpassword;
    private QuizStatus status;
    private QuizType quizType;
    private LocalTime startTime;
    private LocalDate quizDate;
    private Long categoryId; // Only send category ID, not the entire object

    // Constructors
    public QuizUpdateRequest() {
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

    public String getQuizpassword() {
        return quizpassword;
    }

    public void setQuizpassword(String quizpassword) {
        this.quizpassword = quizpassword;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}