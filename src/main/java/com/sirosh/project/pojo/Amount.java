package com.sirosh.project.pojo;

import com.sirosh.project.entity.Unity;

import java.io.Serializable;

/**
 * Created by Illya on 4/9/17.
 */
public class Amount implements Serializable {

    private Double value;

    private Unity unity;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Unity getUnity() {
        return unity;
    }

    public void setUnity(Unity unity) {
        this.unity = unity;
    }
}
