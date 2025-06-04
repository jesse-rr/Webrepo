package com.example.product.config;

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
public class KafkaProducerProduct {

    private final KafkaTemplate<String, NotificationProduct> kafkaTemplate;

    @Async
    public void sendProductNotificationRequest(NotificationProduct notificationProduct) {
        log.info("SENDING PRODUCT NOTIFICATION FROM PRODUCT_SERVICE TO NOTIFICATION_SERVICE");
        Message<NotificationProduct> message = MessageBuilder
                .withPayload(notificationProduct)
                .setHeader(KafkaHeaders.TOPIC, "product-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
