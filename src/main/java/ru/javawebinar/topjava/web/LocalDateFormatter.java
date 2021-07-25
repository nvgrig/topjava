package ru.javawebinar.topjava.web;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Component("LocalDateFormatter")
public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return StringUtils.hasLength(text) ? LocalDateTime.parse(text).toLocalDate() : null;
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.toString();
    }
}
