package com.project.docxp.dao;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.R;
import com.project.docxp.bean.PateintHospitalDetailsBean;
import com.project.docxp.customadapter.CustomerPateintHospitalDetailsRecyclerAdapter;
import com.project.docxp.utility.ServerCrendentialsUtility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Devarshi on 26-01-2018.
 */

public class PatientHospitalDetailsDao {
    private Context context;
    private AsyncHttpClient asyncHttpClient;
    private RequestParams requestParams;
    public PateintHospitalDetailsBean bean;
    private RecyclerView recyclerView;
    CustomerPateintHospitalDetailsRecyclerAdapter adapter;
    FragmentManager fragmentManager;
    GoogleMap map;

    public PateintHospitalDetailsBean getHospitalData(final Context context, PateintHospitalDetailsBean hospitalDetailsBean,
                                                      RecyclerView recycler_pateint_hospiitaldetails, final FragmentManager fragmentManager,
                                                      final TextView textview_hospital_title, final TextView textview_hospitaldetails_address,
                                                      final TextView textview_hospitaldetails_contect,
                                                      final TextView textview_hospitaldetails_website,
                                                      final ImageView imageview_patient_hospital_details) {
        this.bean = hospitalDetailsBean;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.recyclerView = recycler_pateint_hospiitaldetails;
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("hospital_name", bean.getHospital_name());
        requestParams.put("hospital_locality",bean.getHospital_locality());

        System.out.println("=====hospital_name===" + bean.getHospital_name());
        System.out.println("=====hospital_locality===" + bean.getHospital_locality());

        asyncHttpClient.post(context, ServerCrendentialsUtility.URL + "PatientHospitalDetailsController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("===response===" + response + "========" + response.length());
                ArrayList<PateintHospitalDetailsBean> arrayList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        //set data in doc list
                        JSONObject object = response.getJSONObject(i);
                        arrayList.add(new PateintHospitalDetailsBean(object.get("doctor_name").toString(),
                                object.get("doctor_speciality").toString(),
                                object.get("doctor_image").toString()));

                        adapter = new CustomerPateintHospitalDetailsRecyclerAdapter(arrayList, context, fragmentManager);
                        recyclerView.setAdapter(adapter);

                        //ser hospital details
                        String hospital_name = object.get("hospital_name").toString();
                        String hospital_address = object.get("hospital_address").toString();
                        String hospital_contact = object.get("hospital_contact").toString();
                        String hospital_website = object.get("hospital_website").toString();
                        String hospital_image = object.get("hospital_image").toString();
                        Picasso.with(context).load(ServerCrendentialsUtility.URL + hospital_image).placeholder(R.drawable.profile).error(R.drawable.profile).into(imageview_patient_hospital_details);


                        textview_hospital_title.setText(hospital_name);
                        textview_hospitaldetails_address.setText(/*"Address:" + "\n" +*/ hospital_address);
                        textview_hospitaldetails_contect.setText(/*"Contact:" + "\n" +*/ hospital_contact);
                        textview_hospitaldetails_website.setText(/*"Website:" + "\n" +*/ hospital_website);


                        String hospital_lat = object.get("hospital_lat").toString();
                        String hospital_long = object.get("hospital_long").toString();

                        Double latitude = Double.parseDouble(hospital_lat);
                        Double longitude = Double.parseDouble(hospital_long);
                        System.out.println("======$$$$hospital_lat=======" + hospital_lat);
                        System.out.println("=====$$$hospital_long========" + hospital_long);

                        bean.setHospital_lat(latitude);
                        bean.setHospital_long(longitude);

                        System.out.println("===" + object.get("doctor_name").toString() +
                                object.get("doctor_speciality").toString() +
                                object.get("doctor_image").toString());

                        System.out.println("======hospital_name=======" + hospital_name);
                        System.out.println("=====hospital_contact========" + hospital_contact);
                        System.out.println("========hospital_address========" + hospital_address);
                        System.out.println("=====hospital_website======" + hospital_website);
                        System.out.println("======hospital_website======" + hospital_website);
                        System.out.println("======hospital_image======" + hospital_image);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("===array failure===");
            }
        });
        return bean;
    }

    public void  getHospitalLatLongData(PateintHospitalDetailsBean hospitalDetailsBean, GoogleMap myGoogleMap) {
        this.map = myGoogleMap;
        this.bean = hospitalDetailsBean;
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();
        requestParams.put("hospital_name", hospitalDetailsBean.getHospital_name());
        System.out.println("========hospitalDetailsBean.getHospital_name###======" + hospitalDetailsBean.getHospital_name());
        asyncHttpClient.post(ServerCrendentialsUtility.URL + "PatientHospitalDetailsController", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("===response===" + response + "========" + response.length());
                ArrayList<PateintHospitalDetailsBean> arrayList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        System.out.println("======##response======" + response);
                        //set data in doc list
                        JSONObject object = response.getJSONObject(i);
                        String hospital_lat = object.get("hospital_lat").toString();
                        String hospital_long = object.get("hospital_long").toString();

                        Double latitude = Double.parseDouble(hospital_lat);
                        Double longitude = Double.parseDouble(hospital_long);
                        System.out.println("======hospital_lat=======" + hospital_lat);
                        System.out.println("=====hospital_long========" + hospital_long);

                        bean.setHospital_lat(latitude);
                        bean.setHospital_long(longitude);
                        if (map != null) {
                            System.out.println("========myGoogleMap======");
                            MarkerOptions myMarkerOptions = new MarkerOptions();
                            myMarkerOptions.position(new LatLng(latitude, longitude)).title(bean.getHospital_name() + bean.getHospital_address()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

                            map.addMarker(myMarkerOptions);
                            map.animateCamera(CameraUpdateFactory.zoomTo(11));
                            System.out.println("=======finish========");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray
                    errorResponse) {
                System.out.println("===array failure===");
            }

        });

    }
}