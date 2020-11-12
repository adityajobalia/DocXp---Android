package com.project.docxp.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.R;
import com.project.docxp.bean.PatientEmergencyContactBean;
import com.project.docxp.utility.ServerCrendentialsUtility;
import com.project.docxp.utility.SharedPreferenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Devarshi on 31-01-2018.
 */

public class PatientEmergencyContactDao {
    private Context context;
    private PatientEmergencyContactBean bean;
    private AsyncHttpClient client;
    private RequestParams params;
    SharedPreferences preferences;

    public void setContactDatainDB(PatientEmergencyContactBean contactDatainDB, final Context context) {
        this.context = context;
        this.bean = contactDatainDB;
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        client = new AsyncHttpClient();
        params = new RequestParams();
        params.put("name", bean.getContact_name());
        params.put("email", bean.getContact_email());
        params.put("mobile", bean.getContact_mobile());
        params.put("login_email", bean.getLogin_email());
        System.out.println("===###====" + bean.getContact_name() + "=========" + bean.getContact_email() + "==========" + bean.getContact_mobile());
        client.post(ServerCrendentialsUtility.URL + "PatientEmergencyContactFromController", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println("=========response=========" + response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        Toast.makeText(context, object.get("message").toString(), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("=========responseString============");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("========JSONArray========");
            }
        });

    }

    public void getEmergencyContactDataFromDB(Context context, PatientEmergencyContactBean bean, final TextView textview_emergencycontectlist_name, final TextView textview_emergencycontectlist_contect_no, final TextView textview_emergencycontectlist_email) {
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        client = new AsyncHttpClient();
        params = new RequestParams();
        params.put("login_email", bean.getLogin_email());
        System.out.println("=======bean.getLogin_email()========" + bean.getLogin_email());
        client.post(ServerCrendentialsUtility.URL + "PatientEmergencyContactController", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                System.out.println("=========response=========" + response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        textview_emergencycontectlist_name.setCompoundDrawablePadding(10);
                        textview_emergencycontectlist_name.setText(object.get("name").toString());
                        textview_emergencycontectlist_contect_no.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_mobile, 0, 0, 0);
                        textview_emergencycontectlist_contect_no.setCompoundDrawablePadding(10);
                        textview_emergencycontectlist_contect_no.setText(object.get("mobile").toString());
                        textview_emergencycontectlist_email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email,0,0,0);
                        textview_emergencycontectlist_email.setText(object.get("email").toString());
                        System.out.println("============" + object.get("name").toString() + "======" + object.get("mobile").toString() + "===" + object.get("email").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("=========responseString============");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("========JSONArray========");
            }
        });


    }

   /* public void removedata(final Context context, PatientEmergencyContactBean bean) {
        preferences=context.getSharedPreferences(SharedPreferenceData.SHAREPREF,Context.MODE_PRIVATE);
        client = new AsyncHttpClient();
        params = new RequestParams();
        this.context=context;
        this.bean=bean;
        params.put("login_email",bean.getLogin_email());
        params.put("name",bean.getContact_name());
        client.post(ServerCrendentialsUtility.URL+"PatientEmergencyRejectContactController",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("=======response======="+response);
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject object=response.getJSONObject(i);
                        Toast.makeText(context, object.get("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("=======================");
            }
        });
    }*/
}
