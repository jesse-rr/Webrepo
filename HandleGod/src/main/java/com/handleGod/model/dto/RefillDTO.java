package com.handleGod.model.dto;

import com.handleGod.model.enums.RefillStrategy;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Duration;
import java.time.Instant;

public record RefillDTO(
        @NotNull(message = "strategy of refill must be provided")
        RefillStrategy strategy,
        @Positive(message = "tokens to refill must be positive")
        long tokens,
        @NotNull(message = "period of rate-limiting is necessary")
        Duration period,
        Instant timeOfFirstRefill
) {
}
