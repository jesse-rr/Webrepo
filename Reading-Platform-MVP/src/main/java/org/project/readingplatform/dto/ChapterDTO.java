package org.project.readingplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChapterDTO(
        @NotNull @NotBlank
        String title,
        @NotNull @NotBlank
        String content
) {
}
