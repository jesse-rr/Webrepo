package com.example.eventhorizon.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity(name = "discount")
@Table(name = "discount")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Discount {

    @Id
    private String discountId;

    @Column(nullable = false)
    private String externalId;

    private boolean isActive;
    private String discountType;
    private BigDecimal value;
    private BigDecimal minOrderAmount;
    // defined by provider
    private Instant startDate;
    private Instant endDate;
    private Integer maxUses;
    private Integer currentUses;


    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modifiedAt;


    @ManyToOne
    @JoinColumn(name = "provider_discounts")
    private ExternalProvider provider;

    public void setCurrentUses(Integer currentUses) {
        this.currentUses = (currentUses == null) ? 0 : currentUses + 1;

    }
}
