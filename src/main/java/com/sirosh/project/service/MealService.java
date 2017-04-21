package com.sirosh.project.service;

import com.sirosh.project.entity.Dish;
import com.sirosh.project.entity.Meal;
import com.sirosh.project.entity.Nutrients;
import com.sirosh.project.entity.User;
import com.sirosh.project.pojo.Amount;
import com.sirosh.project.pojo.Pageable;


import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 14.02.17.
 */
public interface MealService {

    void add(Meal meal);
    void update(Meal meal);
    void delete(Meal meal);

    List<Meal> get(User user);
    List<Meal> get(User user,Pageable page);
    List<Meal> getByDate(User user,Date date);
    List<Meal> getByPeriod(User user, Date from, Date to);

    Nutrients getNutrientsByDate(User user,Date date);
    Nutrients getNutrientsByMeal(Meal meal);

    Meal getMealByDateTime(User user,Date date,Time time);

    Map<Dish,Amount> getDishes(Meal meal);

}
