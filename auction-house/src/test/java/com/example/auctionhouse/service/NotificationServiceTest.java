package com.example.auctionhouse.service;

import com.example.auctionhouse.model.NotificationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationServiceTest {

    @Test
    void sendEmailVerification() {
        var notf = NotificationModel.builder()
                .receiver("jessericardorogerio@gmail.com")
                .notificationType("emailVerification")
                .build();
        Map<String, String> replacements = Map.of(
                "[USER]", notf.getReceiver(),
                "[CODE]", notf.getCode()
        );
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            System.out.println(entry.getKey() + "-" + entry.getValue() + "\n");
        }
    }
}