package org.project.readingplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.project.readingplatform.models.enums.BookGenre;

import java.util.List;

public record BookDTO(
        @NotNull @NotBlank
        String title,
        @NotNull @NotBlank
        String synopsis,
        @NotNull @NotBlank
        String authorName,
        @NotNull @NotBlank
        String bookImg,
        @NotNull
        List<BookGenre> genres
) {
}
