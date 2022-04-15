package com.delhipolice.mediclaim.constants;

public enum DiaryType {

    INDIVIDUAL("Individual"),
    HOSPITAL("Hospital");

    private String enumValue;

    DiaryType(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumValue() {
        return enumValue;
    }
}
