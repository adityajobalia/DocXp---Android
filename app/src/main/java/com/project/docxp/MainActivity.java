package com.project.docxp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.docxp.utility.ConnectivityReceiver;
import com.project.docxp.utility.MyApplication;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {


    private Button button_main_signin, button_main_signup;
    private boolean networkConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_main_signin = (Button) findViewById(R.id.button_main_signin);
        button_main_signin.setOnClickListener(this);

        button_main_signup = (Button) findViewById(R.id.button_main_signup);
        button_main_signup.setOnClickListener(this);

        checkConnection(getApplicationContext());
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_main_signin:
                networkConnection = checkConnection(getApplicationContext());
                if (networkConnection) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.button_main_signup:
                networkConnection = checkConnection(getApplicationContext());
                if (networkConnection) {
                    Intent signupintent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(signupintent);
                }
                break;
        }
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
                .make(findViewById(R.id.button_main_signup), message, Snackbar.LENGTH_LONG);

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


    @Override
    protected void onResume() {

        super.onResume();
        MyApplication.setConnectivityListener(this);
    }
    @Override
    public void onBackPressed() {
        MainActivity.super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
    }
}
