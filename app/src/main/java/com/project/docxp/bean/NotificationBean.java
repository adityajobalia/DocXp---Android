package com.project.docxp.bean;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by Devarshi on 16-12-2017.
 */

public class NotificationBean implements Serializable {
    private String textviewt_notification_header,textview_notification_details;
    private int imgview_notification;

    public NotificationBean(String textviewt_notification_header, String textview_pateint_notification_details, int imgview_notification) {
        this.textviewt_notification_header = textviewt_notification_header;
        this.textview_notification_details = textview_pateint_notification_details;
        this.imgview_notification = imgview_notification;
    }

    public String getTextviewt_notification_header() {
        return textviewt_notification_header;
    }

    public void setTextviewt_notification_header(String textviewt_notification_header) {
        this.textviewt_notification_header = textviewt_notification_header;
    }

    public String getTextview_pateint_notification_details() {
        return textview_notification_details;
    }

    public void setTextview_pateint_notification_details(String textview_pateint_notification_details) {
        this.textview_notification_details = textview_pateint_notification_details;
    }

    public int getImgview_notification() {
        return imgview_notification;
    }

    public void setImgview_notification(int imgview_notification) {
        this.imgview_notification = imgview_notification;
    }
}

