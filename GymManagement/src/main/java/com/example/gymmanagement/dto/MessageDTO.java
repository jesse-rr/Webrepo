package com.example.gymmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record MessageDTO(
        @NotNull String name,
        @Email String email,
        String phone
) {
}
