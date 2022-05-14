package com.delhipolice.mediclaim.constants;

public enum ClaimType {
    EMERGENCY("Emergency"),
    REFERRAL("Referral"),
    CREDIT("Credit"),
    PERMISSION("Permission"),
    GOVT("Govt.");

    private String enumValue;

    ClaimType(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumValue() {
        return enumValue;
    }
}
