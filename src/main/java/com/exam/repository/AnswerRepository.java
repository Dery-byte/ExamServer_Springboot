package com.exam.repository;

import com.exam.model.exam.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByUser_IdAndQuiz_qId(Long userId, Long quizId);

}
