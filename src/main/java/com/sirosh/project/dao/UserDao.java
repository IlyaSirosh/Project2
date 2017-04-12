package com.sirosh.project.dao;

import com.sirosh.project.entity.User;
import com.sirosh.project.pojo.Email;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 4/9/17.
 */
public interface UserDao {

    //selectors
    User getUserById(Long id);
    User getUserByEmail(Email email);
    User getUserByNickname(String nickname);
    Map<String,Integer> getAuthorsRatingByDishAmountAndNickname(Pageable pageable);
    List<User> getAllUsers();
    Long getUsersAmount();


    User addUser(User user);
    void saveUser(User user);
    void deleteUser(User user);

    Boolean isExists(User user);
    Boolean isEmailExists(Email email);
    Boolean isNicknameExists(String nickname);
}
