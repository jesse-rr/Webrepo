package com.example.notification.model;

public record ProductNotificationResponse(
        String description,
        String senderEmail,
        String senderName,
        String code
) {
}
