package com.example.product.dto;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public record ProductDetails(
    String name,
    String description,
    BigDecimal initialPrice,
    BigDecimal finalPrice,
    int quantity,
    Currency currency,
    List<String> categories,
    List<String> tags
) {
}
