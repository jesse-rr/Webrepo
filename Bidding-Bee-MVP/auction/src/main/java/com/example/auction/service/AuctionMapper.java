package com.example.auction.service;

import com.example.auction.dto.AuctionRequestDTO;
import com.example.auction.model.Auction;
import org.springframework.stereotype.Service;

@Service
public class AuctionMapper {

    public Auction toAuction(AuctionRequestDTO request) {
        return Auction
                .builder()
                .auctionType(request.auctionType())
                .entryFee(request.entryFee())
                .description(request.description())
                .name(request.name())
                .authorizationAccess(request.authorizationAccess())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .build();
    }
}
