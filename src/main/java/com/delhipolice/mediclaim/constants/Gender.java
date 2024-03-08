package com.delhipolice.mediclaim.constants;

public enum Gender {
    MALE("Male","he", "his", "himself"),
    FEMALE("Female", "she", "her", "herself");


    private final String enumValue;
    private final String heOrShe;
    private final String hisOrHer;
    private final String himSelfOrHerself;

    Gender(String enumValue, String heOrShe, String hisOrHer, String himSelfOrHerself) {
        this.enumValue = enumValue;
        this.heOrShe = heOrShe;
        this.hisOrHer = hisOrHer;
        this.himSelfOrHerself = himSelfOrHerself;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public String getHeOrShe() {
        return heOrShe;
    }

    public String getHisOrHer() {
        return hisOrHer;
    }
    public String getHimSelfOrHerself() {
        return himSelfOrHerself;
    }
}
