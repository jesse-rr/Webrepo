package com.example.auction.config;

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
public class KafkaProducerAuction {

    private final KafkaTemplate<String, NotificationAuction> kafkaTemplate;

    @Async
    public void sendAuctionNotificationRequest(NotificationAuction notificationAuction) {
        log.info("SENDING BID NOTIFICATION FROM AUCTION_SERVICE TO NOTIFICATION_SERVICE");
        Message<NotificationAuction> message = MessageBuilder
                .withPayload(notificationAuction)
                .setHeader(KafkaHeaders.TOPIC, "auction-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
