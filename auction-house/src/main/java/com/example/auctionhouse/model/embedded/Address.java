package com.example.auctionhouse.model.embedded;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    @Enumerated(EnumType.STRING)
    private Country addr_country;
    private String addr_street;
    private int addr_number;
    private String addr_extra;
}
