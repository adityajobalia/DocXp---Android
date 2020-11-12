package com.project.docxp.services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.project.docxp.utility.SharedPreferenceManager;

/**
 * Created by JARVIS on 23-03-2018.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public static final String TOKEN_BROADCAST = "broadcast";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Refreshed Token", "Refreshed token: " + refreshedToken);
        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));
        storeToken(refreshedToken);
    }
    private void storeToken(String refreshedToken) {
        SharedPreferenceManager.getInstance(getApplicationContext()).storeToken(refreshedToken);
    }
}
