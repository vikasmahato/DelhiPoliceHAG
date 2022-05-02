package com.delhipolice.mediclaim.constants;

public enum ClaimType {
    OP_EMERGENCY("OP-Emergency"),
    IP_EMERGENCY("IP-Emergency"),
    OP_REFERRAL("Referral"),
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
