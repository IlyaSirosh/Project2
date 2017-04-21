package com.sirosh.project.dao;

import com.sirosh.project.entity.*;
import com.sirosh.project.pojo.Amount;
import com.sirosh.project.pojo.Pageable;


import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 4/9/17.
 */
public interface DishDao {

    void addDish(Dish dish);

    void saveDish(Dish dish);

    void deleteDish(Dish dish);

    Dish getDishByName(String name);
    Dish getDishById(Integer id);
    List<Dish> getDishByName(List<String> name);
    List<String> getDishNamesWithPrefix(String namePrefix);

    List<Dish> getAllDishes(Pageable page);
    List<Dish> getDishesByAuthor(User author,Pageable page);
    List<Dish> getDishesByTypes(List<String> types,Pageable page);
    List<Dish> getDishesByIngredients(List<String> ingredients,Pageable page);
    List<Dish> getDishesByIngredientTypes(List<String> types,Pageable page);

    List<Dish> getDishesByTypesExact(List<String> types,Pageable page);
    List<Dish> getDishesByIngredientsExact(List<String> ingredients,Pageable page);
    List<Dish> getDishesByIngredientTypesExact(List<String> types,Pageable page);

    List<Dish> getDishesByTypesAndIngredients(List<String> types,List<String> ingredients,Pageable page);

//    List<DishVariety> getDishVarieties(Dish dish,Pageable page);
//    int getDishVarietiesCount(Dish dish);

    int getAllDishesCount();
    int getDishesByAuthorCount(User author);
    int getDishesByTypesCount(List<String> types);
    int getDishesByIngredientsCount(List<String> ingredients);
//    int getDishesByIngredientTypesCount(List<IngredientType> types);


    Boolean isNameExists(String name);
}
