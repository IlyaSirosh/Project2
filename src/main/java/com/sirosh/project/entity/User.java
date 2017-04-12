package com.sirosh.project.entity;

import com.sirosh.project.pojo.Email;
import com.sirosh.project.pojo.Password;
import com.sirosh.project.pojo.Role;

/**
 * Created by Illya on 14.02.17.
 */
public class User {

    private Long id;

    private Password password;
    private Email email;
    private String nickname;

    private Role role;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRole(int role) {
        this.role = new Role(role);
    }
}
