package org.example.whatsapp.model.enums;

public enum MessageType {
    TEXT("text"),
    IMAGE("image"),
    AUDIO("audio"),
    VIDEO("video"),
    DOCUMENT("document"),
    LOCATION("location"),
    MULTI_MODEL("multi-model");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }
}
