package com.exam.model.exam;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "theory_questions")
public class TheoryQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long TqId;

    @Column(nullable = false)
    private String  quesNo;

//    # MAKE SURE THE THEORY IS ONLY UPLOADABLE NOTHING ELSE.
    @Column(nullable = false, length = 5000)
    private String question;

    @Transient
    private String answer;
    @Column(nullable = false)
    private String marks;

    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public TheoryQuestions() {
    }

    public TheoryQuestions(Long tqId,String  quesNo, String question, String marks,String answer, Quiz quiz) {
        TqId = tqId;
        this.question = question;
        this.marks = marks;
        this.quiz = quiz;
        this.answer = answer;
        this.quesNo = quesNo;
    }

    public String getQuesNo() {
        return quesNo;
    }

    public void setQuesNo(String quesNo) {
        this.quesNo = quesNo;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getTqId() {
        return TqId;
    }

    public void setTqId(Long tqId) {
        TqId = tqId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }




}
