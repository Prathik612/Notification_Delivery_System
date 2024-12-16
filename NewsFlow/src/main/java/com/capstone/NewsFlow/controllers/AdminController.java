package com.capstone.NewsFlow.controllers;

import com.capstone.NewsFlow.models.Topic;
import com.capstone.NewsFlow.services.TopicService;
import com.capstone.NewsFlow.services.NewsletterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private NewsletterService newsletterService;

    @GetMapping("/topics/new")
    public String showCreateTopicForm(Model model) {
        model.addAttribute("topic", new Topic());
        return "admin/create_topic";
    }

    @PostMapping("/topics/delete/{id}")
    public String deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return "redirect:/home";
    }

    @PostMapping("/topics")
    public String createTopic(@ModelAttribute("topic") Topic topic) {
        topicService.save(topic);
        return "redirect:/home";
    }

    @GetMapping("/newsletters")
    public String showNewsletterForm(Model model) {
        model.addAttribute("topics", topicService.getAllTopics());
        return "admin/send_newsletter";
    }

    @PostMapping("/newsletters")
    public String sendNewsletter(@RequestParam Long topicId, @RequestParam String content) {
        newsletterService.sendNewsletter(topicId, content);
        return "redirect:/home";
    }
}