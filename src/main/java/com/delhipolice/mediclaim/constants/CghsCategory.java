package com.delhipolice.mediclaim.constants;

public enum CghsCategory {
    GENERAL("General"),
    PVT("Private."),
    SEMI_PVT("Semi Private");

    private String enumValue;

    CghsCategory(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumValue() {
        return enumValue;
    }
}
