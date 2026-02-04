package com.exam.controller;

import com.exam.DTO.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    @Autowired
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


//
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(
//            @RequestBody AuthenticationRequest request,
//            HttpServletResponse response  // Add this parameter
//    ) throws UserNotFoundException {
//        AuthenticationResponse authResponse = service.authenticate(request);
//
//        // Set token as HttpOnly cookie
//        Cookie cookie = new Cookie("accessToken", authResponse.getToken());
//        cookie.setHttpOnly(true);        // Prevents JavaScript access
//        cookie.setSecure(true);          // HTTPS only (set to false in development if using HTTP)
//        cookie.setPath("/");             // Available to all paths
//        cookie.setMaxAge(3600);          // 1 hour (adjust based on your token expiration)
//        cookie.setAttribute("SameSite", "None"); // CSRF protection
//        response.addCookie(cookie);
//
//        // Return response without token (or with user details only)
//        return ResponseEntity.ok(AuthenticationResponse.builder()
//                .message("Authentication successful")
////                        .token(authResponse.getToken())
//                .build());
//    }


// NEW



@PostMapping("/authenticate")
public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody AuthenticationRequest request,
        HttpServletRequest httpRequest
) throws UserNotFoundException {

    // ✅ Debug logging (optional - remove in production)
//    log.debug("=== Authentication Request ===");
//    log.debug("Origin: {}", httpRequest.getHeader("Origin"));
//    log.debug("Method: {}", httpRequest.getMethod());
//    log.debug("Path: {}", httpRequest.getRequestURI());
//    log.debug("Content-Type: {}", httpRequest.getHeader("Content-Type"));

    // Authenticate user and generate token
    AuthenticationResponse authResponse = service.authenticate(request);

//    log.info("✅ User authenticated successfully: {}", request.getEmail());

    // Return token in response body (NOT in cookie)
    return ResponseEntity.ok(AuthenticationResponse.builder()
            .token(authResponse.getToken())  // or .accessToken() depending on your DTO
//            .tokenType("Bearer")
            .message("Authentication successful")
//            .email(authResponse.getEmail())  // Optional: return user info
//            .role(authResponse.getRole())    // Optional: return user role
            .build());
}

//





// NEW

@PostMapping("/logout")
public ResponseEntity<?> logout(
        HttpServletRequest request,
        HttpServletResponse response
) {
    // Your existing logout logic...
    // ✅ Clear the cookie properly
    String cookieHeader = "accessToken=; Path=/; Max-Age=0; HttpOnly; Secure; SameSite=None";
    response.addHeader("Set-Cookie", cookieHeader);
    return ResponseEntity.ok(Map.of("message", "Logout successful"));
}


 @GetMapping("/current-user")
    public ResponseEntity<UserResponse> getCurrentUser(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        // Get authorities from Spring Security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Format as comma-separated string
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));
        UserResponse response = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getRole().name(),
                authorities,  // Now it's a clean string like "ROLE_ADMIN, PERMISSION_WRITE"
                user.isEnabled(),
                user.getPhone(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked()
        );
        return ResponseEntity.ok(response);
    }





//    @GetMapping("/current-user")
//    public ResponseEntity<UserResponse> getCurrentUser(Principal principal) {
//        if (principal == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
//        // Get authorities from Spring Security
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        List<Map<String, String>> authorities = auth.getAuthorities().stream()
//                .map(authority -> Map.of("authority", authority.getAuthority()))
//                .collect(Collectors.toList());
//        UserResponse response = new UserResponse(
//                user.getId(),
//                user.getUsername(),
//                user.getEmail(),
//                user.getFirstname(),
//                user.getLastname(),
//                user.getRole().name(),
//                authorities.toString()  // Add authorities
//        );
//        return ResponseEntity.ok(response);
//    }


//    @GetMapping("/current-user")
//    public UserDetails getCurrentUser(Principal principal){
//        return this.userDetailsService.loadUserByUsername(principal.getName());
//    }


    //NEW


//    @GetMapping("/current-user")
//    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
//        // ✅ Better: Use Authentication which provides more info
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(Map.of("error", "Not authenticated"));
//        }
//        // Authentication already contains UserDetails
//        Object principal = authentication.getPrincipal();
//
//        if (principal instanceof UserDetails) {
//            return ResponseEntity.ok(principal);
//        }
//        // Fallback: load from database
//        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
//        return ResponseEntity.ok(userDetails);
//    }





//    @GetMapping("/current-user")
//    public UserDetails getCurrentUser(
//            @AuthenticationPrincipal UserDetails userDetails) {
//        return userDetails;
//    }


//
//    @GetMapping("/current-user")
//    public UserDetails getCurrentUser() {
//        var auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
//            throw new RuntimeException("User not authenticated");
//        }
//        return (UserDetails) auth.getPrincipal();
//    }






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
        String accessToken = extractTokenFromHeader(request);

        if (accessToken == null) {
            System.out.println("❌ No valid Authorization header found");
            return new TokenInfo(0);
        }

        System.out.println("✅ JWT token extracted");

        try {
            Claims claims = jwtService.extractAllClaims(accessToken);
            long exp = claims.getExpiration().getTime() / 1000;
            System.out.println("✅ Token expiration: " + exp + " (" + new Date(exp * 1000) + ")");
            return new TokenInfo(exp);
        } catch (Exception e) {
            System.out.println("❌ Error extracting claims: " + e.getMessage());
            return new TokenInfo(0);
        }
    }


    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }


















    // Get all lecturers
    @GetMapping("/all/lecturers")
    public ResponseEntity<List<LecturerDTO>> getAllLecturers() {
        List<LecturerDTO> lecturers = service.getAllLecturers();
        return ResponseEntity.ok(lecturers);
    }


    // Get all lecturers
    @GetMapping("/all/students")
    public ResponseEntity<List<LecturerDTO>> getAllStudents() {
        List<LecturerDTO> lecturers = service.getAllStudents();
        return ResponseEntity.ok(lecturers);
    }



    // Get lecturer by ID
    @GetMapping("/lecturerbyId/{id}")
    public ResponseEntity<LecturerDTO> getLecturer(@PathVariable Long id) {
        return service.getLecturerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




    @GetMapping("/studentbyId/{id}")
    public ResponseEntity<LecturerDTO> getStudent(@PathVariable Long id) {
        return service.getStuentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }





    // Create lecturer
    @PostMapping
    public ResponseEntity<LecturerDTO> createLecturer(@RequestBody User lecturer) {
        LecturerDTO saved = service.saveOrUpdateLecturer(lecturer);
        return ResponseEntity.ok(saved);
    }

    // Update lecturer
//
    @PutMapping("/update/lecturer/{id}")
    public ResponseEntity<LecturerDTO> updateLecturer(
            @PathVariable Long id,
            @RequestBody LecturerUpdateDTO updateDTO) {
        return service.updateLecturer(id, updateDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @PutMapping("/update/student/{id}")
    public ResponseEntity<LecturerDTO> updateStudent(
            @PathVariable Long id,
            @RequestBody LecturerUpdateDTO updateDTO) {
        System.out.println("UPDATE STUDENT CALLED - ID: " + id);  // ← Add this
        return service.updateStudent(id, updateDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete lecturer
    @DeleteMapping("/lecturer/{id}")
    public ResponseEntity<Void> deleteLecturer(@PathVariable Long id) {
        service.deleteLecturer(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/student/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }









//CONTROLLER FOR GETTING LECTURER, ADMIN,STUDENT

    @GetMapping("/students/counts")
    public StudentResponse getStudents() {
        return service.getStudents();
    }

    @GetMapping("/lecturers/counts")
    public LecturerResponse getLecturers() {
        return service.getLecturers();
    }

    @GetMapping("/admins/counts")
    public AdminResponse getAdmins() {
        return service.getAdmins();
    }






        }


