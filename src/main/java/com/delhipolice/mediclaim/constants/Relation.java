package com.delhipolice.mediclaim.constants;

public enum Relation {
    SELF("Self", ""),
    WIFE("Wife", "W/O"),
    HUSBAND("Husband", "H/O"),
    MOTHER("Mother", "M/O"),
    FATHER("Father", "F/O"),
    SON("Son", "S/O"),
    DAUGHTER("Daughter", "D/O");


    private String enumValue;
    private String relation;

    Relation(String enumValue, String relation) {
        this.enumValue = enumValue;
        this.relation = relation;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public String getRelation() {
        return relation;
    }
}
