package com.handleGod.controller;

import com.handleGod.model.dto.Message;
import com.handleGod.model.dto.RefillDTO;
import com.handleGod.service.RateLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins/rate-limit")
public class RateLimitController {

    private final RateLimitService rateLimitService;

    @PostMapping("/standard")
    public ResponseEntity<Message> setStandardBucket(
            @RequestBody RefillDTO refillDTO,
            @RequestParam(defaultValue = "10") int capacity
    ) {
        return ResponseEntity.ok(rateLimitService.setStandardBucket(refillDTO, capacity));
    }

    @PostMapping("/add")
    public ResponseEntity<Message> setRateLimitOfEndpoint(
            @RequestParam String endpoint,
            @RequestBody RefillDTO refillDTO,
            @RequestParam(defaultValue = "10") int capacity
    ) {
        return ResponseEntity.ok(rateLimitService.setRateLimitOfEndpoint(endpoint, refillDTO, capacity));
    }

    @PutMapping("/update")
    public ResponseEntity<Message> updateRateLimit(
            @RequestParam String endpoint,
            @RequestBody RefillDTO refillDTO,
            @RequestParam(defaultValue = "10") int capacity
    ) {
        return ResponseEntity.ok(rateLimitService.updateRateLimit(endpoint, refillDTO, capacity));
    }

    @DeleteMapping("/{endpoint}")
    public void deleteEndpointFromRateLimit(
            @PathVariable String endpoint
    ) {
        rateLimitService.deleteEndpointFromRateLimit(endpoint);
    }

    @PostMapping("/toggle")
    public void toggleRateLimit() {
        rateLimitService.toggleRateLimit();
    }

    @DeleteMapping("/reset")
    public void resetRateLimit() {
        rateLimitService.resetRateLimit();
    }

    @GetMapping("/enabled")
    public boolean isRateLimitEnabled() {
        return rateLimitService.isRateLimitEnabled();
    }

}