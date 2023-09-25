package com.exam.repository;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository  extends JpaRepository <Quiz, Long> {

    List<Quiz> findBycategory(Category category);


    public List<Quiz> findByActive(Boolean b);
    public  List<Quiz> findByCategoryAndActive(Category c , Boolean b);

}
