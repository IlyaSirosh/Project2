package com.sirosh.project.entity;

/**
 * Created by Illya on 4/10/17.
 */
public class DishVariety extends Dish{

    private Dish main;

    public Dish getMain() {
        return main;
    }

    public void setMain(Dish main) {
        this.main = main;
    }
}
