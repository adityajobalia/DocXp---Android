package com.project.docxp.dao;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.bean.BookAppointmentBean;
import com.project.docxp.utility.ServerCrendentialsUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Lenovo on 23/01/2018.
 */

public class BookAppointmentSpinnerDao {
    private Context context;
    private AsyncHttpClient asyncHttpClient;
    private RequestParams requestParams;
    BookAppointmentBean appointmentBean;
    FragmentActivity fragmentActivity;
    static Spinner spinner_pateint_bookappointmenrt_city, spinner_pateint_bookappointmenrt_doctor,
            spinner_pateint_bookappointmenrt_hospital, spinner_pateint_bookappointmenrt_speciality;
    private ArrayList<String> citylist = new ArrayList<>();
    private ArrayList<String> specialitylist = new ArrayList<>();
    private ArrayList<String> doctorlist = new ArrayList<>();
    private ArrayList<String> hospitallist = new ArrayList<>();
    JSONObject object;
    private static String patient_city;

    public Spinner getCitySpinnerData(Context applicationContext, final BookAppointmentBean appointmentBean, final Spinner spinner_pateint_bookappointmenrt_city) {
        System.out.println("=====spinner dao city spinner====");
        this.context = applicationContext;
        this.fragmentActivity = fragmentActivity;
        this.appointmentBean = appointmentBean;
        this.spinner_pateint_bookappointmenrt_city = spinner_pateint_bookappointmenrt_city;
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        System.out.println("======requestParams========" + requestParams);
        System.out.println("============hi============");
        asyncHttpClient.post(context, ServerCrendentialsUtility.URL + "SpinnerCityController", requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                System.out.println("====inside success method for city spinner====");
                System.out.println("====json array success====");
                List<BookAppointmentBean> appointmentBeanList = new ArrayList<BookAppointmentBean>();
                System.out.println("====response" + response);
                System.out.println("====length==" + response.length());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        System.out.println("===== i =====" + i);
                        object = response.getJSONObject(i);
                        System.out.println("===== object =====" + object);
                        JSONArray cityarray = object.getJSONArray("city");
                        for (int j = 0; j < cityarray.length(); j++) {
                            citylist.add(cityarray.getString(j));
                            Collections.sort(citylist);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, citylist);
                spinner_pateint_bookappointmenrt_city.setAdapter(adapter);
               /*System.out.println("=================get patient_city===================");
               patient_city = spinner_pateint_bookappointmenrt_city.getSelectedItem().toString();
               System.out.println("============patient_city============"+patient_city);*/
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("====json object success====" + response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println("====string success====");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("====spinner string failure====");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("====json array failure====");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("====json object failure====");
            }
        });
        return spinner_pateint_bookappointmenrt_city;
    }

    public void getDoctorSpinnerData(Context applicationContext, final BookAppointmentBean appointmentBean, final Spinner spinner_pateint_bookappointmenrt_doctor, String patient_hospital, String speciality) {
        System.out.println("==========spinner dao : doctor spinner============");
        this.context = applicationContext;
        this.appointmentBean = appointmentBean;
        this.spinner_pateint_bookappointmenrt_doctor = spinner_pateint_bookappointmenrt_doctor;
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("hospital", patient_hospital);
        requestParams.put("speciality",speciality);
        asyncHttpClient.post(context, ServerCrendentialsUtility.URL + "SpinnerDoctorController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("====doctor success json array====");
                System.out.println("====response====" + response);
                System.out.println("====response length====" + response.length());
                doctorlist.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        System.out.println("===inside json array====");
                        object = response.getJSONObject(i);
                        JSONArray doctor = object.getJSONArray("doctorlist");
                        for (int j = 0; j < doctor.length(); j++) {
                            doctorlist.add(doctor.getString(j));
                            Collections.sort(doctorlist);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // spinner_pateint_bookappointmenrt_doctor.setSelection(0);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, doctorlist);
                spinner_pateint_bookappointmenrt_doctor.setAdapter(adapter);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("=====failure json array=====");
            }
        });


    }

    public Spinner getHospitalSpinnerData(Context applicationContext, final BookAppointmentBean appointmentBean, final Spinner spinner_pateint_bookappointmenrt_hospital, String speciality, String city) {
        System.out.println("=====spinner dao : hospital spinner====");
        this.context = applicationContext;
        this.appointmentBean = appointmentBean;
        this.spinner_pateint_bookappointmenrt_hospital = spinner_pateint_bookappointmenrt_hospital;
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("speciality",speciality);
        requestParams.put("city",city);
        asyncHttpClient.post(context, ServerCrendentialsUtility.URL + "SpinnerHospitalController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("====hospital success json array====");
                System.out.println("====response====" + response);
                System.out.println("====response length====" + response.length());
                hospitallist.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        System.out.println("===inside json array====");
                        JSONObject object1 = response.getJSONObject(i);
                        JSONArray hospital = object1.getJSONArray("hospitallist");
                        for (int j = 0; j < hospital.length(); j++) {
                            hospitallist.add(hospital.getString(j));
                            Collections.sort(hospitallist);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, hospitallist);
                spinner_pateint_bookappointmenrt_hospital.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("=====failure json array :====" + errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("=====json object :====" + response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println("=====string success :====" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("=====failure string :====" + responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("=====failure json object :====" + errorResponse);
            }
        });
        return spinner_pateint_bookappointmenrt_hospital;
    }

    public Spinner getSpecialitySpinnerData(final Context context, BookAppointmentBean bookAppointmentBean, final Spinner spinner_pateint_bookappointmenrt_speciality, String city) {
        System.out.println("==========spinner dao : speciality spinner============");
        this.context = context;
        this.appointmentBean = bookAppointmentBean;
        this.spinner_pateint_bookappointmenrt_speciality = spinner_pateint_bookappointmenrt_speciality;
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("city", city);
        asyncHttpClient.post(context, ServerCrendentialsUtility.URL + "SpinnerSpecialityController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("====speciality success json array====");
                System.out.println("====response====" + response);
                System.out.println("====response length====" + response.length());
                specialitylist.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        System.out.println("===speciality inside json array====");
                        object = response.getJSONObject(i);
                        JSONArray speciality = object.getJSONArray("specialitylist");
                        for (int j = 0; j < speciality.length(); j++) {
                            specialitylist.add(speciality.getString(j));
                            Collections.sort(specialitylist);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // spinner_pateint_bookappointmenrt_doctor.setSelection(0);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, specialitylist);
                spinner_pateint_bookappointmenrt_speciality.setAdapter(adapter);

            }
        });
        return spinner_pateint_bookappointmenrt_speciality;
    }

}