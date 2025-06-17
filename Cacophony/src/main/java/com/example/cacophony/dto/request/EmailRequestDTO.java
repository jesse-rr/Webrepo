package com.example.cacophony.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter @ToString
public class EmailRequestDTO {
    @Column(nullable = false, name = "email_address") @Email
    private String emailAddress;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private Map<String, Object> templateVariables;
    private LocalDateTime timestamp;

    public EmailRequestDTO(String emailAddress, String fullName) {
        this.emailAddress = emailAddress;
        this.fullName = fullName;
        this.timestamp = LocalDateTime.now();
        this.templateVariables = new HashMap<>();
        templateVariables.put("name", this.fullName);
    }
}
