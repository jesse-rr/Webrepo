package org.project.readingplatform.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record ReviewDTO(
        @Size(max = 510, message = "COMMENT CANNOT HAVE MORE THAN 510 CHARS")
        @NotNull @NotBlank
        String content,
        @Min(value = 0) @Max(value = 5)
        double rating,
        @NotNull
        Long bookId,
        @NotNull
        UUID accountUUID
) {
}
