package com.example.auction.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "auction")
@Table(name = "auction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auctionId;

    private Long userId;
    private String organizerName;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private AuctionStatus eventStatus;
    private BigDecimal entryFee;
    @Enumerated(EnumType.STRING)
    private AuthorizationAccess authorizationAccess;
    @Enumerated(EnumType.STRING)
    private AuctionType auctionType;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ElementCollection
    private List<Long> productListIds;
}
