package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealStorage implements MealStorage {
    private static final Logger log = getLogger(InMemoryMealStorage.class);

    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();

    private final AtomicInteger counter = new AtomicInteger();

    public InMemoryMealStorage() {
        populate();
    }

    private int getId() {
        return counter.incrementAndGet();
    }

    private void populate() {
        List<Meal> meals = Arrays.asList(
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        int id;
        Meal resultMeal;
        if (meal.getId() == null) {
            log.debug("save process in storage for: new meal");
            id = getId();
            resultMeal = new Meal(id, meal.getDateTime(), meal.getDescription(), meal.getCalories());
            storage.put(id, resultMeal);
        } else {
            log.debug("update process in storage");
            resultMeal = storage.replace(meal.getId(), meal);
        }
        return resultMeal;
    }

    @Override
    public Meal get(int id) {
        log.debug("get process in storage");
        return storage.get(id);
    }

    @Override
    public void delete(int id) {
        log.debug("delete process in storage");
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        log.debug("get all meals from storage");
        return new ArrayList<>(storage.values());
    }
}
