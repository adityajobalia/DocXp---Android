package com.project.docxp.utility;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.project.docxp.R;

/**
 * Created by JARVIS on 23-03-2018.
 */

public class NotificationManager {
    private Context mCtx;
    public static final int NOTIFICATION_ID = 4500;

    public NotificationManager(Context mCtx){
        this.mCtx = mCtx;
    }

    public void showNotification(String from, String message, Intent intent){
        Log.d("notification recieved",message);
        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx,NOTIFICATION_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mCtx);
        Notification notification = builder.setSmallIcon(R.mipmap.splashicon).setAutoCancel(true).setContentIntent(pendingIntent).setContentTitle(from).setContentText(message).build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        android.app.NotificationManager notificationManager = (android.app.NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,notification);
    }
}
