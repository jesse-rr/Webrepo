package com.example.auctionhouse.dto;

import com.example.auctionhouse.model.embedded.PaymentStatus;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public record PaymentResponseDTO(
        BigDecimal finalAmount,
        BigDecimal paymentPlan,
        Currency currency,
        PaymentStatus paymentStatus,
        String description,
        List<Double> discounts
) {
}
