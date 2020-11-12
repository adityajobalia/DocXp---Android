package com.project.docxp.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.bean.BookAppointmentBean;
import com.project.docxp.utility.ServerCrendentialsUtility;
import com.project.docxp.utility.SharedPreferenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Lenovo on 27/12/2017.
 */

public class AppointmentDao {
    Context context;
    private boolean flag = false;
    private static String cityname, hospitalname,doctorname,speciality;
    BookAppointmentBean appointmentBean;
    AsyncHttpClient httpClient = new AsyncHttpClient();
    SharedPreferences preferences;

    public void getBookAppointmentEdittext(Context context, String doctorname, String speciality, final EditText editext_patient_bookappointment_edittext_city, final EditText editext_patient_bookappointment_edittext_hospital) {
        System.out.println("=========speciality appointment dao=========="+speciality);
        this.doctorname = doctorname;
        this.speciality = speciality;
        appointmentBean = new BookAppointmentBean();
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("edittext_doctorname", doctorname);
        requestParams.put("edittext_speciality",speciality);
        httpClient.post(ServerCrendentialsUtility.URL + "BookAppointmentDoctorDetailsController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println("=============success json array response ============="+response);
                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        cityname = object.getString("cityname");
                        hospitalname = object.getString("hospitalname");
                        appointmentBean.setPatient_city(cityname);
                        appointmentBean.setPatient_hospital(hospitalname);

                        editext_patient_bookappointment_edittext_city.setText(cityname);
                        editext_patient_bookappointment_edittext_city.setEnabled(false);
                        editext_patient_bookappointment_edittext_hospital.setText(hospitalname);
                        editext_patient_bookappointment_edittext_hospital.setEnabled(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("=============failure json array==================");
            }
        });

    }

    public void setBookAppointmentEdittext(final BookAppointmentBean bookAppointmentBean, final Context applicationContext, final ProgressDialog progressDialog) {
        this.context = applicationContext;
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);

        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("bookappointment_patient_name", bookAppointmentBean.getPatient_name());
        requestParams.put("bookappointment_patient_email", bookAppointmentBean.getPatient_email());
        requestParams.put("bookappointment_patient_contact", bookAppointmentBean.getPatient_contact());
        requestParams.put("bookappointment_patient_gender", bookAppointmentBean.getPatient_gender());
        requestParams.put("bookappointment_patient_city", cityname);
        requestParams.put("bookappointment_speciality",speciality);
        requestParams.put("bookappointment_patient_doctor", doctorname);
        requestParams.put("bookappointment_patient_hospital", hospitalname);
        requestParams.put("bookappointment_patient_date", bookAppointmentBean.getDate());
        requestParams.put("bookappointment_patient_time", bookAppointmentBean.getTime());
        httpClient.post(ServerCrendentialsUtility.URL + "BookAppointmentController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        if (object.get("message").toString().equals("successfull")) {
                            System.out.println("======" + object.get("message").toString());
                            flag = true;
                            bookAppointmentBean.setMessage((String) object.get("message"));
                            Toast.makeText(context, "Book appointment successful", Toast.LENGTH_SHORT).show();
                            sendNotificationToDoctor(applicationContext,doctorname);
                        } else {
                            progressDialog.dismiss();
                            String messages = object.get("message").toString();
                            bookAppointmentBean.setMessage(messages);
                            Toast.makeText(applicationContext, "Book appointment failed...please try again", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Toast.makeText(applicationContext, "success string", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(applicationContext, "try again !!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(applicationContext, "failure string", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void bookAppointment(final BookAppointmentBean bookAppointmentBean, final Context applicationContext, final ProgressDialog progressDialog) {
        this.context = applicationContext;
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("bookappointment_patient_name", bookAppointmentBean.getPatient_name());
        requestParams.put("bookappointment_patient_email", bookAppointmentBean.getPatient_email());
        requestParams.put("bookappointment_patient_contact", bookAppointmentBean.getPatient_contact());
        requestParams.put("bookappointment_patient_gender", bookAppointmentBean.getPatient_gender());
        requestParams.put("bookappointment_patient_city", bookAppointmentBean.getPatient_city());
        requestParams.put("bookappointment_speciality",bookAppointmentBean.getSpeciality());
        requestParams.put("bookappointment_patient_doctor", bookAppointmentBean.getPatient_doctor());
        requestParams.put("bookappointment_patient_hospital", bookAppointmentBean.getPatient_hospital());
        requestParams.put("bookappointment_patient_date", bookAppointmentBean.getDate());
        requestParams.put("bookappointment_patient_time", bookAppointmentBean.getTime());
        httpClient.post(ServerCrendentialsUtility.URL + "BookAppointmentController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        if (object.get("message").toString().equals("successfull")) {
                            System.out.println("======" + object.get("message").toString());
                            flag = true;
                            bookAppointmentBean.setMessage((String) object.get("message"));
                            Toast.makeText(context, "Book appointment successful", Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, "calling sendNotificationToDoctor", Toast.LENGTH_SHORT).show();
                            sendNotificationToDoctor(applicationContext,bookAppointmentBean.getPatient_doctor());
                            Toast.makeText(context, "called sendNotificationToDoctor", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            String messages = object.get("message").toString();
                            bookAppointmentBean.setMessage(messages);
                            Toast.makeText(applicationContext, "Book appointment failed...please try again", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Toast.makeText(applicationContext, "success string", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(applicationContext, "try again !!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(applicationContext, "failure string", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void setBookAppointmentDoctorSpeciality(final Context context, final BookAppointmentBean bookAppointmentBean, String cityname, String doctor, String hospitalname, String doc_speciality, final ProgressDialog progressDialog) {
        this.context = context;
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("bookappointment_patient_name", bookAppointmentBean.getPatient_name());
        requestParams.put("bookappointment_patient_email", bookAppointmentBean.getPatient_email());
        requestParams.put("bookappointment_patient_contact", bookAppointmentBean.getPatient_contact());
        requestParams.put("bookappointment_patient_gender", bookAppointmentBean.getPatient_gender());
        requestParams.put("bookappointment_patient_city", cityname);
        requestParams.put("bookappointment_patient_doctor", doctor);
        requestParams.put("bookappointment_patient_hospital", hospitalname);
        requestParams.put("bookappointment_speciality",doc_speciality);
        requestParams.put("bookappointment_patient_date", bookAppointmentBean.getDate());
        requestParams.put("bookappointment_patient_time", bookAppointmentBean.getTime());
        httpClient.post(ServerCrendentialsUtility.URL + "BookAppointmentController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        if (object.get("message").toString().equals("successfull")) {
                            System.out.println("======" + object.get("message").toString());
                            flag = true;
                            bookAppointmentBean.setMessage((String) object.get("message"));
                            Toast.makeText(context, "Book appointment successful", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            String messages = object.get("message").toString();
                            bookAppointmentBean.setMessage(messages);
                            Toast.makeText(context, "Book appointment failed...please try again", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println("============json array failure=================");
            }
        });


    }
    public void sendNotificationToDoctor(final Context applicationContext, String patient_doctor){
        RequestParams params = new RequestParams();
        Log.d("=====doctor name=====",patient_doctor);
        params.put("doctorName",patient_doctor);
        params.put("auth","patient");
        httpClient.post(applicationContext,ServerCrendentialsUtility.URL+"SendNotificationController",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(applicationContext, "Notification sent to doctor", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(applicationContext, "Error in send notification to doctor", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
