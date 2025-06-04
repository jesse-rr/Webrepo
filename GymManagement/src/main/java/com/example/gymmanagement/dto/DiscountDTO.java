package com.example.gymmanagement.dto;

import com.example.gymmanagement.model.enums.DiscountType;

public record DiscountDTO(
        String name,
        String description,
        double value,
        DiscountType type,
        LocalDateT
) {
}
