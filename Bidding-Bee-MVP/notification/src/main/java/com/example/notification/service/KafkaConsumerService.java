package com.example.notification.service;

import com.example.notification.model.NotificationModel;
import com.example.notification.model.NotificationType;
import com.example.notification.model.ProductNotificationResponse;
import com.example.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = {"product-topic"}, groupId = "product-group")
    public void consumeProductNotification(ProductNotificationResponse response) {
        log.info("CONSUMING PRODUCT NOTIFICATION");
        var notification = notificationRepository.save(
                NotificationModel.builder()
                        .description(response.description())
                        .notificationType(NotificationType.PRODUCT_NOTIFICATION)
                        .sender(response.senderEmail())
                        .code(response.code())
                        .build()
        );
        emailService.processNotification(response);
    }

    @KafkaListener(topics = {"user-topic"}, groupId = "user-group")
    public void consumeUserNotification() {
        log.info("CONSUMING USER NOTIFICATION");
        var notification = notificationRepository.save(
                NotificationModel.builder()
                        .description()
                        .notificationType(NotificationType.USER_NOTIFICATION)
                        .sender()
                        .code()
                        .build()
        );
    }

    @KafkaListener(topics = {"bid-topic"}, groupId = "bid-group")
    public void consumeBidNotification() {
        log.info("CONSUMING BID NOTIFICATION");
        var notification = notificationRepository.save(
                NotificationModel.builder()
                        .description()
                        .notificationType(NotificationType.BID_NOTIFICATION)
                        .sender()
                        .code()
                        .build()
        );
    }

    @KafkaListener(topics = {"auction-topic"}, groupId = "auction-group")
    public void consumeAuctionNotification() {
        log.info("CONSUMING AUCTION NOTIFICATION");
        var notification = notificationRepository.save(
                NotificationModel.builder()
                        .description()
                        .notificationType(NotificationType.AUCTION_NOTIFICATION)
                        .sender()
                        .code()
                        .build()
        );
    }
}
