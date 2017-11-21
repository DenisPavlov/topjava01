package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestDate.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(USER_MEAL_ID, USER_ID);
        assertMatch(meal, USER_MEAL);
    }

    @Test
    public void delete() throws Exception {
        // TODO: 21.11.17 Реализовать метод
    }

    @Test
    public void getBetweenDates() throws Exception {
        // TODO: 21.11.17 Реализовать метод
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        // TODO: 21.11.17 Реализовать метод
    }

    @Test
    public void getAll() throws Exception {
        // TODO: 21.11.17 Реализовать метод
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(USER_MEAL);
        updated.setDateTime(LocalDateTime.of(2017, Month.JULY, 14, 10, 0));
        updated.setCalories(1000);
        updated.setDescription("Test user meal");
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL.getId(),USER_ID), updated);
    }

    @Test
    public void create() throws Exception {
        // TODO: 21.11.17 При сравнении исключить id
        Meal meal = new Meal(LocalDateTime.of(2017, Month.JULY, 14, 10, 0), "test create method", 1000);
        Meal created = service.create(meal, USER_ID);
        meal.setId(created.getId());
        assertMatch(service.get(created.getId(),USER_ID), meal);
    }

}