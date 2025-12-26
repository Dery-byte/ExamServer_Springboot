package com.exam.controller;

import com.exam.DTO.ForgottenPasswordRequest;
import com.exam.DTO.ResetPasswordRequest;
import com.exam.auth.AuthenticationRequest;
import com.exam.auth.AuthenticationResponse;
import com.exam.auth.RegisterRequest;
import com.exam.helper.UserFoundException;
import com.exam.helper.UserNotFoundException;
import com.exam.model.User;
import com.exam.repository.UserRepository;
import com.exam.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
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
    private UserRepository userRepository;

//    @Autowired
//    private QuestionsService questionsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    OAUTH2 GOOGLE CONTROLLER

    @GetMapping("/")
    public String home(){
        return "index";
    }



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



//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(
//            @RequestBody AuthenticationRequest request
//    ) throws UserNotFoundException {
//        return ResponseEntity.ok(service.authenticate(request));
//    }




    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response  // Add this parameter
    ) throws UserNotFoundException {
        AuthenticationResponse authResponse = service.authenticate(request);

        // Set token as HttpOnly cookie
        Cookie cookie = new Cookie("accessToken", authResponse.getToken());
        cookie.setHttpOnly(true);        // Prevents JavaScript access
        cookie.setSecure(true);          // HTTPS only (set to false in development if using HTTP)
        cookie.setPath("/");             // Available to all paths
        cookie.setMaxAge(3600);          // 1 hour (adjust based on your token expiration)
        cookie.setAttribute("SameSite", "Strict"); // CSRF protection

        response.addCookie(cookie);

        // Return response without token (or with user details only)
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .message("Authentication successful")
                .build());
    }




    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Clear the cookie
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);  // Delete immediately
        response.addCookie(cookie);
        return ResponseEntity.ok().body("Logged out successfully");
    }




    //get the current user details
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){ 
        return (User) this.userDetailsService.loadUserByUsername(principal.getName());
    }

    //Change Password if logged In
    @PutMapping("/updatepassword")
    String changePassword(Principal principal, @RequestBody User users){

        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
//        user.setPassword(users.getPassword());
        user.setPassword(passwordEncoder.encode(users.getPassword()));
        userRepository.save(user);
return "Password changed " + user.getPassword();
    }

    //Change Password if not logged in
    @PutMapping("/changePassword")
    public String changePasswordNoLoggedIn(@RequestBody User users) {
        List<User> user = service.getAllUsers();
        for (User u : user) {
            if (u.getUsername().equals(users.getUsername())) {
                System.out.println("True");
                u.setPassword(passwordEncoder.encode(users.getPassword()));
                userRepository.save(u);
                return "Successful password reset " + " for " + users.getUsername();
            }
        }
        return "Username " + users.getUsername() + " not found ";
    }
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return  service.getAllUsers();
    }











    @PostMapping("/forgotten-password")
    public ResponseEntity<Void> forgottenPassword(
            @RequestBody ForgottenPasswordRequest request
    ) throws MessagingException, UnsupportedEncodingException {
        service.forgottenPassword(request);
        return ResponseEntity.accepted().build();
    }


    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        service.resetPassword(request);
        return ResponseEntity.ok().build();
    }
}


