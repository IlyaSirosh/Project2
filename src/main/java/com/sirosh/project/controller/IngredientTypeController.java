package com.sirosh.project.controller;

import com.sirosh.project.entity.IngredientType;
import com.sirosh.project.service.IngredientTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Illya on 4/19/17.
 */
@RestController
@RequestMapping("/ingredient/type")
public class IngredientTypeController {

    @Autowired
    private IngredientTypeService ingredientTypeService;

    @PostMapping
    public void create(@RequestBody IngredientType ingredientType){
        ingredientTypeService.add(ingredientType);
    }

    @PutMapping
    public void update(@RequestBody IngredientType ingredientType){
        ingredientTypeService.save(ingredientType);
    }

    @DeleteMapping
    public void delete(@RequestBody IngredientType ingredientType){
        ingredientTypeService.delete(ingredientType);
    }

    @GetMapping
    @RequestMapping("/name/{name}")
    public IngredientType getByName(@PathVariable("name") String name){
        return ingredientTypeService.getByName(name);
    }

    @GetMapping
    @RequestMapping("/names/{prefix}")
    public List<String> getNamesWithPrefix(@PathVariable("prefix") String prefix){
        return ingredientTypeService.getIngredientTypeNamesWithPrefix(prefix);
    }

    @GetMapping
    @RequestMapping("/names")
    public List<String> getNames(){
        return ingredientTypeService.getIngredientTypeNames();
    }

    @GetMapping
    @RequestMapping("/{name}/exists")
    public Boolean isExists(@PathVariable("name") String name){
        return ingredientTypeService.isNameExists(name);
    }

}
