package com.project.docxp.utility;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.project.docxp.dao.NavigationDrawerDataDao;

public class ServicesManager extends Service {
    public ServicesManager() {

    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("======onStartCommand=========");
        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
        NavigationDrawerDataDao dao = new NavigationDrawerDataDao();
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        String email = preferences.getString(SharedPreferenceData.LOGIN_EMAIL, "");
        //dao.getDrawerData(email,getApplicationContext());
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
