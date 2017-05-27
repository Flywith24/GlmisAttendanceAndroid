package com.sdjzu.xg14.glmisattendanceandroid.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created on 19/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

@Entity
public class User {
    @Id
    private long id;
    @Property(nameInDb = "USERNAME")
    private String uername;
    @Property(nameInDb = "PASSWORD")
    private String password;

    public User(String uername, String password) {
        this.uername = uername;
        this.password = password;
    }

    @Generated(hash = 1319603477)
    public User(long id, String uername, String password) {
        this.id = id;
        this.uername = uername;
        this.password = password;
    }

    @Generated(hash = 586692638)
    public User() {
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUername(String uername) {
        this.uername = uername;
    }

    public void setId(long id) {
        this.id = id;
    }


}
