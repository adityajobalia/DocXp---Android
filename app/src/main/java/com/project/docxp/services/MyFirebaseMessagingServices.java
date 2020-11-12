package com.project.docxp.services;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project.docxp.DocNotificationActivity;
import com.project.docxp.PatientNotificationActivity;
import com.project.docxp.utility.NotificationManager;
import com.project.docxp.utility.SharedPreferenceData;

import java.util.Map;

/**
 * Created by JARVIS on 23-03-2018.
 */

public class MyFirebaseMessagingServices extends FirebaseMessagingService {
    private static final String TAG = "firebaseMessageService";
    Map<String, String> map;
    SharedPreferences preferences;
    FragmentTransaction fragmentTransaction;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            map = remoteMessage.getData();
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            Log.d(TAG, "Message Notification Body: " + map.get("body"));
        }
        preferences = getSharedPreferences(SharedPreferenceData.SHAREPREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SharedPreferenceData.NOTIFICATION_TITLE, map.get("title"));
        editor.putString(SharedPreferenceData.NOTIFICATION_BODY, map.get("body"));
        editor.apply();
        notifyUser(map.get("title"), map.get("body"));
    }

    public void notifyUser(String from, String notification) {
        if (notification.equals("You have new appointment request.")) {
            Intent intent = new Intent(getApplicationContext(), DocNotificationActivity.class);
            NotificationManager notificationManager = new NotificationManager(getApplicationContext());
            notificationManager.showNotification(from, notification, intent);
        } else if (notification.equals("You appointment is confirmed.") || notification.equals("You appointment is rejected, try again later.")) {
            Intent intent = new Intent(getApplicationContext(), PatientNotificationActivity.class);
            NotificationManager notificationManager = new NotificationManager(getApplicationContext());
            notificationManager.showNotification(from, notification, intent);

        }

    }
}

