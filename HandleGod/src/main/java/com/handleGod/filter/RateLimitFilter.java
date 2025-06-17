package com.handleGod.filter;

import com.handleGod.exception.BucketAlreadyContainsEndpointException;
import com.handleGod.util.FilterInterface;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class RateLimitFilter extends OncePerRequestFilter implements FilterInterface {

    private final Map<String, Bucket> bucketMap = new ConcurrentHashMap<>();
    @Setter
    private Bucket standardBucket;
    private boolean enabled = false;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getContextPath();
        Bucket bucket = bucketMap.get(path);

        if (!enabled) {
            filterChain.doFilter(request, response);
            return;
        }
        if (bucket == null) {
            log.info("BUCKET NOT CREATED FOR PATH :: {} :: USING STANDARD BUCKET",path);
            bucket = standardBucket;
        }

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            filterChain.doFilter(request,response);
        } else {
            response.setStatus(429);
            response.getWriter().write("Too many requests");
            response.addHeader("X-Rate-Limit-Retry-After", String.valueOf(probe.getNanosToWaitForRefill() / 1_000_000_000));
        }
    }

    public void addBucket(String endpoint, Bucket bucket) {
        if (bucketMap.containsKey(endpoint)) {
            throw new BucketAlreadyContainsEndpointException("THE BUCKET ALREADY CONTAINS THE ENDPOINT :: "+endpoint);
        }
        bucketMap.put(endpoint, bucket);
    }

    public void removeBucket(String endpoint) {
        bucketMap.remove(endpoint);
    }

    @Override
    public void reset() {
        bucketMap.clear();
    }

    @Override
    public void toggle() {
        if (standardBucket == null) {
            throw new NullPointerException("STANDARD BUCKET IS NECESSARY TO TOGGLE IT");
        }
        enabled = !this.enabled;
    }

    @Override
    public boolean isFilterEnabled() {
        return enabled;
    }
}
