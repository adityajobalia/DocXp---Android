package com.project.docxp.bean;

import java.io.Serializable;

/**
 * Created by adity on 01-01-2018.
 */

public class ChangePasswordBean implements Serializable {
    private String email,password,message,auth;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
