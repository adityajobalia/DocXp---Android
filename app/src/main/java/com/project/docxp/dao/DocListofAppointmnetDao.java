package com.project.docxp.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.bean.DoclistofappointmentcontentBean;
import com.project.docxp.bean.DocmyschedualBean;
import com.project.docxp.customadapter.CustomDocListofAppointmentAdapter;
import com.project.docxp.utility.DocAppointmentBasedonDate;
import com.project.docxp.utility.DocAppointmentBasedonTime;
import com.project.docxp.utility.ServerCrendentialsUtility;
import com.project.docxp.utility.SharedPreferenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Devarshi on 12-01-2018.
 */

public class DocListofAppointmnetDao {


    private AsyncHttpClient asyncHttpClient;
    private RequestParams requestParams;
    private Context context;
    SharedPreferences preferences;
    DoclistofappointmentcontentBean doclistofappointmentcontentBean;
    private CustomDocListofAppointmentAdapter adapter;
    private RecyclerView recyclerView;
    FragmentActivity fragmentActivity;
    DocmyschedualBean docmyschedualBean;
    SwipeRefreshLayout swiperefresh_doc_listofappointment;

    public void getDocListofAppointmentData(Context applicationContext, final DoclistofappointmentcontentBean doclistofappointmentcontentBean, final FragmentActivity fragmentActivity, RecyclerView recyclerview_doc_listofappointment, final SwipeRefreshLayout swiperefresh_doc_listofappointment) {
        this.context = applicationContext;
        this.fragmentActivity = fragmentActivity;
        this.recyclerView = recyclerview_doc_listofappointment;
        this.doclistofappointmentcontentBean = doclistofappointmentcontentBean;
        this.swiperefresh_doc_listofappointment=swiperefresh_doc_listofappointment;
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("email", doclistofappointmentcontentBean.getDoc_email());
        System.out.println("======emial===" + doclistofappointmentcontentBean.getDoc_email());
        asyncHttpClient.post(context, ServerCrendentialsUtility.URL + "DoctorListofAppointmnetController", requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<DoclistofappointmentcontentBean> beandata = new ArrayList<DoclistofappointmentcontentBean>();
                System.out.println("====response" + response);
                System.out.println("====length==" + response.length());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        System.out.println("---i==" + i);
                        JSONObject object = response.getJSONObject(i);

                        System.out.println("==pateint_name==" + doclistofappointmentcontentBean.getTextview_doc_listofappointment_Name());
                        System.out.println("==pateint_time==" + doclistofappointmentcontentBean.getTextview_doc_listofappointment_Time());
                        System.out.println("==pateint_status==" + doclistofappointmentcontentBean.getStatus());

                        beandata.add(new DoclistofappointmentcontentBean(object.get("patient_name").toString(),
                                object.get("patient_gender").toString(), object.get("patient_email").toString(),
                                object.get("pateint_contact").toString(),
                                object.get("pateint_date").toString(),
                                object.get("patient_time").toString(),
                                object.get("patient_status").toString()));

                        Comparator<DoclistofappointmentcontentBean> comparator = new DocAppointmentBasedonDate();
                        Collections.sort(beandata, comparator);
                        swiperefresh_doc_listofappointment.setRefreshing(true);
                        adapter = new CustomDocListofAppointmentAdapter(beandata, context, fragmentActivity);
                        System.out.println("==go to set data in custom adapter==");
                        recyclerView.setAdapter(adapter);
                        swiperefresh_doc_listofappointment.setRefreshing(false);
                        System.out.println("===finish===");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("=========failure==========");
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("===failure obj====");
            }


        });


    }

    public void getDocListofAppointmentDatabysorting(final Context applicationContext, final DoclistofappointmentcontentBean doclistofappointmentcontentBean, final FragmentActivity fragmentActivity, RecyclerView recyclerview_doc_listofappointment) {
        this.context = applicationContext;
        this.fragmentActivity = fragmentActivity;
        this.recyclerView = recyclerview_doc_listofappointment;
        this.doclistofappointmentcontentBean = doclistofappointmentcontentBean;
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("email", doclistofappointmentcontentBean.getDoc_email());
        requestParams.put("date", doclistofappointmentcontentBean.getDoc_date());
        System.out.println("======emial===" + doclistofappointmentcontentBean.getDoc_email());
        asyncHttpClient.post(context, ServerCrendentialsUtility.URL + "DoctorListOfAppointmentSortingController", requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<DoclistofappointmentcontentBean> beandata = new ArrayList<DoclistofappointmentcontentBean>();
                System.out.println("====response" + response);
                System.out.println("====length==" + response.length());
                if (response.length() == 0) {
                    Toast.makeText(applicationContext.getApplicationContext(), "No such data found", Toast.LENGTH_SHORT).show();

                } else {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            System.out.println("---i==" + i);
                            JSONObject object = response.getJSONObject(i);
                            System.out.println("=====object==" + object);

                            System.out.println("==pateint_name==" + doclistofappointmentcontentBean.getTextview_doc_listofappointment_Name());
                            System.out.println("==pateint_time==" + doclistofappointmentcontentBean.getTextview_doc_listofappointment_Time());
                            System.out.println("==pateint_status==" + doclistofappointmentcontentBean.getStatus());
                            beandata.add(new DoclistofappointmentcontentBean(object.get("patient_name").toString(),
                                    object.get("patient_gender").toString(), object.get("patient_email").toString(),
                                    object.get("pateint_contact").toString(),
                                    object.get("pateint_date").toString(),
                                    object.get("patient_time").toString(),
                                    object.get("patient_status").toString()));

                            Comparator<DoclistofappointmentcontentBean> comparator = new DocAppointmentBasedonTime();
                            Collections.sort(beandata, comparator);

                            adapter = new CustomDocListofAppointmentAdapter(beandata, context, fragmentActivity);
                            System.out.println("==go to set data in custom adapter==");
                            recyclerView.setAdapter(adapter);
                            System.out.println("===finish===");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("=========failure==========");
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("===failure obj====");
            }


        });
    }

    public void removeDataFromAppointment(final Context context, final DoclistofappointmentcontentBean doclistofappointmentcontentBean) {
        this.context = context;
        this.doclistofappointmentcontentBean = doclistofappointmentcontentBean;
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("pateint_name", doclistofappointmentcontentBean.getTextview_doc_listofappointment_Name());
        System.out.println("======reject pateint_name=== " + doclistofappointmentcontentBean.getTextview_doc_listofappointment_Name());
        requestParams.put("pateint_email", doclistofappointmentcontentBean.getTextview_doc_listofappointment_Email());
        requestParams.put("pateint_date", doclistofappointmentcontentBean.getTextview_doc_listofappointment_Date());
        requestParams.put("pateint_time", doclistofappointmentcontentBean.getTextview_doc_listofappointment_Time());
        // requestParams.put("status",doclistofappointmentcontentBean.getStatus());

        asyncHttpClient.post(context, ServerCrendentialsUtility.URL + "DoctorAppointmentRejectionController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject object = null;
                    try {
                        object = response.getJSONObject(i);
                        System.out.println("==response==" + response);
                        String message = object.getString("status");
                        if (message.equals("reject")) {
                            System.out.println("=====message==" + message);
                            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                           } else {
                            Toast.makeText(context.getApplicationContext(), "Not reject", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String message = response.getString("status");
                    if (message.equals("reject")) {
                        System.out.println("=====message==" + message);
                        Toast.makeText(context.getApplicationContext(), "rejected", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Not reject", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("==String==");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("====JSONArray====");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("==JSONObject==");
            }
        });


    }

    public void conformAppointmentData(Context context, DoclistofappointmentcontentBean doclistofappointmentcontentBean) {
        this.context = context;
        this.doclistofappointmentcontentBean = doclistofappointmentcontentBean;
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("pateint_name", doclistofappointmentcontentBean.getTextview_doc_listofappointment_Name());
        System.out.println("======reject pateint_name=== " + doclistofappointmentcontentBean.getTextview_doc_listofappointment_Name());
        requestParams.put("pateint_email", doclistofappointmentcontentBean.getTextview_doc_listofappointment_Email());
        requestParams.put("pateint_date", doclistofappointmentcontentBean.getTextview_doc_listofappointment_Date());
        requestParams.put("pateint_time", doclistofappointmentcontentBean.getTextview_doc_listofappointment_Time());
        requestParams.put("status", doclistofappointmentcontentBean.getStatus());
        asyncHttpClient.post(context, ServerCrendentialsUtility.URL + "DoctorAppointmentConfirmController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("=====response" + response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("==JSONArray==");
            }
        });
    }

    public void completionAppointmentByDoctor(final Context context, DocmyschedualBean bean) {
        this.context = context;
        this.docmyschedualBean = bean;
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("pateint_name", bean.getTextview_doc_myschedula_Name());
        System.out.println("======reject pateint_name=== " + bean.getTextview_doc_myschedula_Name());
        requestParams.put("pateint_email", bean.getTextview_doc_myschedula_Email());
        requestParams.put("pateint_date", bean.getTextview_doc_myschedula_Date());
        requestParams.put("pateint_time", bean.getTextview_doc_myschedula_Time());

        asyncHttpClient.post(context, ServerCrendentialsUtility.URL + "DoctorAppointmentRejectionController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject object = null;
                    try {
                        object = response.getJSONObject(i);
                        System.out.println("==response==" + response);
                        String message = object.getString("status");
                        if (message.equals("reject")) {
                            System.out.println("=====message==" + message);
                            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context.getApplicationContext(), "Not reject", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void sendNotificationToPatient(final Context context, DoclistofappointmentcontentBean doclistofappointmentcontentBean) {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("patientEmail",doclistofappointmentcontentBean.getTextview_doc_listofappointment_Email());
        requestParams.put("auth","doctor");
        requestParams.put("status",doclistofappointmentcontentBean.getStatus());
        asyncHttpClient.post(context,ServerCrendentialsUtility.URL+"SendNotificationController",requestParams,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(context, "Notification sent to patient", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(context, "Failure in sending notification from Doctor side", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
