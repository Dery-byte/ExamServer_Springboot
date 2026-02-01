package com.exam.repository;

import com.exam.model.Role;
import com.exam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);


    List<User> findByRole(Role role);         // Fetch all lecturers

    Optional<User> findByIdAndRole(Long id, Role role);

    User findByPhone(String phone);

//    List<User> findByUser(User user);
}
