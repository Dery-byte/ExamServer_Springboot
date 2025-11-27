package com.exam.repository;

import com.exam.model.User;
import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

//    Optional<Report> findById(Long id);

    List<Report>findByQuiz_qId(Long id);




    List<Report> findByUserAndQuiz(Optional<User> user, Optional<Quiz> quiz);
    List<Report> findByQuiz(Long quiz);

    List<Report> findByUser(User user);

List<Report> findByUserAndQuiz(User user, Quiz quiz);

    Report findByUser_idAndQuiz_qId(Integer id, Long quizId);


    @Modifying
    @Transactional
    @Query("DELETE FROM report r WHERE r.quiz.qId = :quizId")
    void deleteByQuizId(@Param("quizId") Long quizId);




    // fetch reports only for quizzes in a category
    List<Report> findByQuiz_Category(Category category);

    // Fetch reports for Quizzes in a category by a specific user.
    List<Report> findByUser_IdAndQuiz_Category(Long userId, Category category);


}




