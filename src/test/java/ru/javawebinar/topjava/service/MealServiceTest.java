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

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-common.xml",
        "classpath:spring/spring-jdbc.xml",
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
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, meal1.getDateTime(), "Завтрак", 500), USER_ID));
    }

    @Test
    public void delete() {
        service.delete(meal1.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(meal1.getId(), USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteSomeoneElseMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(meal1.getId(), ADMIN_ID));
    }

    @Test
    public void get() {
        Meal meal = service.get(meal1.getId(), USER_ID);
        assertMatch(meal, meal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getSomeoneElseMeal() {
        assertThrows(NotFoundException.class, () -> service.get(meal1.getId(), ADMIN_ID));
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(meal1.getId(), USER_ID), MealTestData.getUpdated());
    }

    @Test
    public void updateNotFound() {
        Meal updated = MealTestData.getUpdated();
        updated.setId(NOT_FOUND);
        assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void updateSomeoneElseMeal() {
        assertThrows(NotFoundException.class, () -> service.update(new Meal(adminMeal), USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate date = LocalDate.of(2020, 1, 30);
        assertMatch(service.getBetweenInclusive(date, date, USER_ID),
                getAllUserMealFilteredByDate());
    }

    @Test
    public void getBetweenEmptyDates() {
        LocalDate startDate = LocalDate.of(1, 1, 1);
        LocalDate endDate = LocalDate.of(3000, 1, 1);
        assertMatch(service.getBetweenInclusive(startDate, endDate, USER_ID),
                getAllUserMeal());
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), getAllUserMeal());
    }
}