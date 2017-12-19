package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class JspMealController {

    @Autowired
    private MealService service;

    @PostMapping("/meals")
    public String setMeal(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                checkNew(meal);
                service.create(meal, AuthorizedUser.id());
            } else {
                assureIdConsistent(meal, Integer.valueOf(request.getParameter("id")));
                service.update(meal, AuthorizedUser.id());
            }
            return "redirect:meals";
        } else if ("filter".equals(action)) {
            LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

            List<Meal> mealsDateFiltered = service.getBetweenDates(
                    startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                    endDate != null ? endDate : DateTimeUtil.MAX_DATE, AuthorizedUser.id());

            model.addAttribute("meals", MealsUtil.getFilteredWithExceeded(mealsDateFiltered,
                    startTime != null ? startTime : LocalTime.MIN,
                    endTime != null ? endTime : LocalTime.MAX,
                    AuthorizedUser.getCaloriesPerDay()
            ));

            return "/meals";
        }
        return "/meals";
    }

    @GetMapping("/meals")
    public String meals(Model model) {
        int userId = AuthorizedUser.id();
        List<MealWithExceed> meals = MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
        model.addAttribute("meals", meals);
        return "/meals";
    }

    @GetMapping(value = "/meals", params = "action=delete")
    public String delete(Model model, HttpServletRequest request){
        int id = getId(request);
        service.delete(id, AuthorizedUser.id());
        return meals(model);
    }

    @GetMapping(value = "/meals", params = "action=update")
    public String update(Model model, HttpServletRequest request){
        final Meal meal = service.get(getId(request), AuthorizedUser.id());
        model.addAttribute("meal", meal);
        return "/mealForm";
    }

    @GetMapping(value = "/meals", params = "action=create")
    public String create(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "/mealForm";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
