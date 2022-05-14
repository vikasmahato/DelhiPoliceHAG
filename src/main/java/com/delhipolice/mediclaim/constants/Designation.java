package com.delhipolice.mediclaim.constants;

public enum Designation {
    COOK("Cook"),
    MTS("MTS"),
    CONSTABLE("Const."),
    W_CONSTABLE("W/Const."),
    HEAD_CONSTABLE("Head Const."),
    W_HEAD_CONSTABLE("W/Head Const."),
    ASST_SI("Asst. Sub-Ins."),
    W_ASST_SI("W/Asst. Sub-Ins."),
    SI("Sub-Inspector"),
    W_SI("W/Sub-Ins."),
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
