package com.example.auctionhouse.controller;

import com.example.auctionhouse.dto.BidRequestDTO;
import com.example.auctionhouse.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bids")
public class BidController {

    private final BidService bidService;

    @PostMapping("/place")
    public ResponseEntity<?> placeBid(
        @RequestBody BidRequestDTO request,
        @RequestParam Long accountId
    ) {
        bidService.placeBid(request, accountId);
        return ResponseEntity.ok().build();
    }
}
