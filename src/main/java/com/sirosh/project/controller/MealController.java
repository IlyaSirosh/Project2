package com.sirosh.project.controller;

import com.sirosh.project.entity.*;
import com.sirosh.project.pojo.Amount;
import com.sirosh.project.pojo.Pageable;
import com.sirosh.project.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 4/19/17.
 */

@RestController
@RequestMapping("/meal")
public class MealController {

    @Autowired
    private MealService mealService;

    @PostMapping
    public void create(@RequestBody Meal meal){
        mealService.add(meal);
    }

    @PutMapping
    public void update(@RequestBody Meal meal){
        mealService.update(meal);
    }

    @DeleteMapping
    public void delete(@RequestBody Meal meal){
        mealService.delete(meal);
    }

    @GetMapping
    @RequestMapping("/user/{id}")
    public List<Meal> getByUser(@PathVariable("id") Integer id){
        User u = new User();
        u.setId(id);
        return  mealService.get(u);
    }

    @GetMapping
    @RequestMapping("/user/{id}/{size}/{page}")
    public List<Meal> getByUser(@PathVariable("id") Integer id,
                                @PathVariable("size")Integer size,
                                @PathVariable("page")Integer page){
        User u = new User();
        u.setId(id);
        return  mealService.get(u,new Pageable(page,size));
    }

    @GetMapping
    @RequestMapping("/user/{id}/date/{date}")
    public List<Meal> getByDate(@PathVariable("id") Integer id,
                                @PathVariable("date")Date date){
        User u = new User();
        u.setId(id);
        return  mealService.getByDate(u,date);
    }

    @GetMapping
    @RequestMapping("/user/{id}/date/{from}/{to}")
    public List<Meal> getByDate(@PathVariable("id") Integer id,
                                @PathVariable("from")Date from,
                                @PathVariable("to")Date to){
        User u = new User();
        u.setId(id);
        return  mealService.getByPeriod(u,from,to);
    }

    @GetMapping
    @RequestMapping("/user/{id}/date/{date}/nutrients")
    public Nutrients getNutrietsByDate(@PathVariable("id") Integer id,
                                       @PathVariable("date")Date date){
        User u = new User();
        u.setId(id);
        return mealService.getNutrientsByDate(u,date);
    }

    @GetMapping
    @RequestMapping("/{id}/nutrients")
    public Nutrients getNutriets(@PathVariable("id") Long id){
        Meal m = new Meal();
        m.setId(id);
        return mealService.getNutrientsByMeal(m);
    }

    @GetMapping
    @RequestMapping("/user/{id}/date/{date}/time/{time}")
    public Meal get(@PathVariable("id")Integer id,
                    @PathVariable("date")Date date,
                    @PathVariable("time")Time time){
        User u = new User();
        u.setId(id);

        return mealService.getMealByDateTime(u,date,time);
    }

    @GetMapping
    @RequestMapping("/{id}/dishes")
    public Map<Dish,Amount> getDishes(@PathVariable("id") Long id){
        Meal m = new Meal();
        m.setId(id);
        return mealService.getDishes(m);
    }




}
