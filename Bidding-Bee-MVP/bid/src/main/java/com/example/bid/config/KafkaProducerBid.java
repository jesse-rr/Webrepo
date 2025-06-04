package com.example.bid.config;

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
public class KafkaProducerBid {

    private final KafkaTemplate<String, NotificationBid> kafkaTemplate;

    @Async
    public void sendBidNotificationRequest(NotificationBid notificationBid) {
        log.info("SENDING BID NOTIFICATION FROM BID_SERVICE TO NOTIFICATION_SERVICE");
        Message<NotificationBid> message = MessageBuilder
                .withPayload(notificationBid)
                .setHeader(KafkaHeaders.TOPIC, "bid-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
