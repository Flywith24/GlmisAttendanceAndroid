package com.sdjzu.xg14.glmisattendanceandroid.model;

/**
 * Created on 19/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class User {
    private long id;
    private String uername;
    private String password;

    public User(String uername, String password) {
        this.uername = uername;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getUername() {
        return uername;
    }

    public String getPassword() {
        return password;
    }


}
