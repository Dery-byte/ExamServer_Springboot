package com.exam.service;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService  {
@Autowired
private CategoryRepository categoryRepository;

@Autowired
    Registered_coursesRepository registeredCoursesRepository;
@Autowired
    NumberOfTheoryToAnswerRepository numberOfTheoryToAnswerRepository;
@Autowired
QuizRepository quizRepository;
@Autowired
    TheoryQuestionsRepository theoryQuestionsRepository;

@Autowired
ReportRepository reportRepository;




    public Category addCategory(Category category){
        return  this.categoryRepository.save(category);
    }

    public Category UpdateCategory(Category category){
        return  this.categoryRepository.save(category);
    }

    public Set<Category> getCategories(){
        return new LinkedHashSet<>(this.categoryRepository.findAll());
    }


    public Category getCategory(Long categoryId){
        return this.categoryRepository.findById(categoryId).get();
    }










    public void deleteCategory(Long categoryId){

        List<Quiz> quizzes = quizRepository.findByCategory_cid(categoryId);
        for (Quiz quiz : quizzes) {
            reportRepository.deleteByQuizId(quiz.getqId());
            theoryQuestionsRepository.deleteByQuizId(quiz.getqId());
            numberOfTheoryToAnswerRepository.deleteByQuiz_Id(quiz.getqId());
        }

        this.quizRepository.deleteByCategory_cid(categoryId);
//        this.numberOfTheoryToAnswerRepository.deleteByQuiz_Id(categoryId);
        this.registeredCoursesRepository.deleteByCategory_cid(categoryId);
        this.categoryRepository.deleteById(categoryId);
    }












    public static class QuestionsSBService {
    }
}
