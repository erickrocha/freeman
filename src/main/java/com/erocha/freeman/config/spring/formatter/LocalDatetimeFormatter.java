package com.erocha.freeman.config.spring.formatter;

import org.springframework.format.Formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDatetimeFormatter implements Formatter<LocalDateTime> {

    @Override
    public LocalDateTime parse(String text, Locale locale) {
        return LocalDateTime.from(DateTimeFormatter.ISO_ZONED_DATE_TIME.parse(text));
    }

    @Override
    public String print(LocalDateTime date, Locale locale) {
        return DateTimeFormatter.ISO_ZONED_DATE_TIME.format(date);
    }

}
