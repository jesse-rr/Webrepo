package com.example.bid.repository;

import com.example.bid.model.Bid;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {


    @Query("SELECT b FROM bid b WHERE b.productId= :productId")
    List<Bid> getAllBidIdsFromProductId(Long productId);

    @Query("SELECT b FROM bid b WHERE b.productId= :productId ORDER BY b.bidAmount LIMIT 1")
    Optional<Bid> getHighestBidIdFromProductId(Long productId);

    @Query("SELECT b FROM bid b WHERE b.userId= :userId")
    List<Bid> getAllBidsFromUserId(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE bid b SET b.canceledBid= true WHERE b.bidId= :bidId")
    void updateBidToCanceledById(Long bidId);
}
