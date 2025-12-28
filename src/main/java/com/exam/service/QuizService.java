package com.exam.service;

import com.exam.model.User;
import com.exam.model.exam.*;
import com.exam.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuizService {


    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private TheoryQuestionsRepository theoryQuestionsRepository;

    @Autowired
    private AnswerRepository answerRepository;


    @Autowired
    private NumberOfTheoryToAnswerRepository numberOfTheoryToAnswerRepository;
    @Autowired
    private UserRepository userRepository;

    public Quiz addQuiz(Quiz quiz){
//        quiz.setStartTimeFromAMPM(quiz.getStartTime());
        return this.quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Quiz quiz){
        return this.quizRepository.save(quiz);
    }

    public Set<Quiz> getQuizzes(){
        return new HashSet<>(this.quizRepository.findAll());
    }

    public Quiz getQuiz(Long qid){
        return this.quizRepository.findById(qid).get();

    }
//    public String getQuizPass(Long qid){
//        return this.quizRepository.getQuizPassword(qid);
//
//    }

//
//    public void deleteQuiz(Long quizId){
//        Quiz quiz = quizRepository.findById(quizId).get();
//        this.quizRepository.delete(quiz);
//
//    }



    @Transactional
    public void deleteQuiz(Long quizId) {

        // 1. Delete all answers for theory questions of this quiz
        List<TheoryQuestions> theoryQuestions = theoryQuestionsRepository.findByQuiz_qId(quizId);
        for (TheoryQuestions tq : theoryQuestions) {
            answerRepository.deleteByTheoryQuestionId(tq.getTqId());
        }
        // Delete children first
        theoryQuestionsRepository.deleteByQuiz_qId(quizId);
        numberOfTheoryToAnswerRepository.deleteByQuiz_Id(quizId);
        // Delete quiz if present
        quizRepository.findById(quizId).ifPresent(quizRepository::delete);
    }



    public List<Quiz> getQuizzesOfCategory(Category category) {
        return this.quizRepository.findBycategory(category);
    }

    //get Active Quizzes
    public List<Quiz> getActiveQuizzes(){
        return this.quizRepository.findByActive(true);
    }

    //Get Acvtive And Categories
    public List<Quiz> getActiveQuizzesofCategory(Category c){
        return this.quizRepository.findByCategoryAndActive(c, true);
    }








    public List<Quiz> getTakenQuizzesOfCategory(Category category) {
        List<Report> reports = reportRepository.findByQuiz_Category(category);
        // extract unique quizzes
        return reports.stream()
                .map(Report::getQuiz)
                .distinct()
                .toList();
    }





//    public List<Quiz> getTakenQuizzesOfCategoryByUser(Long userId, Category category) {
//        List<Report> reports =
//                reportRepository.findByUser_IdAndQuiz_Category(userId, category);
//        return reports.stream()
//                .map(Report::getQuiz)
//                .distinct()
//                .toList();
//    }





    public List<Quiz> getTakenQuizzesOfCategoryByUser(Long userId, Category category) {
        List<Report> reports =
                reportRepository.findByUser_IdAndQuiz_Category(userId, category);
        return reports.stream()
                .map(Report::getQuiz)
                .distinct()
                .toList();
    }





    // ADD QUIZ AND USER
    @Transactional
    public Quiz addQuizForLoggedInUser(Quiz quiz, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        quiz.setUser(user); // link quiz to user
        return quizRepository.save(quiz);
    }

// fetch quiz based on the users
    @Transactional(readOnly = true)
    public List<Quiz> getQuizzesForLoggedInUser(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return quizRepository.findByUser_Id(user.getId());
    }


}
