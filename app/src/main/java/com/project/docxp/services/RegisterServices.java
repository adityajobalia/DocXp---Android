package com.project.docxp.services;

import android.app.ProgressDialog;

import com.project.docxp.RegisterActivity;
import com.project.docxp.bean.RegisterBean;
import com.project.docxp.dao.RegisterDao;

/**
 * Created by adity on 17-12-2017.
 */

public class RegisterServices {

    public void verifyUser(RegisterBean regBean, RegisterActivity registerActivity, ProgressDialog progressDialog) {
        RegisterDao registerDao = new RegisterDao();
       registerDao.registerUser(regBean,registerActivity,progressDialog);
    }
}
