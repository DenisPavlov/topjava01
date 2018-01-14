package ru.javawebinar.topjava.to;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MealTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public MealTo() {
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime + '\'' +
                ", description" + description + '\'' +
                ", calories" + calories + '\'' +
                "}";
    }
}
