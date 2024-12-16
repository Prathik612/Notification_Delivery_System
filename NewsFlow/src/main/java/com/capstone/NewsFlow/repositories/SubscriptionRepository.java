package com.capstone.NewsFlow.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.capstone.NewsFlow.models.Subscription;
import com.capstone.NewsFlow.models.Topic;
import com.capstone.NewsFlow.models.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUser(User user);
    List<Subscription> findByTopic(Topic topic);

    @Modifying
    @Query("DELETE FROM Subscription s WHERE s.topic = :topic")
    void deleteAllByTopic(Topic topic);
}
