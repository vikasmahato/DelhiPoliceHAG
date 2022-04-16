package com.delhipolice.mediclaim.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FinancialYearGenerator {
    private FinancialYearGenerator() {
    }

    public static String getActualFinancialYear(Date givenDate) {

       Calendar calendar = new GregorianCalendar();
       calendar.setTime(givenDate);

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = (calendar.get(Calendar.MONTH)+1);

        String financialYear;
        if (currentMonth<4) {
            financialYear = (currentYear-1) + "-" + currentYear;
        } else {
            financialYear = currentYear + "-" +  (currentYear+1);
        }
        return financialYear;
    }
}
