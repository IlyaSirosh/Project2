package com.sirosh.project.service;

import com.sirosh.project.model.Role;
import com.sirosh.project.model.User;

import java.util.List;

/**
 * Created by Illya on 14.02.17.
 */
public interface UserService {


    User getById(int id);
    User getByEmail(String email);
    void add(User user);
    void update(User user);
    List<User> getByRole(Role role);

}
