package com.project.docxp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class GeofenceActivity extends AppCompatActivity {
    private ToggleButton togglebtn_geofence;
    double lat1,lon1,lat2,lon2,geodistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofence);

        lat1 = 21.76740;
        lon1 = 72.14240;
        lat2 = 21.76340;
        lon2 = 72.14220;

        togglebtn_geofence = (ToggleButton) findViewById(R.id.togglebtn_geofence);
        togglebtn_geofence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (togglebtn_geofence.isChecked()){
                    //Toast.makeText(GeofenceActivity.this, "checked", Toast.LENGTH_SHORT).show();
                    geodistance = distance(lat1,lon1,lat2,lon2);
                    Toast.makeText(GeofenceActivity.this, "distance is : "+geodistance, Toast.LENGTH_SHORT).show();
                    if (geodistance <= 100){
                        Toast.makeText(GeofenceActivity.this, "You are "+geodistance+" away from the hospital", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    //Toast.makeText(GeofenceActivity.this, "not checked", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        // haversine great circle distance approximation, returns meters
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60; // 60 nautical miles per degree of seperation
        dist = dist * 1852; // 1852 meters per nautical mile
        System.out.println("---distance---"+dist);
        return (dist);
    }
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
