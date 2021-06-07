package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.concurrent.ConcurrentHashMap;

public class MapStorage implements Storage{
    private final ConcurrentHashMap<Integer, Meal> storage = new ConcurrentHashMap<>();

    @Override
    public void update(Meal meal) {
        storage.replace(meal.getId(), meal);
    }

    @Override
    public void save(Meal meal) {
        storage.put(meal.getId(), meal);
    }

    @Override
    public Meal get(Integer id) {
        return storage.get(id);
    }

    @Override
    public void delete(Integer id) {
        storage.remove(id);
    }
}
