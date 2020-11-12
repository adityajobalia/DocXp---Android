package com.project.docxp.dao;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.ForgetPasswordActivity;
import com.project.docxp.OTPActivity;
import com.project.docxp.utility.ServerCrendentialsUtility;
import com.project.docxp.utility.SharedPreferenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class OtpDao {
    public Context mCtx;
    public void sendVerificationOtp(final ProgressDialog progressDialog, ForgetPasswordActivity forgetPasswordActivity, final int otp, final String email) {
        this.mCtx = forgetPasswordActivity;
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("otp",otp);
        requestParams.put("email",email);
        httpClient.post(mCtx, ServerCrendentialsUtility.URL+"MailServlet",requestParams,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0;i<response.length();i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String auth = object.getString("auth");
                        if (auth.equals("success")){
                            String message = object.getString("message");
                            String user = object.getString("user");
                            progressDialog.dismiss();
                            Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mCtx, OTPActivity.class);
                            intent.putExtra("otp",otp);
                            intent.putExtra("email",email);
                            intent.putExtra("user",user);
                            mCtx.startActivity(intent);
                        }
                        else if (auth.equals("failure")){
                            progressDialog.dismiss();
                            Toast.makeText(mCtx, "No such data found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Toast.makeText(mCtx, "Failed to send OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
