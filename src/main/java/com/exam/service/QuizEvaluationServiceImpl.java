package com.exam.service;

import com.exam.DTO.QuizEvaluationResult;
import com.exam.model.User;
import com.exam.model.exam.Questions;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Report;
import com.exam.repository.ReportRepository;
import com.exam.service.Impl.QuizEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class QuizEvaluationServiceImpl implements QuizEvaluationService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionsService questionsService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;


    @Override
    public QuizEvaluationResult evaluateQuiz(List<Questions> questions, String username, Long quizId) {
        if (questions == null || questions.isEmpty()) {
            throw new IllegalArgumentException("No questions provided");
        }
        User user = (User) userDetailsService.loadUserByUsername(username);
        Quiz quiz = quizService.getQuiz(quizId);
        if (user == null || quiz == null) {
            throw new IllegalArgumentException("User or quiz not found");
        }
        double marksGot = 0.0;
        int correctAnswers = 0;
        int attempted = 0;
        double maxMarks = Double.parseDouble(
                questions.get(0).getQuiz().getMaxMarks()
        );
        for (Questions q : questions) {
            if (q == null) continue;
            Questions question = questionsService.get(q.getQuesId());
            if (question == null) continue;
            List<String> correctAnswersList =
                    q.getcorrect_answer() != null ? Arrays.asList(q.getcorrect_answer()) : null;
            List<String> givenAnswersList =
                    q.getGivenAnswer() != null ? Arrays.asList(q.getGivenAnswer()) : null;
            if (correctAnswersList != null && givenAnswersList != null) {
                Collections.sort(correctAnswersList);
                Collections.sort(givenAnswersList);
                if (correctAnswersList.equals(givenAnswersList)) {
                    correctAnswers++;
                    double marksSingle = maxMarks / questions.size();
                    marksGot += marksSingle;
                }
            }
            if (isAttempted(givenAnswersList)) {
                attempted++;
            }
        }
        // Save report
//        Report report = new Report();

        // CHECK IF REPORT EXISTS
        Report report = reportRepository.findByUserAndQuiz(user, quiz).orElse(new Report());
        report.setQuiz(quiz);
        report.setUser(user);
        report.setProgress("Completed");
        report.setMarks(BigDecimal.valueOf(marksGot));
//        report.setMarksB(BigDecimal.ZERO);
        reportService.addReport(report);



        return new QuizEvaluationResult(
                marksGot,
                correctAnswers,
                attempted,
                maxMarks
        );
    }








    private boolean isAttempted(List<String> answers) {
        if (answers == null) return false;

        return answers.stream()
                .anyMatch(a -> a != null && !a.trim().isEmpty());
    }





}
