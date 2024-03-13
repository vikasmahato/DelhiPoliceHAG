package com.delhipolice.mediclaim.constants;

public enum Relation {
    SELF("Self", ""),
    WIFE("Wife", "W/o"),
    HUSBAND("Husband", "H/o"),
    MOTHER("Mother", "M/o"),
    FATHER("Father", "F/o"),
    MOTHER_IN_LAW("Mother In Law", "Mother-in-law of"),
    FATHER_IN_LAW("Father In Law", "Father-in-law of"),
    BROTHER("Brother", "Brother of"),
    SISTER("Sister", "Sister of"),
    SON("Son", "S/o"),
    DAUGHTER("Daughter", "D/o");


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
