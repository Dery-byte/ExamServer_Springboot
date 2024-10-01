package com.exam.model.exam;


import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long quesId;
    @Column(length = 5000)
    private String content;

    private String image;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

//    private String answer;

    @Convert(converter = StringArrayConverter.class)
    private String[] correct_answer;
    @Transient
    private String[] givenAnswer;

    public String[] getGivenAnswer() {
        return givenAnswer;
    }

    public Questions(String[] givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public void setGivenAnswer(String[] givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Questions() {
    }

//    public Questions(Long quesId, String content, String image,
//                     String option1, String option2, String option3,
//                     String option4, String answer, Quiz quiz) {
//        this.quesId = quesId;
//        this.content = content;
//        this.image = image;
//        this.option1 = option1;
//        this.option2 = option2;
//        this.option3 = option3;
//        this.option4 = option4;
////        this.answer = answer;
//        this.quiz = quiz;
//    }


    public Questions(Long quesId, String content, String image,
                     String option1, String option2,
                     String option3, String option4,
                     String[] correct_answer, String[] givenAnswer, Quiz quiz) {
        this.quesId = quesId;
        this.content = content;
        this.image = image;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correct_answer = correct_answer;
        this.givenAnswer = givenAnswer;
        this.quiz = quiz;
    }

    public void setcorrect_answer(String[] correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String[] getcorrect_answer() {
        return correct_answer;
    }

    // Method to set answer from a comma-separated string
    public void setAnswer(String correct_answer) {
        if (correct_answer.contains(",")) {
            this.correct_answer = correct_answer.split("\\s*,\\s*"); // Split by comma and trim spaces
        } else {
            this.correct_answer = new String[]{correct_answer};
        }
    }

    public Long getQuesId() {
        return quesId;
    }

    public void setQuesId(Long quesId) {
        this.quesId = quesId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

//    public String getAnswer() {
//        return answer;
//    }
//    public void setAnswer(String answer) {
//        this.answer = answer;
//    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
