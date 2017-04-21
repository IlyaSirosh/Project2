package com.sirosh.project.service;

import com.sirosh.project.entity.*;
import com.sirosh.project.pojo.Pageable;


import java.util.List;

/**
 * Created by Illya on 14.02.17.
 */
public interface DishService {

    void add(Dish dish);
    void update(Dish dish);
    void delete(Dish dish);

    Dish getByName(String name);
    Dish getById(Integer id);
    List<Dish> getByNames(List<String> name);
    List<String> getDishNamesWithPrefix(String namePrefix);

    List<Dish> getAll(Pageable page);
    List<Dish> getByAuthor(User author,Pageable page);
    List<Dish> getByDishTypes(List<String> types,Pageable page);
    List<Dish> getByIngredients(List<String> ingredients,Pageable page);
    List<Dish> getByIngredientTypes(List<String> types, Pageable page);
    List<Dish> getByDishTypesExact(List<String> types,Pageable page);
    List<Dish> getByIngredientsExact(List<String> ingredients,Pageable page);
    List<Dish> getByIngredientTypesExact(List<String> types, Pageable page);

    List<DishType> getTypes(Dish dish);
    List<Ingredient> getIngredients(Dish dish);
    List<IngredientType> getIngredientTypes(Dish dish);


    List<Dish> getByDishTypesAndIngredients(List<String> types,List<String> ingredients,Pageable page);


    Integer getAllCount();
    Integer getCountByAuthor(User author);
    Integer getCountByDishTypes(List<String> types);
    Integer getCountByIngredients(List<String> ingredients);


    Boolean isNameExists(String name);


}
