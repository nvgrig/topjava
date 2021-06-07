package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.IdGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal {
    private final int uuid;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.uuid = IdGenerator.getId();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal(int uuid, LocalDateTime dateTime, String description, int calories) {
        this.uuid = uuid;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public int getUuid() {
        return uuid;
    }
}
