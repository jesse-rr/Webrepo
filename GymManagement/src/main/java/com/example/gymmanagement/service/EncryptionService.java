package com.example.gymmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptionService {

    private final PasswordEncoder passwordEncoder;
    @Value(value = "${application.spring}")
    private String pepper;

    public String encryptPasswd(char[] passwd) {
        if (passwd.length <= 0) {
            throw new IllegalArgumentException("INVALID ENCRYPTION PASSWORD LENGTH");
        }
        String concatPasswd = new String(passwd) + pepper;
        return passwordEncoder.encode(concatPasswd);
    }

    public boolean validatePasswd(char[] passwd, String encPasswd) {
        if (passwd.length <= 0) {
            throw new IllegalArgumentException("INVALID ENCRYPTION PASSWORD LENGTH");
        }
        String concatPasswd = new String(passwd) + pepper;
        return passwordEncoder.matches(concatPasswd, encPasswd);
    }
}
