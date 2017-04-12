package com.sirosh.project.service.impl;

import com.sirosh.project.dao.UserDao;
import com.sirosh.project.entity.User;
import com.sirosh.project.pojo.Email;
import com.sirosh.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Illya on 4/12/17.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User getById(Long id) {
        return userDao.getUserById(id);
    }

    public User getByEmail(Email email) {
        return userDao.getUserByEmail(email);
    }

    public User getByNickname(String nickname) {
        return userDao.getUserByNickname(nickname);
    }

    public Map<String, Integer> getAuthorsRatingByDishAmountAndNickname(Pageable pageable) {
        return userDao.getAuthorsRatingByDishAmountAndNickname(pageable);
    }

    public User add(User user) {

        if(userDao.isEmailExists(user.getEmail())) throw new IllegalArgumentException("email exists");
        if(userDao.isNicknameExists(user.getNickname())) throw new IllegalArgumentException("nick exists");

        user.getPassword().encode();
        return userDao.addUser(user);
    }

    public void save(User user) {

        if(user.getId()==0) throw new IllegalArgumentException("cannot save user without id");

        userDao.saveUser(user);
    }

    public void delete(User user) {
        userDao.deleteUser(user);
    }

    public Long getAmount() {
        return userDao.getUsersAmount();
    }

    public List<User> getAll() {
        return userDao.getAllUsers();
    }

    public Boolean isExists(User user) {
        return userDao.isExists(user);
    }

    public Boolean isEmailExists(Email email) {
        return userDao.isEmailExists(email);
    }

    public Boolean isNicknameExists(String nickname) {
        return userDao.isNicknameExists(nickname);
    }

    public Boolean isRegistered(User user) {

        User u = null;

        user.getPassword().encode();

        if(user.getNickname()!=null)
            u = userDao.getUserByNickname(user.getNickname());
        else if (user.getEmail()!=null)
            u = userDao.getUserByEmail(user.getEmail());

        return u!=null && u.getPassword().equals(user.getPassword());
    }
}
