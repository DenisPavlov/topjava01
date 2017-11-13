package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        // TODO: 13.11.17 Изменить при работе с БД.
        MealsUtil.MEALS.forEach(meal -> save(meal, 2));
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.getUserId() != userId) return null;
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = get(id, userId);
        return meal != null && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal result = repository.get(id);
        return result == null || result.getUserId() != userId ? null : result;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> result = repository.values().stream().
                filter(meal -> meal.getUserId() == userId).
                sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime())).
                collect(Collectors.toList());
        return result.isEmpty() ? null : result;
    }
}

