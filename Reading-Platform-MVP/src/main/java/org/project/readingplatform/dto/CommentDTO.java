package org.project.readingplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CommentDTO(
        @Size(max = 510, message = "COMMENT CANNOT HAVE MORE THAN 510 CHARS")
        @NotNull @NotBlank
        String content,
        @NotNull
        Long chapterId,
        @NotNull
        UUID accountUUID
) {
}
