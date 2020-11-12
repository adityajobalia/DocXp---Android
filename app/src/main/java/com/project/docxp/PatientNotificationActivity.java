package com.project.docxp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.project.docxp.bean.NotificationBean;
import com.project.docxp.customadapter.CustomNotificationAdapter;
import com.project.docxp.patientfragment.PatientMyAppointmentFragment;
import com.project.docxp.utility.SharedPreferenceData;

import java.util.ArrayList;

public class PatientNotificationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listview_pateint_notification;
    //private String[] textviewt_notification_header={"Header","Header","Header","Header","Header"};
    //private String[] textview_pateint_notification_details={"Details","Details","Details","Details","Details"};
    private ArrayAdapter arrayAdapter;
    SharedPreferences preferences;
    private TextView patient_notification_textView;
    PatientMyAppointmentFragment  fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_notification);
        preferences = getSharedPreferences(SharedPreferenceData.SHAREPREF, MODE_PRIVATE);
        String from = preferences.getString(SharedPreferenceData.NOTIFICATION_TITLE, "");
        String body = preferences.getString(SharedPreferenceData.NOTIFICATION_BODY, "");
        getPatientNotificationActivityID();
        if (TextUtils.isEmpty(from)){
            patient_notification_textView = (TextView) findViewById(R.id.patient_notification_textView);
            patient_notification_textView.setVisibility(View.VISIBLE);
        }else {
            ArrayList<NotificationBean> notificationBeans = new ArrayList<NotificationBean>();
            notificationBeans.add(new NotificationBean(from, body, R.mipmap.splashicon));
            arrayAdapter = new CustomNotificationAdapter(this, R.layout.custom_listview_notification, notificationBeans);
            listview_pateint_notification.setAdapter(arrayAdapter);
            listview_pateint_notification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragment= new PatientMyAppointmentFragment();
                    fragmentTransaction.replace(R.id.patient_content_display, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.show(fragment);
                    fragmentTransaction.commitAllowingStateLoss();

                }
            });
        }

    }


    public void getPatientNotificationActivityID() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_pateint_notification);
        toolbar.setTitle("Notification");
        setSupportActionBar(toolbar);
        listview_pateint_notification = (ListView) findViewById(R.id.listview_pateint_notification);
    }

    @Override
    public void onBackPressed() {
        PatientNotificationActivity.super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
    }
}
