package com.handleGod.model.dto;


import lombok.Getter;

import java.time.Instant;

@Getter
public class Message {
    private final String endpoint;
    private final String message;
    private final Instant date;
    private final boolean isSuccessful;

    public Message(String endpoint, String message) {
        this.endpoint = endpoint;
        this.message = message;
        this.date = Instant.now();
        this.isSuccessful = true;
    }
}
