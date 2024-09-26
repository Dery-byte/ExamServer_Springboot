package com.exam.service;

import com.exam.model.User;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Report;
import com.exam.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    @Lazy
    private ReportRepository reportRepository;


    public  Report userQuizIDs(Long rid){
        return this.reportRepository.findById(rid).get();
    }
    public List<Report> getUserIdAndQuizId(){
        return this.reportRepository.findAll();
    }
    public Report addReport(Report report){
        return reportRepository.save(report);
    }

    public List<Report> getReportByUserAndType(Optional<User> user, Optional<Quiz> quiz){
        return reportRepository.findByUserAndQuiz(user,quiz);
    }


    //report By Quiz
    public List<Report> reportByQuiz_Id(Long quiz){
        return reportRepository.findByQuiz_qId(quiz);
    }
    ///Report By User
    public List<Report> reportByUser_id(User user){
        return reportRepository.findByUser(user);
    }


    public List<Report> findReportsByUserAndQuiz(User user, Quiz quiz) {
        return reportRepository.findByUserAndQuiz(user, quiz);
    }

    public Report findByUserAndQuiz(Integer id, Long quizId) {

        return reportRepository.findByUser_idAndQuiz_qId(id,quizId);
    }



}
