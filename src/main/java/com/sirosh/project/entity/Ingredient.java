package com.sirosh.project.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Illya on 14.02.17.
 */
public class Ingredient {

    private Integer id;
    private String name;

    private List<IngredientType> types;
    //private Nutrients nutrients;

    private User author;

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

    public List<IngredientType> getTypes() {
        return types;
    }

    public void setTypes(List<IngredientType> types) {
        this.types = types;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


    public List<Integer> getIngredientTypeIds(){

        List<Integer> ids = new ArrayList<Integer>();

        if(types!=null) {
            for (IngredientType x : types) {
                ids.add(x.getId());
            }
        }else{
            ids.add(-1);
        }

        return ids;
    }

    public List<String> getIngredientTypeNames(){

        List<String> ids = new ArrayList<String>();

        if(types!=null) {
            for (IngredientType x : types) {
                ids.add(x.getName());
            }
        }else{
            ids.add("_");
        }

        return ids;
    }
}
