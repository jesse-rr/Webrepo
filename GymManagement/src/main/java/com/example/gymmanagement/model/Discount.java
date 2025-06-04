package com.example.gymmanagement.model;

import com.example.gymmanagement.model.enums.DiscountType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "discounts")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;

    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 512)
    private String description;
    @Column(nullable = false, precision = 10, scale = 2)
    private double value;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private DiscountType type;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    private Integer maxUses;
    @Column(nullable = false)
    private boolean isActive = true;

    @OneToOne(mappedBy = "discount")
    private Payment payment;
}
