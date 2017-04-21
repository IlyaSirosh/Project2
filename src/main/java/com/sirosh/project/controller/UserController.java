package com.sirosh.project.controller;

import com.sirosh.project.entity.User;
import com.sirosh.project.pojo.Email;
import com.sirosh.project.pojo.Pageable;
import com.sirosh.project.pojo.Password;
import com.sirosh.project.pojo.Role;
import com.sirosh.project.service.UnityService;
import com.sirosh.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 4/12/17.
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public void create(@RequestBody User user){
        userService.add(user);
    }

    @GetMapping("/id/{id}")
    public User get(@PathVariable("id") Integer id){
        return userService.getById(id);
    }

    @GetMapping("/nickname/{nickname}")
    public User getByNick(@PathVariable("nickname") String nickname){
        return userService.getByNickname(nickname);
    }

    @GetMapping("/email/{email}")
    public User getByEmail(@PathVariable("email") String email){
        return userService.getByEmail(email);
    }


    @PutMapping
    public void save(@RequestBody User user){
        userService.save(user);
    }

    @DeleteMapping
    @RequestMapping("/id/{id}")
    public void delete(@PathVariable Integer id){
        userService.delete(id);
    }

    @GetMapping
    @RequestMapping("/nickname/{nickname}/exists")
    public Boolean isNicknameExists(@PathVariable String nickname){
        return userService.isNicknameExists(nickname);
    }

    @GetMapping
    @RequestMapping("/email/{email}/exists")
    public Boolean isEmailExists(@PathVariable String email){
        return userService.isEmailExists(email);
    }

    @GetMapping
    @RequestMapping("/all")
    public List<User> getAll(){

        return userService.getAll();
    }

    @GetMapping
    @RequestMapping("/all/count")
    public Long getAllCount(){

        return userService.getAmount();
    }

    @GetMapping
    @RequestMapping("/dish/rating/{size}/{page}")
    public Map<String,Integer> getDishRating( @PathVariable("size")Integer size,
                                              @PathVariable("page")Integer page){
        return userService.getAuthorsRatingByDishAmountAndNickname(new Pageable(page,size));
    }
}
