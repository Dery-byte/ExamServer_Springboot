package com.exam.repository;

import com.exam.model.exam.NumberOfTheoryToAnswer;
import com.exam.model.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface NumberOfTheoryToAnswerRepository extends JpaRepository<NumberOfTheoryToAnswer, Long> {
    List<NumberOfTheoryToAnswer> findByQuiz(Quiz quiz);
}
