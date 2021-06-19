DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('30.01.2020 10:00', 'Завтрак', 500, 100000),
       ('30.01.2020 13:00', 'Обед', 1000, 100000),
       ('30.01.2020 20:00', 'Ужин', 500, 100000),
       ('31.01.2020 00:00', 'Еда на граничное значение', 100, 100000),
       ('31.01.2020 10:00', 'Завтрак', 1000, 100000),
       ('31.01.2020 13:00', 'Обед', 500, 100000),
       ('31.01.2020 20:00', 'Ужин', 410, 100000),
       ('01.06.2015 14:00', 'Админ ланч', 510, 100001),
       ('01.06.2015 21:00', 'Админ ужин', 1500, 100001);