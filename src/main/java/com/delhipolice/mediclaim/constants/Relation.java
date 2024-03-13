package com.delhipolice.mediclaim.constants;

import static com.delhipolice.mediclaim.constants.Gender.FEMALE;
import static com.delhipolice.mediclaim.constants.Gender.MALE;

public enum Relation {
    SELF("Self", "", null),
    WIFE("Wife", "W/o", FEMALE),
    HUSBAND("Husband", "H/o", MALE),
    MOTHER("Mother", "M/o", FEMALE),
    FATHER("Father", "F/o", MALE),
    MOTHER_IN_LAW("Mother In Law", "Mother-in-law of", FEMALE),
    FATHER_IN_LAW("Father In Law", "Father-in-law of", MALE),
    BROTHER("Brother", "Brother of", MALE),
    SISTER("Sister", "Sister of", FEMALE),
    SON("Son", "S/o", MALE),
    DAUGHTER("Daughter", "D/o", FEMALE);


    private String enumValue;
    private String relation;

    private Gender gender;

    Relation(String enumValue, String relation, Gender gender) {
        this.enumValue = enumValue;
        this.relation = relation;
        this.gender = gender;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public String getRelation() {
        return relation;
    }

    public Gender getGender() {
        return gender;
    }
}
