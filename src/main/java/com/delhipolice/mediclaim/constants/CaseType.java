package com.delhipolice.mediclaim.constants;

public enum CaseType {
    EMERGENCY("Emergency"),
    GOVT("Govt."),
    REFERRAL("Referral");

    private String enumValue;

    CaseType(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumValue() {
        return enumValue;
    }
}
