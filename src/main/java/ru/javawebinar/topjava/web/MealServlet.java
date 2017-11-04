package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Meal> meals = MealsUtil.getMeals();
        List<MealWithExceed> mealWithExceedList = MealsUtil.getMealWithExceeded(meals, 2000);
        request.setAttribute("mealWithExceedList", mealWithExceedList);
        request.setAttribute("dateTimeFormat", MealsUtil.formatter);

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
