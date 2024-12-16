package com.capstone.NewsFlow.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.NewsFlow.models.Subscription;
import com.capstone.NewsFlow.models.Topic;
import com.capstone.NewsFlow.models.User;
import com.capstone.NewsFlow.repositories.SubscriptionRepository;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> getSubscriptionsByUser(User user) {
        return subscriptionRepository.findByUser(user);
    }

    public void save(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public void delete(Subscription subscription) {
        subscriptionRepository.delete(subscription);
    }

    public List<User> getUsersSubscribedToTopic(Topic topic) {
        List<Subscription> subscriptions = subscriptionRepository.findByTopic(topic);
        return subscriptions.stream()
                .map(Subscription::getUser)
                .collect(Collectors.toList());
    }
}
