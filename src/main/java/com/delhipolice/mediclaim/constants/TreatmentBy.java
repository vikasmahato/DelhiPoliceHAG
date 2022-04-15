package com.delhipolice.mediclaim.constants;

public enum TreatmentBy {
    SELF("Self"),
    RELATIVE("Relative");

    private String enumValue;

    TreatmentBy(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumValue() {
        return enumValue;
    }
}
