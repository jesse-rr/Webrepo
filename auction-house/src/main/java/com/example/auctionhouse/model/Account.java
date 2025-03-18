package com.example.auctionhouse.model;

import com.example.auctionhouse.model.embedded.AccountType;
import com.example.auctionhouse.model.embedded.Address;
import com.example.auctionhouse.model.embedded.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "account")
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String profileImage;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(columnDefinition = "TEXT")
    private String configs;
    @Embedded
    private Address address;
    @Column(insertable = false)
    private LocalDateTime deletionTimestamp;

    @OneToOne
    @JoinColumn(name = "user_account")
    private User user;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Payment> paymentList;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Bid> bidList;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Product> productList;
}
