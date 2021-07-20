package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends AbstractMealController{

    @GetMapping({"/get/{id}","/get"})
    public String get(@PathVariable(required = false) Integer id, Model model) {
        int userId = SecurityUtil.authUserId();
        final Meal meal = (id == null) ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                service.get(id, userId);
        log.info("get meal {} for user {}", id, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/meals")
    public String getAll(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        model.addAttribute("meals",
                MealsUtil.getTos(service.getAll(userId),
                        SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(HttpServletRequest request) {
        String id = Objects.requireNonNull(request.getParameter("id"));
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (id.isEmpty()) {
            log.info("create {} for user {}", meal, userId);
            service.create(meal, userId);
        } else {
            meal.setId(Integer.parseInt(id));
            log.info("update {} for user {}", meal, userId);
            service.update(meal, userId);
        }
        return "redirect:/meals";
    }

    @GetMapping("/getBetween")
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        model.addAttribute("meals", MealsUtil.getFilteredTos(mealsDateFiltered,
                SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }
}
