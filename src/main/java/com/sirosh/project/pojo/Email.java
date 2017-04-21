package com.sirosh.project.pojo;

import java.io.Serializable;

/**
 * Created by Illya on 4/9/17.
 */
public class Email implements Serializable {

    private String value;

    public Email(String email) {
        this.value = email;
    }

    public String getEmail() {
        return value;
    }

    public void setEmail(String email) {
        this.value = email;
    }

}
