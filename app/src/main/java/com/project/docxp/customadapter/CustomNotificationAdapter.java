package com.project.docxp.customadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.docxp.DocNotificationActivity;
import com.project.docxp.PatientNotificationActivity;
import com.project.docxp.R;
import com.project.docxp.bean.NotificationBean;

import java.util.ArrayList;

/**
 * Created by Devarshi on 16-12-2017.
 */

public class CustomNotificationAdapter extends ArrayAdapter<NotificationBean> {
    private Context context;
    private int layout;
    private ArrayList ls;
    private CustomNotificationViewHolder customNotificationViewHolder;
    private NotificationBean item;
    public CustomNotificationAdapter(PatientNotificationActivity patientNotificationActivity, int custom_notification, ArrayList<NotificationBean> notificationBeans) {
        super(patientNotificationActivity, custom_notification, notificationBeans);
        this.context = patientNotificationActivity;
        this.layout = custom_notification;
        this.ls = notificationBeans;
    }

    public CustomNotificationAdapter(DocNotificationActivity docNotificationActivity, int custom_listview_notification, ArrayList<NotificationBean> notificationBeans) {
        super(docNotificationActivity, custom_listview_notification, notificationBeans);
        this.context = docNotificationActivity;
        this.layout = custom_listview_notification;
        this.ls = notificationBeans;
    }

    public class CustomNotificationViewHolder {
        TextView textviewt_notification_header, textview_notification_details;
        ImageView imgview_notification;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        item= getItem(position);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            customNotificationViewHolder = new CustomNotificationViewHolder();

            convertView = layoutInflater.inflate(R.layout.custom_listview_notification, null, false);
            customNotificationViewHolder.textviewt_notification_header = (TextView) convertView.findViewById(R.id.textviewt_notification_header);
            customNotificationViewHolder.textview_notification_details = (TextView) convertView.findViewById(R.id.textview_notification_details);
            customNotificationViewHolder.imgview_notification=(ImageView)convertView.findViewById(R.id.imgview_notification);
            convertView.setTag(customNotificationViewHolder);

        } else {
            customNotificationViewHolder = (CustomNotificationViewHolder) convertView.getTag();

        }
       customNotificationViewHolder.textviewt_notification_header.setText(item.getTextviewt_notification_header());
        customNotificationViewHolder.textview_notification_details.setText(item.getTextview_pateint_notification_details());
        customNotificationViewHolder.imgview_notification.setImageResource(item.getImgview_notification());


        return convertView;
    }
}
