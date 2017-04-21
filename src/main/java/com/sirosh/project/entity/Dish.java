package com.sirosh.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sirosh.project.pojo.Amount;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 14.02.17.
 */
public class Dish implements Serializable {

    private Integer id;
    private String name;

    private List<Ingredient> ingredients;
    private Nutrients nutrients;

    private List<DishType> types;

    private String image;

    private String description;

    private User author;

    public static String defaultImage = "http://example.com/images/default.jpeg";


    public Dish(){
        this.types = new ArrayList<DishType>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public void addType(DishType type){
        types.add(type);
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }

    public List<DishType> getTypes() {
        return types;
    }

    public void setTypes(List<DishType> types) {
        this.types = types;
    }

    public String getImage() {
        return image==null?defaultImage:image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @JsonIgnore
    public Double getCarbohydrates(){
        return nutrients.getCarbohydrates();
    }
    @JsonIgnore
    public Double getProteins(){
        return nutrients.getProteins();
    }
    @JsonIgnore
    public Double getFats(){
        return nutrients.getFats();
    }
    @JsonIgnore
    public Double getCalories(){
        return nutrients.getCalories();
    }
    @JsonIgnore
    public List<Integer> getDishTypeIds(){

        List<Integer> l = new ArrayList<Integer>();

        if(types!=null)
            for(DishType x: types)
                l.add(x.getId());
        else
            l.add(-1);

        return l;
    }
    @JsonIgnore
    public List<Integer> getIngredientIds(){

        List<Integer> l = new ArrayList<Integer>();

        if(ingredients!=null)
            for(Ingredient x: ingredients)
                l.add(x.getId());
        else
            l.add(-1);

        return l;
    }

}
