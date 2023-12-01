package com.erocha.freeman.config.spring.formatter;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {

    @Override
    public LocalDate parse(String text, Locale locale) {
        return LocalDate.from(DateTimeFormatter.ISO_DATE.parse(text));
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        return DateTimeFormatter.ISO_DATE.format(date);
    }
}
