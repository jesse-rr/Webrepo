package com.example.auctionhouse.repository;

import com.example.auctionhouse.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
