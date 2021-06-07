package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    void update(Meal meal);

    void save(Meal meal);

    Meal get(Integer uuid);

    void delete(Integer uuid);

    List<Meal> getAll();
}
