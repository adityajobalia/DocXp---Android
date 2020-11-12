package com.project.docxp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.docxp.services.OtpServices;
import com.project.docxp.utility.ConnectivityReceiver;

import java.util.Random;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Button verificationButton;
    private EditText edittext_email_forgetPassword;
    private CardView forgetPassword_cardview;
    int otp;
    ProgressDialog progressDialog;
    String email;
    private boolean networkConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetPassword_cardview = (CardView) findViewById(R.id.forgetPassword_cardview);
        forgetPassword_cardview.setElevation(24f);
        progressDialog = new ProgressDialog(ForgetPasswordActivity.this);
        //go to changepass activity
        edittext_email_forgetPassword = (EditText) findViewById(R.id.edittext_email_forgetPassword);
        Random random = new Random();
        otp = random.nextInt(999999 - 000000) + 000000;

        verificationButton = (Button) findViewById(R.id.btn_forget_password_button);
        verificationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        email = edittext_email_forgetPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            edittext_email_forgetPassword.setError("Enter appropriate Email");
        } else {
            if (checkConnection(getApplicationContext())){
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("Verifying Email Id...");
                progressDialog.show();
                OtpServices otpServices = new OtpServices();
                otpServices.verifyOtp(progressDialog, ForgetPasswordActivity.this, otp, email);
            }
        }
    }
    @Override
    public void onBackPressed() {
        ForgetPasswordActivity.super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
    }
    // Method to manually check connection status
    private boolean checkConnection(Context applicationContext) {
        boolean isConnected = ConnectivityReceiver.isConnected(applicationContext);
        System.out.println("======isConnected======="+isConnected);
        boolean networkStatus = showSnack(isConnected);
        System.out.println("======networkStatus======"+networkStatus);
        return networkStatus;
    }

    // Showing the status in Snackbar
    private boolean showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "";
            color = Color.WHITE;
        } else {
            message = "No Internet Connection";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.btn_forget_password_button), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        if (message.equals("")) {
            return true;
        } else {
            snackbar.show();
            return false;
        }
    }
}
