package com.example.auctionhouse.model;

import com.example.auctionhouse.model.embedded.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity(name = "bid")
@Table(name = "bid")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bid extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidId;

    private BigDecimal totalAmount;
    private int paymentQuantities;

    @OneToMany(mappedBy = "bid", fetch = FetchType.EAGER)
    private List<Payment> payments;
    @ManyToOne
    @JoinColumn(name = "account_bid")
    private Account account;
}
