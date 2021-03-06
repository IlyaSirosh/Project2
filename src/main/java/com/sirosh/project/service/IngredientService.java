package com.sirosh.project.service;

import com.sirosh.project.entity.Dish;
import com.sirosh.project.entity.Ingredient;
import com.sirosh.project.entity.IngredientType;
import com.sirosh.project.pojo.Pageable;


import java.util.List;

/**
 * Created by Illya on 4/12/17.
 */
public interface IngredientService {

    void add(Ingredient ingredient);
    void save(Ingredient ingredient);
    void delete(Ingredient ingredient);



    List<Ingredient> getAll(Pageable page);

    List<Ingredient> getByTypes(List<String> types,Pageable page);
    List<Ingredient> getByTypes(List<String> types);

    List<IngredientType> getTypes(Ingredient ingredient);
    List<IngredientType> getTypes(String ingredient);

    List<String> getIngredientNames();
    List<String> getIngredientNames(Pageable page);

    List<String> getIngredientNamesWithPrefix(String namePrefix);
    List<String> getIngredientNamesWithPrefix(String namePrefix, Pageable page);

    Ingredient getById(Integer id);
    Ingredient getByName(String name);
    List<Ingredient> getByNames(List<String> names);
    Boolean isExixts(String name);


}
