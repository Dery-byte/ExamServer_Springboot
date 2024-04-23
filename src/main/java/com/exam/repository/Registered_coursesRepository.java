package com.exam.repository;

import com.exam.model.exam.Registered_courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Registered_coursesRepository extends JpaRepository<Registered_courses, Long> {
}
