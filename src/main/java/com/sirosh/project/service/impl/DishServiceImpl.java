package com.sirosh.project.service.impl;

import com.sirosh.project.dao.DishDao;
import com.sirosh.project.dao.DishTypeDao;
import com.sirosh.project.dao.IngredientDao;
import com.sirosh.project.dao.IngredientTypeDao;
import com.sirosh.project.entity.*;
import com.sirosh.project.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Illya on 4/12/17.
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishDao dishDao;

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private DishTypeDao dishTypeDao;

    @Autowired
    private IngredientTypeDao ingredientTypeDao;

    public Dish add(Dish dish) {

        if(isNameExists(dish.getName()))
            throw new IllegalArgumentException("dish name already exist");

        return dishDao.addDish(dish);
    }

    public void update(Dish dish) {

        if(dish.getId()==0)
            throw new IllegalArgumentException("incomplete type Dish (id)");

        dishDao.saveDish(dish);
    }

    public void delete(Dish dish) {
        dishDao.deleteDish(dish);
    }

    public Dish getByName(String name) {
        return dishDao.getDishByName(name);
    }

    public Dish getById(Integer id) {
        return dishDao.getDishById(id);
    }

    public List<Dish> getByNames(List<String> name) {
        return dishDao.getDishByName(name);
    }

    public List<String> getDishNamesWithPrefix(String namePrefix) {
        return dishDao.getDishNamesWithPrefix(namePrefix);
    }

    public List<Dish> getAll(Pageable page) {
        return dishDao.getAllDishes(page);
    }

    public List<Dish> getByAuthor(User author, Pageable page) {
        return dishDao.getDishesByAuthor(author, page);
    }

    public List<Dish> getByDishTypes(List<String> types, Pageable page) {
        return dishDao.getDishesByTypes(types, page);
    }

    public List<Dish> getByIngredients(List<String> ingredients, Pageable page) {
        return dishDao.getDishesByIngredients(ingredients,page);
    }

    public List<Dish> getByIngredientTypes(List<String> types, Pageable page) {
        return dishDao.getDishesByIngredientTypes(types, page);
    }

    public List<DishType> getTypes(Dish dish) {
        return dishTypeDao.getDishTypesByDish(dish);
    }

    public List<Ingredient> getIngredients(Dish dish) {
        return ingredientDao.getDishIngredients(dish);
    }

    public List<IngredientType> getIngredientTypes(Dish dish) {
        return ingredientTypeDao.getIngredientTypesByDish(dish);
    }

    public Integer getAllCount() {
        return dishDao.getAllDishesCount();
    }

    public Integer getCountByAuthor(User author) {
        return dishDao.getDishesByAuthorCount(author);
    }

    public Integer getCountByDishTypes(List<String> types) {
        return dishDao.getDishesByTypesCount(types);
    }

    public Integer getCountByIngredients(List<String> ingredients) {
        return dishDao.getDishesByIngredientsCount(ingredients);
    }

    public Boolean isNameExists(String name) {
        return dishDao.isNameExists(name);
    }
}
