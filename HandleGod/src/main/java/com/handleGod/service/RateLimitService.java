package com.handleGod.service;

import com.handleGod.exception.InvalidRefillStrategy;
import com.handleGod.filter.RateLimitFilter;
import com.handleGod.model.dto.Message;
import com.handleGod.model.dto.RefillDTO;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.handleGod.model.enums.RefillStrategy.*;
import static io.github.bucket4j.BandwidthBuilder.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateLimitService {

    private final RateLimitFilter rateLimitFilter;

    public Message setStandardBucket(RefillDTO refillDTO, int capacity) {
        Bandwidth bandwidth = setRefillStrategy(refillDTO, capacity);
        Bucket bucket = Bucket.builder()
                .addLimit(bandwidth)
                .build();
        rateLimitFilter.setStandardBucket(bucket);
        return new Message(null, "successful adding of standard bucket in case of none");
    }

    public Message setRateLimitOfEndpoint(String endpoint, RefillDTO refillDTO, int capacity) {
        Bandwidth bandwidth = setRefillStrategy(refillDTO, capacity);
        Bucket bucket = Bucket.builder()
                .addLimit(bandwidth)
                .build();

        rateLimitFilter.addBucket(endpoint, bucket);
        return new Message(endpoint, "successful adding of rate-limit");
    }

    public Message updateRateLimit(String endpoint, RefillDTO refillDTO, int capacity) {
        rateLimitFilter.removeBucket(endpoint);
        setRateLimitOfEndpoint(endpoint, refillDTO, capacity);
        return new Message(endpoint, "successful update of rate-limit");
    }

    public void deleteEndpointFromRateLimit(String endpoint) {
        rateLimitFilter.removeBucket(endpoint);
    }

    public void resetRateLimit() {
        rateLimitFilter.reset();
    }

    public void toggleRateLimit() {
        rateLimitFilter.toggle();
    }

    public boolean isRateLimitEnabled() {
        return rateLimitFilter.isFilterEnabled();
    }

    // ---- HELPER METHOD ----
    private Bandwidth setRefillStrategy(RefillDTO refillDTO, int capacity) {
        if (refillDTO.timeOfFirstRefill() == null && (refillDTO.strategy() == ALIGNED || refillDTO.strategy() == ADAPTATIVE)) {
            throw new NullPointerException("WITH ALIGNED/ADAPTATIVE RATE-LIMITING, THE TIME_OF_FIRST_REFILL IS NECESSARY, IT CANNOT BE NULL");
        }
        BandwidthBuilderRefillStage refill = Bandwidth.builder()
                .capacity(capacity);

        switch (refillDTO.strategy()) {
            case GREEDY:
                return refill.refillGreedy(refillDTO.tokens(), refillDTO.period()).build();
            case INTERVALLY:
                return refill.refillIntervally(refillDTO.tokens(), refillDTO.period()).build();
            case ALIGNED:
                return refill.refillIntervallyAligned(refillDTO.tokens(), refillDTO.period(), refillDTO.timeOfFirstRefill()).build();
            case ADAPTATIVE:
                return refill.refillIntervallyAlignedWithAdaptiveInitialTokens(refillDTO.tokens(), refillDTO.period(), refillDTO.timeOfFirstRefill()).build();
            default:
                throw new InvalidRefillStrategy(String.format("INVALID REFILL STRATEGY OF :: {}", refillDTO.strategy()));
        }
    }
}