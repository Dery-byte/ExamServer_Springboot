package com.exam.repository;

import com.exam.model.exam.Category;
import com.exam.model.exam.Registered_courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


}
