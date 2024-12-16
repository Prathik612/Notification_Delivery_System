package com.capstone.NewsFlow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.capstone.NewsFlow.kafka.KafkaProducerService;
import com.capstone.NewsFlow.models.*;
import com.capstone.NewsFlow.services.*;

import java.util.List;

@Controller
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public SubscriptionController(KafkaProducerService kafkaProducerService,
                                  SubscriptionService subscriptionService,
                                  TopicService topicService,
                                  UserService userService) {
        this.kafkaProducerService = kafkaProducerService;
        this.subscriptionService = subscriptionService;
        this.topicService = topicService;
        this.userService = userService;
    }

    @PostMapping("/subscribe/{topicId}")
    public String subscribe(@PathVariable Long topicId, @AuthenticationPrincipal UserDetails currentUser) {
        String username = currentUser.getUsername();
        User user = userService.findByUsername(username);
        Topic topic = topicService.getTopicById(topicId);

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setTopic(topic);

        subscriptionService.save(subscription);

        kafkaProducerService.topicSubscribedEvent(topic.getName());

        return "redirect:/home";
    }

    @PostMapping("/unsubscribe/{topicId}")
    public String unsubscribe(@PathVariable Long topicId, @AuthenticationPrincipal UserDetails currentUser) {
        String username = currentUser.getUsername();
        User user = userService.findByUsername(username);
        Topic topic = topicService.getTopicById(topicId);

        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByUser(user);
        subscriptions.stream()
                .filter(sub -> sub.getTopic().getId().equals(topicId))
                .findFirst()
                .ifPresent(subscriptionService::delete);

        kafkaProducerService.topicUnSubscribedEvent(topic.getName());

        return "redirect:/home";
    }
}