package com.project.docxp.dao;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.R;
import com.project.docxp.patientfragment.PatientBookAppointmentFragment;
import com.project.docxp.utility.ServerCrendentialsUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by adity on 15-01-2018.
 */

public class SpinnerDao {
    public Context mCtx;
    private AsyncHttpClient httpClient = new AsyncHttpClient();
    private RequestParams params;
    int id;

    public void getCityData(final FragmentActivity activity, final ArrayList<String> arrayList, final int spinner_patient_bookappointment_city) {
        this.mCtx = activity;
        this.id = spinner_patient_bookappointment_city;
        params = new RequestParams();
        params.put("auth", "city");
        httpClient.post(ServerCrendentialsUtility.URL + "SpinnerController", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String city = object.get("city"+i).toString();
                        Log.d("cities", city);
                        arrayList.add(city);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(mCtx, android.R.layout.simple_spinner_dropdown_item, arrayList);
                Spinner spinner_patient_bookappointment_city = (Spinner) activity.findViewById(id);
                spinner_patient_bookappointment_city.setAdapter(adapter);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.e("onFailure", "Failure method is called");
            }
        });
    }
}
