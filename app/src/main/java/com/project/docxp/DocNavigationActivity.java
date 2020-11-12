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
import android.widget.Toast;

import com.project.docxp.bean.ProfileBean;
import com.project.docxp.doctorfragment.DocHomeFragment;
import com.project.docxp.doctorfragment.DocListOfAppointmentsFragment;
import com.project.docxp.doctorfragment.DocMyScheduleFragment;
import com.project.docxp.services.NavigationDrawerDataServices;
import com.project.docxp.utility.ConnectivityReceiver;
import com.project.docxp.utility.SharedPreferenceData;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DocNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Class fragment;
    private DocHomeFragment doc_homefragment = new DocHomeFragment();
    private DocListOfAppointmentsFragment doc_listofappointments = new DocListOfAppointmentsFragment();
    private DocMyScheduleFragment doc_myschedule = new DocMyScheduleFragment();
    private BottomNavigationView bottomNavigation_doctor;
    private int visibility;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private static CircleImageView imageView_doctor_nav_header;
    private static TextView textview_doc_name;
    SharedPreferences preferences;
    private String email;
    private boolean networkConnection;
    private Context context = this;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.button_doc_home: {
                            item.setCheckable(true);
                            fragmentTransaction = getfragmentTransaction();
                            fragmentTransaction.replace(R.id.doc_content_display, doc_homefragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade__out);
                            fragmentTransaction.show(doc_homefragment);
                            fragmentTransaction.commitAllowingStateLoss();
                            break;
                        }
                        case R.id.button_doc_profile: {
                            Toast.makeText(DocNavigationActivity.this, "profile", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DocNavigationActivity.this, DocProfileActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
                            break;
                        }
                        case R.id.button_doc_notification: {
                            Toast.makeText(DocNavigationActivity.this, "notification", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DocNavigationActivity.this, DocNotificationActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
                            break;
                        }
                    }
                    return true;
                }

            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_navigation);

        preferences = getSharedPreferences(SharedPreferenceData.SHAREPREF, MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email = preferences.getString(SharedPreferenceData.LOGIN_EMAIL, "");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_doctor);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);

        View doctor_view = navigationView.getHeaderView(0);
        textview_doc_name = (TextView) doctor_view.findViewById(R.id.textview_doc_name);
        imageView_doctor_nav_header = (CircleImageView) doctor_view.findViewById(R.id.imageView_doctor_nav_header);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                NavigationDrawerDataServices services = new NavigationDrawerDataServices();
                services.getDocNavigationDrawerData(DocNavigationActivity.this, email, textview_doc_name, imageView_doctor_nav_header);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                NavigationDrawerDataServices services = new NavigationDrawerDataServices();
                services.getDocNavigationDrawerData(DocNavigationActivity.this, email, textview_doc_name, imageView_doctor_nav_header);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                NavigationDrawerDataServices services = new NavigationDrawerDataServices();
                services.getDocNavigationDrawerData(DocNavigationActivity.this, email, textview_doc_name, imageView_doctor_nav_header);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                NavigationDrawerDataServices services = new NavigationDrawerDataServices();
                services.getDocNavigationDrawerData(DocNavigationActivity.this, email, textview_doc_name, imageView_doctor_nav_header);
            }
        });

        toggle.syncState();

        bottomNavigation_doctor = (BottomNavigationView) findViewById(R.id.bottom_navigation_doctor);
        bottomNavigation_doctor.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        fragmentTransaction = getfragmentTransaction();
        fragment = DocHomeFragment.class;
        fragmentTransaction.add(R.id.doc_content_display, doc_homefragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade__out);
        fragmentTransaction.show(doc_homefragment);
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
                            DocNavigationActivity.super.onBackPressed();
                            System.exit(1);
                        }
                    }).create().show();*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_navigation, menu);
        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.doc_nav_home: {
                item.setChecked(true);
                fragment = DocHomeFragment.class;
                if (visibility != 0) {
                    visibility = 0;
                    fragmentTransaction = getfragmentTransaction();
                    fragmentTransaction.replace(R.id.doc_content_display, doc_homefragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade__out);
                    fragmentTransaction.show(doc_homefragment);
                    fragmentTransaction.commitAllowingStateLoss();
                }
                break;
            }
            case R.id.doc_nav_profile: {

                Intent profileintent = new Intent(DocNavigationActivity.this, DocProfileActivity.class);
                startActivity(profileintent);

                overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
                break;
            }
            case R.id.doc_nav_listofappointments: {
                item.setChecked(true);
                fragment = DocListOfAppointmentsFragment.class;
                if (visibility != 2) {
                    visibility = 2;
                    fragmentTransaction = getfragmentTransaction();
                    fragmentTransaction.replace(R.id.doc_content_display, doc_listofappointments);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade__out);
                    fragmentTransaction.show(doc_listofappointments);
                    fragmentTransaction.commitAllowingStateLoss();
                }
                break;

            }
            case R.id.doc_nav_myschedule: {
                item.setChecked(true);
                fragment = DocMyScheduleFragment.class;
                if (visibility != 3) {
                    visibility = 3;
                    fragmentTransaction = getfragmentTransaction();
                    fragmentTransaction.replace(R.id.doc_content_display, doc_myschedule);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade__out);
                    fragmentTransaction.show(doc_myschedule);
                    fragmentTransaction.commitAllowingStateLoss();
                }
                break;
            }
            case R.id.doc_nav_logout: {
                if (networkConnection = checkConnection(context)) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    editor.commit();
                    Intent intent = new Intent(DocNavigationActivity.this, LoginActivity.class);
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

    public void setDrawerData(ProfileBean drawerData) {
        ProfileBean bean = drawerData;
        textview_doc_name.setText(bean.getName());
        Picasso.with(getApplicationContext()).load(bean.getProfile_image()).into(imageView_doctor_nav_header);
        System.out.println("======bean.getName()=====" + bean.getName());
    }

    // Method to manually check connection status
    private boolean checkConnection(Context applicationContext) {
        boolean isConnected = ConnectivityReceiver.isConnected(applicationContext);
        System.out.println("======isConnected=======" + isConnected);
        boolean networkStatus = showSnack(isConnected);
        System.out.println("======networkStatus======" + networkStatus);
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
                .make(findViewById(R.id.nav_view_doctor), message, Snackbar.LENGTH_LONG);

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
