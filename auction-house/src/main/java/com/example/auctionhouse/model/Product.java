package com.example.auctionhouse.model;

import com.example.auctionhouse.model.embedded.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@Entity(name = "product")
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;
    private String description;
    private BigDecimal initialPrice;
    private BigDecimal currentPrice;
    private BigDecimal bidIncrement;
    private int quantity;
    private Currency currency;

    private LocalDateTime auctionDuration;
    private String category;
    @ElementCollection
    private List<String> tags;

    @ManyToOne
    @JoinColumn(name = "account_products")
    private Account account;
}
