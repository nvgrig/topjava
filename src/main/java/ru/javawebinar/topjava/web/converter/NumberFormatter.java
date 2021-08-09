package ru.javawebinar.topjava.web.converter;

import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Locale;

public class NumberFormatter implements Formatter<Integer> {

    @Override
    public @Nullable
    Integer parse(@Nullable String str, Locale locale) throws ParseException {
        return StringUtils.hasLength(str) ? Integer.parseInt(str) : null;
    }

    @Override
    public String print(Integer number, Locale locale) {
        return number.toString();
    }
}
