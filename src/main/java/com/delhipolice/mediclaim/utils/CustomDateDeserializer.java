package com.delhipolice.mediclaim.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CustomDateDeserializer extends JsonDeserializer<Date> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomDateDeserializer.class);
    private static final List<String> FORMATS = Arrays.asList("dd-MM-yyyy", "yyyy-MM-dd", "MM/dd/yyyy");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String dateAsString = jsonParser.getText();

        for (String format : FORMATS) {
            try {
                return new SimpleDateFormat(format).parse(dateAsString);
            } catch (ParseException e) {
                LOGGER.error("Failed to parse date: " + dateAsString + " with format: " + format, e);
                // Ignore and try next format
            }
        }

        throw new RuntimeException("Unparseable date: \"" + dateAsString + "\". Supported formats: " + String.join(", ", FORMATS));
    }
}