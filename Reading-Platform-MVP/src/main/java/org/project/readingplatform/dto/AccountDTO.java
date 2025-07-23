package org.project.readingplatform.dto;

import jakarta.validation.constraints.NotNull;
import org.project.readingplatform.models.enums.Gender;

import java.util.UUID;

public record AccountDTO(
        @NotNull
        UUID accountUUID,
        String accountImage,
        String bio,
        String phone,
        Gender gender
) {
}
