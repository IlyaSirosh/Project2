package com.sirosh.project.service;

import com.sirosh.project.pojo.Email;
import com.sirosh.project.pojo.Role;
import com.sirosh.project.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 14.02.17.
 */
public interface UserService {


    User getById(Long id);
    User getByEmail(Email email);
    User getByNickname(String nickname);
    Map<String,Integer> getAuthorsRatingByDishAmountAndNickname(Pageable pageable);

    User add(User user);
    void save(User user);
    void delete(User user);

    Long getAmount();
    List<User> getAll();

    Boolean isExists(User user);
    Boolean isEmailExists(Email email);
    Boolean isNicknameExists(String nickname);


    Boolean isRegistered(User user);

}
