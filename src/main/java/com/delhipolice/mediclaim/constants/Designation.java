package com.delhipolice.mediclaim.constants;

public enum Designation {
    COOK("Cook"),
    MTS("MTS"),
    CONSTABLE("CI"),
    W_CONSTABLE("W/CI."),
    HEAD_CONSTABLE("HC"),
    W_HEAD_CONSTABLE("W/HC"),
    ASST_SI("ASI"),
    W_ASST_SI("W/ASI"),
    SI("SI"),
    W_SI("W/SI"),
    INSPECTOR("Ins."),
    W_INSPECTOR("W/Ins."),
    ACP("ACP"),
    ADCP("ADCP"),
    DCP("DCP");


    private String enumValue;

    Designation(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumValue() {
        return enumValue;
    }

}
