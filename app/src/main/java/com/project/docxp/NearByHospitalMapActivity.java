package com.project.docxp;


import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

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
import com.project.docxp.bean.NearByHospitalBean;
import com.project.docxp.services.NearByHospitalServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class NearByHospitalMapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        ActivityCompat.OnRequestPermissionsResultCallback {
    private GoogleApiClient myGoogleApiClient;
    private Location myLocation;
    private double latitude, longitude;
    private GoogleMap myGoogleMap;
    private Marker myMarker;
    private LatLng mylatLng;
    private MarkerOptions myMarkerOptions;
    private LocationRequest myLocationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_hospital_map);
        System.out.println("=======SupportMapFragment======");

        SupportMapFragment mySupportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.near_by_hospital_map);
        mySupportMapFragment.getMapAsync(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermssion();
        }
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
        latitude = myLocation.getLatitude();
        longitude = myLocation.getLongitude();
        System.out.println("======latitude======"+latitude+"======longitude======="+longitude);
        mylatLng = new LatLng(latitude, longitude);
        myMarkerOptions = new MarkerOptions();
        myMarkerOptions.position(new LatLng(latitude, longitude)).title("Current Position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        myGoogleMap.addMarker(myMarkerOptions);

        //move map camera
        myGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
        myGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mylatLng));

        getAddress();

        //stop location updates
        if (myGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(myGoogleApiClient, this);
        }
    }

    @Override
    public void onBackPressed() {
        NearByHospitalMapActivity.super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
    }
    private void getAddress() {
        System.out.println("start get address.....");
        Address address = getAddress(latitude, longitude);
        System.out.println(" address....." + address);
        if (address != null) {
            System.out.println(" if condition  address.....");
            String addressline = address.getAddressLine(0);
            String locality = address.getLocality();
            System.out.println("country code....." + locality);
            //Toast.makeText(this, "adress"+addressline, Toast.LENGTH_LONG).show();
            Toast.makeText(this, "locality===" + locality, Toast.LENGTH_LONG).show();
            NearByHospitalBean bean = new NearByHospitalBean();
            bean.setLocality(locality);
            NearByHospitalServices services = new NearByHospitalServices();
            services.getNearByHospitalMarkers(myGoogleMap, bean);
        }
    }

    private Address getAddress(double latitude, double longitude) {
        Geocoder myGeocoder;
        List<Address> myAddressesList;
        Address myAddress = null;
        myGeocoder = new Geocoder(this, Locale.getDefault());
        try {
            myAddressesList = myGeocoder.getFromLocation(latitude, longitude, 1);
            myAddress = myAddressesList.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myAddress;
    }
}
