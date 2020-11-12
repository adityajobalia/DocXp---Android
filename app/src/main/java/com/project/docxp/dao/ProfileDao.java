package com.project.docxp.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.DocProfileActivity;
import com.project.docxp.PatientProfileActivity;
import com.project.docxp.bean.ProfileBean;
import com.project.docxp.utility.ServerCrendentialsUtility;
import com.project.docxp.utility.SharedPreferenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class ProfileDao {
    private AsyncHttpClient httpClient;
    private RequestParams requestParams;
    public Context mCtx;
    SharedPreferences preferences;
    ProfileBean profileBean;

    public void getPatientData(final PatientProfileActivity patientProfileActivity, final ProfileBean profileBean, ProgressDialog progressDialog) {
        this.mCtx = patientProfileActivity;
        this.profileBean = profileBean;
        preferences = mCtx.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        httpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("email", profileBean.getEmail());
        System.out.println("==========email" + profileBean.getEmail());
        requestParams.put("password", profileBean.getPassword());
        System.out.println("==========password" + profileBean.getPassword());
        httpClient.post(mCtx, ServerCrendentialsUtility.URL + "PatientProfileController", requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String name = object.getString("name");

                        String mobile = object.getString("mobile");
                        String auth = object.getString("auth");
                        String profileimage = object.getString("profileimage");


                        switch (auth) {
                            case "doctor": {
                                break;
                            }
                            case "patient": {
                                Log.d("Profile DAO", "setting values into SharedPreferences");
                                profileBean.setProfile_image(profileimage);
                                profileBean.setName(name);
                                profileBean.setMobile(mobile);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(SharedPreferenceData.PATIENT_PROFILE_NAME, name);
                                editor.putString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, profileBean.getEmail());
                                editor.putString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, profileBean.getPassword());
                                editor.putString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, mobile);
                                editor.putString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, profileimage);

                                editor.apply();

                                patientProfileActivity.prepareData(profileBean);
                                break;
                            }
                        }
                    } catch (JSONException e) {
                        Log.e("Error in ProfileDao", "JSON exception");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(mCtx, "Failed to retrieve profile data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatePatientData(final ProgressDialog progressDialog, ProfileBean profileBean, String field, final PatientProfileActivity patientProfileActivity) {
        this.mCtx = patientProfileActivity;
        this.profileBean = profileBean;
        httpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        preferences = mCtx.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        switch (field) {
            case "name": {
                requestParams.put("name", this.profileBean.getName());
                System.out.println("=============name===" + this.profileBean.getName());
                requestParams.put("email", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                System.out.println("============email===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                requestParams.put("field", "name");
                requestParams.put("password", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));
                System.out.println("============pass===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));

                requestParams.put("mobile", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));
                System.out.println("============mobile===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));

                requestParams.put("image", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));
                System.out.println("============image===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));

                httpClient.post(mCtx, ServerCrendentialsUtility.URL + "ProfileUpdateController", requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                System.out.println("============jsonobj" + object);
                                String message = object.getString("message");
                                System.out.println("========msg" + message);
                                if (message.equals("success")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(mCtx, "Name changed successfully", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.remove(SharedPreferenceData.PATIENT_PROFILE_NAME);
                                    System.out.println("json===========name=====" + SharedPreferenceData.PATIENT_PROFILE_NAME + "=====" + ProfileDao.this.profileBean.getName());
                                    editor.putString(SharedPreferenceData.PATIENT_PROFILE_NAME, ProfileDao.this.profileBean.getName());
                                    editor.apply();
                                    System.out.println("json  ======image==" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));
                                    ProfileDao.this.profileBean.setProfile_image(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));
                                    System.out.println("json  ======pass==" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));
                                    ProfileDao.this.profileBean.setPassword(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));
                                    System.out.println("json  ======email==" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                                    ProfileDao.this.profileBean.setEmail(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                                    System.out.println("json  ======mobile==" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));

                                    ProfileDao.this.profileBean.setMobile(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            patientProfileActivity.prepareData(ProfileDao.this.profileBean);

                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Toast.makeText(mCtx, "Name updation failure", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
            case "email": {
                requestParams.put("email", this.profileBean.getEmail());
                System.out.println("==========email=" + this.profileBean.getEmail());
                requestParams.put("oldemail", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                System.out.println("===========oldemail=" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                requestParams.put("field", "email");
                requestParams.put("name", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));
                System.out.println("===========name=========" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));
                requestParams.put("password", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));
                System.out.println("===========pass=========" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));

                requestParams.put("mobile", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));
                System.out.println("===========mobile=========" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));

                requestParams.put("image", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));
                System.out.println("===========image=========" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));

                httpClient.post(mCtx, ServerCrendentialsUtility.URL + "ProfileUpdateController", requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                System.out.println("=====jobj=======" + object);
                                String message = object.getString("message");
                                System.out.println("====msg=" + message);
                                if (message.equals("success")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(mCtx, "Email changed successfully", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.remove(SharedPreferenceData.PATIENT_PROFILE_EMAIL);
                                    editor.remove(SharedPreferenceData.LOGIN_EMAIL);
                                    editor.putString(SharedPreferenceData.LOGIN_EMAIL, ProfileDao.this.profileBean.getEmail());
                                    System.out.println("===========jemail=========" + ProfileDao.this.profileBean.getEmail());

                                    editor.putString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ProfileDao.this.profileBean.getEmail());
                                    editor.apply();
                                    System.out.println("===========jimage=========" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));

                                    ProfileDao.this.profileBean.setProfile_image(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));

                                    System.out.println("===========jpass=========" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));

                                    ProfileDao.this.profileBean.setPassword(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));
                                    System.out.println("===========jname=========" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));

                                    ProfileDao.this.profileBean.setName(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));
                                    System.out.println("===========jmobile=========" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));

                                    ProfileDao.this.profileBean.setMobile(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            patientProfileActivity.prepareData(ProfileDao.this.profileBean);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Toast.makeText(mCtx, "Email updation failure..", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
            case "password": {
                requestParams.put("email", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                System.out.println("=====email===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                requestParams.put("password", this.profileBean.getPassword());
                System.out.println("====pass==" + this.profileBean.getPassword());
                requestParams.put("field", "password");
                requestParams.put("name", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));
                System.out.println("==========name==" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));
                requestParams.put("mobile", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));
                System.out.println("====mobile===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));
                requestParams.put("image", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));
                System.out.println("=====image===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));
                httpClient.post(mCtx, ServerCrendentialsUtility.URL + "ProfileUpdateController", requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                System.out.println("=====jobj=" + object);
                                String message = object.getString("message");
                                System.out.println("========mes===" + message);
                                if (message.equals("success")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(mCtx, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.remove(SharedPreferenceData.PATIENT_PROFILE_PASSWORD);
                                    editor.remove(SharedPreferenceData.LOGIN_PASSWORD);
                                    editor.putString(SharedPreferenceData.LOGIN_PASSWORD, ProfileDao.this.profileBean.getPassword());
                                    editor.putString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ProfileDao.this.profileBean.getPassword());
                                    editor.apply();
                                    System.out.println("===========jimage=========" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));

                                    ProfileDao.this.profileBean.setProfile_image(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));
                                    System.out.println("===========jname=========" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));

                                    ProfileDao.this.profileBean.setName(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));
                                    System.out.println("===========jmobile=========" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));

                                    ProfileDao.this.profileBean.setMobile(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));
                                    System.out.println("===========jemail=========" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));

                                    ProfileDao.this.profileBean.setEmail(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            patientProfileActivity.prepareData(ProfileDao.this.profileBean);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Toast.makeText(mCtx, "Password Updation failure", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
            case "mobile": {
                requestParams.put("email", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                System.out.println("====emal===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                requestParams.put("mobile", this.profileBean.getMobile());
                System.out.println("===mobile===" + this.profileBean.getMobile());
                requestParams.put("field", "mobile");
                requestParams.put("password", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));
                System.out.println("===pass===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));
                requestParams.put("name", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));
                System.out.println("=======name==" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));
                requestParams.put("image", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));
                System.out.println("===image==" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));
                httpClient.post(mCtx, ServerCrendentialsUtility.URL + "ProfileUpdateController", requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                System.out.println("===jobj==" + object);
                                String message = object.getString("message");
                                System.out.println("======jmsg=" + message);
                                if (message.equals("success")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(mCtx, "Mobile changed successfully", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.remove(SharedPreferenceData.PATIENT_PROFILE_MOBILE);

                                    System.out.println("===========jmobie=========" + ProfileDao.this.profileBean.getMobile());

                                    editor.putString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ProfileDao.this.profileBean.getMobile());
                                    editor.apply();
                                    System.out.println("====jimage====" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));
                                    ProfileDao.this.profileBean.setProfile_image(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ""));
                                    System.out.println("====jname====" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));

                                    ProfileDao.this.profileBean.setName(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));
                                    System.out.println("====jpass====" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));

                                    ProfileDao.this.profileBean.setPassword(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));
                                    System.out.println("====jemail====" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                                    ProfileDao.this.profileBean.setEmail(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            patientProfileActivity.prepareData(ProfileDao.this.profileBean);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Toast.makeText(mCtx, "Mobile updation failure", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }
    }


    public void getDoctorData(final DocProfileActivity docProfileActivity, final ProfileBean profileBean, ProgressDialog progressDialog) {

        System.out.println("======go to get data in doctor===");
        this.mCtx = docProfileActivity;
        this.profileBean = profileBean;
        preferences = mCtx.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        httpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("email", profileBean.getEmail());
        requestParams.put("password", profileBean.getPassword());
        httpClient.post(mCtx, ServerCrendentialsUtility.URL + "DoctorProfileController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String name = object.getString("name");

                        String mobile = object.getString("mobile");
                        String speciality = object.getString("speciality");
                        String auth = object.getString("auth");
                        String profileimage = object.getString("profileimage");


                        switch (auth) {
                            case "doctor": {
                                Log.d("Profile DAO", "setting values into SharedPreferences");
                                profileBean.setName(name);
                                profileBean.setMobile(mobile);
                                profileBean.setSpeciality(speciality);
                                profileBean.setProfile_image(profileimage);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(SharedPreferenceData.DOCTOR_PROFILE_NAME, name);
                                System.out.println("=============" + name);
                                editor.putString(SharedPreferenceData.DOCTOR_PROFILE_SPECIALITY, speciality);
                                System.out.println("=============" + speciality);
                                editor.putString(SharedPreferenceData.DOCTOR_PROFILE_EMAIL, profileBean.getEmail());
                                System.out.println("=============" + profileBean.getEmail());
                                editor.putString(SharedPreferenceData.DOCTOR_PROFILE_PASSWORD, profileBean.getPassword());
                                System.out.println("=============" + profileBean.getPassword());
                                editor.putString(SharedPreferenceData.DOCTOR_PROFILE_MOBILE, mobile);
                                System.out.println("=============" + mobile);
                                editor.putString(SharedPreferenceData.DOCTOR_PROFILE_IMAGE, profileimage);
                                System.out.println("=============" + profileimage);

                                editor.apply();

                                docProfileActivity.prepareData(profileBean);
                                break;
                            }
                            case "patient": {

                                break;
                            }
                        }
                    } catch (JSONException e) {
                        Log.e("Error in ProfileDao", "JSON exception");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(mCtx, "Failed to fetch profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateDoctorData(final ProgressDialog progressDialog, final ProfileBean profileBean, String field, final DocProfileActivity docProfileActivity) {
        this.mCtx = docProfileActivity;
        this.profileBean = profileBean;
        httpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        preferences = mCtx.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        switch (field) {
            case "mobile": {
                requestParams = new RequestParams();
                requestParams.put("email", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_EMAIL, ""));
                requestParams.put("mobile", profileBean.getMobile());
                requestParams.put("field", "mobile");
                requestParams.put("speciality", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_SPECIALITY, ""));
                requestParams.put("password", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_PASSWORD, ""));
                requestParams.put("name", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_NAME, ""));
                requestParams.put("profileimage", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_IMAGE, ""));
                System.out.println("============image"+ preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_IMAGE, ""));
                httpClient.post(mCtx, ServerCrendentialsUtility.URL + "DoctorUpdateController", requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String message = object.getString("message");
                                if (message.equals("success")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(mCtx, "Mobile changed successfully", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.remove(SharedPreferenceData.DOCTOR_PROFILE_MOBILE);
                                    editor.putString(SharedPreferenceData.DOCTOR_PROFILE_MOBILE, profileBean.getMobile());
                                    editor.apply();
                                    profileBean.setName(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_NAME, ""));
                                    profileBean.setPassword(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_PASSWORD, ""));
                                    profileBean.setSpeciality(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_SPECIALITY, ""));
                                    profileBean.setEmail(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_EMAIL, ""));
                                    profileBean.setProfile_image(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_IMAGE, ""));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            docProfileActivity.prepareData(profileBean);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Toast.makeText(mCtx, "Mobile updation failure", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
            case "password": {
                requestParams = new RequestParams();
                requestParams.put("email", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_EMAIL, ""));
                requestParams.put("password", profileBean.getPassword());
                requestParams.put("field", "password");
                requestParams.put("speciality", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_SPECIALITY, ""));
                requestParams.put("name", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_NAME, ""));
                requestParams.put("mobile", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_MOBILE, ""));
                requestParams.put("profileimage", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_IMAGE, ""));
                System.out.println("============image"+ preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_IMAGE, ""));

                httpClient.post(mCtx, ServerCrendentialsUtility.URL + "DoctorUpdateController", requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String message = object.getString("message");
                                if (message.equals("success")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(mCtx, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.remove(SharedPreferenceData.DOCTOR_PROFILE_PASSWORD);
                                    editor.remove(SharedPreferenceData.LOGIN_PASSWORD);
                                    editor.putString(SharedPreferenceData.LOGIN_PASSWORD, profileBean.getPassword());
                                    editor.putString(SharedPreferenceData.DOCTOR_PROFILE_PASSWORD, profileBean.getPassword());
                                    editor.apply();
                                    profileBean.setSpeciality(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_SPECIALITY,""));
                                    profileBean.setName(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_NAME, ""));
                                    profileBean.setMobile(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_MOBILE, ""));
                                    profileBean.setEmail(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_EMAIL, ""));
                                    profileBean.setProfile_image(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_IMAGE, ""));

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            docProfileActivity.prepareData(profileBean);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Toast.makeText(mCtx, "Password Updation failure", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
            case "email": {
                requestParams = new RequestParams();
                requestParams.put("email", profileBean.getEmail());
                requestParams.put("oldemail", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_EMAIL, ""));
                requestParams.put("field", "email");
                requestParams.put("speciality", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_SPECIALITY, ""));
                requestParams.put("name", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_NAME, ""));
                requestParams.put("password", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_PASSWORD, ""));
                requestParams.put("mobile", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_MOBILE, ""));
                requestParams.put("profileimage", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_IMAGE, ""));
                System.out.println("============image"+ preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_IMAGE, ""));

                httpClient.post(mCtx, ServerCrendentialsUtility.URL + "DoctorUpdateController", requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String message = object.getString("message");
                                if (message.equals("success")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(mCtx, "Email changed successfully", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.remove(SharedPreferenceData.DOCTOR_PROFILE_EMAIL);
                                    editor.remove(SharedPreferenceData.LOGIN_EMAIL);
                                    editor.putString(SharedPreferenceData.LOGIN_EMAIL, profileBean.getEmail());
                                    editor.putString(SharedPreferenceData.DOCTOR_PROFILE_EMAIL, profileBean.getEmail());
                                    editor.apply();
                                    profileBean.setSpeciality(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_SPECIALITY,""));
                                    profileBean.setPassword(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_PASSWORD, ""));
                                    profileBean.setName(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_NAME, ""));
                                    System.out.println("============email name"+preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_NAME, ""));
                                    profileBean.setMobile(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_MOBILE, ""));
                                    System.out.println("===========email mobile"+preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_MOBILE, ""));
                                    profileBean.setProfile_image(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_IMAGE, ""));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            docProfileActivity.prepareData(profileBean);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Toast.makeText(mCtx, "Email updation failure..", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
        }

    }

    public void updatePatientImage(final ProgressDialog progressDialog, final ProfileBean profileBean, String field, final PatientProfileActivity profileActivity) {
        this.mCtx = profileActivity;
        this.profileBean = profileBean;
        httpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        preferences = mCtx.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        switch (field) {
            case "profileimage": {
                requestParams= new RequestParams();
                System.out.println("======go to updateimage===");
                requestParams.put("profileimage", this.profileBean.getProfile_image());
                System.out.println("==============image===" + this.profileBean.getProfile_image());
                requestParams.put("name", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));
                System.out.println("==============name===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));
                requestParams.put("email", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                System.out.println("=============email===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));

                requestParams.put("field", "profileimage");
                System.out.println("=======field=" + "profileimage");
                requestParams.put("password", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));
                System.out.println("=============password===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));
                requestParams.put("mobile", preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));
                System.out.println("=============mobile===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));

                httpClient.post(mCtx, ServerCrendentialsUtility.URL + "ProfileUpdateController", requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                System.out.println("============jsonobj" + object);
                                String message = object.getString("message");
                                System.out.println("=========mes=" + message);
                                if (message.equals("success")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(mCtx, "Image changed successfully", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.remove(SharedPreferenceData.PATIENT_PROFILE_IMAGE);
                                    System.out.println("=========jimage" + ProfileDao.this.profileBean.getProfile_image());
                                    editor.putString(SharedPreferenceData.PATIENT_PROFILE_IMAGE, ProfileDao.this.profileBean.getProfile_image());
                                    editor.apply();
                                    System.out.println("=========jname=" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));
                                   profileBean.setName(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_NAME, ""));
                                    System.out.println("=============jpassword===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));
                                    profileBean.setPassword(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_PASSWORD, ""));
                                    System.out.println("=============jemail===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));

                                    profileBean.setEmail(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_EMAIL, ""));
                                    System.out.println("=============jmobile===" + preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));

                                   profileBean.setMobile(preferences.getString(SharedPreferenceData.PATIENT_PROFILE_MOBILE, ""));

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("=== in   profileActivity.prepareData(profileBean);=="+profileActivity);
                            //profileActivity.prepareData(profileBean);
                            System.out.println("====in profileActivity.getProfileData();=="+profileActivity);
                            profileActivity.getProfileData();

                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Toast.makeText(mCtx, "Name updation failure", Toast.LENGTH_SHORT).show();
                    }
                });


                break;
            }

        }

    }

    public void updateDocImage(final ProgressDialog progressDialog, final ProfileBean profileBean, String field, final DocProfileActivity docProfileActivity) {
        this.mCtx =docProfileActivity;
        this.profileBean = profileBean;
        httpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        preferences = mCtx.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        switch (field) {
            case "profileimage": {
                requestParams= new RequestParams();
                System.out.println("======go to updateimage===");
                requestParams.put("profileimage", this.profileBean.getProfile_image());
                System.out.println("==============image===" + this.profileBean.getProfile_image());
                requestParams.put("name", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_NAME, ""));
                System.out.println("==============name===" + preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_NAME, ""));
                requestParams.put("email", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_EMAIL, ""));
                System.out.println("=============email===" + preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_EMAIL, ""));

                requestParams.put("field", "profileimage");
                System.out.println("=======field=" + "profileimage");
                requestParams.put("password", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_PASSWORD, ""));
                System.out.println("=============password===" + preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_PASSWORD, ""));
                requestParams.put("mobile", preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_MOBILE, ""));
                System.out.println("=============mobile===" + preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_MOBILE, ""));

                httpClient.post(mCtx, ServerCrendentialsUtility.URL + "DoctorUpdateController", requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                System.out.println("============jsonobj" + object);
                                String message = object.getString("message");
                                System.out.println("=========mes=" + message);
                                if (message.equals("success")) {
                                   // progressDialog.dismiss();
                                    Toast.makeText(mCtx, "Image changed successfully", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.remove(SharedPreferenceData.DOCTOR_PROFILE_IMAGE);
                                    System.out.println("=========jimage" + ProfileDao.this.profileBean.getProfile_image());
                                    editor.putString(SharedPreferenceData.DOCTOR_PROFILE_IMAGE, ProfileDao.this.profileBean.getProfile_image());
                                    editor.apply();
                                    System.out.println("=========jname=" + preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_NAME, ""));
                                    profileBean.setName(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_NAME, ""));
                                    System.out.println("=============jpassword===" + preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_PASSWORD, ""));
                                   profileBean.setPassword(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_PASSWORD, ""));
                                    System.out.println("=============jemail===" + preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_EMAIL, ""));

                                    profileBean.setEmail(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_EMAIL, ""));
                                    System.out.println("=============jmobile===" + preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_MOBILE, ""));

                                   profileBean.setMobile(preferences.getString(SharedPreferenceData.DOCTOR_PROFILE_MOBILE, ""));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //docProfileActivity.prepareData(profileBean);
                            docProfileActivity.getProfileData();

                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Toast.makeText(mCtx, "Name updation failure", Toast.LENGTH_SHORT).show();
                    }
                });


                break;
            }

        }
    }

}