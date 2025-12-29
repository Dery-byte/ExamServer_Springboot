package com.exam.controller;

import com.exam.DTO.ForgottenPasswordRequest;
import com.exam.DTO.ResetPasswordRequest;
import com.exam.DTO.TokenInfo;
import com.exam.auth.AuthenticationRequest;
import com.exam.auth.AuthenticationResponse;
import com.exam.auth.RegisterRequest;
import com.exam.helper.UserFoundException;
import com.exam.helper.UserNotFoundException;
import com.exam.model.User;
import com.exam.repository.UserRepository;
import com.exam.service.AuthenticationService;
import com.exam.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @Autowired
    private final UserDetailsService userDetailsService;


    @Autowired
    private JwtService jwtService;

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


// STUDENT
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws UserFoundException {
        return ResponseEntity.ok(service.register(request));
    }

//LECTURER
    @PostMapping("/register/lecturer")
    public ResponseEntity<AuthenticationResponse> registerLecturer(
            @RequestBody RegisterRequest request
    ) throws UserFoundException {
        return ResponseEntity.ok(service.registerAslecturer(request));
    }
    //ADMIN
    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody RegisterRequest request
    ) throws UserFoundException {
        return ResponseEntity.ok(service.registerAsAdmin(request));
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
//                        .token(authResponse.getToken())
                .build());
    }























    @PostMapping("/logoutssss")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        System.out.println("🔍 Logout endpoint HIT!");
        // Clear BOTH cookies (accessToken and token)
        // Clear accessToken cookie
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false); // Set to true in production with HTTPS
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);
        response.addCookie(accessTokenCookie);

        // Clear token cookie (this is the main one!)
//        Cookie tokenCookie = new Cookie("token", null);
//        tokenCookie.setHttpOnly(true);
//        tokenCookie.setSecure(false); // Set to true in production with HTTPS
//        tokenCookie.setPath("/");
//        tokenCookie.setMaxAge(0);
//        response.addCookie(tokenCookie);

        System.out.println("✅ User logged out - cookies cleared");

        Map<String, Object> responseBody = Map.of(
                "message", "Logged out successfully",
                "timestamp", System.currentTimeMillis()
        );

        System.out.println("📤 Sending response: " + responseBody);

        return ResponseEntity.ok().body(responseBody);
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
















    @GetMapping("/token-info")
    public TokenInfo getTokenInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        // Debug: Check if cookies exist
        if (cookies == null) {
            System.out.println("❌ No cookies found in request");
            return new TokenInfo(0);
        }

        // Debug: Log all cookies
        System.out.println("🍪 Cookies found: " + cookies.length);
        for (Cookie cookie : cookies) {
            System.out.println("  - " + cookie.getName() + " = " + cookie.getValue().substring(0, Math.min(20, cookie.getValue().length())) + "...");
        }

        String accessToken = null;
        for (Cookie cookie : cookies) {
            if ("accessToken".equals(cookie.getName())) {
                accessToken = cookie.getValue();
                System.out.println("✅ Token cookie found");
                break;
            }
        }

        if (accessToken == null) {
            System.out.println("❌ No 'token' cookie found");
            return new TokenInfo(0);
        }

        try {
            Claims claims = jwtService.extractAllClaims(accessToken);
            long exp = claims.getExpiration().getTime() / 1000;
            System.out.println("✅ Token expiration: " + exp + " (" + new Date(exp * 1000) + ")");
            return new TokenInfo(exp);
        } catch (Exception e) {
            System.out.println("❌ Error extracting claims: " + e.getMessage());
            e.printStackTrace();
            return new TokenInfo(0);
        }
    }
























    // Get all lecturers
    @GetMapping("/all/lecturers")
    public ResponseEntity<List<User>> getAllLecturers() {
        return ResponseEntity.ok(service.getAllLecturers());
    }

    // Get lecturer by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getLecturer(@PathVariable Long id) {
        return service.getLecturerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create or update lecturer
    @PostMapping
    public ResponseEntity<User> createLecturer(@RequestBody User lecturer) {
        User saved = service.saveOrUpdateLecturer(lecturer);
        return ResponseEntity.ok(saved);
    }

    // Update lecturer
    @PutMapping("/{id}")
    public ResponseEntity<User> updateLecturer(@PathVariable Long id, @RequestBody User lecturer) {
        return service.getLecturerById(id)
                .map(existing -> {
                    lecturer.setId(id);
                    User updated = service.saveOrUpdateLecturer(lecturer);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete lecturer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecturer(@PathVariable Long id) {
        service.deleteLecturer(id);
        return ResponseEntity.noContent().build();
    }







}


