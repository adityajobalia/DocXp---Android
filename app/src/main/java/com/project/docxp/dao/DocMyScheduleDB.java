package com.project.docxp.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.bean.DocmyschedualBean;
import com.project.docxp.customadapter.CustomerDocmyschedualAdapter;
import com.project.docxp.utility.DocConfirmAppointmentonDate;
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
 * Created by Devarshi on 25-01-2018.
 */

public class DocMyScheduleDB {
    private Context context;
    private FragmentActivity fragmentActivity;
    private DocmyschedualBean bean ;
    private RecyclerView recyclerView;
    private AsyncHttpClient asyncHttpClient;
    private RequestParams requestParams;
    private CustomerDocmyschedualAdapter adapter;
    private SharedPreferences preferences;
    private SwipeRefreshLayout layout;

    public void getConfirmAppointmentList(final Context context, final FragmentActivity fragmentActivity, RecyclerView recycler_doc_myschedule, DocmyschedualBean bean, final SwipeRefreshLayout swiperefresh_doc_myschedual) {
        this.context = context;
        this.fragmentActivity = fragmentActivity;
        this.bean=bean;
        this.layout=swiperefresh_doc_myschedual;
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        this.recyclerView = recycler_doc_myschedule;
        asyncHttpClient= new AsyncHttpClient();
        requestParams= new RequestParams();
        requestParams.put("email",bean.getDoc_email());
        System.out.println("===email=="+bean.getDoc_email());
        requestParams.put("status","confirm");
        asyncHttpClient.post(context, ServerCrendentialsUtility.URL+"DoctorMyScheduleController",requestParams,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<DocmyschedualBean> list = new ArrayList<>();
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject object=response.getJSONObject(i);

                        list.add(new DocmyschedualBean(object.get("patient_name").toString(),
                                object.get("patient_gender").toString(),
                                object.get("patient_email").toString(),
                                object.get("pateint_contact").toString(),
                                object.get("pateint_date").toString(),
                                object.get("patient_time").toString(),
                                object.get("patient_status").toString()));
                        Comparator<DocmyschedualBean> comparator= new DocConfirmAppointmentonDate();
                        Collections.sort(list,comparator);
                        layout.setRefreshing(true);
                        adapter= new CustomerDocmyschedualAdapter(list,context,fragmentActivity);
                        recyclerView.setAdapter(adapter);
                        System.out.println("=====finfish==");
                        swiperefresh_doc_myschedual.setRefreshing(false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("===failure array==");
            }
        });
    }
}
