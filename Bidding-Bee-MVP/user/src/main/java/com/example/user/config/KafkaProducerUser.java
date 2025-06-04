package com.example.user.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerUser {

    private final KafkaTemplate<String, NotificationUser> kafkaTemplate;

    @Async
    public void sendProductNotificationRequest(NotificationUser notificationUser) {
        log.info("SENDING PRODUCT NOTIFICATION FROM USER_SERVICE TO NOTIFICATION_SERVICE");
        Message<NotificationUser> message = MessageBuilder
                .withPayload(notificationUser)
                .setHeader(KafkaHeaders.TOPIC, "user-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
