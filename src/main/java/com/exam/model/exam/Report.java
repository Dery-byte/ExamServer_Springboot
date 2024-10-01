package com.exam.model.exam;

import com.exam.model.User;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name="report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(precision = 10, scale = 1)
    private BigDecimal marks;
    private BigDecimal marksB;


    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'Completed'")
    private String progress;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    public Report(Long id, BigDecimal marks, BigDecimal marksB, Quiz quiz, User user, String progress) {
        this.progress = progress;
        this.id = id;
        this.marks = marks;
        this.quiz = quiz;
        this.marksB = marksB;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Report() {
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMarks() {
        return marks;
    }

    public void setMarks(BigDecimal marks) {
        this.marks = marks;
    }

    public BigDecimal getMarksB() {
        return marksB;
    }

    public void setMarksB(BigDecimal marksB) {
        this.marksB = marksB;
    }
}
