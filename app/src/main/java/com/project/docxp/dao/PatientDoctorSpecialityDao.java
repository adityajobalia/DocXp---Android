package com.project.docxp.dao;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.bean.DoctorSpecialityDetailsBean;
import com.project.docxp.customadapter.CustomDoctorSpecialityAdapter;
import com.project.docxp.utility.BasedonDoctornameComparision;
import com.project.docxp.utility.ServerCrendentialsUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Devarshi on 24-01-2018.
 */

public class PatientDoctorSpecialityDao {
    private Context context;
    private FragmentActivity activity;
    private RecyclerView view;
    private AsyncHttpClient asyncHttpClient;
    private RequestParams requestParams;
    private CustomDoctorSpecialityAdapter adapter;
    private SwipeRefreshLayout layout;


    public void getPatientDoctorSpecialityList(Context applicationContext, final FragmentActivity fragmentActivity, String title, final RecyclerView recyclerView_patient_doctorspeciality, final SwipeRefreshLayout swiperefresh_patient_doctorspecciality) {
        this.context = applicationContext;
        this.activity = fragmentActivity;
        this.view = recyclerView_patient_doctorspeciality;
        this.layout=swiperefresh_patient_doctorspecciality;
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("title", title);
        asyncHttpClient.post(context, ServerCrendentialsUtility.URL + "PatientDoctorSpecialityController", requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("====responnse==" + response);
                ArrayList<DoctorSpecialityDetailsBean> list = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject object = response.getJSONObject(i);

                        list.add(new DoctorSpecialityDetailsBean(
                                object.get("doctor_profileimage").toString(),
                                object.get("doctor_name").toString(),
                                object.get("doctor_speciality").toString(),
                                object.get("doctor_hospitalname").toString(),
                                object.getString("doctor_city").toString(),
                                object.get("doctor_contact").toString(), object.get("doctor_email").toString()));
                        Comparator<DoctorSpecialityDetailsBean> comparator = new BasedonDoctornameComparision();
                        Collections.sort(list, comparator);
                        swiperefresh_patient_doctorspecciality.setRefreshing(true);
                        adapter = new CustomDoctorSpecialityAdapter(list, context, fragmentActivity);
                        view.setAdapter(adapter);
                        swiperefresh_patient_doctorspecciality.setRefreshing(false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("===failure==");
            }
        });


    }
}
