package com.example.auction.model;

import lombok.Getter;

@Getter
public enum AuthorizationAccess {
    MEMBERS_ONLY("Restricted to members only"),
    FRIENDS("Only friends of the organizer or platform"),
    VIP("Access reserved for VIPs"),
    LIMITED_ACCESS("Access is limited based on specific criteria"),
    OPEN_TO_PUBLIC("Open to everyone, no restrictions"),
    PRIVATE("Accessible by invitation or specific invitation-only groups");

    private final String description;

    AuthorizationAccess(String description) {
        this.description = description;
    }
}
