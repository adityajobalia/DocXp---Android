package com.project.docxp.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.DocNavigationActivity;
import com.project.docxp.PatientNavigationActivity;
import com.project.docxp.bean.ProfileBean;
import com.project.docxp.utility.ServerCrendentialsUtility;
import com.project.docxp.utility.SharedPreferenceData;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Devarshi on 21-02-2018.
 */

public class NavigationDrawerDataDao {

    private AsyncHttpClient httpClient;
    private RequestParams params;
    SharedPreferences preferences;
    TextView textview_name;
    CircleImageView imageView_nav_header;
    ProfileBean bean;
    public Context context;


     public void getDocDrawrData(DocNavigationActivity docNavigationActivity, String email, final TextView textview_doc_name, final CircleImageView imageView_doctor_nav_header) {
          httpClient = new AsyncHttpClient();
          params = new RequestParams();
          context = docNavigationActivity;

          this.textview_name = textview_doc_name;
          this.imageView_nav_header = imageView_doctor_nav_header;

          preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
          params.put("doc_email", email);
          System.out.println("====doc_email=======" + email);
          httpClient.post(ServerCrendentialsUtility.URL + "DoctorNavigationDrawerDataController", params, new JsonHttpResponseHandler() {

              @Override
              public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                  System.out.println("==========JSONArray=============");
                  System.out.println("======response========" + response.length());

                  for (int i = 0; i < response.length(); i++) {
                      try {
                          JSONObject object = response.getJSONObject(i);
                          String doc_name = object.get("doc_nm").toString();
                          String doc_profile = object.get("doc_profile").toString();
                          bean = new ProfileBean();
                          String imagepath = ServerCrendentialsUtility.URL + doc_profile;
                          bean.setProfile_image(imagepath);
                          bean.setName(doc_name);
                          System.out.println("=========doc_name=======" + bean.getName());
                          System.out.println("========doc_profile=======" + bean.getProfile_image());
                          textview_name.setText(bean.getName());
                          Picasso.with(context).load(imagepath).into(imageView_nav_header);
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              }

              @Override
              public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                  System.out.println("========onFailure===========");
              }
          });
      }

      public void getPatientDrawrData(PatientNavigationActivity patientNavigationActivity, String email, TextView textview_patient_name, CircleImageView imageView_patient_nav_header) {
          httpClient = new AsyncHttpClient();
          params = new RequestParams();
          context = patientNavigationActivity;

          this.textview_name = textview_patient_name;
          this.imageView_nav_header = imageView_patient_nav_header;

          preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
          params.put("patient_email", email);
          System.out.println("====patient_email=======" + email);
          httpClient.post(ServerCrendentialsUtility.URL + "PatientNavigationDrawerDataController", params, new JsonHttpResponseHandler() {

              @Override
              public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                  System.out.println("==========JSONArray=============");
                  System.out.println("======response========" + response.length());

                  for (int i = 0; i < response.length(); i++) {
                      try {
                          JSONObject object = response.getJSONObject(i);
                          String patient_nm = object.get("patient_nm").toString();
                          String patient_profile = object.get("patient_profile").toString();
                          bean = new ProfileBean();
                          String imagepath = ServerCrendentialsUtility.URL + patient_profile;

                          System.out.println("=========patient_nm=======" + patient_nm);
                          System.out.println("========patient_profile=======" +patient_profile);
                          textview_name.setText(patient_nm);
                          Picasso.with(context).load(imagepath).into(imageView_nav_header);

                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              }

              @Override
              public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                  System.out.println("========onFailure===========");
              }
          });

      }


  /*  public void getDrawerData(String email, Context applicationContext) {
        context=applicationContext;
        params= new RequestParams();
        httpClient= new AsyncHttpClient();
        params.put("doc_email", email);
        System.out.println("====doc_email=======" + email);
        httpClient.post(ServerCrendentialsUtility.URL + "DoctorNavigationDrawerDataController", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("==========JSONArray=============");
                System.out.println("======response========" + response.length());

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        String doc_name = object.get("doc_nm").toString();
                        String doc_profile = object.get("doc_profile").toString();
                        bean = new ProfileBean();
                        String imagepath = ServerCrendentialsUtility.URL + doc_profile;
                        bean.setProfile_image(imagepath);
                        bean.setName(doc_name);

                        System.out.println("=========doc_name=======" + bean.getName());
                        System.out.println("========###new doc_profile=======" + bean.getProfile_image());
                        Intent intent = new Intent(context, DocNavigationActivity.class);
                        intent.putExtra("doc_name",doc_name);
                        intent.putExtra("doc_profile",imagepath);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        *//*SharedPreferences.Editor editor = preferences.edit();
                        editor.remove(SharedPreferenceData.DOCTOR_PROFILE_NAME);
                        editor.remove(SharedPreferenceData.DOCTOR_PROFILE_IMAGE);
                        editor.putString(SharedPreferenceData.DOCTOR_PROFILE_NAME,doc_name);
                        editor.putString(SharedPreferenceData.DOCTOR_PROFILE_IMAGE,doc_profile);*//*
                       context.startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("========onFailure===========");
            }
        });
   }*/
}
