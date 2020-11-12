package com.project.docxp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.messaging.FirebaseMessaging;
import com.project.docxp.bean.LoginBean;
import com.project.docxp.services.LoginServices;
import com.project.docxp.services.MyFirebaseInstanceIDService;
import com.project.docxp.utility.ConnectivityReceiver;
import com.project.docxp.utility.MyApplication;
import com.project.docxp.utility.SharedPreferenceManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private TextView txtview_forgotPassword;
    private Button btn_signin;
    private CardView cardView;
    private EditText login_edittext_email, login_edittext_password;
    private ProgressDialog progressDialog;
    private AwesomeValidation awesomeValidation;
    String email,password;
    boolean networkConnection;
    private BroadcastReceiver broadcastReceiver;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkConnection(getApplicationContext());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getLoginActivityID();
        cardView.setCardElevation(24.0f);
        progressDialog = new ProgressDialog(LoginActivity.this);
        txtview_forgotPassword.setOnClickListener(this);
        btn_signin.setOnClickListener(this);

    }

    public BroadcastReceiver getBroadCast(LoginBean bean){
        FirebaseMessaging.getInstance().subscribeToTopic(bean.getAuth());
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                flag = true;
            }
        };
        if (SharedPreferenceManager.getInstance(LoginActivity.this).getToken() != null){
            flag = true;
            Log.d("token",SharedPreferenceManager.getInstance(LoginActivity.this).getToken());
        }

        registerReceiver(broadcastReceiver,new IntentFilter(MyFirebaseInstanceIDService.TOKEN_BROADCAST));
        return broadcastReceiver;
    }

    public void getLoginActivityID() {
        btn_signin = (Button) findViewById(R.id.login_button_loginbutton);
        txtview_forgotPassword = (TextView) findViewById(R.id.login_textview_forgotpassword);
        cardView = (CardView) findViewById(R.id.login_cardview);
        login_edittext_email = (EditText) findViewById(R.id.login_edittext_email);
        login_edittext_password = (EditText) findViewById(R.id.login_edittext_password);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.login_edittext_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.login_edittext_password, ".{6,}", R.string.passworderror);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_textview_forgotpassword:
                        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                        startActivity(intent);
                break;

            case R.id.login_button_loginbutton: {
                networkConnection = checkConnection(getApplicationContext());
                if (networkConnection) {
                    email = login_edittext_email.getText().toString().trim();
                    password = login_edittext_password.getText().toString().trim();

                    LoginBean logBean = new LoginBean();

                    if (awesomeValidation.validate()) {
                        logBean.setEmail(email);
                        logBean.setPassword(password);

                        progressDialog.setMessage("Logging in...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();

                        LoginServices loginServices = new LoginServices();
                        loginServices.verifyLoginUser(LoginActivity.this, logBean, progressDialog);
                    }
                }
            }
                break;
            }
        }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    // Method to manually check connection status
    private boolean checkConnection(Context applicationContext) {
        boolean isConnected = ConnectivityReceiver.isConnected(applicationContext);
        boolean networkStatus = showSnack(isConnected);
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
                .make(findViewById(R.id.login_button_loginbutton), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);

        if (message.equals("")){
            return true;
        }
        else{
            snackbar.show();
            return false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }
    @Override
    public void onBackPressed() {
        LoginActivity.super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }*/
}