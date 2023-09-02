package com.exam.controller;

import com.exam.model.exam.Report;
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
    @GetMapping("/getReport")
    public List<Report> getSpecificQuestionsOfQuizAdmin(){
       return this.reportService.getUserIdAndQuizId();
    }

    @GetMapping("/getReport/{rid}")
    public Report getUserQuizIds(@PathVariable("rid") Long rid){
        return this.reportService.userQuizIDs(rid);
    }
}
