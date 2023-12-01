package com.erocha.freeman.config.spring.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {

    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return LocalTime.from(DateTimeFormatter.ISO_TIME.parse(text));
    }

    @Override
    public String print(LocalTime time, Locale locale) {
        return DateTimeFormatter.ISO_TIME.format(time);
    }

}
