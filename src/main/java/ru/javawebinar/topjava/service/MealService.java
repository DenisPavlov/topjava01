package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {

    Meal create(Meal meal, int userId);

    void delete(int id, int userId);

    // null if not found
    Meal get(int id, int userId);

    // null if not found
    List<Meal> getAll(int userId);

    void update(Meal meal, int userId);
}