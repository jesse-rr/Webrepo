package com.example.auctionhouse.repository;

import com.example.auctionhouse.model.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {

}
