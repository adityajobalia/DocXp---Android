package com.project.docxp.bean;

import java.io.Serializable;

/**
 * Created by adity on 18-12-2017.
 */

public class LoginBean implements Serializable {
    private String email;
    private String password;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    private String auth;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
