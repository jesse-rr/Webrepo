package com.example.auction.service;

import com.example.auction.config.KafkaProducerAuction;
import com.example.auction.config.NotificationAuction;
import com.example.auction.dto.AuctionRequestDTO;
import com.example.auction.model.Auction;
import com.example.auction.model.AuctionStatus;
import com.example.auction.repository.AuctionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final KafkaProducerAuction kafkaProducerAuction;
    private final AuctionMapper auctionMapper;

    public void addAuction(AuctionRequestDTO request) {
        log.info("ADDING AUCTION REQUEST :: {}", request);
        auctionRepository.save(auctionMapper.toAuction(request));

        kafkaProducerAuction.sendAuctionNotificationRequest(
                new NotificationAuction(

                )
        );
    }

    public Auction getAuctionById(Long auctionId) {
        log.info("RETRIEVING AUCTION WITH ID :: {}",auctionId);
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("AUCTION NOT FOUND WITH ID :: "+ auctionId));
    }

    public void removeAuctionById(Long auctionId) {
        log.info("DELETING AUCTION BY ID :: {}",auctionId);
        var auction = getAuctionById(auctionId);
        if (auction.getEventStatus().equals(AuctionStatus.OPEN)) {
            throw new RuntimeException("YOU CANNOT DELETE AN OPENED AUCTION");
        }
        auctionRepository.delete(auction);

        kafkaProducerAuction.sendAuctionNotificationRequest(
                new NotificationAuction(

                )
        );
    }
}
