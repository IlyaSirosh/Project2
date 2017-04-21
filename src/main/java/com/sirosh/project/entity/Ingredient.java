package com.sirosh.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Illya on 14.02.17.
 */
public class Ingredient implements Serializable {

    private Integer id;
    private String name;

    private List<IngredientType> types;
    //private Nutrients nutrients;

    public Ingredient(){
        this.types = new ArrayList<IngredientType>();
    }

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

    public void addType(IngredientType type){
        types.add(type);
    }

    @JsonIgnore
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

    @JsonIgnore
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
