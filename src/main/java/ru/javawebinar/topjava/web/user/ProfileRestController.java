package ru.javawebinar.topjava.web.user;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
@Scope("singleton")
public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public void update(User user) {
        super.update(user, authUserId());
    }
}