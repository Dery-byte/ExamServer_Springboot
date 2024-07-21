package com.exam.controller;

import com.exam.model.exam.Questions;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.TheoryQuestions;
import com.exam.repository.QuizRepository;
import com.exam.service.TheoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
//@RequestMapping("/questions")
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class TheoryController {

    @Autowired
    private TheoryService theoryService;

    @Autowired
    QuizRepository quizRepository;


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




    @PostMapping("/theoryupload/{quizId}")
    public ResponseEntity<String> uploadQuestions(
            @PathVariable Long quizId,
            @RequestBody List<TheoryQuestions> theoryQuestions) {
        // Get the quiz entity from the database using the quizId
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            int numberOfQuestions = Integer.parseInt(quiz.getNumberOfQuestions());
            // Set the quiz for each question
            theoryQuestions.forEach(question -> question.setQuiz(quiz));
            if(theoryQuestions.size()<= numberOfQuestions)
            {
                // Save questions
                List<TheoryQuestions> savedQuestions = theoryService.saveAllQuestions(theoryQuestions);
                return new ResponseEntity<>("Uploaded " + savedQuestions.size() + " questions to the quiz with ID: " + quizId, HttpStatus.CREATED);
            }
            else{
                return ResponseEntity.status( HttpStatus.BAD_REQUEST).body("Number of questions should be " + numberOfQuestions+ " but you provided " + theoryQuestions.size());
            }
        } else {
            return new ResponseEntity<>("Quiz with ID " + quizId +  " not found.", HttpStatus.NOT_FOUND);
        }
    }

}
