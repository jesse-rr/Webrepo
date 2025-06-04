package com.example.auction.dto;

import com.example.auction.model.AuctionType;
import com.example.auction.model.AuthorizationAccess;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AuctionRequestDTO(
        @NotNull
        String name,
        @NotNull
        String description,
        @Positive
        BigDecimal entryFee,
        @NotNull
        AuthorizationAccess authorizationAccess,
        @NotNull
        AuctionType auctionType,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
