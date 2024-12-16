package com.capstone.NewsFlow.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.capstone.NewsFlow.models.Subscription;
import com.capstone.NewsFlow.models.Topic;
import com.capstone.NewsFlow.models.User;
import com.capstone.NewsFlow.services.SubscriptionService;
import com.capstone.NewsFlow.services.TopicService;
import com.capstone.NewsFlow.services.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        if (currentUser == null) {
            return "redirect:/login";
        }

        Iterable<Topic> topics = topicService.getAllTopics();

        String username = currentUser.getUsername();
        User user = userService.findByUsername(username);

        if (user == null) {
            return "error";
        }

        List<Subscription> subscriptions = subscriptionService.getSubscriptionsByUser(user);

        List<Long> subscriptionTopicIds = subscriptions.stream()
                .map(subscription -> subscription.getTopic().getId())
                .collect(Collectors.toList());

        model.addAttribute("topics", topics);
        model.addAttribute("subscriptionTopicIds", subscriptionTopicIds);
        return "home";
    }
}