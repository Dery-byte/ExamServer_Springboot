package com.exam.controller;

import com.exam.model.exam.Questions;
import com.exam.model.exam.Quiz;
import com.exam.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
//@RequestMapping("/questions")
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class QuestionsController {
    @Autowired
    private QuestionsService questionsService;

    // get questions of any quiz
    @GetMapping("question/quiz/all/{qid}")
    public  ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid){
        Quiz quiz = new Quiz();
        quiz.setqId(qid);
        Set<Questions> questionsOfQuiz =this.questionsService.getQuestionsOfQuiz(quiz);
        return ResponseEntity.ok(questionsOfQuiz);
//        return ResponseEntity.ok(list);
    }
    //Get the specific questions from any quiz
    // get questions of any quiz


    //GET RANDOM QUESTIONS AND LIMITED
    @GetMapping("/random-records")
    public ResponseEntity<List<Questions>> getRandomRecords() {

        return this.questionsService.getRandomRecords();
    }

//GET LIMITED QUESTIONS
//@GetMapping("/limited-records")
//public Page<Questions> getLimitedRecords(
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "10") int size
//) {
//    return questionsService.getLimitedRecords(page, size);
//}



    @GetMapping("question/{quesId}")
    public  Questions getSpecificQuestionsOfQuizAdmin(@PathVariable("quesId") Long quesId ){
       return this.questionsService.getQuestions(quesId);
//        return ResponseEntity.ok(list);
    }

    //add question
    @PostMapping("question/add")
    public ResponseEntity<Questions> add(@RequestBody Questions questions){
        return ResponseEntity.ok(this.questionsService.addQuestions(questions));
    }

    //Delete Question
    @DeleteMapping("/question/{quesId}")
    public void deleteQuestion(@PathVariable("quesId") Long quesId){
        this.questionsService.deleteQuestion(quesId);
    }



    //update Questions
    @PutMapping("/question/updateQuestions")
    public Questions updateQuestion(@RequestBody Questions questions){
        return  this.questionsService.updateQuestions(questions);

    }

//    //evaluate Quiz
    @PostMapping("question/eval-quiz")
    public  ResponseEntity<?> evalQuiz(@RequestBody List<Questions> questions) {
        System.out.println(questions);
        double marksGot = 0;
        int correctAnswers = 0;
        int attempted = 0;
//        double maxMarks =0;
        double maxMarks = 0;
        for (Questions q : questions) {
//            System.out.println(questions1.getGivenAnswer());
            maxMarks = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks());

            //Single question
            Questions question = this.questionsService.get(q.getQuesId());

            if (question.getAnswer().equals(q.getGivenAnswer())) {
                //correct
                correctAnswers++;
                double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / questions.size();
                //this.questions[0].quiz.maxMarks/this.questions.length;
                marksGot += marksSingle;

            }
            if (q.getGivenAnswer() != "") {
                attempted++;

            }
        }
        ;
        Map<String, Object> map = Map.of("marksGot", marksGot, "correctAnswers", correctAnswers, "attempted", attempted, "maxMarks", maxMarks);
        return ResponseEntity.ok(map);

    }














































//
//    @Autowired
//    private QuizService quizService;
//
//    //add question
//    @PostMapping("/add")
//    public ResponseEntity<Questions> add(@RequestBody Questions questions){
//        return ResponseEntity.ok(this.questionsService.addQuestions(questions));
//    }
//
//    // update question
//    @PutMapping("/")
//    public ResponseEntity<Questions> update(@RequestBody Questions questions){
//        return ResponseEntity.ok(this.questionsService.updateQuestions(questions));
//    }
//
//    // get questions of any quiz
//    @GetMapping("/quiz/{qid}")
//    public  ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid){
////
////        Quiz quiz = new Quiz();
////        quiz.setqId(qid);
////        Set<Questions> questionsOfQuiz =this.questionsService.getQuestionsOfQuiz(quiz);
////        return ResponseEntity.ok(questionsOfQuiz);
//
//Quiz quiz = this.quizService.getQuiz(qid);
//Set<Questions> questions = quiz.getQuestions();
//List<Questions> list = new ArrayList<>(questions);
//
//if(list.size()>Integer.parseInt(quiz.getNumberOfQuestions())){
//    list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions()+1));
//}
//
//
//list.forEach((q)->{
//    q.setAnswer("");
//});
//        Collections.shuffle(list);
//return ResponseEntity.ok(list);
//    }
//
//    //get suingle question
//    @GetMapping("/{quesId}")
//    public Questions get(@PathVariable("quesId") Long quesId )
//    {
//        return this.questionsService.getQuestions(quesId);
//    }
//
//    @DeleteMapping("/{quesId}")
//    public void delete(@PathVariable("quesId") Long quesId){
//        this.questionsService.deleteQuestion(quesId);
//    }
//
//    //evaluate Quiz
//    @PostMapping("/eval-quiz")
//    public  ResponseEntity<?> evalQuiz(@RequestBody List<Questions> questions){
//        System.out.println(questions);
//        return ResponseEntity.ok("Got questions with answers!");
//
//    }


}
