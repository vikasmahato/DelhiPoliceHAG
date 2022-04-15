package com.delhipolice.mediclaim.constants;

public enum Designation {
    COOK("Cook"),
    MTS("MTS"),
    CONSTABLE("Constable"),
    W_CONSTABLE("W/Constable"),
    HEAD_CONSTABLE("Head Constable"),
    W_HEAD_CONSTABLE("W/Head Constable"),
    ASST_SI("Assistant Sub-Inspector"),
    W_ASST_SI("W/Assistant Sub-Inspector"),
    SI("Sub-Inspector"),
    W_SI("W/Sub-Inspector"),
    INSPECTOR("Inspector"),
    W_INSPECTOR("W/Inspector"),
    ACP("Assistant Commissioner of Police"),
    ADCP("Additional Deputy Commissioner of Police"),
    DCP("Deputy Commissioner of Police");


    private String enumValue;

    Designation(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumValue() {
        return enumValue;
    }

}
