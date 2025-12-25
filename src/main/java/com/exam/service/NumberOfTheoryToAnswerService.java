package com.exam.service;
import com.exam.model.User;
import com.exam.model.exam.NumberOfTheoryToAnswer;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Report;
import com.exam.repository.NumberOfTheoryToAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumberOfTheoryToAnswerService {
    @Autowired
    private NumberOfTheoryToAnswerRepository numberOfTheoryToAnswerRepository;

    public NumberOfTheoryToAnswer addQuestions(NumberOfTheoryToAnswer numberOfTheoryToAnswer){
        return this.numberOfTheoryToAnswerRepository.save(numberOfTheoryToAnswer);
    }


    public NumberOfTheoryToAnswer updateNoTheoryAnswer(NumberOfTheoryToAnswer numberOfTheoryToAnswer){
        return this.numberOfTheoryToAnswerRepository.save(numberOfTheoryToAnswer);
    }


    public List<NumberOfTheoryToAnswer> TheoryTOByQuiz_Id(Quiz quiz){
        return numberOfTheoryToAnswerRepository.findByQuiz(quiz);
    }

    public List<NumberOfTheoryToAnswer> findByQuizId(Long quizId) {
        return numberOfTheoryToAnswerRepository.findByQuiz_qId(quizId);
    }
}
