package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealStorage mealStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        log.debug("storage init");
        mealStorage = new MapMealStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("mealId");
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        Meal meal = new Meal((id.equals("")?null:Integer.parseInt(id)), LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        log.debug("saving... " + meal);
        mealStorage.save(meal);
        List<Meal> meals = mealStorage.getAll();
        log.debug("refresh meals table");
        request.setAttribute("mealsTo", MealsUtil.filteredByStreams(meals,
                LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        if (action == null) {
            List<Meal> meals = mealStorage.getAll();
            log.debug("refresh meals table");
            request.setAttribute("mealsTo", MealsUtil.filteredByStreams(meals,
                    LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        String id = request.getParameter("mealId");
        Meal meal = null;
        switch (action) {
            case "delete":
                log.debug("deleting meal by id:" + id);
                mealStorage.delete(Integer.parseInt(id));
                log.debug("refresh meals table");
                request.setAttribute("mealsTo", MealsUtil.filteredByStreams(mealStorage.getAll(),
                        LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                return;
            case "update":
                log.debug("updating meal by id:" + id);
                meal = mealStorage.get(Integer.parseInt(id));
                break;
            case "save":
                log.debug("saving new meal");
                meal = new Meal(null, null, "", 0);
                break;
        }
        log.debug("redirect to meal edit form");
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/mealEditForm.jsp").forward(request, response);
    }
}
