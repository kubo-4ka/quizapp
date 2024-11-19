package com.myapp.quizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.quizapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
