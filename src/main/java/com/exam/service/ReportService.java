package com.exam.service;

import com.exam.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
//private QuestionsService questionsService;
//    @Autowired
//    private QuestionsController questionsController;
//    @Autowired
//    private QuestionsService questionsService;
//public Report AddReport(Report report){
//        return reportRepository.save(report);
//    }
}
