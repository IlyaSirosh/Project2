package com.sirosh.project.service;

import com.sirosh.project.model.Dish;
import com.sirosh.project.model.DishType;
import com.sirosh.project.model.User;

import java.util.List;

/**
 * Created by Illya on 14.02.17.
 */
public interface DishService {


    Dish getById(int id);
    List<Dish> getByType(DishType type);
    List<Dish> getByAuthor(User user);

    void add(Dish dish);
    void update(Dish dish);


}
