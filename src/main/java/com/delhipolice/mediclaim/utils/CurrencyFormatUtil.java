package com.delhipolice.mediclaim.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

@Component
public class CurrencyFormatUtil {

    private String insertCommas(String number) {
        int indexOfDecimal = number.indexOf(".");

        String decimalPart = number.substring(indexOfDecimal);
        String numericPartReversed =  new StringBuilder(number.substring(0, indexOfDecimal)).reverse().toString();

        if(numericPartReversed.length() > 3) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < numericPartReversed.length(); i++) {
                if (i == 3) {
                    result.append(",");
                }
                if(i > 3 && (i-3) % 2 == 0) {
                    result.append(",");
                }
                result.append(numericPartReversed.charAt(i));
            }
            return result.append(".sR").reverse().append(decimalPart).append("/-").toString();
        }


        return "Rs." + number + "/-";
    }
    public String formatCurrencyInr(double amount) {
        String number = new DecimalFormat("#############.00").format(amount);
        return insertCommas(number);
    }

    public String formatCurrencyInr(String amount) {
        return insertCommas(amount);
    }

    public String formatCurrencyInr(BigDecimal amount) {
        return formatCurrencyInr(amount == null ? 0 : amount.doubleValue());
    }
}
