package com.project.docxp.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by adity on 20-12-2017.
 */

public class SharedPreferenceManager {

    private static final String SHARED_PREFERENCE_FCM_NAME = "fcmSharedPreference";
    private static final String KEY_ACCESS_TOKEN = "token";
    private static Context mCtx;
    private static SharedPreferenceManager preferenceManager;

    private SharedPreferenceManager(Context context){
        mCtx=context;
    }

    public static synchronized SharedPreferenceManager getInstance(Context context){
        if (preferenceManager==null){
            preferenceManager =new SharedPreferenceManager(context);
        }
        return preferenceManager;
    }

    public boolean storeToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_FCM_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN,token);
        editor.apply();
        return true;
    }

    public String getToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_FCM_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESS_TOKEN,null);
    }

    public void deleteValue(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREFERENCE_FCM_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear()
                .apply();
    }
}
