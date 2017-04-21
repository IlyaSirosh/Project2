package com.sirosh.project.pojo;

import java.io.Serializable;

/**
 * Created by Illya on 14.02.17.
 */

public class Role implements Serializable {
    public static final Role ADMIN = new Role(1);
    public static final Role USER = new Role(2);

    private int role;

    public Role(int role){
        this.role = role;
    }


    public boolean isEquals(Role o){
        return role == o.role;
    }

    public int getInt(){
        return role;
    }
}
