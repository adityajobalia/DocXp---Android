package com.project.docxp;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.project.docxp.bean.PateintHospitalDetailsBean;
import com.project.docxp.bean.PatientFindHospitalBean;
import com.project.docxp.services.PatientHospitalDetailsServices;
import com.project.docxp.utility.DataParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HospitalMap extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    ArrayList<LatLng> MarkerPoints;
    private GoogleApiClient myGoogleApiClient;
    private Location myLocation;
    private GoogleMap myGoogleMap;
    private Marker myMarker;
    private LatLng srclatLng, deslatlong;
    PatientFindHospitalBean bean;
    private MarkerOptions myMarkerOptionsrc, myMarkerOptiondes;
    private LocationRequest myLocationRequest;
    PateintHospitalDetailsBean hospitalDetailsBean;
    PatientHospitalDetailsServices services = new PatientHospitalDetailsServices();
    double longitude, latitude, hospital_longitude, hospital_latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_map);

        // Initializing
        MarkerPoints = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        hospitalDetailsBean = (PateintHospitalDetailsBean) bundle.getSerializable("hospital_obj");
        System.out.println("=======###hospitalDetailsBean ==" + hospitalDetailsBean.getHospital_name());
        System.out.println("========###@@@lat========" + hospitalDetailsBean.getHospital_lat() + hospitalDetailsBean.getHospital_long());

        hospital_longitude = hospitalDetailsBean.getHospital_long();
        hospital_latitude = hospitalDetailsBean.getHospital_lat();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.hospitalmap);
        mapFragment.getMapAsync(HospitalMap.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermssion();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myGoogleMap = googleMap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoolgeAPI();
                myGoogleMap.setMyLocationEnabled(true);
            }

        } else {
            buildGoolgeAPI();
            myGoogleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;
        if (myMarker != null) {
            myMarker.remove();
        }
        //Place current location marker
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        System.out.println("======latitude======" + latitude + "======longitude=======" + longitude);
        srclatLng = new LatLng(latitude, longitude);
        getroutebetweenuserandhospital();
        if (myGoogleApiClient != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(myGoogleApiClient, this);
        }

    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    public void getroutebetweenuserandhospital() {
        MarkerPoints.add(srclatLng);
        myMarkerOptionsrc = new MarkerOptions();
        myMarkerOptionsrc.position(srclatLng).title("current location");
        if (MarkerPoints.size() == 1) {
            myMarkerOptionsrc.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }

        // Already two locations
        if (MarkerPoints.size() > 1) {
            MarkerPoints.clear();
            myGoogleMap.clear();
        }
        deslatlong = new LatLng(hospital_latitude, hospital_longitude);
        MarkerPoints.add(deslatlong);
        myMarkerOptiondes = new MarkerOptions();
        myMarkerOptiondes.position(deslatlong).title(hospitalDetailsBean.getHospital_name());

        /**
         * For the start location, the color of marker is GREEN and
         * for the end location, the color of marker is RED.
         */
        if (MarkerPoints.size() == 2) {
            myMarkerOptiondes.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }
        myGoogleMap.addMarker(myMarkerOptionsrc);
        myGoogleMap.addMarker(myMarkerOptiondes);
        myGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(5));
        myGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(srclatLng));
        myGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(deslatlong));

        // Checks, whether start and end locations are captured
        if (MarkerPoints.size() >= 2) {
            LatLng origin = MarkerPoints.get(0);
            LatLng dest = MarkerPoints.get(1);

            // Getting URL to the Google Directions API
            String url = getUrl(origin, dest);
            Log.d("onMapClick", url.toString());
            FetchUrl FetchUrl = new FetchUrl();

            // Start downloading json data from Google Directions API
            FetchUrl.execute(url);
            //move map camera
            myGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
            myGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                myGoogleMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }

    private void getHospitalLatLong(PateintHospitalDetailsBean hospitalDetailsBean) {

        System.out.println("=======#####hospital contact==" + hospitalDetailsBean.getHospital_name());
        services.getHospitalLatLong(hospitalDetailsBean, myGoogleMap);

    }

    public boolean checkLocationPermssion() {
        return true;
    }

    private synchronized void buildGoolgeAPI() {
        myGoogleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).
                build();
        myGoogleApiClient.connect();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        myLocationRequest = new LocationRequest();
        myLocationRequest.setInterval(1000);
        myLocationRequest.setFastestInterval(1000);
        myLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(myGoogleApiClient, myLocationRequest, this);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        myGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onBackPressed() {
        HospitalMap.super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
    }

}
