package com.sirosh.project.dao;

import com.sirosh.project.entity.Dish;
import com.sirosh.project.entity.Ingredient;
import com.sirosh.project.entity.IngredientType;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Illya on 4/10/17.
 */
public interface IngredientDao {

    Ingredient addIngredient(Ingredient ingredient);

    void saveIngredient(Ingredient ingredient);
    void deleteIngredient(Ingredient ingredient);

    List<Ingredient> getAllIngredients(Pageable page);

    List<Ingredient> getIngredientsByTypes(List<String> types);
    List<Ingredient> getIngredientsByTypes(List<String> types,Pageable page);

    List<String> getIngredientNames();
    List<String> getIngredientNames(Pageable page);

    List<String> getIngredientNamesWithPrefix(String namePrefix);
    List<String> getIngredientNamesWithPrefix(String namePrefix, Pageable page);

    Ingredient getIngredientById(Integer id);
    Ingredient getIngredientByName(String name);
    List<Ingredient> getIngredientsByNames(List<String> names);
    List<Ingredient> getDishIngredients(Dish dish);
    List<Integer> getDishIngredientIds(Dish dish);
    Boolean isExixts(String name);
}
