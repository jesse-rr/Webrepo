package com.example.vpn.models;

import lombok.Getter;

@Getter
public enum ParseType {
    JSON(".json"),
    TXT(".txt"),
    CSV(".csv");

    private final String parseValue;

    ParseType(String parseValue) {
        this.parseValue = parseValue;
    }
}
