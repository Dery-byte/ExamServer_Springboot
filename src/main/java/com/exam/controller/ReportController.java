package com.exam.controller;

import com.exam.model.exam.Report;
import com.exam.repository.ReportRepository;
import com.exam.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
public class ReportController {

@Autowired
private ReportService reportService;
@Autowired
    ReportRepository reportRepository;


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


//    @GetMapping("/getReport/{qid}")
//    public Report getByQuizId(@PathVariable("qid") Long qid){
//        return this.reportService.userQuizIDs(qid);
//    }

    //get Report by userId and QuizId
//    @GetMapping("/reports/{userId}/{quizId}")
//    public List<Report> getReport(@PathVariable("userId") Long userId, @PathVariable("quizId") Long quizId){
//        User user = new User();
//        user.setId(Math.toIntExact(userId));
//        Quiz quiz = new Quiz();
//        quiz.setqId(quizId);
//        return  this.reportService.getReports(quiz, user);
//    }

// By QUIZ ID
//@GetMapping("/byQuiz/{quizId}")
//public ResponseEntity<List<Report>> getReportsByQuizId(@PathVariable Long quizId) {
//    List<Report> reports = reportService.getReportsByQuizId(quizId);
//    return ResponseEntity.ok(reports);
//}



//    @GetMapping("/marks-progress/{qId}")
//    public List<Object[]> getMarksAndProgressByQId(@PathVariable Long qId) {
//        return reportRepository.findMarksAndProgressByQId(qId);
//    }




}
