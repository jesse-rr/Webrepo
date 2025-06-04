package com.example.bid.dto;

import java.math.BigDecimal;
import java.util.Currency;

public record BidRequestDTO(
        Long userId,
        Long productId,
        BigDecimal bidAmount,
        Currency currency
) {
}
