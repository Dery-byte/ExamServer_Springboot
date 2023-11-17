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
    public List<Report> reportByQuiz_Id(Quiz quiz){
        return reportRepository.findByQuiz(quiz);
    }
    ///Report By User
    public List<Report> reportByUser_id(User user){
        return reportRepository.findByUser(user);
    }




//    get report by user_Id and Quiz_id
//    public List<Report> getSpecificUserReport(Quiz quiz, User user){
//        return reportRepository.findByUserIdAndQuizId(Math.toIntExact(quiz.getqId()), Long.valueOf(user.getId()));
//    }



    //custome formular to get report by quiz id
//    public List<Report> getReportsByQuizId(Long quizId) {
//        return reportRepository.findByQuizId(quizId);
//    }



    //get Report by userId and QuizId
//    public List<Report> getReports(Quiz quiz, User user){
//      return reportRepository.findByquiz(quiz, user);
//
//
//    }
}
