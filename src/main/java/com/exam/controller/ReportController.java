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
import org.springframework.web.bind.annotation.*;

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
    private AuthenticationService authenticationService;

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

//Report on quiz id
@GetMapping("/getReports/{quiz_Id}")
public ResponseEntity<List<Report>>  getQuizIds(@PathVariable("quiz_Id") Long quiz_Id){
        Quiz quiz = quizService.getQuiz(quiz_Id);
        List<Report> reports = reportService.reportByQuiz_Id(quiz);
    return ResponseEntity.ok(reports);
}

// Reports by user ID
    @GetMapping("/getReportsByUser/{user_Id}")
    public ResponseEntity<List<Report>>  getReportUser_Id(@PathVariable("user_Id") Integer user_Id){
        User user = authenticationService.getUserById(user_Id);
        List<Report> reports = reportService.reportByUser_id(user);
        return ResponseEntity.ok(reports);
    }




    @GetMapping("/user/{user_Id}/{quiz_Id}")
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




}
