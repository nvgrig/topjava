package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = service.create(getNew(), MEAL_USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId,MEAL_USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500), MEAL_USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_1.getId(), MEAL_USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_1.getId(), MEAL_USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_NOT_FOUND, MEAL_USER_ID));
    }

    @Test
    public void deleteSomeoneElseMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_1.getId(), MEAL_ADMIN_ID));
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL_1.getId(), MEAL_USER_ID);
        assertMatch(meal, MEAL_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_NOT_FOUND, MEAL_USER_ID));
    }

    @Test
    public void getSomeoneElseMeal() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_1.getId(), MEAL_ADMIN_ID));
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, MEAL_USER_ID);
        assertMatch(service.get(MEAL_1.getId(), MEAL_USER_ID), MealTestData.getUpdated());
    }

    @Test
    public void updateNotFound() {
        Meal updated = MealTestData.getUpdated();
        updated.setId(MEAL_NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(updated, MEAL_USER_ID));
    }

    @Test
    public void updateSomeoneElseMeal() {
        assertThrows(NotFoundException.class, () -> service.update(ADMIN_MEAL, MEAL_USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate date = LocalDate.of(2020, 1,30);
        assertMatch(service.getBetweenInclusive(date, date, MEAL_USER_ID),
                getAllUserMealFillteredByDate());
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(MEAL_USER_ID), getAllUserMeal());
    }


}