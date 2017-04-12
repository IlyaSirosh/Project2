package com.sirosh.project.entity;

import com.sirosh.project.pojo.Amount;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 14.02.17.
 */
public class Dish {

    private Integer id;
    private String name;

    private Map<Ingredient,Amount> ingredients;
    private Nutrients nutrients;

    private List<DishType> types;

    private URL image;

    private String description;

    private User author;

    public static URL defaultImage;

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

    public Map<Ingredient, Amount> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Ingredient, Amount> ingredients) {
        this.ingredients = ingredients;
    }

    public Nutrients getNutrients() {
        return nutrients;
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

    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
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


    public Double getCarbohydrates(){
        return nutrients.getCarbohydrates();
    }

    public Double getProteins(){
        return nutrients.getProteins();
    }

    public Double getFats(){
        return nutrients.getFats();
    }

    public Double getCalories(){
        return nutrients.getCalories();
    }

    public List<Integer> getDishTypeIds(){

        List<Integer> l = new ArrayList<Integer>();

        if(types!=null)
            for(DishType x: types)
                l.add(x.getId());
        else
            l.add(-1);

        return l;
    }

    public List<Integer> getIngredientIds(){

        List<Integer> l = new ArrayList<Integer>();

        if(ingredients!=null)
            for(Ingredient x: ingredients.keySet())
                l.add(x.getId());
        else
            l.add(-1);

        return l;
    }


}
