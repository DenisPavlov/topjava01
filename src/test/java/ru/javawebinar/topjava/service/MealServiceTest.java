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
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test(expected = NotFoundException.class)
    public void notFoundGet() throws Exception {
        service.get(USER_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.create(USER_MEAL, USER_ID);
        service.delete(USER_MEAL.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), USER_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(USER_MEAL_ID, ADMIN_ID);
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
        assertMatch(service.getAll(USER_ID), USER_MEALS);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(USER_MEAL);
        updated.setCalories(10000);
        updated.setDescription("Test user meal");
        service.update(updated, USER_ID);

        assertThat(service.getAll(USER_ID)).contains(USER_MEAL);
    }

    // TODO: 21.11.17 update чужую еду
    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() throws Exception {
        Meal updated = new Meal(USER_MEAL);
        updated.setCalories(10000);
        updated.setDescription("Test user meal");
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal meal = new Meal(LocalDateTime.of(2017, Month.JULY, 14, 10, 0), "test create method", 1000);
        Meal created = service.create(meal, USER_ID);
        meal.setId(created.getId());
        assertMatch(service.get(created.getId(),USER_ID), meal);
    }

}