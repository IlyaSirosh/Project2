package com.sirosh.project.entity;

import com.sirosh.project.pojo.Amount;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Illya on 14.02.17.
 */
public class Meal implements Serializable {

    private Long id;
    private User user;

    private Map<Dish,Amount> dishes;

    //private Nutrients nutrients;

    private Date date;
    private Time time;

    public Meal(){
        this.dishes = new HashMap<Dish, Amount>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Dish, Amount> getDishes() {
        return dishes;
    }

    public void setDishes(Map<Dish, Amount> dishes) {
        this.dishes = dishes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }


    public void addDish(Dish dish,Amount amount){
        this.dishes.put(dish,amount);
    }
}
