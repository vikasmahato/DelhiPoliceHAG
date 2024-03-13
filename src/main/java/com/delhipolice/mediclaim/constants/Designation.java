package com.delhipolice.mediclaim.constants;

public enum Designation {
    COOK("Cook"),
    MTS("MTS"),
    CONSTABLE("Ct."),
    W_CONSTABLE("W/Ct."),
    HEAD_CONSTABLE("HC"),
    W_HEAD_CONSTABLE("W/HC"),
    ASST_SI("ASI"),
    W_ASST_SI("W/ASI"),
    SI("SI"),
    W_SI("W/SI"),
    INSPECTOR("Inspr."),
    W_INSPECTOR("W/Inspr."),
    ACP("ACP"),
    ADCP("Addl. DCP/Crime"),
    DCP("DCP(HQ)/Crime"),
    AACP("Addl. CP/Crime"),
    JCP("Jt. CP/Crime"),
    SCP("Spl. CP/Crime");


    private String enumValue;

    Designation(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumValue() {
        return enumValue;
    }

}
