package com.sirosh.project.controller;

import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirosh.project.entity.*;
import com.sirosh.project.pojo.Pageable;
import com.sirosh.project.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Illya on 4/19/17.
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    public void create(@RequestBody Dish dish){
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(dish));
        }catch (Exception e){}
        dishService.add(dish);
    }

    @PutMapping
    public void update(@RequestBody Dish dish){
        dishService.update(dish);
    }

    @DeleteMapping
    public void delete(@RequestBody Dish dish){
        dishService.delete(dish);
    }

    @GetMapping
    @RequestMapping("/name/{name}")
    public Dish getByName(@PathVariable("name")String name){
        return dishService.getByName(name);
    }

    @GetMapping
    @RequestMapping("/id/{id}")
    public Dish getById(@PathVariable("id")Integer id){
        return dishService.getById(id);
    }

    @GetMapping
    @RequestMapping("/by/names/{names}")
    public List<Dish> getByNames(@PathVariable("names") List<String> names){
        return dishService.getByNames(names);
    }

    @GetMapping
    @RequestMapping("/names/{prefix}")
    public List<String> getNamesWithPrefix(@PathVariable("prefix")String prefix){
        return dishService.getDishNamesWithPrefix(prefix);
    }

    @GetMapping
    @RequestMapping("/all/{size}/{page}")
    public List<Dish> getAll(@PathVariable("size")Integer size,
                             @PathVariable("page")Integer page){
        return dishService.getAll(new Pageable(page,size));
    }

    @GetMapping
    @RequestMapping("/by/author/{id}/{size}/{page}")
    public List<Dish> getByAuthor(@PathVariable("id")Integer id,
                                  @PathVariable("size")Integer size,
                                  @PathVariable("page")Integer page){
        User u = new User();
        u.setId(id);
        return dishService.getByAuthor(u,new Pageable(page,size));
    }

    @GetMapping
    @RequestMapping("/by/author/name/{name}/{size}/{page}")
    public List<Dish> getByAuthor(@PathVariable("name")String name,
                                  @PathVariable("size")Integer size,
                                  @PathVariable("page")Integer page){
        User u = new User();
        u.setNickname(name);
        return dishService.getByAuthor(u,new Pageable(page,size));
    }


    @GetMapping
    @RequestMapping("/by/types/{types}/ingredients/{ingredients}/{size}/{page}")
    public List<Dish> getByTypesAndIngredients(@PathVariable("types")List<String> types,
                                               @PathVariable("ingredients")List<String> ingredients,
                                               @PathVariable("size")Integer size,
                                               @PathVariable("page")Integer page){
        return dishService.getByDishTypesAndIngredients(types,ingredients,new Pageable(page,size));
    }

    @GetMapping
    @RequestMapping("/by/types/{types}/{size}/{page}")
    public List<Dish> getByTypes(@PathVariable("types")List<String> types,
                                 @PathVariable("size")Integer size,
                                 @PathVariable("page")Integer page){
        return dishService.getByDishTypes(types,new Pageable(page,size));
    }

    @GetMapping
    @RequestMapping("/by/ingredients/{ingredients}/{size}/{page}")
    public List<Dish> getByIngredients(@PathVariable("ingredients")List<String> ingredients,
                                 @PathVariable("size")Integer size,
                                 @PathVariable("page")Integer page){
        return dishService.getByIngredients(ingredients,new Pageable(page,size));
    }

//    @GetMapping
//    @RequestMapping("/by/ingredient/types/{ingredients}/{size}/{page}")
//    public List<Dish> getByIngredientTypes(@PathVariable("ingredients")List<String> ingredients,
//                                       @PathVariable("size")Integer size,
//                                       @PathVariable("page")Integer page){
//        return dishService.getByIngredientTypes(ingredients,new Pageable(page,size));
//    }

    @GetMapping
    @RequestMapping("/by/types/{types}/exact/{size}/{page}")
    public List<Dish> getByTypesExact(@PathVariable("types")List<String> types,
                                 @PathVariable("size")Integer size,
                                 @PathVariable("page")Integer page){
        return dishService.getByDishTypes(types,new Pageable(page,size));
    }

    @GetMapping
    @RequestMapping("/by/ingredients/{ingredients}/exact/{size}/{page}")
    public List<Dish> getByIngredientsExact(@PathVariable("ingredients")List<String> ingredients,
                                       @PathVariable("size")Integer size,
                                       @PathVariable("page")Integer page){
        return dishService.getByIngredients(ingredients,new Pageable(page,size));
    }

//    @GetMapping
//    @RequestMapping("/by/ingredient/types/{ingredients}/exact/{size}/{page}")
//    public List<Dish> getByIngredientTypesExact(@PathVariable("ingredients")List<String> ingredients,
//                                           @PathVariable("size")Integer size,
//                                           @PathVariable("page")Integer page){
//        return dishService.getByIngredientTypes(ingredients,new Pageable(page,size));
//    }

    @GetMapping
    @RequestMapping("/{id}/types")
    public List<DishType> getTypes(@PathVariable("id") Integer id){
        Dish d = new Dish();
        d.setId(id);
        return dishService.getTypes(d);
    }

    @GetMapping
    @RequestMapping("/{id}/ingredients")
    public List<Ingredient> getIngredients(@PathVariable("id") Integer id){
        Dish d = new Dish();
        d.setId(id);
        return dishService.getIngredients(d);
    }

    @GetMapping
    @RequestMapping("/{id}/ingredient/types")
    public List<IngredientType> getIngredientTypes(@PathVariable("id") Integer id){
        Dish d = new Dish();
        d.setId(id);
        return dishService.getIngredientTypes(d);
    }

    @GetMapping
    @RequestMapping("/all/count")
    public Integer getAllCount(){
        return dishService.getAllCount();
    }

    @GetMapping
    @RequestMapping("/by/author/{id}/count")
    public Integer getCountByAuthor(@PathVariable("id")Integer id){
        User u = new User();
        u.setId(id);
        return dishService.getCountByAuthor(u);
    }

    @GetMapping
    @RequestMapping("/by/types/{list}/count")
    public Integer getCountByTypes(@PathVariable("list")List<String> list){
        return dishService.getCountByDishTypes(list);
    }

    @GetMapping
    @RequestMapping("/by/ingredients/{list}/count")
    public Integer getAllCount(@PathVariable("list")List<String> list){
        return dishService.getCountByIngredients(list);
    }

    @GetMapping
    @RequestMapping("/{name}/exists")
    public Boolean isExists(@PathVariable("name")String name){
        return dishService.isNameExists(name);
    }



}
