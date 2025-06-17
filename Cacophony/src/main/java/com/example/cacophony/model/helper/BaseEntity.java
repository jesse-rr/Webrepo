package com.example.cacophony.model.helper;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
}
