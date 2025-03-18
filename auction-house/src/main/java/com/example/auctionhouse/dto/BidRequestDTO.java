package com.example.auctionhouse.dto;

import java.math.BigDecimal;
import java.util.Currency;

public record BidRequestDTO(
        Long productId,
        BigDecimal bidValue,
        Currency currency
) {
}
