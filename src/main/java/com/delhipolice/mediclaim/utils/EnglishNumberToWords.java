package com.delhipolice.mediclaim.utils;

import java.text.DecimalFormat;
import java.util.Arrays;

public class EnglishNumberToWords {
    private static final String[] tensNames = {
            "",
            " ten",
            " twenty",
            " thirty",
            " forty",
            " fifty",
            " sixty",
            " seventy",
            " eighty",
            " ninety"
    };
    private static final String[] numNames = {
            "",
            " one",
            " two",
            " three",
            " four",
            " five",
            " six",
            " seven",
            " eight",
            " nine",
            " ten",
            " eleven",
            " twelve",
            " thirteen",
            " fourteen",
            " fifteen",
            " sixteen",
            " seventeen",
            " eighteen",
            " nineteen"
    };

    private EnglishNumberToWords() {}

    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20){
            soFar = numNames[number % 100];
            number /= 100;
        }
        else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) return soFar;
        return numNames[number] + " hundred" + soFar;
    }

    public static String convert(Double amount) {
        int number = amount.intValue();
        int paisa = (int) ((amount - (double) number) * 100);

        if (number == 0) { return "zero"; }

        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        String snumber = df.format(number);

        int crores = Integer.parseInt(snumber.substring(0,5));
        int lakhs = Integer.parseInt(snumber.substring(5,7));
        int thousands = Integer.parseInt(snumber.substring(7,9));
        int hundreds = Integer.parseInt(snumber.substring(9,12));

        String tradCrores = convertLessThanOneThousand(crores) + " crore ";
        String tradLakhs = convertLessThanOneThousand(lakhs) + " lakh ";
        String tradThousands = convertLessThanOneThousand(thousands) + " thousand ";
        String tradHundreds = convertLessThanOneThousand(hundreds);

        String result = "";

        if (crores != 0)
            result += tradCrores;
        if (lakhs != 0)
            result += tradLakhs;
        if (thousands != 0)
            result += tradThousands;
        if (hundreds != 0)
            result += tradHundreds;

        String value = result.trim();
        if(paisa != 0)
            value += " and" + convertLessThanOneThousand(paisa) + " paise";

        return "Rs. "  + toTitleCase(value) + " only/-";
    }

    public static String toTitleCase(String givenString) {
        String[] arr = givenString.toLowerCase().split(" ");
        arr = Arrays.stream(arr)
                .filter(str -> !str.isEmpty())
                .toArray(String[]::new);

        StringBuilder sb = new StringBuilder();

        for (String s : arr) {
            sb.append(Character.toUpperCase(s.charAt(0)))
                    .append(s.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
