package com.capstone.NewsFlow.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.NewsFlow.models.Topic;
import com.capstone.NewsFlow.repositories.SubscriptionRepository;
import com.capstone.NewsFlow.repositories.TopicRepository;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Iterable<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    @Transactional
    public void deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id).orElse(null);
        if (topic != null) {
            subscriptionRepository.deleteAllByTopic(topic);
            topicRepository.delete(topic);
        }
    }

    public Topic getTopicById(Long id) {
        return topicRepository.findById(id).orElse(null);
    }
}
