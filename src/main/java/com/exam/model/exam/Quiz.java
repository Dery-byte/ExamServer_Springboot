package com.exam.model.exam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long qId;

    private String title;

    @Column(length =  5000)
    private String description;
    private double maxMarks;
    private String numberOfQuestions;
    private  boolean active = false;
    private boolean attempted=false;
    private String quizpassword;
    //add ...



    //Trying to check for one quiz attempts

    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Report> reports = new LinkedHashSet<>();





    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

     @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
     @JsonIgnore
     private Set<Questions> questions = new HashSet<>();




    @JsonCreator
    public Quiz() {
    }

    public Quiz(Long qId, String title, String description, double maxMarks, String numberOfQuestions, boolean active, boolean attempted, String quizpassword, Set<Report> reports, Category category, Set<Questions> questions) {
        this.qId = qId;
        this.title = title;
        this.description = description;
        this.maxMarks = maxMarks;
        this.numberOfQuestions = numberOfQuestions;
        this.active = active;
        this.attempted = attempted;
        this.quizpassword = quizpassword;
        this.reports = reports;
        this.category = category;
        this.questions = questions;
    }



    public boolean isAttempted() {
        return attempted;
    }

    public void setAttempted(boolean attempted) {
        this.attempted = attempted;
    }

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

    public double getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(double maxMarks) {
        this.maxMarks = maxMarks;
    }

    public String getQuizpassword() {
        return quizpassword;
    }

    public void setQuizpassword(String quizpassword) {
        this.quizpassword = quizpassword;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Questions> questions) {
        this.questions = questions;
    }
}
