package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MapStorage implements Storage{
    private final ConcurrentHashMap<Integer, Meal> storage = new ConcurrentHashMap<>();

    public MapStorage() {
        populate();
    }

    @Override
    public void update(Meal meal) {
        storage.replace(meal.getUuid(), meal);
    }

    @Override
    public void save(Meal meal) {
        storage.put(meal.getUuid(), meal);
    }

    @Override
    public Meal get(Integer uuid) {
        return storage.get(uuid);
    }

    @Override
    public void delete(Integer uuid) {
        storage.remove(uuid);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    private void populate(){
        List<Meal> meals = MealsUtil.createListOfMeals();
        meals.forEach(meal -> storage.put(meal.getUuid(), meal));
    }
}
