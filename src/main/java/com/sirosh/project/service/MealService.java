package com.sirosh.project.service;

import com.sirosh.project.model.Meal;
import com.sirosh.project.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Illya on 14.02.17.
 */
public interface MealService {


    Meal getById(int id);
    List<Meal> getByUser(User user);
    List<Meal> getByUserAndDate(User user,Date date);

    void add(Meal meal);
    void update(Meal meal);

}
