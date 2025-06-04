package com.example.bid.service;

import com.example.bid.config.KafkaProducerBid;
import com.example.bid.config.NotificationBid;
import com.example.bid.dto.BidRequestDTO;
import com.example.bid.model.Bid;
import com.example.bid.repository.BidRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidService {

    private final BidRepository bidRepository;
    private final KafkaProducerBid kafkaProducerBid;
    private final BidMapper bidMapper;

    public void addBid(BidRequestDTO request) {
        log.info("PLACING BID REQUEST :: {}",request);
        bidRepository.save(Bid.builder()
                        .currency(request.currency())
                        .userId(request.userId())
                        .bidAmount(request.bidAmount())
                        .productId(request.productId())
                .build());

        kafkaProducerBid.sendBidNotificationRequest(
                new NotificationBid(

                )
        );
    }

    public List<Bid> getBidsByProduct(Long productId) {
        log.info("RETRIEVING ALL BETS ON PRODUCT ID :: {}",productId);
        return bidRepository.getAllBidIdsFromProductId(productId);
    }

    public Bid getHighestBidId(Long productId) {
        log.info("RETRIEVING HIGHEST BID FOR PRODUCT ID :: {}",productId);
        return bidRepository.getHighestBidIdFromProductId(productId)
                .orElseThrow(() -> new EntityNotFoundException("HIGHEST BID NOT FOUND WITH PRODUCT ID :: "+productId));
    }

    // TODO make sure its possible, already placed bids cannot be canceled and auction allows
    public void cancelBid(Long bidId) {
        log.info("CANCELING BID WITH ID :: {}",bidId);
        var bid = getBidById(bidId);
        if (bid.isCanceledBid()) {
            throw new RuntimeException("BID ALREADY CANCELED");
        }
        bidRepository.updateBidToCanceledById(bidId);

        kafkaProducerBid.sendBidNotificationRequest(
                new NotificationBid(

                )
        );
    }

    private Bid getBidById(Long bidId) {
        log.info("RETRIEVING BID WITH ID :: {}",bidId);
        return bidRepository.findById(bidId)
                .orElseThrow(() -> new EntityNotFoundException("BID NOT FOUND WITH ID :: "+bidId));
    }

    public List<Bid> getAllBidsFromUserId(Long userId) {
        log.info("RETRIEVING ALL BIDS FROM USER ID :: {}",userId);
        return bidRepository.getAllBidsFromUserId(userId);
    }

    public boolean validateBidAsHighest(Long bidId, Long productId) {
         BigDecimal bidValue = bidRepository.findById(bidId)
                 .map(Bid::getBidAmount)
                 .orElseThrow(() -> new EntityNotFoundException("BID NOT FOUND WITH ID :: "+bidId));
        if (getHighestBidId(productId).getBidAmount().compareTo(bidValue) <= 0) {return false; }
        return true;
    }
}
