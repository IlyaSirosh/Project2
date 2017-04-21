package com.sirosh.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sirosh.project.pojo.Email;
import com.sirosh.project.pojo.Password;
import com.sirosh.project.pojo.Role;

import java.io.Serializable;

/**
 * Created by Illya on 14.02.17.
 */
public class User implements Serializable{

    private Integer id;
    @JsonIgnore
    private String password;
    private String email;
    private String nickname;

    private String role;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
