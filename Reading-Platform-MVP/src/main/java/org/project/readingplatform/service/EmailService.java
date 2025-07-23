package org.project.readingplatform.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static jakarta.mail.Message.RecipientType.TO;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final RedisService redisService;

    private String generateVerificationCode() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    @Async
    public void sendEmailVerificationCode(String email) throws MessagingException {
        log.info("EMAIL VERIFICATION BEING SEND WITH EMAIL :: {}",email);
        String code = generateVerificationCode();
        redisService.storeVerificationCode(email, code);

        MimeMessage message = mailSender.createMimeMessage();
        message.setSubject("EMAIL VERIFICATION - CODE: ",code);
        message.setFrom(new InternetAddress("jessericardorogerio@gmail.com"));
        message.setRecipients(TO, email);
        message.setText("This is a email verification for this email, if this is wrong, ignore it." +
                "Code: "+code);

        mailSender.send(message);
    }

    @Async
    public void sendAccountDeletionVerification(
            String email
    ) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setSubject("ACCOUNT DELETION REQUEST");
        message.setFrom(new InternetAddress("jessericardorogerio@gmail.com"));
        message.setRecipients(TO, email);
        message.setText("Your account for email: ",email," has been requested to deletion, if this is wrong, please enter site: http://localhost:8080/api/v1/homepage, configurations and cancel deletion! " +
                "You have 24 hours.");

        mailSender.send(message);
    }
}
