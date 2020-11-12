package com.project.docxp.patientfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.docxp.R;
import com.project.docxp.bean.PatientMyAppointmentBean;
import com.project.docxp.customadapter.CustomMyAppointmentAdapter;
import com.project.docxp.services.PatientMyAppointmentServices;
import com.project.docxp.utility.SharedPreferenceData;

public class PatientMyAppointmentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private TextView patient_myappointment;
    private RecyclerView recyclerView_patient_myappointment;
    private RecyclerView.LayoutManager layoutManager_patient_myappointment;
    private Context context;
    private int second = 1000;
    private CustomMyAppointmentAdapter patient_myAppointmentAdapter;
    private FragmentActivity fragmentActivity;
    SharedPreferences preferences;
    PatientMyAppointmentBean bean = new PatientMyAppointmentBean();
    PatientMyAppointmentServices services;
    SwipeRefreshLayout swiperefresh_pateint_myappointment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getContext();
        fragmentActivity = getActivity();
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_my_appointment, container, false);
        getPatientMyAppointmentID(view);


        recyclerView_patient_myappointment.setHasFixedSize(true);
        recyclerView_patient_myappointment.setNestedScrollingEnabled(false);
        layoutManager_patient_myappointment = new LinearLayoutManager(view.getContext());
        recyclerView_patient_myappointment.setLayoutManager(layoutManager_patient_myappointment);
        //get Appointment data
        getPateintAppointmentData();
        swiperefresh_pateint_myappointment.setOnRefreshListener(this);

        return view;

    }

    private void getPatientMyAppointmentID(View view) {
        swiperefresh_pateint_myappointment=(SwipeRefreshLayout)view.findViewById(R.id.swiperefresh_pateint_myappointment);
        recyclerView_patient_myappointment = (RecyclerView) view.findViewById(R.id.recyclerview_patient_myappointment);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentActivity.setTitle("My Appointment");
    }
    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()

            {
                swiperefresh_pateint_myappointment.setRefreshing(true);
                getPateintAppointmentData();

            }
        }, second);
    }

    public void getPateintAppointmentData() {
        System.out.println("==== go to getPateintAppointmentData===");
        String patientemail = preferences.getString(SharedPreferenceData.LOGIN_EMAIL, "");
        bean.setPatient_email(patientemail);
        services = new PatientMyAppointmentServices();
        services.getPatientAppointment(context.getApplicationContext(), bean, fragmentActivity, recyclerView_patient_myappointment,swiperefresh_pateint_myappointment);
    }
}
