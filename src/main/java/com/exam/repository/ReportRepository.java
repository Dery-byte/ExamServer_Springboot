package com.exam.repository;

import com.exam.model.User;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findById(Long id);
    List<Report> findByUserAndQuiz(Optional<User> user, Optional<Quiz> quiz);
    List<Report> findByQuiz(Long quiz);

    List<Report> findByUser(User user);

List<Report> findByUserAndQuiz(User user, Quiz quiz);

    Report findByUser_idAndQuiz_qId(Integer id, Long quizId);
}




