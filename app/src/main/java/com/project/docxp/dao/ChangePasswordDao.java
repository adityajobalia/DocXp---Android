package com.project.docxp.dao;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.ChangePasswordActivity;
import com.project.docxp.LoginActivity;
import com.project.docxp.bean.ChangePasswordBean;
import com.project.docxp.utility.ServerCrendentialsUtility;
import com.project.docxp.utility.SharedPreferenceData;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by adity on 01-01-2018.
 */

public class ChangePasswordDao {
    public Context mCtx;
    public void updatePassword(ChangePasswordBean passwordBean, ChangePasswordActivity changePasswordActivity, final ProgressDialog progressDialog) {
        this.mCtx = changePasswordActivity;
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("user",passwordBean.getAuth());
        requestParams.put("email",passwordBean.getEmail());
        requestParams.put("password",passwordBean.getPassword());
        httpClient.post(mCtx, ServerCrendentialsUtility.URL+"ChangePasswordController",requestParams,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0;i<response.length();i++){
                    try {
                        String message = response.getJSONObject(i).getString("message");
                        if (message.equals("success")){
                            progressDialog.dismiss();
                            Toast.makeText(mCtx, "Password updated successfully", Toast.LENGTH_SHORT).show();
                            mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(mCtx, "Password update failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
