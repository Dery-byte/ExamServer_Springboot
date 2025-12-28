package com.exam.service;


import com.exam.model.exam.Questions;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.TheoryQuestions;
import com.exam.repository.ReportRepository;
import com.exam.repository.TheoryQuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TheoryService {

    @Autowired
    private TheoryQuestionsRepository theoryQuestionsRepository;
    @Autowired
    @Lazy
    private ReportRepository reportRepository;


    public TheoryQuestions addQuestions(TheoryQuestions theoryQuestions){
        return this.theoryQuestionsRepository.save(theoryQuestions);
    }













    public TheoryQuestions updateQuestions(TheoryQuestions theoryQuestions){
        return this.theoryQuestionsRepository.save(theoryQuestions);
    }















    public Set<TheoryQuestions> getAllQuestions(){
        return new HashSet<>(this.theoryQuestionsRepository.findAll());
    }


    public TheoryQuestions getQuestions(Long quesId){
        return this.theoryQuestionsRepository.findById(quesId).get();
    }

    public Set<TheoryQuestions> getQuestionsForSpecificQuiz(Quiz quiz){
        return this.theoryQuestionsRepository.findByQuiz(quiz);
    }
    public void deleteQuestion(Long TqId){
        TheoryQuestions theoryQuestions = new TheoryQuestions();
        theoryQuestions.setTqId(TqId);
        this.theoryQuestionsRepository.delete(theoryQuestions);
    }

    //    uploading the questions
    public List<TheoryQuestions> saveAllQuestions(List<TheoryQuestions> theoryQuestions) {
        return theoryQuestionsRepository.saveAll(theoryQuestions);
    }

}
