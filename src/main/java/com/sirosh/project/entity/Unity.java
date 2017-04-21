package com.sirosh.project.entity;

import java.io.Serializable;

/**
 * Created by Illya on 14.02.17.
 */
public class Unity implements Serializable {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
