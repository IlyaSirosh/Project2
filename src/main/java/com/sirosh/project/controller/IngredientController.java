package com.sirosh.project.controller;

import com.sirosh.project.dao.impl.utils.Wrapper;
import com.sirosh.project.entity.Ingredient;
import com.sirosh.project.entity.IngredientType;
import com.sirosh.project.pojo.Pageable;
import com.sirosh.project.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Illya on 4/18/17.
 */

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @PostMapping
    public void create(@RequestBody Ingredient ingredient){

        System.out.println("id="+ingredient.getId()+" name="+ingredient.getName()+" types="+ingredient.getTypes()+" author="+ingredient.getAuthor());

        ingredientService.add(ingredient);
    }

    @GetMapping
    @RequestMapping("/name/{name}")
    public Ingredient read(@PathVariable("name") String name){
        return ingredientService.getByName(name);
    }

    @GetMapping
    @RequestMapping("/id/{id}")
    public Ingredient read(@PathVariable("id") Integer id){
        return ingredientService.getById(id);
    }

    @PutMapping
    public void update(@RequestBody Ingredient ingredient){
        ingredientService.save(ingredient);
    }

    @DeleteMapping
    @RequestMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        Ingredient i = new Ingredient();
        i.setId(id);
        ingredientService.delete(i);
    }

    @DeleteMapping
    public void delete(@RequestBody Ingredient ingredient){
        ingredientService.delete(ingredient);
    }

    @GetMapping
    @RequestMapping("/all/{page}/{size}")
    public List<Ingredient> getAll(@PathVariable("page") Integer page,
                                   @PathVariable("size") Integer size){

        return ingredientService.getAll(new Pageable(page,size));
    }

    @GetMapping
    @RequestMapping("/by/types/{list}/{size}/{page}")
    public List<Ingredient> getByTypes(@PathVariable("page") Integer page,
                                       @PathVariable("size") Integer size,
                                       @PathVariable("list")List<String> types){

        System.out.println(page);
        System.out.println(size);

        return ingredientService.getByTypes(types,new Pageable(page,size));
    }

    @GetMapping
    @RequestMapping("/by/types/{list}")
    public List<Ingredient> getByTypes(
                                       @PathVariable("list")List<String> types){

        return ingredientService.getByTypes(types);
    }

    @GetMapping
    @RequestMapping("/types/by/{name}")
    public List<IngredientType> getTypes(@PathVariable("name")String name){
        return ingredientService.getTypes(name);
    }

    @GetMapping
    @RequestMapping("/names")
    public List<String> getNames(){
        return ingredientService.getIngredientNames();
    }

    @GetMapping
    @RequestMapping("/names/{prefix}")
    public List<String> getNames(@PathVariable("prefix") String prefix){
        return ingredientService.getIngredientNamesWithPrefix(prefix);
    }

    @GetMapping
    @RequestMapping("/by/names/{list}")
    public List<Ingredient> getByNames(@PathVariable("list")List<String> names){
        return ingredientService.getByNames(names);
    }

    @GetMapping
    @RequestMapping("/{name}/exists")
    public Boolean isExists(@PathVariable("name") String name){
        return ingredientService.isExixts(name);
    }


}


