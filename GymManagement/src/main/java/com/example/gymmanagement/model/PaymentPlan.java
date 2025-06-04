package com.example.gymmanagement.model;

import com.example.gymmanagement.model.enums.BaseEntity;
import com.example.gymmanagement.model.enums.PaymentFrequency;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "payment_plans")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentPlan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @Column(nullable = false, unique = true, length = 50)
    private String name;
    @Column(nullable = false, length = 512)
    private String description;
    @Positive @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private PaymentFrequency frequency;
    @ElementCollection // null = everytime
    private List<String> accessHours;

    @OneToMany(mappedBy = "plan")
    private Set<User> subscribedUsers;

    @OneToOne(mappedBy = "plan")
    private Payment payment;

}
