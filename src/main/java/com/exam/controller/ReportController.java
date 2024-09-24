package com.exam.controller;

import com.exam.model.User;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Report;
import com.exam.repository.ReportRepository;
import com.exam.service.AuthenticationService;
import com.exam.service.QuizService;
import com.exam.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class ReportController {

@Autowired
@Lazy
private ReportService reportService;
    @Autowired
    @Lazy
    ReportRepository reportRepository;
    @Autowired
    private QuizService quizService;

    @Autowired
    private final UserDetailsService userDetailsService;


    @Autowired
    private AuthenticationService authenticationService;

    public ReportController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //get all reports
    @GetMapping("/getReport")
    public List<Report> getSpecificQuestionsOfQuizAdmin(){
       return this.reportService.getUserIdAndQuizId();
    }


//    get report by report id
    @GetMapping("/getReport/{rid}")
    public Report getUserQuizIds(@PathVariable("rid") Long rid){
        return this.reportService.userQuizIDs(rid);
    }


    //ADD THE SECTION MARKS METHODS
    @PutMapping("addtheoryMark")
    public ResponseEntity<?> addmarks(@RequestBody Report report, Principal principal) {
        // Validate if the principal is present
        if (principal == null) {
            return ResponseEntity.badRequest().body("Principal is null");
        }
        // Fetch the user using the principal (currently logged-in user)
        User user = (User) this.userDetailsService.loadUserByUsername(principal.getName());
        // Get the quiz ID from the report
        Long quizId = report.getQuiz().getqId();

        System.out.println("Quiz ID: " + quizId);
        System.out.println("User ID: " + user.getId());
        // Validate if quizId is present
        if (quizId == null) {
            return ResponseEntity.badRequest().body("Quiz ID is missing");
        }
        // Find the existing report by user ID and quiz ID
        Report existingReport = this.reportService.findByUserAndQuiz(user.getId(), quizId);

        System.out.println("existing report: " + existingReport);
        // Validate if the report exists
        if (existingReport == null) {
            Report newReport = new Report();
            newReport.setUser(user);
            newReport.setQuiz(report.getQuiz());
            newReport.setMarksB(report.getMarksB());
            newReport.setProgress("Completed");
            newReport.setMarks(BigDecimal.valueOf(0));
            Report report1 = reportRepository.save(newReport);
            return ResponseEntity.ok(report1);

        }
        // Update the MarksB field with the value from the request body
        existingReport.setMarksB(report.getMarksB());
        // Save the updated report entity back to the database
        Report updatedReport = reportRepository.save(existingReport);
        // Return the updated report in the response
        return ResponseEntity.ok(updatedReport);
    }




    // ADD THE SECTION B MARKS ENDS HERE

//Report on quiz id
@GetMapping("/getReports/{quiz_Id}")
public ResponseEntity<List<Report>>  getQuizIds(@PathVariable("quiz_Id") Long quiz_Id){
        Quiz quiz = quizService.getQuiz(quiz_Id);
        List<Report> reports = reportService.reportByQuiz_Id(quiz.getqId());
    return ResponseEntity.ok(reports);
}

// Reports by user ID
    @GetMapping("/getReportsByUser/{user_Id}")
    public ResponseEntity<List<Report>>  getReportUser_Id(@PathVariable("user_Id") Integer user_Id){
        User user = authenticationService.getUserById(user_Id);
        List<Report> reports = reportService.reportByUser_id(user);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/getReportByUidAndQid/{user_Id}/{quiz_Id}")
    public ResponseEntity<?> getQuizResultsByUserAndType( @PathVariable("user_Id") Integer user_Id, @PathVariable("quiz_Id") Long quiz_Id){
        Optional<Quiz> quiz = Optional.ofNullable(quizService.getQuiz(quiz_Id));
        Optional<User> user = Optional.ofNullable(authenticationService.getUserById(user_Id));
        Optional<User> users = Optional.of(Optional.ofNullable(authenticationService.getUserById(user_Id)).orElseThrow());
        if(quiz.isPresent() && user.isPresent()){
            List<Report> quizResults = reportService.getReportByUserAndType(user,quiz);
            return ResponseEntity.ok(quizResults);
        }
        else{
//            Make sure to handle the error here
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Quiz not Found");
        }
}






//GET THE QUIZ BY QUID_ID AND USER_ID
//    @GetMapping("/getReportByUidAndQid/{quiz_Id}/{user_Id}")
//    public ResponseEntity<?> getQuizResultsByQuizIdAndUserId( @PathVariable("quiz_Id") Long quiz_Id , @PathVariable("user_Id") Integer user_Id ){
//        Optional<Quiz> quiz = Optional.ofNullable(quizService.getQuiz(quiz_Id));
//        Optional<User> user = Optional.ofNullable(authenticationService.getUserById(user_Id));
//        Optional<User> users = Optional.of(Optional.ofNullable(authenticationService.getUserById(user_Id)).orElseThrow());
//        if(quiz.isPresent() && user.isPresent()){
//            List<Report> quizResults = reportService.getReportByUserAndType(user,quiz);
//            return ResponseEntity.ok(quizResults);
//        }
//        else{
////            Make sure to handle the error here
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Quiz not Found");
//        }
//    }


}



