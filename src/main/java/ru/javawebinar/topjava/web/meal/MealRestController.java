package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
@Scope("singleton")
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll(authUserId());
    }

    public List<MealTo> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getFiltered");
        return MealsUtil.getFilteredTos(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY,
                getStartDateTime(startDate, startTime), getEndDateTime(endDate, endTime));
    }

    private static LocalDateTime getStartDateTime(LocalDate startDate, LocalTime startTime) {
        return (startDate == null ? LocalDate.MIN : startDate)
                .atTime(startTime == null ? LocalTime.MIN : startTime);
    }

    private static LocalDateTime getEndDateTime(LocalDate endDate, LocalTime endTime) {
        return (endDate == null ? LocalDate.MAX : endDate)
                .atTime(endTime == null ? LocalTime.MAX : endTime);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal, meal.getId());
        assureIdConsistent(meal, meal.getId());
        service.update(meal, authUserId());
    }
}