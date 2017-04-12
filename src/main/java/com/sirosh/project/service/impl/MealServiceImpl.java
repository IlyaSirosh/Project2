package com.sirosh.project.service.impl;

import com.sirosh.project.dao.DishDao;
import com.sirosh.project.dao.MealDao;
import com.sirosh.project.entity.Dish;
import com.sirosh.project.entity.Meal;
import com.sirosh.project.entity.Nutrients;
import com.sirosh.project.entity.User;
import com.sirosh.project.pojo.Amount;
import com.sirosh.project.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 4/12/17.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealDao mealDao;

    public Meal add(Meal meal) {
        if(meal.getDishes()==null||meal.getUser()==null
                || meal.getDate()==null||meal.getTime()==null)
            throw new IllegalArgumentException();

        return mealDao.addMeal(meal);
    }

    public void update(Meal meal) {
        if(meal.getId()==0)
            throw new IllegalArgumentException();
        mealDao.saveMeal(meal);
    }

    public void delete(Meal meal) {
        mealDao.deleteMeal(meal);
    }

    public List<Meal> get(User user) {
        return mealDao.getAllMealsByUser(user);
    }

    public List<Meal> get(User user, Pageable page) {
        return mealDao.getAllMealsByUser(user,page);
    }

    public List<Meal> getByDate(User user, Date date) {
        return mealDao.getMealsByUserAndDate(user, date);
    }

    public List<Meal> getByPeriod(User user, Date from, Date to) {
        return mealDao.getMealsByUserAndDate(user, from, to);
    }

    public Nutrients getNutrientsByDate(User user, Date date) {
        return mealDao.getNutrientsByUserAndDate(user, date);
    }

    public Nutrients getNutrientsByMeal(Meal meal) {
        return mealDao.getNutrientsByMeal(meal);
    }

    public Meal getMealByDateTime(User user, Date date, Time time) {
        return mealDao.getMealByUserDateTime(user, date, time);
    }

    public Map<Dish, Amount> getDishes(Meal meal) {
        return mealDao.getDishesByMeal(meal);
    }
}
