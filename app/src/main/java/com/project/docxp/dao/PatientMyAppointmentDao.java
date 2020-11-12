package com.project.docxp.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.bean.PatientMyAppointmentBean;
import com.project.docxp.customadapter.CustomMyAppointmentAdapter;
import com.project.docxp.utility.PatientAppointmentBasedonTime;
import com.project.docxp.utility.ServerCrendentialsUtility;
import com.project.docxp.utility.SharedPreferenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Devarshi on 22-01-2018.
 */

public class PatientMyAppointmentDao {
    private AsyncHttpClient asyncHttpClient;
    private RequestParams requestParams;
    private Context context;
    SharedPreferences preferences;
    PatientMyAppointmentBean bean;
    CustomMyAppointmentAdapter adapter;
    RecyclerView recyclerView;
    FragmentActivity activity;
    SwipeRefreshLayout  layout;

    public void getPatientAppointmentData(Context applicationContext, final PatientMyAppointmentBean bean, FragmentActivity fragmentActivity, RecyclerView recyclerView_patient_myappointment, SwipeRefreshLayout swiperefresh_pateint_myappointment) {
        this.context = applicationContext;
        this.activity = fragmentActivity;
        this.bean = bean;
        this.layout=swiperefresh_pateint_myappointment;
        this.recyclerView = recyclerView_patient_myappointment;
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("email", bean.getPatient_email());
        System.out.println("===bean.getPatient_email()==" + bean.getPatient_email());
        asyncHttpClient.post(context, ServerCrendentialsUtility.URL + "PatientMyAppointmentController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<PatientMyAppointmentBean> beans = new ArrayList<>();
                System.out.println("===response==" + response);
                System.out.println("===response.length()===" + response.length());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        beans.add(new PatientMyAppointmentBean(object.get("patient_nm").toString(),
                                object.get("doc_name").toString(),
                                object.get("hospital_name").toString(),
                                object.get("city").toString(),
                                object.get("date").toString(),
                                object.get("time").toString()));
                        Comparator<PatientMyAppointmentBean>  comparator= new PatientAppointmentBasedonTime();
                        Collections.sort(beans,comparator);
                        System.out.println("====="+object.get("patient_nm").toString()+object.get("doc_name").toString()+
                                object.get("hospital_name").toString()+
                                object.get("city").toString()+
                                object.get("date").toString()+
                                object.get("time").toString()+"====");
                        layout.setRefreshing(true);
                        adapter= new CustomMyAppointmentAdapter(beans,context.getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        layout.setRefreshing(false);
                        System.out.println("===finish===");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("===failed JSONArray==");
            }
        });

    }
}