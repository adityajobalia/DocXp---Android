package com.project.docxp.dao;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.bean.PatientFindHospitalBean;
import com.project.docxp.customadapter.CustomPatientFindHospitalAdapter;
import com.project.docxp.utility.BasedonHospitalnameComparision;
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

public class PatientFindHospitalDao {
    private AsyncHttpClient httpClient;
    private RequestParams params;
    private PatientFindHospitalBean bean;
    private CustomPatientFindHospitalAdapter adapter;
    private RecyclerView recyclerView;
    private Context context;
    private SwipeRefreshLayout layout;

    FragmentActivity fragmentActivity;

    public void getPatientFinHospitalListData(Context applicationContext, PatientFindHospitalBean bean, final FragmentActivity fragmentActivity, RecyclerView recyclerView_patient_findhospital, SwipeRefreshLayout swiperefresh_patient_findhospital) {
        this.context = applicationContext;
        this.fragmentActivity = fragmentActivity;
        this.bean = bean;
        this.layout=swiperefresh_patient_findhospital;
        this.recyclerView=recyclerView_patient_findhospital;
        httpClient= new AsyncHttpClient();
        params= new RequestParams();
        System.out.println("====in  getPatientFinHospitalListData====");
        httpClient.post(context, ServerCrendentialsUtility.URL+"PatientFinHospitalController",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<PatientFindHospitalBean> list= new ArrayList<>();
                System.out.println("===in on success getPatientFinHospitalListData====="+response);
                System.out.println("===length=="+response.length());
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject object=response.getJSONObject(i);
                        PatientFindHospitalBean bean1= new PatientFindHospitalBean(object.get("hospital_name").toString(),
                                object.get("hospital_locality").toString());
                        System.out.println("===name=="+object.get("hospital_name").toString());
                        System.out.println("===locality=="+object.get("hospital_locality").toString());
                        list.add(bean1);
                        Comparator<PatientFindHospitalBean> comparator= new BasedonHospitalnameComparision();
                        Collections.sort(list,comparator);
                        layout.setRefreshing(true);
                        adapter= new CustomPatientFindHospitalAdapter(list,context,fragmentActivity);
                        recyclerView.setAdapter(adapter);
                        layout.setRefreshing(false);
                        System.out.println("==finish===");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("===getPatientFinHospitalListData failure==");
            }
        });


    }

    public void getHospitalByCity(Context applicationContext, String city, final FragmentActivity fragmentActivity, RecyclerView recyclerView_patient_findhospital, SwipeRefreshLayout swiperefresh_patient_findhospital) {
        this.context = applicationContext;
        this.fragmentActivity = fragmentActivity;
        this.layout=swiperefresh_patient_findhospital;
        this.recyclerView=recyclerView_patient_findhospital;
        httpClient= new AsyncHttpClient();
        params= new RequestParams();
        params.put("city",city);
        System.out.println("====city======="+city);
        System.out.println("====in  getHospitalByCity====");
        httpClient.post(context, ServerCrendentialsUtility.URL+"PatientFindHospitalByCityController",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<PatientFindHospitalBean> list= new ArrayList<>();
                System.out.println("===in on success PatientFindHospitalByCityController====="+response);
                System.out.println("===length=="+response.length());

                if (response.length() == 0) {
                    Toast.makeText(context.getApplicationContext(), "No such data found", Toast.LENGTH_SHORT).show();

                } else {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            PatientFindHospitalBean bean1 = new PatientFindHospitalBean(object.get("hospital_name").toString(),
                                    object.get("hospital_locality").toString());
                            System.out.println("===name==" + object.get("hospital_name").toString());
                            System.out.println("===locality==" + object.get("hospital_locality").toString());
                            list.add(bean1);
                            Comparator<PatientFindHospitalBean> comparator = new BasedonHospitalnameComparision();
                            Collections.sort(list, comparator);
                            layout.setRefreshing(true);
                            adapter = new CustomPatientFindHospitalAdapter(list, context, fragmentActivity);
                            recyclerView.setAdapter(adapter);
                            layout.setRefreshing(false);
                            System.out.println("==finish===");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("===getPatientFinHospitalListData failure==");
            }
        });
    }
}
