package com.project.docxp.dao;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.DocNavigationActivity;
import com.project.docxp.LoginActivity;
import com.project.docxp.PatientNavigationActivity;
import com.project.docxp.bean.LoginBean;
import com.project.docxp.utility.ServerCrendentialsUtility;
import com.project.docxp.utility.SharedPreferenceData;
import com.project.docxp.utility.SharedPreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginDao {
    private boolean flag = false;
    public Context context;
    SharedPreferences preferences;
    private RequestParams requestParams;
    private AsyncHttpClient httpClient = new AsyncHttpClient();

    public void logInUser(final LoginActivity loginActivity, final LoginBean logBean, final ProgressDialog progressDialog) {
        this.context = loginActivity;
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("login_edittext_email", logBean.getEmail());
        requestParams.put("login_edittext_password", logBean.getPassword());
        httpClient.post(ServerCrendentialsUtility.URL + "LoginController", requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String auth = object.get("auth").toString();
                        logBean.setAuth(auth);
                        flag = true;
                        if (logBean.getAuth().equals("patient")) {
                            Toast.makeText(context, "patient", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(SharedPreferenceData.LOGIN_EMAIL, logBean.getEmail());
                            editor.putString(SharedPreferenceData.LOGIN_PASSWORD, logBean.getPassword());
                            editor.putString(SharedPreferenceData.LOGIN_AUTH, logBean.getAuth());
                            editor.apply();
                            registerToken(loginActivity, logBean);
                            Intent intent = new Intent(context, PatientNavigationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            loginActivity.finish();
                        } else if (logBean.getAuth().equals("doctor")) {
                            Toast.makeText(context, "doctor", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(SharedPreferenceData.LOGIN_EMAIL, logBean.getEmail());
                            editor.putString(SharedPreferenceData.LOGIN_PASSWORD, logBean.getPassword());
                            editor.putString(SharedPreferenceData.LOGIN_AUTH, logBean.getAuth());
                            editor.apply();
                            registerToken(loginActivity, logBean);
                            Intent intent = new Intent(context, DocNavigationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            loginActivity.finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, auth, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("Error in LoginDao class....");
                Toast.makeText(loginActivity, "No such Account found...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registerToken(LoginActivity loginActivity, final LoginBean logBean) {
        this.context = loginActivity;
        final BroadcastReceiver broadcastReceiver = loginActivity.getBroadCast(logBean);
        System.out.println("boolean value====" + flag);
        if (broadcastReceiver != null) {
            requestParams = new RequestParams();
            requestParams.add("email", preferences.getString(SharedPreferenceData.LOGIN_EMAIL, ""));
            requestParams.add("token", SharedPreferenceManager.getInstance(loginActivity).getToken());
            requestParams.add("auth", logBean.getAuth());
            httpClient.post(context, ServerCrendentialsUtility.URL + "NotificationController", requestParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    switch (logBean.getAuth()) {
                        case "patient": {
                            Toast.makeText(context, "Patient Token registered", Toast.LENGTH_LONG).show();
                            break;
                        }
                        case "doctor": {
                            Toast.makeText(context, "Doctor Token registered", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(context, "Error in registering token", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
