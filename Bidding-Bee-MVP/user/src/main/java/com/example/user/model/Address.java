package com.example.user.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class Address {

    @Enumerated(EnumType.STRING)
    private Country country;
    private String street;
    private String number;
    private String extra;
}
