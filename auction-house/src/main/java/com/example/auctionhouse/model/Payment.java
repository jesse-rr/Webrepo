package com.example.auctionhouse.model;

import com.example.auctionhouse.model.embedded.BaseEntity;
import com.example.auctionhouse.model.embedded.PaymentPlan;
import com.example.auctionhouse.model.embedded.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Entity(name = "payment")
@Table(name = "payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentUUID;

    private String description;
    private BigDecimal finalAmount;
    private BigDecimal initialAmount;
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private PaymentPlan paymentPlan;
    @ElementCollection
    private List<Double> discounts;

    @ManyToOne
    @JoinColumn(name = "account_payments")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "bid_payments")
    private Bid bid;
}
