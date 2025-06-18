package com.project.inventorymanagement.model.helper;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Column(nullable = false)
    private String zipCode;
    private String country;
    private String city;
    private String street;
    private String extra;
}
