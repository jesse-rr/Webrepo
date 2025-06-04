package com.example.product.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public record ProductRequestDTO(
        @NotNull
        Long userId,
        @NotNull
        String name,
        @NotNull
        String description,
        @Positive
        BigDecimal initialPrice,
        @Positive
        BigDecimal minimalBid,
        @Positive
        int quantity,
        @NotNull
        Currency currency,
        @NotNull
        List<String> categories
) {
}
