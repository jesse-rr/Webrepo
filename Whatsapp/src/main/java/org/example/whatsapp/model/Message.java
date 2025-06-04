package org.example.whatsapp.model;

import org.example.whatsapp.model.metadata.MessageMetadata;

import java.time.Instant;


public class Message {
    private String encContent;
    private Instant ephemeralDuration;
    private boolean isViewed;
    private MessageMetadata metadata;

    public MessageMetadata getMetadata() {
        return metadata;
    }
}
