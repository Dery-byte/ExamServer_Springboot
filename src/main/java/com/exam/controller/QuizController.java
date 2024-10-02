package com.exam.controller;


import com.exam.model.exam.Category;
import com.exam.model.exam.Questions;
import com.exam.model.exam.Quiz;
import com.exam.repository.QuizRepository;
import com.exam.repository.ReportRepository;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
//@RequestMapping("/quiz")
@RequestMapping("/api/v1/auth")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    @Lazy
    ReportRepository reportRepository;
    @GetMapping("/getQuizzes")
    public ResponseEntity<?> quizzes(){
        return ResponseEntity.ok(this.quizService.getQuizzes());
    }

    @PostMapping("/addQuiz")
    public ResponseEntity<Quiz> add(@RequestBody Quiz quiz ){
        return ResponseEntity.ok(this.quizService.addQuiz(quiz));



    }
    @DeleteMapping("delete/quiz/{qid}")
    public  void delete(@PathVariable("qid") Long qid){
        this.quizService.deleteQuiz(qid);
    }




    //update quiz
    @PutMapping("/update")
    public  ResponseEntity<Quiz> update(@RequestBody Quiz quiz){
        return ResponseEntity.ok(this.quizService.updateQuiz(quiz));
    }

    //get Single quiz
    @GetMapping("singleQuiz/{qid}")
    public Quiz quiz (@PathVariable("qid") Long qid){
        return this.quizService.getQuiz(qid);
    }

    // get questions of any quiz
    @GetMapping("question/quiz/{qid}")
    public  ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid){
        Quiz quiz = this.quizService.getQuiz(qid);
        Set<Questions> questions = quiz.getQuestions();
        List<Questions> list = new ArrayList<>(questions);

//        Quiz quiz = new Quiz();
//        quiz.setqId(qid);
//        Set<Questions> questionsOfQuiz =this.questionsService.getQuestionsOfQuiz(quiz);


        if(list.size()>Integer.parseInt(quiz.getNumberOfQuestions())){
            list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions()+1));
        }
        list.forEach((q)->{
//            q.setAnswer(new String[0]);
            q.setcorrect_answer(new String[0]);
//            q.setAnswer("");
        });
        Collections.shuffle(list);

        return ResponseEntity.ok(list);
    }






    //get specific question
    @GetMapping("/quiz/category/{cid}")
    public List<Quiz> getQuizzesOfCategory(@PathVariable("cid")  Long cid){
        Category category = new Category();
        category.setCid(cid);
        return (List<Quiz>) this.quizService.getQuizzesOfCategory(category);
    }


    //get Active quizzes
    @GetMapping("/active/quizzes")
    public List<Quiz> activeQuizzes(){
        return this.quizService.getActiveQuizzes();
    }



    /// Active quizzes of category

    @GetMapping("/category/active/{cid}")
    public List<Quiz> activeQuizzesOfCategory(@PathVariable("cid") Long cid){
        Category category =new Category();
        category.setCid(cid);
        return this.quizService.getActiveQuizzesofCategory(category);
    }
//    @GetMapping("/category/active/{cid}")
//    public List<Quiz> activeQuizzesOfCategory(@PathVariable("cid") Long cid){
//        Category category =new Category();
//        category.setCid(cid);
//        return this.quizService.getQuizzesOfCategory(category);
//    }












    ///UNCOMMENT

//    //add quiz service
////    @PostMapping("/addQuiz")
////    public ResponseEntity<Quiz> add(@RequestBody Quiz quiz ){
////        return ResponseEntity.ok(this.quizService.addQuiz(quiz));
////    }
//
//    //update quiz
//    @PutMapping("/update")
//    public  ResponseEntity<Quiz> update(@RequestBody Quiz quiz){
//        return ResponseEntity.ok(this.quizService.updateQuiz(quiz));
//    }
//
//    //get quiz
//    @GetMapping("/")
//    public ResponseEntity<?> quizzes(){
//        return ResponseEntity.ok(this.quizService.getQuizzes());
//    }
//
//    //get Single quiz
//    @GetMapping("/{qid}")
//    public Quiz quiz (@PathVariable("qid") Long qid){
//        return this.quizService.getQuiz(qid);
//    }
//
//
//    //Delete quiz
//    @DeleteMapping("/{qid}")
//    public  void delete(@PathVariable("qid") Long qid){
//        this.quizService.deleteQuiz(qid);
//    }
//
//
//
//    //get specific question
//    @GetMapping("/category/{cid}")
//    public List<Quiz> getQuizzesOfCategory(@PathVariable("cid")  Long cid){
//        Category category = new Category();
//        category.setCid(cid);
//        return (List<Quiz>) this.quizService.getQuizzesOfCategory(category);
//    }
//
//    //get Active quizzes
//    @GetMapping("/active/quizzes")
//        public List<Quiz> activeQuizzes(){
//            return this.quizService.getActiveQuizzes();
//        }
//     /// Active quizzes of category
//     @GetMapping("/category/active/{cid}")
//     public List<Quiz> activeQuizzesOfCategory(@PathVariable("cid") Long cid){
//        Category category =new Category();
//        category.setCid(cid);
//         return this.quizService.getQuizzesOfCategory(category);
//     }


}
