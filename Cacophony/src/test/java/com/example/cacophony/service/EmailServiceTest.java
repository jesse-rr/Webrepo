package com.example.cacophony.service;

import com.example.cacophony.dto.request.EmailRequestDTO;
import com.example.cacophony.model.helper.TemplateNames;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

class EmailServiceTest {

    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;
    private String emailAddress;
    private String password;
    private Map<String, Object> templateVariables;
    private EmailRequestDTO emailRequestDTO;

    @BeforeEach
    void setUp() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());

        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost("smtp.gmail.com");
        mailSenderImpl.setPort(587);
        mailSenderImpl.setUsername(emailAddress);
        mailSenderImpl.setPassword(password);

        Properties props = mailSenderImpl.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        emailAddress = "jessericardorogerio@gmail.com";
        emailRequestDTO = new EmailRequestDTO(
                "jessericardorogerio@gmail.com",
                "Jesse Ricardo Rogerio"
        );
        password = "PLACEHOLDER";
        templateVariables = null;
        templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        mailSender = mailSenderImpl;
    }

    @Test
    void sendEmail() {
        for (TemplateNames template : TemplateNames.values()) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

                Context context = new Context();
                context.setVariables(templateVariables);
                String htmlContent = templateEngine.process("/email/" + template.getName(), context);

                helper.setTo(new InternetAddress(emailRequestDTO.getEmailAddress()));
                helper.setFrom(new InternetAddress(emailAddress));
                helper.setSubject("TODO");
                helper.setText(htmlContent, true);

                mailSender.send(message);
            } catch (MessagingException e) {
            }
        }
    }
}