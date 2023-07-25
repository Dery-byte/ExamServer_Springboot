package com.exam.controller;

import com.exam.auth.AuthenticationRequest;
import com.exam.auth.AuthenticationResponse;
import com.exam.auth.RegisterRequest;
import com.exam.helper.UserFoundException;
import com.exam.helper.UserNotFoundException;
import com.exam.model.User;
import com.exam.model.exam.Category;
import com.exam.model.exam.Questions;
import com.exam.model.exam.Quiz;
import com.exam.service.AuthenticationService;
import com.exam.service.CategoryService;
import com.exam.service.QuestionsService;
import com.exam.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @Autowired
    private final UserDetailsService userDetailsService;
//    @Autowired
//    private CategoryService categoryService;
//    @Autowired
//    private QuizService quizService;

    @Autowired
    private QuestionsService questionsService;



//    @PostMapping("/add")
//    public ResponseEntity<Category> addCategory(@RequestBody Category category){
//        Category category1 = this.categoryService.addCategory(category);
//        return ResponseEntity.ok(category1);
//    }
//    @GetMapping("/getCategories")
//    public ResponseEntity<?> getCategories(){
//        return ResponseEntity.ok(this.categoryService.getCategories());
//    }
//
//    //getCategory
//    @GetMapping("/{categoryId}")
//    public Category getCategory(@PathVariable("categoryId") Long categoryId){
//        return this.categoryService.getCategory(categoryId);
//    }


//
//    @GetMapping("/getQuizzes")
//    public ResponseEntity<?> quizzes(){
//        return ResponseEntity.ok(this.quizService.getQuizzes());
//    }
//
//    @PostMapping("/addQuiz")
//    public ResponseEntity<Quiz> add(@RequestBody Quiz quiz ){
//        return ResponseEntity.ok(this.quizService.addQuiz(quiz));
//    }
//    @DeleteMapping("/quiz/{qid}")
//    public  void delete(@PathVariable("qid") Long qid){
//        this.quizService.deleteQuiz(qid);
//    }
    //update quiz
//    @PutMapping("/update")
//    public  ResponseEntity<Quiz> update(@RequestBody Quiz quiz){
//        return ResponseEntity.ok(this.quizService.updateQuiz(quiz));
//    }

//    //get Single quiz
//    @GetMapping("/{qid}")
//    public Quiz quiz (@PathVariable("qid") Long qid){
//        return this.quizService.getQuiz(qid);
//    }

    // get questions of any quiz
//    @GetMapping("question/quiz/{qid}")
//    public  ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid){
////
////        Quiz quiz = new Quiz();
////        quiz.setqId(qid);
////        Set<Questions> questionsOfQuiz =this.questionsService.getQuestionsOfQuiz(quiz);
////        return ResponseEntity.ok(questionsOfQuiz);
//
//        Quiz quiz = this.quizService.getQuiz(qid);
//        Set<Questions> questions = quiz.getQuestions();
//        List<Questions> list = new ArrayList<>(questions);
//
//        if(list.size()>Integer.parseInt(quiz.getNumberOfQuestions())){
//            list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions()+1));
//        }
//
//
//        list.forEach((q)->{
//            q.setAnswer("");
//        });
//        Collections.shuffle(list);
//
//        return ResponseEntity.ok(list);
//    }



//    // get questions of any quiz
//    @GetMapping("question/quiz/all/{qid}")
//    public  ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid){
////
//        Quiz quiz = new Quiz();
//        quiz.setqId(qid);
//        Set<Questions> questionsOfQuiz =this.questionsService.getQuestionsOfQuiz(quiz);
//        return ResponseEntity.ok(questionsOfQuiz);
////        return ResponseEntity.ok(list);
//    }

    //add question
//    @PostMapping("question/add")
//    public ResponseEntity<Questions> add(@RequestBody Questions questions){
//        return ResponseEntity.ok(this.questionsService.addQuestions(questions));
//    }

//Delete Question
//    @DeleteMapping("/question/{quesId}")
//    public void deleteQuestion(@PathVariable("quesId") Long quesId){
//        this.questionsService.deleteQuestion(quesId);
//    }


    //get specific question
//    @GetMapping("/quiz/category/{cid}")
//    public List<Quiz> getQuizzesOfCategory(@PathVariable("cid")  Long cid){
//        Category category = new Category();
//        category.setCid(cid);
//        return (List<Quiz>) this.quizService.getQuizzesOfCategory(category);
//    }
//    //get Active quizzes
//    @GetMapping("/active/quizzes")
//    public List<Quiz> activeQuizzes(){
//        return this.quizService.getActiveQuizzes();
//    }
//    /// Active quizzes of category
//    @GetMapping("/category/active/{cid}")
//    public List<Quiz> activeQuizzesOfCategory(@PathVariable("cid") Long cid){
//        Category category =new Category();
//        category.setCid(cid);
//        return this.quizService.getQuizzesOfCategory(category);
//    }

//    evaluate Quiz
//    @PostMapping("question/eval-quiz")
//    public  ResponseEntity<?> evalQuiz(@RequestBody List<Questions> questions) {
//        System.out.println(questions);
//        double marksGot = 0;
//        int correctAnswers = 0;
//        int attempted = 0;
//
//        for (Questions q : questions) {
////            System.out.println(questions1.getGivenAnswer());
//
//            //Single question
//            Questions question = this.questionsService.get(q.getQuesId());
//
//            if (question.getAnswer().equals(q.getGivenAnswer())) {
//                //correct
//                correctAnswers++;
//                double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();
//                //this.questions[0].quiz.maxMarks/this.questions.length;
//                marksGot += marksSingle;
//
//            }
//            if (q.getGivenAnswer() != "") {
//                attempted++;
//
//            }
//            }
//            ;
//        Map<String, Object> map =Map.of("marksGot",marksGot,"correctAnswers",correctAnswers, "attempted",attempted);
//            return ResponseEntity.ok(map);
//
//        }




































































    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws UserFoundException {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) throws UserNotFoundException {
        return ResponseEntity.ok(service.authenticate(request));
    }

    //get the current user details
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){ 
        return (User) this.userDetailsService.loadUserByUsername(principal.getName());

    }

}
