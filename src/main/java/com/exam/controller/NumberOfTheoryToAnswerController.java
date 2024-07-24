package com.exam.controller;

import com.exam.model.exam.NumberOfTheoryToAnswer;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Report;
import com.exam.model.exam.TheoryQuestions;
import com.exam.service.NumberOfTheoryToAnswerService;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/questions")
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class NumberOfTheoryToAnswerController {
    @Autowired
    private NumberOfTheoryToAnswerService numberOfTheoryToAnswerService;
    @Autowired
    private QuizService quizService;


    @PostMapping("numberOfTheoryQuestion/add")
    public ResponseEntity<NumberOfTheoryToAnswer> add(@RequestBody NumberOfTheoryToAnswer numberOfTheoryToAnswer) {
        return ResponseEntity.ok(this.numberOfTheoryToAnswerService.addQuestions(numberOfTheoryToAnswer));
    }


    @PutMapping("/numberOfTheoryQuestion/update")
    public NumberOfTheoryToAnswer updateQuestion(@RequestBody NumberOfTheoryToAnswer numberOfTheoryToAnswer) {
        return this.numberOfTheoryToAnswerService.updateNoTheoryAnswer(numberOfTheoryToAnswer);
    }


    @GetMapping("/numberOfTheoryQuestion/{quiz_Id}")
    public ResponseEntity<List<NumberOfTheoryToAnswer>> getQuizIds(@PathVariable("quiz_Id") Long quiz_Id) {
        Quiz quiz = quizService.getQuiz(quiz_Id);
        List<NumberOfTheoryToAnswer> numberToAnswer = numberOfTheoryToAnswerService.TheoryTOByQuiz_Id(quiz);
        return ResponseEntity.ok(numberToAnswer);
    }
}