package com.sirosh.project.controller;

import com.sirosh.project.entity.DishType;
import com.sirosh.project.service.DishTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Illya on 4/19/17.
 */

@RestController
@RequestMapping("/dish/type")
public class DishTypeController {


    @Autowired
    private DishTypeService dishTypeService;

    @PostMapping
    public void create(@RequestBody DishType dishType){
        dishTypeService.add(dishType);
    }

    @PutMapping
    public void update(@RequestBody DishType dishType){
        dishTypeService.save(dishType);
    }

    @DeleteMapping
    public void delete(@RequestBody DishType dishType){
        dishTypeService.delete(dishType);
    }


    @GetMapping
    @RequestMapping("/name/{name}")
    public DishType getByName(@PathVariable("name") String name){
        return dishTypeService.getByName(name);
    }

    @GetMapping
    @RequestMapping("/all")
    public List<DishType> getAll(){
        return dishTypeService.getAll();
    }

    @GetMapping
    @RequestMapping("/all/names")
    public List<String> getNames(){
        return dishTypeService.getNames();
    }

    @GetMapping
    @RequestMapping("names/{prefix}")
    public List<String> getNamesWithPrefix(@PathVariable("prefix") String prefix){
        return dishTypeService.getNamesWithPrefix(prefix);
    }

    @GetMapping
    @RequestMapping("name/{name}/exists")
    public Boolean isNameExists(@PathVariable("name") String name){
        return dishTypeService.isNameExists(name);
    }
}
