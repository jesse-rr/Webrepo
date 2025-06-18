package com.project.inventorymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank @NotNull
        String SKU,
        @NotBlank @NotNull
        String name,
        @NotBlank @NotNull
        String description,
        @Positive
        BigDecimal price,
        @NotNull
        Long supplierId
) {
}
