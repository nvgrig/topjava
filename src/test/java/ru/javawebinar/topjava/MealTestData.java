package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static int mealId = START_SEQ + 2;
    public static final int MEAL_USER_ID = 100000;
    public static final int MEAL_ADMIN_ID = 100001;
    public static final int MEAL_NOT_FOUND = 10;

    public static final Meal MEAL_1 = new Meal(getMealId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(getMealId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(getMealId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(getMealId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL_5 = new Meal(getMealId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_6 = new Meal(getMealId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_7 = new Meal(getMealId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal ADMIN_MEAL = new Meal(getMealId(), LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);

    private static int getMealId() {
        return mealId++;
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "New meal", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_1);
        updated.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        updated.setDescription("Updated description");
        updated.setCalories(9999);
        return updated;
    }

    public static List<Meal> getAllUserMeal() {
        return Stream.of(MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6, MEAL_7)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public static List<Meal> getAllUserMealFillteredByDate() {
        return Stream.of(MEAL_1, MEAL_2, MEAL_3)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
