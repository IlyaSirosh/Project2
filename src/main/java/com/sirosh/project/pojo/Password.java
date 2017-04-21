package com.sirosh.project.pojo;

import java.io.Serializable;

/**
 * Created by Illya on 4/9/17.
 */
public class Password implements Serializable {

    private String value;
    private boolean encoded;

    public Password(String password) {
        this.value = password;
        this.encoded = false;
    }

    public String getPassword() {
        return value;
    }

    public void encode(){

        if(!encoded){
            //TODO encode function
            encoded=true;
        }

    };

    public void setPassword(String password) {
        this.value = password;
    }

    public boolean isEncoded() {
        return encoded;
    }

    public void setEncoded(boolean encoded) {
        this.encoded = encoded;
    }
}
