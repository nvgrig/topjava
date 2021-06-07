package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

public interface Storage {
    void update(Meal meal);

    void save(Meal meal);

    Meal get(String uuid);

    void delete(String uuid);
}
