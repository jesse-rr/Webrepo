package com.example.auction.model;

import lombok.Getter;

@Getter
public enum AuctionType {
    ENGLISH_AUCTION(false),
    DUTCH_AUCTION(false),
    SEALED_BID_AUCTION(true),
    REVERSE_AUCTION(false),
    VICKREY_AUCTION(true),
    SILENT_AUCTION(true),
    ABSOLUTE_AUCTION(false),
    RESERVE_AUCTION(true),
    TIMED_AUCTION(true),
    ONLINE_AUCTION(false),
    PENNY_AUCTION(false),
    LOTTERY_AUCTION(false),
    BLIND_AUCTION(true),
    BIDDING_WAR_AUCTION(false),
    CHARITY_AUCTION(false),
    ITEM_OR_SERVICE_AUCTION(false),
    CASH_AUCTION(false),
    MULTI_UNIT_AUCTION(false),
    ESTATE_AUCTION(false),
    LIVE_AUCTION(false),
    BIDDING_POOL_AUCTION(false);

    private final boolean isReserveType;

    AuctionType(boolean isReserveType) {
        this.isReserveType = isReserveType;
    }
}