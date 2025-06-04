package com.example.gymmanagement.model.enums;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@MappedSuperclass
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
    @Column(nullable = false)
    private boolean isActive = true;
    @CreatedDate @Column(nullable = false, updatable = false)
    private LocalDate createdAt;
    @LastModifiedDate @Column(insertable = false)
    private LocalDate modifiedAt;
}
