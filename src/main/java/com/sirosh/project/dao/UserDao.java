package com.sirosh.project.dao;

import com.sirosh.project.entity.User;
import com.sirosh.project.pojo.Email;
import com.sirosh.project.pojo.Pageable;


import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 4/9/17.
 */
public interface UserDao {

    //selectors
    User getUserById(Integer id);
    User getUserByEmail(String email);
    User getUserByNickname(String nickname);
    Map<String,Integer> getAuthorsRatingByDishAmountAndNickname(Pageable pageable);
    List<User> getAllUsers();
    Long getUsersAmount();


    void addUser(User user);
    void saveUser(User user);
    void deleteUser(Integer id);

    Boolean isExists(User user);
    Boolean isEmailExists(String email);
    Boolean isNicknameExists(String nickname);
}
