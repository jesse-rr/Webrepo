package org.project.readingplatform.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final String verification_code = "verification_code:";
    @Value("${email.verification-code.expiration-time}")
    private long expirationTime;

    public void storeVerificationCode(String email, String code) {
        log.info("STORING VERIFICATION CODE: <{}>", code);
        redisTemplate.opsForValue().set(verification_code + email, code, expirationTime, TimeUnit.MINUTES);
    }

    public void deleteVerificationCode(String email) {
        log.info("DELETING VERIFICATION CODE WITH USER_EMAIL: <{}>", email);
        redisTemplate.delete(verification_code + email);
    }

    public String getCodeByUserEmail(String email) {
        log.info("GET USER CODE WITH EMAIL <{}>", email);
        return redisTemplate.opsForValue().get(verification_code + email);
    }
}