package com.example.eventhorizon.model;

import com.example.eventhorizon.encrypt.AES256Encryptor;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Embeddable
@Getter
@Setter
public class SecurityConfigs {

    @Column(nullable = false, updatable = false, name = "encrypted_api_key")
    private String apiKey;
    @Transient
    private transient String plainAPIKey;
    @Column(nullable = false)
    private Instant authExpiration;
    private Integer rateLimit;
    private Integer quotaUsed;

    public void setQuotaUsed(Integer quotaUsed) {
        this.quotaUsed = (quotaUsed == null) ? 0 : quotaUsed + 1;
    }

    public void setApiKey(String apiKey) {
        this.plainAPIKey = plainAPIKey;
        try {
            this.apiKey = AES256Encryptor.encrypt(plainAPIKey);
        } catch (Exception e) {
            throw new RuntimeException("FAILED TO ENCRYPT API KEY FROM PLAIN ",e);
        }
    }

    public String getApiKey() {
        try {
            return AES256Encryptor.decrypt(this.apiKey);
        } catch (Exception e) {
            throw new RuntimeException("FAILED TO DECRYPT API KEY",e);
        }
    }
}
