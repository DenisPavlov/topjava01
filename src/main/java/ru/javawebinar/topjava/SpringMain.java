package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);

            System.out.println("Вся еда");
            System.out.println(mealRestController.getAll());

            System.out.println("Добавил новую");
            Meal meal = new Meal(LocalDateTime.of(2017, Month.MAY, 30, 10, 0), "Завтрак", 999, AuthorizedUser.id());
            mealRestController.create(meal);
            List<Meal> meals = mealRestController.getAll();
            for (Meal meal1 : meals) {
                System.out.println(meal1);
            }

            System.out.println("Получил новую");
            System.out.println(mealRestController.get(7));

            meal = new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), 333, AuthorizedUser.id());
            System.out.println("Обновил одну еду");
            mealRestController.update(meal);
            System.out.println(mealRestController.getAll());

            //создать еду пользователю с id = 2
//            Meal meal2 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 999, 2);
//            mealRestController.create(meal2);
        }
    }
}
