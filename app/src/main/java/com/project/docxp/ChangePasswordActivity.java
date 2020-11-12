package com.project.docxp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.docxp.bean.ChangePasswordBean;
import com.project.docxp.services.ChangePasswordServices;
import com.project.docxp.utility.ConnectivityReceiver;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView change_cardview;
    private EditText edittext_changePassword_password,getEdittext_changePassword_confirmPassword;
    private Button forget_password_button;
    String intentEmail,intentUser;
    ProgressDialog progressDialog;
    ChangePasswordBean passwordBean;
    private boolean networkConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        intentEmail = getIntent().getExtras().getString("email");
        intentUser = getIntent().getExtras().getString("user");
        progressDialog = new ProgressDialog(ChangePasswordActivity.this);
        getId();
        change_cardview.setCardElevation(24f);
        forget_password_button.setOnClickListener(this);
    }

    private void getId() {
        change_cardview = (CardView) findViewById(R.id.changePassword_cardview);
        edittext_changePassword_password = (EditText) findViewById(R.id.edittext_changePassword_password);
        getEdittext_changePassword_confirmPassword = (EditText) findViewById(R.id.edittext_changePassword_confirmPassowrd);
        forget_password_button = (Button) findViewById(R.id.forget_password_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forget_password_button:{
                String password = edittext_changePassword_password.getText().toString();
                String confirmPassword = getEdittext_changePassword_confirmPassword.getText().toString();
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(this, "Both fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else if (password.equals(confirmPassword)){
                    if (networkConnection = checkConnection(getApplicationContext())) {
                        passwordBean = new ChangePasswordBean();
                        passwordBean.setAuth(intentUser);
                        passwordBean.setEmail(intentEmail);
                        passwordBean.setPassword(password);
                        progressDialog.setTitle("Please wait");
                        progressDialog.setMessage("Changing Password...");
                        progressDialog.show();
                        ChangePasswordServices services = new ChangePasswordServices();
                        services.verifyPassword(passwordBean, ChangePasswordActivity.this, progressDialog);
                    }
                }
                else {
                    if (networkConnection = checkConnection(getApplicationContext())) {
                        Toast.makeText(this, "Password doesn't match!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
    @Override
    public void onBackPressed() {
        ChangePasswordActivity.super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
    }

    private boolean checkConnection(Context applicationContext) {
        boolean isConnected = ConnectivityReceiver.isConnected(applicationContext);
        System.out.println("======isConnected======="+isConnected);
        boolean networkStatus = showSnack(isConnected);
        System.out.println("======networkStatus======"+networkStatus);
        return networkStatus;
    }

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
                .make(findViewById(R.id.pateint_profile_image), message, Snackbar.LENGTH_LONG);

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
