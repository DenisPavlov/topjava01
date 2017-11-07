package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.impl.MealsDaoImplForRAM;
import ru.javawebinar.topjava.dao.interfaces.MealsDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private MealsDao mealsDao;

    @Override
    public void init() throws ServletException {
        super.init();
        this.mealsDao = new MealsDaoImplForRAM();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        Meal meal = new Meal(dateTime, request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));

        String idParameter = request.getParameter("mealId");
        if (idParameter != null && !idParameter.isEmpty()) {
            meal.setId(Integer.parseInt(idParameter));
            mealsDao.update(meal);
        } else {
            mealsDao.create(meal);
        }

        toListMeal(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null ){
            toListMeal(request, response);
        } else if ( action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealsDao.delete(mealId);
            response.sendRedirect("meals");
        } else if (action.equalsIgnoreCase("edit")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealsDao.getMealById(mealId);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/addmeal.jsp").forward(request, response);
        }


    }

    private void toListMeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Meal> meals = mealsDao.getAll();
        List<MealWithExceed> mealWithExceedList = MealsUtil.getWithExceeded(meals, 2000);
        request.setAttribute("mealWithExceedList", mealWithExceedList);
        request.setAttribute("dateTimeFormat", formatter);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
