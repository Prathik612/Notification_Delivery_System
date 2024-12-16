package com.capstone.NewsFlow.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    
    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmailSentEvent(String newsletterTitle) {
        kafkaTemplate.send("email-sent", newsletterTitle);
    }

    public void topicSubscribedEvent(String newsletterTitle) {
        kafkaTemplate.send("topic-subscribed", newsletterTitle);
    }

    public void topicUnSubscribedEvent(String newsletterTitle) {
        kafkaTemplate.send("topic-unsubscribed", newsletterTitle);
    }

}