package com.example.gymmanagement.model.enums;

public enum PaymentFrequency {
    MONTHLY(1),
    QUARTERLY(3),
    BIANNUAL(6),
    ANNUAL(12),
    CUSTOM(-1);

    private final int defaultMonths;

    PaymentFrequency(int defaultMonths) {
        this.defaultMonths = defaultMonths;
    }

    public int getDefaultMonths() {
        return this.defaultMonths;
    }

    public static boolean isValidCustomPeriod(PaymentFrequency frequency, Integer customMonths) {
        return frequency == PaymentFrequency.CUSTOM && customMonths > 0;
    }
}
