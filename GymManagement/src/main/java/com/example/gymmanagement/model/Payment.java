package com.example.gymmanagement.model;

import com.example.gymmanagement.model.enums.BaseEntity;
import com.example.gymmanagement.model.enums.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentUUID;

    @Column(length = 512)
    private String description;
    @Column(nullable = false, precision = 10, scale = 2) @Positive
    private BigDecimal totalAmount;
    @Column(nullable = false) @Enumerated(EnumType.STRING)
    private PaymentType type;

    @OneToOne
    @JoinColumn(name = "plan_payment")
    private PaymentPlan plan;
    @ManyToOne
    @JoinColumn(name = "user_payment")
    private User user;
    @OneToOne
    @JoinColumn(name = "discount_payment")
    private Discount discount;
}
