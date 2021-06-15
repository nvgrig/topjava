package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalDateTime ldt, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return (ldt.toLocalDate().compareTo(startDateTime.toLocalDate()) >= 0
                && ldt.toLocalDate().compareTo(endDateTime.toLocalDate()) <= 0)
                && (ldt.toLocalTime().compareTo(startDateTime.toLocalTime()) >= 0
                && ldt.toLocalTime().compareTo(endDateTime.toLocalTime()) < 0);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

