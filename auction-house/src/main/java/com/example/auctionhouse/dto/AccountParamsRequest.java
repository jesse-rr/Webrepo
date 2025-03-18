package com.example.auctionhouse.dto;

import com.example.auctionhouse.model.embedded.AccountType;
import com.example.auctionhouse.model.embedded.Address;

import java.util.Map;

public record AccountParamsRequest(
        String profileImage,
        Map<String, Object> configs,
        AccountType accountType,
        Address address
) {
}
