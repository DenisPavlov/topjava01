package ru.javawebinar.topjava.dao.interfaces;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsDao {
    List<Meal> getMeals();

    void addMeal(Meal meal);

    void deleteMeal(int mealId);

    Meal getMealById(int mealId);

    void updateMeal(Meal meal);
}
