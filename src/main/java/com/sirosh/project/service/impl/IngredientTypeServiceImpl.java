package com.sirosh.project.service.impl;

import com.sirosh.project.dao.IngredientTypeDao;
import com.sirosh.project.entity.IngredientType;
import com.sirosh.project.service.IngredientTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Illya on 4/12/17.
 */
@Service
public class IngredientTypeServiceImpl implements IngredientTypeService {

    @Autowired
    private IngredientTypeDao ingredientTypeDao;

    public void add(IngredientType ingredientType) {

//        if(isNameExists(ingredientType.getName()))
//            throw new IllegalArgumentException();

        ingredientTypeDao.addIngredientType(ingredientType);
    }

    public void save(IngredientType ingredientType) {
//        if(ingredientType.getId()==0)
//            throw new IllegalArgumentException();

        ingredientTypeDao.saveIngredientType(ingredientType);
    }

    public void delete(IngredientType ingredientType) {
        ingredientTypeDao.deleteIngredientType(ingredientType);
    }

    public IngredientType getByName(String name) {
        return ingredientTypeDao.getIngredientTypeByName(name);
    }

    public List<String> getIngredientTypeNamesWithPrefix(String namePrefix) {
        return ingredientTypeDao.getIngredientTypesNameWithPrefix(namePrefix);
    }

    public List<String> getIngredientTypeNames() {
        return ingredientTypeDao.getIngredientTypesName();
    }

    public Boolean isNameExists(String name) {
        return ingredientTypeDao.isNameExists(name);
    }
}
