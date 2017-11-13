package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureUserIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal){
        log.info("create {}", meal);
        checkNew(meal);
//        assureUserIdConsistent(meal, AuthorizedUser.id());
        return service.create(meal, AuthorizedUser.id());
    }

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll(AuthorizedUser.id());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, AuthorizedUser.id());
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal, AuthorizedUser.id());
        assureUserIdConsistent(meal, AuthorizedUser.id());
        service.update(meal, AuthorizedUser.id());
    }

    public List<MealWithExceed> getMealWithExceeds() {
        log.info("getMealWithExceeds");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), MealsUtil.DEFAULT_CALORIES_PER_DAY);

    }

    public List<MealWithExceed> getFilteredMealWithExceeds(String startDate, String endDate, String startTime, String endTime) {
        log.info("getFiltererWithExceedForDateTime");
        LocalDate startВ = startDate.equals("") ? LocalDate.MIN : LocalDate.parse(startDate);
        LocalDate endD = endDate.equals("") ? LocalDate.MAX : LocalDate.parse(endDate);
        LocalTime startT = startTime.equals("") ? LocalTime.MIN : LocalTime.parse(startTime);
        LocalTime endT = endTime.equals("") ? LocalTime.MAX : LocalTime.parse(endTime);
        return MealsUtil.getFiltererWithExceedForDateTime(getMealWithExceeds(), startВ, endD, startT, endT);
    }

}