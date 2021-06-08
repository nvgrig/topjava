package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MapMealStorage implements MealStorage {
    private static final Logger log = getLogger(MapMealStorage.class);

    private final Map<Integer, Meal> storage = new HashMap<>();

    private final AtomicInteger counter = new AtomicInteger();

    private int getId() {
        return counter.incrementAndGet();
    }

    public MapMealStorage() {
        populate();
    }

    private void populate() {
        List<Meal> meals = Arrays.asList(
                new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        int id;
        if (meal.getId()==null) {
            log.debug("save process in storage for: new meal");
            id = getId();
            storage.put(id, new Meal(id, meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        } else {
            log.debug("update process in storage for: " + meal);
            id = meal.getId();
            storage.put(id, meal);
        }
        return get(id);
    }

    @Override
    public Meal get(int id) {
        log.debug("get process in storage by id: " + id);
        return storage.get(id);
    }

    @Override
    public void delete(int id) {
        log.debug("delete process in storage by id: " + id);
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        log.debug("get all meals from storage");
        return new ArrayList<>(storage.values());
    }
}
