DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id) VALUES
  ('2017-07-01 08:00:00', 'Завтрак user', '300', 100000),
  ('2017-07-01 12:30:00', 'Обед user', '1700', 100000),
  ('2017-07-01 18:00:00', 'Ужин user', '1500', 100000),
  ('2017-07-02 08:00:00', 'Звтрак admin', '1500', 100001),
  ('2017-07-02 12:00:00', 'Обед admin', '1500', 100001),
  ('2017-07-02 18:00:00', 'Ужин admin', '1500', 100001)
