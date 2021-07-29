package ru.javawebinar.topjava.web;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

@Component
public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return StringUtils.hasLength(text) ? LocalDateTime.parse(text).toLocalTime() : null;
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object.toString();
    }
}
