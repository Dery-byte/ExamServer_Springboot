package com.exam.repository;


import com.exam.model.exam.Quiz;
import com.exam.model.User;
import com.exam.model.exam.TheoryProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheoryProgressRepository extends JpaRepository<TheoryProgress, Long> {

    List<TheoryProgress> findByUserAndQuiz(User user, Quiz quiz);

    Optional<TheoryProgress> findByUserAndQuizAndQuesNo(User user, Quiz quiz, String quesNo);

    void deleteByUserAndQuiz(User user, Quiz quiz);
}