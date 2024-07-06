package com.exam.controller;

import com.exam.model.exam.Questions;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.TheoryQuestions;
import com.exam.service.TheoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@RestController
//@RequestMapping("/questions")
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class TheoryController {

    @Autowired
    private TheoryService theoryService;


    @PostMapping("theoryquestion/add")
    public ResponseEntity<TheoryQuestions> add(@RequestBody TheoryQuestions theoryQuestions){
        return ResponseEntity.ok(this.theoryService.addQuestions(theoryQuestions));
    }

    @GetMapping("theoryquestion/{quesId}")
    public  TheoryQuestions getSpecificQuestionsOfQuizAdmin(@PathVariable("quesId") Long quesId ){
        return this.theoryService.getQuestions(quesId);
//        return ResponseEntity.ok(list);
    }

    //Delete Question
    @DeleteMapping("/theoryquestion/{quesId}")
    public void deleteQuestion(@PathVariable("quesId") Long quesId){
        this.theoryService.deleteQuestion(quesId);
    }

    //update Questions
    @PutMapping("/theoryquestion/updateQuestions")
    public TheoryQuestions updateQuestion(@RequestBody TheoryQuestions theoryQuestions){
        return  this.theoryService.updateQuestions(theoryQuestions);
    }




    @GetMapping("theoryquestion/quiz/all/{qid}")
    public  ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid){
        Quiz quiz = new Quiz();
        quiz.setqId(qid);
        Set<TheoryQuestions> questionsOfQuiz =this.theoryService.getQuestionsForSpecificQuiz(quiz);
        List<TheoryQuestions> list = new ArrayList<>(questionsOfQuiz);
//        Collections.shuffle(list);
//        return ResponseEntity.ok(questionsOfQuiz);
        return ResponseEntity.ok(list);
    }




}
