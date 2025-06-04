package com.example.auction.model;

import lombok.Getter;

@Getter
public enum AuctionStatus {
    CLOSED(false),
    OPEN(false),
    CANCELED(false),
    UPCOMING(true);

    private final boolean isChangeable;

    AuctionStatus(boolean isChangeable) {
        this.isChangeable = isChangeable;
    }
}
