package com.exam.controller;

import com.exam.model.exam.Report;
import com.exam.repository.ReportRepository;
import com.exam.service.QuizService;
import com.exam.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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












}
