package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private ConfigurableApplicationContext appCtx;

    private ProfileRestController profileRestController;

    @Override
    public void init() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        profileRestController = appCtx.getBean(ProfileRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        SecurityUtil.setAuthUserId(Integer.parseInt(userId));
        User user = profileRestController.get(SecurityUtil.authUserId());
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }
}
