package com.sirosh.project.service.impl;

import com.sirosh.project.dao.DishTypeDao;
import com.sirosh.project.entity.DishType;
import com.sirosh.project.service.DishTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Illya on 4/12/17.
 */
@Service
public class DishTypeServiceImpl implements DishTypeService {

    @Autowired
    private DishTypeDao dishTypeDao;

    public void add(DishType type) {
//        if(isNameExists(type.getName()))
//            throw new IllegalArgumentException();

        dishTypeDao.addDishType(type);
    }

    public void save(DishType type) {
//        if(type.getId()==0)
//            throw new IllegalArgumentException();
        dishTypeDao.saveDishType(type);
    }

    public void delete(DishType type) {
        dishTypeDao.deleteDishType(type);
    }

    public List<DishType> getAll() {
        return dishTypeDao.getAllDishTypes();
    }

    public List<String> getNames() {
        return dishTypeDao.getDishTypeNames();
    }

    public List<String> getNamesWithPrefix(String namePrefix) {
        return dishTypeDao.getDishTypeNamesWithPrefix(namePrefix);
    }

    public DishType getByName(String name) {
        return dishTypeDao.getDishTypeByName(name);
    }

    public List<DishType> getByNames(List<String> names) {
        return dishTypeDao.getDishTypesByNames(names);
    }

    public Boolean isNameExists(String name) {
        return dishTypeDao.isNameExists(name);
    }
}
