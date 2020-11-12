package com.project.docxp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.project.docxp.patientfragment.PatientBookAppointmentFragment;
import com.project.docxp.patientfragment.PatientEmergencyContactListFragment;
import com.project.docxp.patientfragment.PatientFindHospitalFragment;
import com.project.docxp.patientfragment.PatientHomeFragment;
import com.project.docxp.patientfragment.PatientMyAppointmentFragment;
import com.project.docxp.services.NavigationDrawerDataServices;
import com.project.docxp.utility.ConnectivityReceiver;
import com.project.docxp.utility.DocXpPermissions;
import com.project.docxp.utility.SharedPreferenceData;

import de.hdodenhof.circleimageview.CircleImageView;


public class PatientNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Class fragment;
    private BottomNavigationView bottomNavigation_patient;
    private PatientHomeFragment patient_homeFragment = new PatientHomeFragment();
    private PatientFindHospitalFragment patient_findhospital = new PatientFindHospitalFragment();
    private PatientMyAppointmentFragment patient_myappointment = new PatientMyAppointmentFragment();
    private PatientEmergencyContactListFragment patient_emergencycontectlist = new PatientEmergencyContactListFragment();
    private PatientBookAppointmentFragment patient_bookappointment = new PatientBookAppointmentFragment();
    private int visibility;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    SharedPreferences preferences;
    private String email;
    private TextView textview_patient_name;
    private CircleImageView imageView_patient_nav_header;
    private boolean networkConnection;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.button_patient_home: {
                    item.setChecked(true);
                    fragmentTransaction = getfragmentTransaction();
                    fragmentTransaction.replace(R.id.patient_content_display, patient_homeFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade__out);
                    fragmentTransaction.show(patient_homeFragment);
                    fragmentTransaction.commitAllowingStateLoss();
                    break;
                }
                case R.id.button_patient_hospital: {
                    item.setChecked(true);
                    fragmentTransaction = getfragmentTransaction();
                    fragmentTransaction.replace(R.id.patient_content_display, patient_findhospital);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade__out);
                    fragmentTransaction.show(patient_findhospital);
                    fragmentTransaction.commitAllowingStateLoss();
                    break;
                }
                case R.id.button_patient_notification: {
                    Intent intent = new Intent(PatientNavigationActivity.this, PatientNotificationActivity.class);
                    startActivity(intent);
                    break;
                }
            }
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences(SharedPreferenceData.SHAREPREF, MODE_PRIVATE);

        email = preferences.getString(SharedPreferenceData.LOGIN_EMAIL, "");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        View patient_view = navigationView.getHeaderView(0);
        textview_patient_name = (TextView) patient_view.findViewById(R.id.textview_patient_name);
        imageView_patient_nav_header = (CircleImageView) patient_view.findViewById(R.id.imageView_patient_nav_header);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                NavigationDrawerDataServices services= new NavigationDrawerDataServices();
                services.getPatientNavigationDrawerData(PatientNavigationActivity.this,email,textview_patient_name,imageView_patient_nav_header);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                NavigationDrawerDataServices services= new NavigationDrawerDataServices();
                services.getPatientNavigationDrawerData(PatientNavigationActivity.this,email,textview_patient_name,imageView_patient_nav_header);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                NavigationDrawerDataServices services= new NavigationDrawerDataServices();
                services.getPatientNavigationDrawerData(PatientNavigationActivity.this,email,textview_patient_name,imageView_patient_nav_header);

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                NavigationDrawerDataServices services= new NavigationDrawerDataServices();
                services.getPatientNavigationDrawerData(PatientNavigationActivity.this,email,textview_patient_name,imageView_patient_nav_header);

            }
        });
        toggle.syncState();

        bottomNavigation_patient = (BottomNavigationView) findViewById(R.id.bottom_navigation_patient);
        bottomNavigation_patient.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = PatientHomeFragment.class;
        fragmentTransaction.add(R.id.patient_content_display, patient_homeFragment);
       // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade__out);
        fragmentTransaction.show(patient_homeFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
           /* new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            PatientNavigationActivity.super.onBackPressed();
                            System.exit(1);
                        }
                    }).create().show();*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_geofence:
                Intent geointent = new Intent(PatientNavigationActivity.this, GeofenceActivity.class);
                startActivity(geointent);
                return true;

           /* case R.id.action_exit:
                onBackPressed();
                return true;*/
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.patient_nav_home: {
                item.setChecked(true);
                fragment = PatientHomeFragment.class;
                if (visibility != 0) {
                    visibility = 0;

                    fragmentTransaction = getfragmentTransaction();
                    fragmentTransaction.replace(R.id.patient_content_display, patient_homeFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade__out);
                    fragmentTransaction.show(patient_homeFragment);
                    fragmentTransaction.commit();
                }
                break;

            }
            case R.id.patient_nav_profile: {
                Intent patientIntent = new Intent(PatientNavigationActivity.this, PatientProfileActivity.class);
                startActivity(patientIntent);
                overridePendingTransition(R.anim.slide_down,R.anim.slide_down);
                break;
            }
            case R.id.patient_nav_nearbyhospital: {
                boolean locationPermissions = DocXpPermissions.checkPermissionsArray(DocXpPermissions.LOCATION_PERMISSIONS,getApplicationContext());
                networkConnection = checkConnection(getApplicationContext());
                if (locationPermissions && networkConnection ) {
                    Intent patientIntent = new Intent(PatientNavigationActivity.this, NearByHospitalMapActivity.class);
                    startActivity(patientIntent);
                    overridePendingTransition(R.anim.slide_down,R.anim.slide_down);
                }
                else {
                    DocXpPermissions.verifyLocationPermission(DocXpPermissions.LOCATION_PERMISSIONS,PatientNavigationActivity.this);
                }

                break;
            }
            case R.id.patient_nav_findhospital: {
                item.setChecked(true);
                fragment = PatientFindHospitalFragment.class;
                if (visibility != 1) {
                    visibility = 1;
                    fragmentTransaction = getfragmentTransaction();
                    fragmentTransaction.replace(R.id.patient_content_display, patient_findhospital);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade__out);
                    fragmentTransaction.show(patient_findhospital);
                    fragmentTransaction.commit();
                }
                break;

            }
            case R.id.patient_nav_bookappointment: {
                item.setChecked(true);
                fragment = PatientBookAppointmentFragment.class;
                if (visibility != 2) {
                    visibility = 2;

                    System.out.println("book appointment....");
                    fragmentTransaction = getfragmentTransaction();
                    fragmentTransaction.replace(R.id.patient_content_display, patient_bookappointment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade__out);
                    fragmentTransaction.show(patient_bookappointment);
                    fragmentTransaction.commit();
                    System.out.println("book appointment....");
                }
                break;

            }
            case R.id.patient_nav_myappointment: {

                item.setChecked(true);
                fragment = PatientMyAppointmentFragment.class;
                if (visibility != 3) {
                    visibility = 3;

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.patient_content_display, patient_myappointment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade__out);
                    fragmentTransaction.show(patient_myappointment);
                    fragmentTransaction.commit();
                }
                break;

            }
            case R.id.patient_nav_emergencycontactlist: {
                item.setChecked(true);
                fragment = PatientEmergencyContactListFragment.class;
                if (visibility != 4) {
                    visibility = 4;
                    fragmentTransaction = getfragmentTransaction();
                    fragmentTransaction.replace(R.id.patient_content_display, patient_emergencycontectlist);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade__out);
                    fragmentTransaction.show(patient_emergencycontectlist);
                    fragmentTransaction.commit();
                }
                break;

            }
            case R.id.patient_nav_logout: {
               if (networkConnection = checkConnection(getApplicationContext())){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                    editor.apply();
                    Intent intent= new Intent(PatientNavigationActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    private FragmentTransaction getfragmentTransaction() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        return fragmentTransaction;
    }

    // Method to manually check connection status
    private boolean checkConnection(Context applicationContext) {
        boolean isConnected = ConnectivityReceiver.isConnected(applicationContext);
        System.out.println("======isConnected======="+isConnected);
        boolean networkStatus = showSnack(isConnected);
        System.out.println("======networkStatus======"+networkStatus);
        return networkStatus;
    }

    // Showing the status in Snackbar
    private boolean showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "";
            color = Color.WHITE;
        } else {
            message = "No Internet Connection";
            color = Color.RED;
        }
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.nav_view), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);

        if (message.equals("")) {
            return true;
        } else {
            snackbar.show();
            return false;
        }

    }

}

