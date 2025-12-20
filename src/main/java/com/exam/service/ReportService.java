package com.exam.service;

import com.exam.model.User;
import com.exam.model.exam.Answer;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Report;
import com.exam.repository.AnswerRepository;
import com.exam.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @Autowired
    @Lazy
    private ReportRepository reportRepository;


    @Autowired
    private AnswerRepository answerRepository;


    public Map<String, Object> getQuizReportsWithAnswers(Long quizId) {
        // Get all reports for this quiz
        List<Report> reports = reportRepository.findByQuiz_qId(quizId);

        // Get all answers for this quiz
        List<Answer> answers = answerRepository.findByQuiz_qId(quizId);

        // Group answers by report
        Map<Long, List<Answer>> answersByReport = answers.stream()
                .filter(a -> a.getReport() != null)
                .collect(Collectors.groupingBy(a -> a.getReport().getId()));

        // Build response
        List<Map<String, Object>> reportData = reports.stream()
                .map(report -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("reportId", report.getId());
                    data.put("userId", report.getUser().getId());
                    data.put("username", report.getUser().getUsername());
                    data.put("totalScore", report.getMarks());
                    data.put("totalMaxMarks", report.getMarksB());
                    data.put("percentage", report.getPercentage());
                    data.put("grade", report.getGrade());
                    data.put("submissionDate", report.getSubmissionDate());

                    // Add answers for this report
                    List<Answer> reportAnswers = answersByReport.getOrDefault(report.getId(), new ArrayList<>());
                    data.put("answers", reportAnswers.stream()
                            .map(this::createAnswerDTO)
                            .collect(Collectors.toList()));
                    data.put("answerCount", reportAnswers.size());

                    return data;
                })
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("quizId", quizId);
        result.put("totalReports", reports.size());
        result.put("totalAnswers", answers.size());
        result.put("reports", reportData);

        return result;
    }

    /**
     * Get specific report with all its answers
     */
    public Map<String, Object> getReportWithAnswers(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found: " + reportId));

        List<Answer> answers = answerRepository.findByReport_Id(reportId);

        Map<String, Object> result = new HashMap<>();
        result.put("reportId", report.getId());
        result.put("userId", report.getUser().getId());
        result.put("username", report.getUser().getUsername());
        result.put("quizId", report.getQuiz().getqId());
        result.put("quizTitle", report.getQuiz().getTitle());
        result.put("totalScore", report.getMarks());
        result.put("totalMaxMarks", report.getMarksB());
        result.put("percentage", report.getPercentage());
        result.put("grade", report.getGrade());
        result.put("submissionDate", report.getSubmissionDate());
        result.put("evaluationMethod", report.getEvaluationMethod());
        result.put("answers", answers.stream()
                .map(this::createAnswerDTO)
                .collect(Collectors.toList()));
        result.put("answerCount", answers.size());

        return result;
    }

    /**
     * Get all reports for a user with quiz ID filter
     */
    public List<Map<String, Object>> getUserReportsForQuiz(Long userId, Long quizId) {
        List<Report> reports = reportRepository.findByUser_IdAndQuiz_qId(userId, quizId);

        return reports.stream()
                .map(report -> {
                    List<Answer> answers = answerRepository.findByReport_Id(report.getId());

                    Map<String, Object> data = new HashMap<>();
                    data.put("reportId", report.getId());
                    data.put("totalScore", report.getMarks());
                    data.put("totalMaxMarks", report.getMarksB());
                    data.put("percentage", report.getPercentage());
                    data.put("grade", report.getGrade());
//                    data.put("QuesNo", report)
                    data.put("submissionDate", report.getSubmissionDate());
                    data.put("answers", answers.stream()
                            .map(this::createAnswerDTO)
                            .collect(Collectors.toList()));

                    return data;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get latest report for user and quiz with answers
     */
    public Map<String, Object> getLatestReportForQuiz(Long userId, Long quizId) {
        Optional<Report> reportOpt = reportRepository.findTopByUser_IdAndQuiz_qIdOrderBySubmissionDateDesc(userId, quizId);

        if (reportOpt.isEmpty()) {
            throw new RuntimeException("No report found for user " + userId + " and quiz " + quizId);
        }

        Report report = reportOpt.get();
        List<Answer> answers = answerRepository.findByReport_Id(report.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("reportId", report.getId());
        result.put("userId", userId);
        result.put("quizId", quizId);
        result.put("totalScore", report.getMarks());
        result.put("percentage", report.getPercentage());
        result.put("grade", report.getGrade());
        result.put("submissionDate", report.getSubmissionDate());
        result.put("isLatest", true);
        result.put("answers", answers.stream()
                .map(this::createAnswerDTO)
                .collect(Collectors.toList()));

        return result;
    }

    // Helper method
    private Map<String, Object> createAnswerDTO(Answer answer) {
        Map<String, Object> dto = new HashMap<>();
        dto.put("quesNo", answer.getQuesNo());
        dto.put("answerId", answer.getAnswerId());
        dto.put("questionId", answer.getTheoryQuestion().getTqId());
        dto.put("questionText", answer.getTheoryQuestion().getQuestion());
        dto.put("studentAnswer", answer.getStudentAnswer());
        dto.put("score", answer.getScore());
        dto.put("maxMarks", answer.getMaxMarks());
        dto.put("percentage", answer.getMaxMarks() > 0
                ? (answer.getScore() / answer.getMaxMarks()) * 100 : 0);
        dto.put("feedback", answer.getFeedback());
        dto.put("keyMissed", answer.getKeyMissed());
        return dto;
    }
































































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
        return reportRepository.findByUserAndQuiz(Optional.ofNullable(user), Optional.ofNullable(quiz));
    }

    public Report findByUserAndQuiz(Integer id, Long quizId) {
        return reportRepository.findByUser_idAndQuiz_qId(id,quizId);
    }



}
