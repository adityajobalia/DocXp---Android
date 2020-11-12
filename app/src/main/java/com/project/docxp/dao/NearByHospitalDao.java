package com.project.docxp.dao;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.docxp.bean.NearByHospitalBean;
import com.project.docxp.utility.ServerCrendentialsUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Devarshi on 13-02-2018.
 */

public class NearByHospitalDao {
    private AsyncHttpClient asyncHttpClient;
    private RequestParams params;

    public void getNearByHospitalMarker(final GoogleMap myGoogleMap, NearByHospitalBean bean) {
        asyncHttpClient = new AsyncHttpClient();
        params = new RequestParams();
        params.put("locality", bean.getLocality());
        System.out.println("========locality======" + bean.getLocality());
        asyncHttpClient.post(ServerCrendentialsUtility.URL + "NearByHospitalController", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("====success json array====");
                System.out.println("====response====" + response);
                System.out.println("====response length====" + response.length());
                ArrayList<NearByHospitalBean> list = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        System.out.println("===inside json array====");
                        JSONObject object = response.getJSONObject(i);
                        list.add(new NearByHospitalBean(object.getDouble("latitude"), object.getDouble("longitude"), object.get("hospitalname").toString(),object.get("address").toString()));
                        System.out.println("======list==========" + list);
                        for (int j = 0; j < list.size(); j++) {
                            if (myGoogleMap != null) {
                                System.out.println("========myGoogleMap======");
                                MarkerOptions myMarkerOptions = new MarkerOptions();
                                myMarkerOptions.position(new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude())).title(list.get(i).getH_name()+"\n"+list.get(i).getAddress()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                                myGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                                myGoogleMap.addMarker(myMarkerOptions);
                                System.out.println("=======finish========");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("=======JSONArray faild======");
            }
        });

    }
}
