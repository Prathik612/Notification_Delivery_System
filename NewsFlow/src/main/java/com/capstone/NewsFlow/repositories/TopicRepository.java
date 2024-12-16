package com.capstone.NewsFlow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.NewsFlow.models.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}

