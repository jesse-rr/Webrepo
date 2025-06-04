package com.example.gymmanagement.service;

import com.example.gymmanagement.dto.MessageDTO;
import com.example.gymmanagement.dto.MessageTemplateDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    @Value(value = "${mail.username}")
    private String application_email;

    public void sendMessage(MessageTemplateDTO template, MessageDTO messageDTO) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(new InternetAddress(application_email));
        helper.setTo(new InternetAddress(messageDTO.email()));
        helper.setSubject(template.subject());
        helper.setText(template.contentHTML(), true); // isHTML

        if (template.contentImg() != null) {
            Resource resource = new ClassPathResource("static/images/logo.png");
            helper.addInline("logo", resource);
        }

        mailSender.send(message);
        log.info("SENDING EMAIL SUCCESSFULLY TO EMAIL :: {}", messageDTO.email());
    }
}
