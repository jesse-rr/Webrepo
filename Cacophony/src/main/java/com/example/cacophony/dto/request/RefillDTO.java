package com.example.cacophony.dto.request;

import com.example.cacophony.model.helper.RefillStrategy;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Duration;
import java.time.Instant;

public record RefillDTO(
        @NotNull
        RefillStrategy strategy,
        @Positive
        long tokens,
        @NotNull
        Duration period,
        Instant timeOfFirstRefill,
        @Positive
        long capacity
) {

        public static RefillDTO bruteForceProtection() {
                return new RefillDTO(
                        RefillStrategy.INTERVALLY,  // Most predictable for security
                        5,                          // 5 attempts
                        Duration.ofMinutes(1),      // Per minute
                        null,                       // Not needed
                        5                           // Same as tokens
                );
        }

        // Stricter version for sensitive endpoints
        public static RefillDTO strictBruteForceProtection() {
                return new RefillDTO(
                        RefillStrategy.INTERVALLY,
                        3,                          // Only 3 attempts
                        Duration.ofMinutes(1),
                        null,
                        3
                );
        }

        // More lenient version for less sensitive areas
        public static RefillDTO lenientBruteForceProtection() {
                return new RefillDTO(
                        RefillStrategy.INTERVALLY,
                        10,                         // 10 attempts
                        Duration.ofMinutes(1),
                        null,
                        10
                );
        }
}
