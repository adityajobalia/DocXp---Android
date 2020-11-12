package com.project.docxp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.docxp.bean.NotificationBean;
import com.project.docxp.customadapter.CustomNotificationAdapter;
import com.project.docxp.utility.SharedPreferenceData;

import java.util.ArrayList;

public class DocNotificationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listview_doc_notification;
    //private String[] textviewt_notification_header={"Header","Header","Header","Header","Header"};
    //private String[] textview_pateint_notification_details={"Details","Details","Details","Details","Details"};
    private ArrayAdapter arrayAdapter;
    SharedPreferences preferences;
    private TextView doctor_notification_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_notification);
        preferences = getSharedPreferences(SharedPreferenceData.SHAREPREF, MODE_PRIVATE);
        String from = preferences.getString(SharedPreferenceData.NOTIFICATION_TITLE, "");
        String body = preferences.getString(SharedPreferenceData.NOTIFICATION_BODY, "");
        getDocNotificationActivityID();

        if (TextUtils.isEmpty(from)) {
            doctor_notification_textView = (TextView) findViewById(R.id.doctor_notification_textView);
            doctor_notification_textView.setVisibility(View.VISIBLE);
        } else {
            ArrayList<NotificationBean> notificationBeans = new ArrayList<NotificationBean>();
            notificationBeans.add(new NotificationBean(from, body, R.mipmap.splashicon));
            arrayAdapter = new CustomNotificationAdapter(this, R.layout.custom_listview_notification, notificationBeans);
            listview_doc_notification.setAdapter(arrayAdapter);
            listview_doc_notification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(DocNotificationActivity.this, "click", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    public void getDocNotificationActivityID() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_doc_notification);
        toolbar.setTitle("Notification");
        setSupportActionBar(toolbar);
        listview_doc_notification = (ListView) findViewById(R.id.listview_doc_notification);
    }

    @Override
    public void onBackPressed() {
        DocNotificationActivity.super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
    }
}
