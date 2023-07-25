package com.exam.repository;

import com.exam.model.exam.Questions;
import com.exam.model.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuestionsRepository extends JpaRepository <Questions, Long> {
    Set<Questions> findByQuiz(Quiz quiz);
}
