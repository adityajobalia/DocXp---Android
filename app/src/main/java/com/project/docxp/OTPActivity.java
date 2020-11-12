package com.project.docxp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OTPActivity extends AppCompatActivity {

    Button otp_button;
    EditText edittext_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Intent intent = getIntent();
        final String otp = intent.getExtras().get("otp").toString();
        final String email = intent.getExtras().getString("email");
        final String user = intent.getExtras().getString("user");
        edittext_otp = (EditText) findViewById(R.id.edittext_otp);
        otp_button = (Button) findViewById(R.id.otp_button);

        otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userOtp = edittext_otp.getText().toString();
                if (TextUtils.isEmpty(userOtp)) {
                    edittext_otp.setError("Enter OTP");
                } else if (otp.equals(userOtp)) {
                    Toast.makeText(OTPActivity.this, "OTP verified successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OTPActivity.this, ChangePasswordActivity.class).putExtra("email", email).putExtra("user", user));
                } else {
                    Toast.makeText(OTPActivity.this, "OTP verification failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        OTPActivity.super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
    }
}
