package com.example.cacophony.service;

import com.example.cacophony.dto.request.EmailRequestDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value(value = "${spring.mail.username}") @Email
    private String emailAddress;

    @Async
    public void sendEmail(
            @NotNull String templateName,
            @Valid EmailRequestDTO emailRequestDTO
    ){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(emailRequestDTO.getTemplateVariables());
            String htmlContent = templateEngine.process(templateName, context);

            helper.setTo(new InternetAddress(emailRequestDTO.getEmailAddress()));
            helper.setFrom(new InternetAddress(emailAddress));
            helper.setSubject("TODO");
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("SENDING EMAIL TEMPLATE :: {} :: TO EMAIL :: {}",templateName, emailRequestDTO.getEmailAddress());
        } catch (MessagingException e) {
            log.error("MESSAGING ERROR IN EMAIL SERVICE OF :: {} :: STACK TRACE {}",e.getMessage(),e.getStackTrace());
        }
    }
}
