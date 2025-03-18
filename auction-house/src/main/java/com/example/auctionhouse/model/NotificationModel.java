package com.example.auctionhouse.model;

import com.example.auctionhouse.model.embedded.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "notification")
@Table(name = "notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationModel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String notificationType;
    private String receiver; // EMAIL | PHONE
    private String code;
}
