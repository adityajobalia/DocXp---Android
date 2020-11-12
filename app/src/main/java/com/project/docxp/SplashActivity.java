package com.project.docxp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.project.docxp.utility.SharedPreferenceData;

public class SplashActivity extends AppCompatActivity {
    private ImageView splash_imageview;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //startService(new Intent(SplashActivity.this, ServicesManager.class));

        /**
         *changing to full screen
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        preferences = getSharedPreferences(SharedPreferenceData.SHAREPREF,MODE_PRIVATE);
        splash_imageview = (ImageView) findViewById(R.id.splash_imageview);

        /**
         * Alpha animation
         */

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(3500);
        splash_imageview.setAnimation(alphaAnimation);
        /**
         * Handler object for automatic transiion between activities
         */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!preferences.getString(SharedPreferenceData.LOGIN_EMAIL,"").isEmpty()){
                    String auth = preferences.getString(SharedPreferenceData.LOGIN_AUTH,"");
                    if (auth.equals("patient")){
                        startActivity(new Intent(SplashActivity.this,PatientNavigationActivity.class));
                        finish();
                    }
                    else if(auth.equals("doctor")) {
                        startActivity(new Intent(SplashActivity.this,DocNavigationActivity.class));
                        finish();
                    }
                }else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3500);
    }
    @Override
    public void onBackPressed() {
        SplashActivity.super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
    }

}
