package com.capstone.NewsFlow.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.capstone.NewsFlow.models.Topic;
import com.capstone.NewsFlow.models.User;

import jakarta.mail.internet.MimeMessage;

@Service
public class NewsletterService {

    @Autowired
    private TopicService topicService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private JavaMailSender mailSender;

    public void sendNewsletter(Long topicId, String content) {
        Topic topic = topicService.getTopicById(topicId);
        if (topic == null) {
            return;
        }

        List<User> subscribers = subscriptionService.getUsersSubscribedToTopic(topic);

        for (User user : subscribers) {
            sendEmail(user.getEmail(), "Newsletter: " + topic.getName(), content);
        }
    }
    
    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom("${spring.mail.username}");
            helper.setTo(to);
            helper.setSubject(subject);

            String emailContent = """
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body {
                            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                            background-color: #f4f4f4;
                            color: #333;
                        }
                        .email-container {
                            width: auto;
                            margin: 0 auto;
                            background-color: #ffffff;
                            border-radius: 8px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                            overflow: hidden;
                        }
                        .header {
                            background-color: #162533;
                            padding: 20px;
                            color: white;
                            text-align: center;
                        }
                        .content {
                            padding: 20px;
                            text-align: left;
                        }
                        .footer {
                            background-color: #162533;
                            padding: 10px;
                            color: white;
                            text-align: center;
                            font-size: 0.9em;
                        }
                    </style>
                </head>
                <body>
                    <div class="email-container">
                        <div class="header">
                            <h1>NewsFlow</h1>
                        </div>
                        <div class="content">
                            <p>Dear User,</p>
                            <p>%s</p>
                            <p>Best regards,<br>NewsFlow Team</p>
                        </div>
                        <div class="footer">
                            <p>&copy; 2024 NewsFlow. All rights reserved.</p>
                        </div>
                    </div>
                </body>
                </html>
            """.formatted(text);

            helper.setText(emailContent, true);
            mailSender.send(mimeMessage);

            System.out.println("Email sent successfully to " + to);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
