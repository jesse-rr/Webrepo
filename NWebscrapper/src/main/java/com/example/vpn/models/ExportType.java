package com.example.vpn.models;

import lombok.Getter;

@Getter
public enum ExportType {
    CSV(".csv"),
    JSON(".json"),
    TXT(".txt");

    private final String extensionValue;

    ExportType(String extensionValue) {
        this.extensionValue = extensionValue;
    }
}
