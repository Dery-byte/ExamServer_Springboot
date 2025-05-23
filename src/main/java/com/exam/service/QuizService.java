package com.exam.service;

import com.exam.model.User;
import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.model.exam.Registered_courses;
import com.exam.model.exam.Report;
import com.exam.repository.NumberOfTheoryToAnswerRepository;
import com.exam.repository.QuizRepository;
import com.exam.repository.Registered_coursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuizService {


    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private NumberOfTheoryToAnswerRepository numberOfTheoryToAnswerRepository;
    @Autowired
    private Registered_coursesRepository registeredCoursesRepository;

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
        Quiz quiz = quizRepository.findById(quizId).get();
        this.quizRepository.delete(quiz);

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
