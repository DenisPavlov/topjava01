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

    private static CopyOnWriteArrayList<Meal> meals;

    //счетчик
    private static AtomicInteger counter;

    public MealsDaoImplForRAM() {
        if (counter==null) counter = new AtomicInteger(0);
    }

    @Override
    public List<Meal> getMeals() {
        if (meals == null) {
            meals = new CopyOnWriteArrayList<>(Arrays.asList(
                    getNewMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                    getNewMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                    getNewMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                    getNewMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                    getNewMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                    getNewMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
            ));
        }
        return meals;
    }

    @Override
    public void addMeal(Meal meal) {
        meal = getNewMeal(meal);
        meals.add(meal);
    }

    @Override
    public void deleteMeal(int mealId) {
        Meal curMeal = null;

        for (Meal meal : meals ) {
            if (meal.getId() == mealId) {
                curMeal = meal;
                break;
            }
        }
        if (curMeal != null) meals.remove(curMeal);
    }

    @Override
    public Meal getMealById(int mealId) {
        for (Meal meal : meals ) {
            if (meal.getId() == mealId) return meal;
        }
        return null;
    }

    @Override
    public void updateMeal(Meal meal) {
        int index = 0;
        for (Meal currMeal : meals ) {
            if (currMeal.getId() == meal.getId()) {
                index = meals.indexOf(currMeal);
                break;
            }
        }
        if (index != 0) meals.set(index, meal);
    }

    //добавить одну запись и инкремент счетчика
    private Meal getNewMeal(LocalDateTime dateTime, String description, int calories) {
        Meal meal = meal = new Meal(dateTime, description, calories);
        meal.setId(counter.incrementAndGet());
        return meal;
    }

    private Meal getNewMeal(Meal meal) {
        meal.setId(counter.incrementAndGet());
        return meal;
    }
}
