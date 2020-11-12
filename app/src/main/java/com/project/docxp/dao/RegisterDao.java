package com.project.docxp.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.LoginActivity;
import com.project.docxp.RegisterActivity;
import com.project.docxp.bean.RegisterBean;
import com.project.docxp.utility.ServerCrendentialsUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import cz.msebera.android.httpclient.Header;

public class RegisterDao {
    private boolean flag = false;
    public Context context;

    public void registerUser(final RegisterBean regBean, RegisterActivity registerActivity, ProgressDialog progressDialog) {
        this.context = registerActivity;

        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("register_edittext_name", regBean.getName());
        requestParams.put("register_edittext_email", regBean.getEmail());
        requestParams.put("register_edittext_password", regBean.getPassword());
        requestParams.put("register_edittext_mobile", regBean.getMobile());
        requestParams.put("login_circular_logo",regBean.getProfileimage());
        httpClient.post(ServerCrendentialsUtility.URL+"RegisterController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        if (object.get("message").toString().equals("success")) {
                            flag = true;
                            regBean.setMessage((String) object.get("message"));
                            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(context, LoginActivity.class).putExtra("register_edittext_mobile", regBean.getMobile()));
                        } else {
                            String messages = object.get("message").toString();
                            regBean.setMessage(messages);
                            Toast.makeText(context, "Account already exists!", Toast.LENGTH_SHORT).show();
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
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(context, "Registration Failed.... Try again  "+flag, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }
}
