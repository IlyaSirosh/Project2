package com.sirosh.project.model;

import java.util.Map;

/**
 * Created by Illya on 14.02.17.
 */
public class Dish {

    private int id;
    private String name;

    private Map<Ingredient,Unity> ingredients;
    private Nutrients nutrients;

    private DishType type;

    private String description;

    private User author;



}
