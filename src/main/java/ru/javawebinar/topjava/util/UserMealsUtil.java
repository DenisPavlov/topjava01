package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> resultList = new ArrayList<>();

        Map<LocalDate, List<UserMeal>> userMealMapPerDate = new HashMap<>();

        for (UserMeal userMeal : mealList ) {
            if (!userMealMapPerDate.containsKey(userMeal.getDateTime().toLocalDate())) {
                ArrayList<UserMeal> userMeals = new ArrayList<>();
                userMeals.add(userMeal);
                userMealMapPerDate.put(userMeal.getDateTime().toLocalDate(), userMeals);
            } else {
                userMealMapPerDate.get(userMeal.getDateTime().toLocalDate()).add(userMeal);
            }

        }

        for (Map.Entry<LocalDate, List<UserMeal>> entry : userMealMapPerDate.entrySet() ) {

            int sumCaloriesPerDate = 0;

            for (UserMeal userMeal :entry.getValue()) {
                sumCaloriesPerDate += userMeal.getCalories();
            }

            if (sumCaloriesPerDate > caloriesPerDay ) {
                for (UserMeal userMeal :entry.getValue() ) {
                    if (userMeal.getDateTime().toLocalTime().isAfter(startTime) && userMeal.getDateTime().toLocalTime().isBefore(endTime))
                        resultList.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
                }
            } else {
                for (UserMeal userMeal :entry.getValue() ) {
                    if (userMeal.getDateTime().toLocalTime().isAfter(startTime) && userMeal.getDateTime().toLocalTime().isBefore(endTime))
                        resultList.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), false));
                }
            }
        }

        return resultList;
    }
}
