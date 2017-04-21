package com.sirosh.project.service;

import com.sirosh.project.pojo.Email;
import com.sirosh.project.pojo.Pageable;
import com.sirosh.project.pojo.Role;
import com.sirosh.project.entity.User;


import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 14.02.17.
 */
public interface UserService {


    User getById(Integer id);
    User getByEmail(String email);
    User getByNickname(String nickname);
    Map<String,Integer> getAuthorsRatingByDishAmountAndNickname(Pageable pageable);

    void add(User user);
    void save(User user);
    void delete(Integer id);

    Long getAmount();
    List<User> getAll();

    Boolean isExists(User user);
    Boolean isEmailExists(String email);
    Boolean isNicknameExists(String nickname);


    Boolean isRegistered(User user);

}
