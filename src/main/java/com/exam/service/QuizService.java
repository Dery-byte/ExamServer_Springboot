package com.exam.service;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuizService {


    @Autowired
    private QuizRepository quizRepository;

    public Quiz addQuiz(Quiz quiz){
        return this.quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Quiz quiz){
        return this.quizRepository.save(quiz);
    }

    public Set<Quiz> getQuizzes(){
        return new HashSet<>(this.quizRepository.findAll());
    }

    public Quiz getQuiz(Long qid){
        return this.quizRepository.findById(qid).get();

    }

//    public String getQuizPass(Long qid){
//        return this.quizRepository.getQuizPassword(qid);
//
//    }


    public void deleteQuiz(Long quizId){
//        Quiz quiz = new Quiz();
//        quiz.setqId(quizId);
        this.quizRepository.deleteById(quizId);

    }


    public List<Quiz> getQuizzesOfCategory(Category category) {
        return this.quizRepository.findBycategory(category);
    }

    //get Active Quizzes
    public List<Quiz> getActiveQuizzes(){
        return this.quizRepository.findByActive(true);
    }

    //Get Acvtive And Categories
    public List<Quiz> getActiveQuizzesofCategory(Category c){
        return this.quizRepository.findByCategoryAndActive(c, true);
    }

}
