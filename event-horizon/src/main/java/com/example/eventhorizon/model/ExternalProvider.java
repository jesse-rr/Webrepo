package com.example.eventhorizon.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "provider")
@Table(name = "provider")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExternalProvider {

    @Id
    private String providerId;

    private String name;
    private String baseUrl;
    @Enumerated(EnumType.STRING)
    private ProviderStatus status;
    @Embedded
    private SecurityConfigs securityConfigs;


    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modifiedAt;


    @OneToMany(mappedBy = "provider", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Discount> discounts;
}
