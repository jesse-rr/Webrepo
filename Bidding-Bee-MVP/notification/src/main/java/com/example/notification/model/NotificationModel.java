package com.example.notification.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "notification")
@Table(name = "notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class NotificationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String code;
    private String sender;
    private String description;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime timestamp;
}
