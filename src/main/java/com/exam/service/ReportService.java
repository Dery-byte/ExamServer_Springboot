package com.exam.service;

import com.exam.model.exam.Report;
import com.exam.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;


    public  Report userQuizIDs(Long rid){
        return this.reportRepository.findById(rid).get();
    }
    public List<Report> getUserIdAndQuizId(){
        return this.reportRepository.findAll();
    }
//private QuestionsService questionsService;
//    @Autowired
//    private QuestionsController questionsController;
//    @Autowired
//    private QuestionsService questionsService;
//public Report AddReport(Report report){
//        return reportRepository.save(report);
//    }
}
