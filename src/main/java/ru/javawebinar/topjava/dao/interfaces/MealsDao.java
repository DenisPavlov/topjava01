package ru.javawebinar.topjava.dao.interfaces;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsDao {
    List<Meal> getAll();
    Meal create(Meal meal);
    boolean delete(int id);
    Meal getMealById(int id);
    void update(Meal meal);
}
