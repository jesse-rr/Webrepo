package org.project.readingplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.project.readingplatform.models.enums.ReportType;

import java.util.UUID;

public record ReportDTO(
        @NotNull
        ReportType type,
        @NotNull @NotBlank
        String userInfo,
        @NotNull
        UUID accountUUID,
        Long commentId,
        Long reviewId,
        Long bookId
) {
}
