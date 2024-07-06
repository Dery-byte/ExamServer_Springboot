package com.exam.repository;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.TheoryQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface TheoryQuestionsRepository extends JpaRepository<TheoryQuestions, Long> {
    Set<TheoryQuestions> findByQuiz(Quiz quiz);



}
