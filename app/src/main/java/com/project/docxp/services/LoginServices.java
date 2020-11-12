package com.project.docxp.services;

import android.app.ProgressDialog;

import com.project.docxp.LoginActivity;
import com.project.docxp.bean.LoginBean;
import com.project.docxp.dao.LoginDao;

/**
 * Created by adity on 17-12-2017.
 */

public class LoginServices {
    public boolean flag;
    public void verifyLoginUser(LoginActivity loginActivity, LoginBean logBean, ProgressDialog progressDialog) {
        LoginDao loginDao = new LoginDao();
        loginDao.logInUser(loginActivity,logBean,progressDialog);
    }
}
