package com.sirosh.project.dao;

import com.sirosh.project.entity.Dish;
import com.sirosh.project.entity.DishType;

import java.util.List;

/**
 * Created by Illya on 4/10/17.
 */
public interface DishTypeDao {


    List<DishType> getAllDishTypes();

    void addDishType(DishType type);

    void saveDishType(DishType type);

    void deleteDishType(DishType type);

    List<String> getDishTypeNames();
    List<String> getDishTypeNamesWithPrefix(String namePrefix);

    DishType getDishTypeById(Integer id);
    DishType getDishTypeByName(String name);
    List<DishType> getDishTypesByNames(List<String> names);
    List<DishType> getDishTypesByDish(Dish dish);
    List<Integer> getDishTypeIdsByDish(Dish dish);


    Boolean isNameExists(String name);

}
