package org.project.readingplatform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
        @NotNull @NotBlank
        String username,
        @NotNull @Email
        String email,
        @NotNull @NotBlank
        String login,
        @NotNull @NotBlank
        String password,
        @NotNull @NotBlank
        String matchingPassword
) {
}
