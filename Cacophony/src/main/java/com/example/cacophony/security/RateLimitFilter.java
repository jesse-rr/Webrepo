package com.example.cacophony.security;

import com.example.cacophony.dto.request.RefillDTO;
import com.example.cacophony.util.FilterInterface;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BandwidthBuilder;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.InvalidParameterException;

import static com.example.cacophony.model.helper.RefillStrategy.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter implements FilterInterface {

    private Bucket standardBucket = setStandardBucket();
    private boolean enabled = true;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (!enabled) {
            return;
        }
        if (standardBucket == null) {
            log.info("BUCKET NULL, CREATING STANDARD BUCKET");
        }
        ConsumptionProbe probe = standardBucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            filterChain.doFilter(request,response);
        } else {
            response.setStatus(429);
            response.getWriter().write("Too many requests");
            response.addHeader("X-Rate-Limit-Retry-After", String.valueOf(probe.getNanosToWaitForRefill() / 1_000_000_000));
        }
    }

    public Bucket setStandardBucket() {
        Bandwidth bandwidth = setRefillStrategy(RefillDTO.lenientBruteForceProtection()); // CHANGE THIS TO CHANGE BRUTE FORCE DEFENSE POWER
        return Bucket.builder()
                .addLimit(bandwidth)
                .build();
    }

    private Bandwidth setRefillStrategy(RefillDTO refillDTO) {
        if (refillDTO.timeOfFirstRefill() == null && (refillDTO.strategy() == ALIGNED || refillDTO.strategy() == ADAPTATIVE)) {
            throw new NullPointerException("WITH ALIGNED/ADAPTATIVE RATE-LIMITING, THE TIME_OF_FIRST_REFILL IS NECESSARY, IT CANNOT BE NULL");
        }
        BandwidthBuilder.BandwidthBuilderRefillStage refill = Bandwidth.builder()
                .capacity(refillDTO.capacity());

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
                throw new InvalidParameterException(String.format("INVALID REFILL STRATEGY OF :: {}", refillDTO.strategy()));
        }
    }


    @Override
    public void reset() {
        this.standardBucket = setStandardBucket();
        this.enabled = true;
    }

    @Override
    public void toggle() {
        enabled = !this.enabled;
    }

    @Override
    public boolean isFilterEnabled() {
        return enabled;
    }

}
