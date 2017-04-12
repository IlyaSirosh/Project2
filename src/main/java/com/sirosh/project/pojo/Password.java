package com.sirosh.project.pojo;

/**
 * Created by Illya on 4/9/17.
 */
public class Password {

    private String password;
    private boolean encoded;

    public Password(String password) {
        this.password = password;
        this.encoded = false;
    }

    public String getPassword() {
        return password;
    }

    public void encode(){

        if(!encoded){
            //TODO encode function
            encoded=true;
        }

    };

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEncoded() {
        return encoded;
    }

    public void setEncoded(boolean encoded) {
        this.encoded = encoded;
    }
}
