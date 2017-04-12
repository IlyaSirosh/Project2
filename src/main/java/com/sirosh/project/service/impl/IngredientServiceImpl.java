package com.sirosh.project.service.impl;

import com.sirosh.project.dao.IngredientDao;
import com.sirosh.project.dao.IngredientTypeDao;
import com.sirosh.project.entity.Ingredient;
import com.sirosh.project.entity.IngredientType;
import com.sirosh.project.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Illya on 4/12/17.
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientDao ingredientDao;
    @Autowired
    private IngredientTypeDao ingredientTypeDao;

    public Ingredient add(Ingredient ingredient) {

        if(isExixts(ingredient.getName()))
            throw new IllegalArgumentException();

        return ingredientDao.addIngredient(ingredient);
    }

    public void save(Ingredient ingredient) {

        if(ingredient.getId()==0)
            throw new IllegalArgumentException();

        ingredientDao.saveIngredient(ingredient);

    }

    public void delete(Ingredient ingredient) {
        ingredientDao.deleteIngredient(ingredient);
    }

    public List<Ingredient> getAll(Pageable page) {
        return ingredientDao.getAllIngredients(page);
    }

    public List<Ingredient> getByTypes(List<String> types, Pageable page) {
        return ingredientDao.getAllIngredients(page);
    }

    public List<IngredientType> getTypes(Ingredient ingredient) {
        return ingredientTypeDao.getIngredientTypesByIngredient(ingredient);
    }

    public List<IngredientType> getTypes(String ingredient) {
        return ingredientTypeDao.getIngredientTypesByIngredient(ingredient);
    }

    public List<String> getIngredientNames() {
        return ingredientDao.getIngredientNames();
    }

    public List<String> getIngredientNames(Pageable page) {
        return ingredientDao.getIngredientNames(page);
    }

    public List<String> getIngredientNamesWithPrefix(String namePrefix) {
        return ingredientDao.getIngredientNamesWithPrefix(namePrefix);
    }

    public List<String> getIngredientNamesWithPrefix(String namePrefix, Pageable page) {
        return ingredientDao.getIngredientNamesWithPrefix(namePrefix,page);
    }

    public Ingredient getByName(String name) {
        return ingredientDao.getIngredientByName(name);
    }

    public List<Ingredient> getByNames(List<String> names) {
        return ingredientDao.getIngredientsByNames(names);
    }

    public Boolean isExixts(String name) {
        return ingredientDao.isExixts(name);
    }
}
