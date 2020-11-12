package com.project.docxp.services;

import android.app.ProgressDialog;

import com.project.docxp.ForgetPasswordActivity;
import com.project.docxp.dao.OtpDao;

/**
 * Created by adity on 30-12-2017.
 */

public class OtpServices {
    public void verifyOtp(ProgressDialog progressDialog, ForgetPasswordActivity forgetPasswordActivity, int otp, String email) {
        OtpDao otpDao = new OtpDao();
        otpDao.sendVerificationOtp(progressDialog,forgetPasswordActivity,otp,email);
    }
}
