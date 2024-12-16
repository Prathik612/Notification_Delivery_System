package com.capstone.NewsFlow.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.NewsFlow.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}