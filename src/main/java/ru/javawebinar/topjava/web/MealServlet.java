package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.storage.Storage;
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

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        log.debug("storage init");
        storage = new MapStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            List<Meal> meals = storage.getAll();
            request.setAttribute("mealsTo", MealsUtil.filteredByStreams(meals,
                    LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        Meal meal = new Meal(LocalDateTime.now(), "", 0);
        switch (action) {
            case "delete":
                storage.delete(Integer.parseInt(uuid));
                request.setAttribute("mealsTo", MealsUtil.filteredByStreams(storage.getAll(),
                        LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                return;
            case "view":
            case "edit":
                meal = storage.get(Integer.parseInt(uuid));
                break;
            case "add":
                break;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/view.jsp").forward(request, response);
    }
}
