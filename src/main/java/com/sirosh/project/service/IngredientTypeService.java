package com.sirosh.project.service;

import com.sirosh.project.entity.IngredientType;

import java.util.List;

/**
 * Created by Illya on 4/12/17.
 */
public interface IngredientTypeService {

    void add(IngredientType ingredientType);
    void save(IngredientType ingredientType);
    void delete(IngredientType ingredientType);

    IngredientType getByName(String name);
    List<String> getIngredientTypeNamesWithPrefix(String namePrefix);
    List<String>  getIngredientTypeNames();

    Boolean isNameExists(String name);
}
