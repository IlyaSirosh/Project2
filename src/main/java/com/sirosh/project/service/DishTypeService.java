package com.sirosh.project.service;

import com.sirosh.project.entity.Dish;
import com.sirosh.project.entity.DishType;

import java.util.List;

/**
 * Created by Illya on 4/12/17.
 */
public interface DishTypeService {

    DishType add(DishType type);
    void save(DishType type);
    void delete(DishType type);

    List<DishType> getAll();
    List<String> getNames();
    List<String> getNamesWithPrefix(String namePrefix);
    DishType getByName(String name);
    List<DishType> getByNames(List<String> names);
    Boolean isNameExists(String name);

}
