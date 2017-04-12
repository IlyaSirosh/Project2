package com.sirosh.project.dao;

import com.sirosh.project.entity.Dish;
import com.sirosh.project.entity.Meal;
import com.sirosh.project.entity.Nutrients;
import com.sirosh.project.entity.User;
import com.sirosh.project.pojo.Amount;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 4/9/17.
 */
public interface MealDao {


    // Add

    Meal addMeal(Meal meal);

    // save

    void saveMeal(Meal meal);

    // getters
    List<Meal> getAllMealsByUser(User user);
    List<Meal> getAllMealsByUser(User user, Pageable page);

    Map<Dish,Amount> getDishesByMeal(Meal meal);

    Nutrients getNutrientsByUserAndDate(User user,Date date);
    Nutrients getNutrientsByMeal(Meal meal);

    List<Meal> getMealsByUserAndDate(User user, Date date);

    List<Meal> getMealsByUserAndDate(User user, Date from, Date to);

    Meal getMealById(Integer id);
    Meal getMealByUserDateTime(User user,Date date,Time time);

    // delete

    void deleteMeal(Meal meal);


}
