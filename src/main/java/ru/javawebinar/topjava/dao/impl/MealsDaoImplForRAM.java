package ru.javawebinar.topjava.dao.impl;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.interfaces.MealsDao;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsDaoImplForRAM implements MealsDao {

    private static final Logger log = getLogger(MealsDaoImplForRAM.class);

    private CopyOnWriteArrayList<Meal> meals;

    private AtomicInteger counter;

    public MealsDaoImplForRAM() {
        if (counter==null) counter = new AtomicInteger(0);
        if (meals == null) {
            meals = new CopyOnWriteArrayList<>(Arrays.asList(
                    createNewMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500)),
                    createNewMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000)),
                    createNewMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500)),
                    createNewMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000)),
                    createNewMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500)),
                    createNewMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510))
            ));
        }
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public Meal create(Meal meal) {
        meals.add(createNewMeal(meal));
        return meal;
    }

    @Override
    public boolean delete(int id) {
        Meal meal = getMealById(id);
        return meals.remove(meal);
    }

    @Override
    public Meal getMealById(int id) {
        for (Meal meal : meals ) {
            if (meal.getId() == id) return meal;
        }
        return null;
    }

    @Override
    public void update(Meal meal) {
        Meal curMeal = getMealById(meal.getId());
        meals.set(meals.indexOf(curMeal), meal);
    }

    private Meal createNewMeal(Meal meal) {
        meal.setId(counter.incrementAndGet());
        return meal;
    }
}
