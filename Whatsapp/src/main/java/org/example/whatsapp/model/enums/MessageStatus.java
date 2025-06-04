package org.example.whatsapp.model.enums;

public enum MessageStatus {
    SENT("sent"),
    DELIVERED("delivered"),
    READ("read"),
    VIEW_ONCE("view_once"),
    TIMED("timed");

    private final String value;

    MessageStatus(String value) {
        this.value = value;
    }
}
