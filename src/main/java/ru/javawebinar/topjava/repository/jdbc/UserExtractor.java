package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserExtractor implements ResultSetExtractor<List<User>> {
    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<User> users = new ArrayList<>();
        User user = null;
        while (rs.next()) {
            int id = Integer.parseInt(rs.getString("id"));
            String name = rs.getString("name");
            String email = rs.getString("email");
            String password = rs.getString("password");
            Date registered = rs.getDate("registered");
            boolean enabled = rs.getBoolean("enabled");
            int caloriesPerDay = rs.getInt("calories_per_day");
            Role role = Enum.valueOf(Role.class, rs.getString("role"));
            if (user == null || user.getId() != id) {
                user = new User(id, name, email, password, caloriesPerDay,
                        enabled, registered, Collections.singleton(role));
                users.add(user);
            } else {
                users.remove(user);
                Set<Role> roles = user.getRoles();
                roles.add(role);
                user.setRoles(roles);
                users.add(user);
            }
        }
        return users;
    }
}
