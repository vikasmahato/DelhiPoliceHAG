package com.delhipolice.mediclaim.constants;

public enum DiaryType {
    HOSPITAL("Hospital"),
    INDIVIDUAL("Individual");

    private String enumValue;

    DiaryType(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumValue() {
        return enumValue;
    }
}
