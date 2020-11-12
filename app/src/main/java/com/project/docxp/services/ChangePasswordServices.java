package com.project.docxp.services;

import android.app.ProgressDialog;

import com.project.docxp.ChangePasswordActivity;
import com.project.docxp.bean.ChangePasswordBean;
import com.project.docxp.dao.ChangePasswordDao;

/**
 * Created by adity on 01-01-2018.
 */

public class ChangePasswordServices {
    public void verifyPassword(ChangePasswordBean passwordBean, ChangePasswordActivity changePasswordActivity, ProgressDialog progressDialog) {
        ChangePasswordDao passwordDao = new ChangePasswordDao();
        passwordDao.updatePassword(passwordBean,changePasswordActivity,progressDialog);
    }
}
