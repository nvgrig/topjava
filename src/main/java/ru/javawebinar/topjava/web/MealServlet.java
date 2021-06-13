package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.InMemoryMealStorage;
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
        mealStorage = new InMemoryMealStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = request.getParameter("mealId").isEmpty() ? null : Integer.parseInt(request.getParameter("mealId"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(id, dateTime, description, calories);
        log.debug("saving");
        mealStorage.save(meal);
        List<Meal> meals = mealStorage.getAll();
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        String id = request.getParameter("mealId");
        Meal meal;
        switch (action) {
            case "update":
                log.debug("updating meal");
                meal = mealStorage.get(Integer.parseInt(id));
                break;
            case "save":
                log.debug("saving new meal");
                meal = new Meal(null, null, "", 0);
                break;
            case "delete":
                log.debug("deleting meal");
                mealStorage.delete(Integer.parseInt(id));
                response.sendRedirect("meals");
                return;
            default:
                log.debug("refresh meals table");
                request.setAttribute("mealsTo", MealsUtil.filteredByStreams(mealStorage.getAll(),
                        LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                return;
        }
        log.debug("redirect to meal edit form");
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/mealEditForm.jsp").forward(request, response);
    }
}
