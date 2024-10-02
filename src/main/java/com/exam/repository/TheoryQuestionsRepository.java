package com.exam.repository;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.TheoryQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Repository
public interface TheoryQuestionsRepository extends JpaRepository<TheoryQuestions, Long> {
    Set<TheoryQuestions> findByQuiz(Quiz quiz);


    @Modifying
    @Transactional
    @Query("DELETE FROM TheoryQuestions t WHERE t.quiz.qId = :quizId")
    void deleteByQuizId(@Param("quizId") Long quizId);

}
