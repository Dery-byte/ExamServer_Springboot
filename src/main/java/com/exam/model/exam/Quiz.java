package com.exam.model.exam;

import com.exam.model.QuizStatus;
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
    @Column(length =  100)
    private String description;
    private String maxMarks;
    @Column(length =  50, nullable = false)
    private String quizTime;

    @Column(length =  50, nullable = false)
    private String numberOfQuestions;
    private  boolean active = false;
    private boolean attempted=false;
    @Column(length =  50, nullable = false)
    private String quizpassword;
    //add ...



    //Trying to check for one quiz attempts

    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Report> reports = new LinkedHashSet<>();
    @Enumerated(EnumType.STRING)

    private QuizStatus status = QuizStatus.OPEN;




    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Category category;

     @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
     @JsonIgnore
     private Set<Questions> questions = new HashSet<>();


    public String getQuizTime() {
        return quizTime;
    }

    public void setQuizTime(String quizTime) {
        this.quizTime = quizTime;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public Quiz(Long qId, String title, String description, String maxMarks, String numberOfQuestions, boolean active, boolean attempted, String quizpassword, Set<Report> reports, Category category, Set<Questions> questions) {
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
    @JsonCreator
    public Quiz() {
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

    public String getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(String maxMarks) {
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

    public QuizStatus getStatus() {
        return status;
    }

    public void setStatus(QuizStatus status) {
        this.status = status;
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
