package com.sirosh.project.dao;

import com.sirosh.project.entity.Dish;
import com.sirosh.project.entity.Ingredient;
import com.sirosh.project.entity.IngredientType;

import java.util.List;

/**
 * Created by Illya on 4/10/17.
 */
public interface IngredientTypeDao {


    void addIngredientType(IngredientType type);
    void saveIngredientType(IngredientType type);
    void deleteIngredientType(IngredientType type);

    List<String>  getIngredientTypesNameWithPrefix(String namePrefix);
    List<String>  getIngredientTypesName();

    IngredientType getIngredientTypeById(Integer name);
    IngredientType getIngredientTypeByName(String name);
    List<IngredientType> getIngredientTypesByNames(List<String> names);
    List<IngredientType> getIngredientTypesByIngredient(Ingredient ingredient);
    List<IngredientType> getIngredientTypesByIngredient(String ingredient);
    List<IngredientType> getIngredientTypesByIngredient(List<Integer> ingredientIds);
    List<IngredientType> getIngredientTypesByDish(Dish dish);

    Boolean isNameExists(String name);
}
