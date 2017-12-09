package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static javafx.scene.input.KeyCode.T;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    Meal save(Meal meal);

    Optional<Meal> findByIdAndUserId(Integer id, Integer userId);

    List<Meal> findAllByUserId(Integer userId, Sort sort);

    @Transactional
    @Modifying
    int deleteByIdAndUserId(Integer id, Integer userId);

    List<Meal> findAllByUserIdAndDateTimeBetween(Integer userId, LocalDateTime startDate, LocalDateTime endDate, Sort sort);
}
