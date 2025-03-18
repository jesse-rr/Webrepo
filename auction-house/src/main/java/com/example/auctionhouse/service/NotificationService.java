package com.example.auctionhouse.service;

import com.example.auctionhouse.config.JsonUtil;
import com.example.auctionhouse.model.NotificationModel;
import com.example.auctionhouse.repository.NotificationRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender mailSender;
    private final NotificationRepository notificationRepository;

    @Value(value = "${spring.mail.username}")
    private String smtp_username;
    @Value(value = "${app.auction-name}")
    private String auction_name;

    @Async
    public void sendEmailVerification(String receiver) throws MessagingException {
        NotificationModel model = saveNotification(receiver, "emailVerification");
        Map<String, String> replacements = Map.of(
                "[CODE]", model.getCode(),
                "[LINK]", "http://localhost:8080/api/v1/verify/"+model.getCode()
        );
        processEmail(model.getReceiver(), replacements, model.getNotificationType());
    }

    @Async
    public void sendPasswdAlterationNotification(String email) throws MessagingException {
        NotificationModel model = saveNotification(email, "passwordAlteration");
        Map<String, String> replacements = Map.of(
                "[CODE]", model.getCode(),
                "[LINK]", "http://localhost:8080/api/v1/auctions/" + model.getCode() + "/get"
        );
        processEmail(model.getReceiver(), replacements, model.getNotificationType());
    }

    @Async
    public void sendBidUpdateNotification(String email, String itemName, String bidAmount) throws MessagingException {
        NotificationModel model = saveNotification(email, "bidUpdate");
        Map<String, String> replacements = Map.of(
                "[ITEM]", itemName,
                "[NEW BID AMOUNT]", bidAmount,
                "[TIME]", "Auction End Time"
        );
        processEmail(model.getReceiver(), replacements, model.getNotificationType());
    }

    @Async
    public void sendAuctionWonNotification(String email, String itemName, String winningBid) throws MessagingException {
        NotificationModel model = saveNotification(email, "auctionWon");
        Map<String, String> replacements = Map.of(
                "[ITEM]", itemName,
                "[WINNING BID]", winningBid
        );
        processEmail(model.getReceiver(), replacements, model.getNotificationType());
    }

    @Async
    public void sendAuctionLostNotification(String email, String itemName, String winningBid) throws MessagingException {
        NotificationModel model = saveNotification(email, "auctionLost");
        Map<String, String> replacements = Map.of(
                "[ITEM]", itemName,
                "[WINNING BID]", winningBid
        );
        processEmail(model.getReceiver(), replacements, model.getNotificationType());
    }

    @Async
    public void sendAccountRemovalNotification(String email) throws MessagingException {
        NotificationModel model = saveNotification(email, "accountRemoval");
        Map<String, String> replacements = Map.of(
                "[SUPPORT EMAIL]", "support@auctionhouse.com"
        );
        processEmail(model.getReceiver(), replacements, model.getNotificationType());
    }

    @Async
    public void sendAccountDeletionConfirmationNotification(String email) throws MessagingException {
        NotificationModel model = saveNotification(email, "accountDeletionConfirmation");
        processEmail(model.getReceiver(), null, model.getNotificationType());
    }

    @Async
    public void sendDeletionRequestNotification(String email) throws MessagingException {
        NotificationModel model = saveNotification(email, "deletionRequest");
        Map<String, String> replacements = Map.of(
                "[USER]", model.getReceiver(),
                "[SUPPORT EMAIL]", "support@auctionhouse.com"
        );
        processEmail(model.getReceiver(), replacements, model.getNotificationType());
    }

    private void processEmail(String receiver, Map<String, String> replacements, String notificationType) throws MessagingException {
        log.info("SENDING NOTIFICATION :: {} :: TO EMAIL :: {}", notificationType, receiver);
        MimeMessage message = mailSender.createMimeMessage();
        Map<String, Object> templates = JsonUtil.jsonToMap(JsonUtil.loadJsonContent("src/main/resources/configurations/templates.json"));
        Map<String, String> template = (Map<String, String>) templates.get(notificationType);

        String body = template.get("body");
        body = body.replace("[AUCTION]", auction_name);
        body = body.replace("[USER]", receiver);

        if (replacements != null) {
            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                body = body.replace(entry.getKey(), entry.getValue());
            }
        }

        message.setSubject(template.get("subject"));
        if (notificationType.equals("emailVerification")) {
            message.setSubject(template.get("subject").replace("[CODE]", replacements.get("[CODE]")));
        }
        message.setFrom(smtp_username);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        message.setText(body);

        mailSender.send(message);
    }

    private NotificationModel saveNotification(String receiver, String emailNotification) {
        return notificationRepository.save(NotificationModel.builder()
                .receiver(receiver)
                .notificationType(emailNotification)
                .code(genUniqueCode())
                .build());
    }

    public String genUniqueCode() {
        return UUID.randomUUID().toString().substring(0,14).replace("-", "");
    }
}
