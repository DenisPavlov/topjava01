package ru.javawebinar.topjava;

import org.assertj.core.util.IterableUtil;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestDate {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ+1;
    public static final int USER_MEAL_ID = START_SEQ + 2;

    public static final Meal USER_MEAL = new Meal(USER_MEAL_ID, LocalDateTime.of(2017, Month.JULY, 1, 8, 0),"Завтрак user", 300 );
    public static final Meal USER_BETWEEN_MEAL = new Meal(USER_MEAL_ID+1, LocalDateTime.of(2017, Month.JULY, 1, 12, 30),"Обед user", 1700);

    public static final List<Meal> USER_MEALS = Arrays.asList(USER_MEAL,
            new Meal(USER_MEAL_ID+1, LocalDateTime.of(2017, Month.JULY, 1, 12, 30),"Обед user", 1700 ),
            new Meal(USER_MEAL_ID+2, LocalDateTime.of(2017, Month.JULY, 1, 18, 00),"Ужин user", 1500 ));

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).containsOnlyElementsOf(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal expected) {
        assertThat(actual).doesNotContain(expected);
    }

}
