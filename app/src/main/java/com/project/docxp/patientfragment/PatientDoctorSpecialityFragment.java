package com.project.docxp.patientfragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.project.docxp.R;
import com.project.docxp.bean.DoctorSpecialityDetailsBean;
import com.project.docxp.customadapter.CustomDoctorSpecialityAdapter;
import com.project.docxp.services.PatientDoctorSpecialitySrvices;
import com.project.docxp.utility.BasedonDoctornameComparision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDoctorSpecialityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private RecyclerView recyclerView_patient_doctorspeciality;
    private RecyclerView.LayoutManager layoutManager_patient_doctorspeciality;
    private Context context;
    private FragmentManager fragmentManager;
    private String title;
    private int second = 1000;
    private FragmentActivity fragmentActivity;
    private SwipeRefreshLayout swiperefresh_patient_doctorspecciality;

    public PatientDoctorSpecialityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_doctor_speciality, container, false);
        context = getContext();
        Bundle bundle = getArguments();
        title = bundle.getString("title");
        fragmentActivity = getActivity();
        fragmentManager = getActivity().getSupportFragmentManager();

        getPatientDoctorSpecialityID(view);
        recyclerView_patient_doctorspeciality.setHasFixedSize(true);
        recyclerView_patient_doctorspeciality.setNestedScrollingEnabled(false);
        layoutManager_patient_doctorspeciality = new LinearLayoutManager(view.getContext());
        recyclerView_patient_doctorspeciality.setLayoutManager(layoutManager_patient_doctorspeciality);
        getPatientDoctorSpecialityData();
        swiperefresh_patient_doctorspecciality.setOnRefreshListener(this);
        return view;
    }

    private void getPatientDoctorSpecialityID(View view) {
        swiperefresh_patient_doctorspecciality=(SwipeRefreshLayout)view.findViewById(R.id.swiperefresh_patient_doctorspecciality);
        recyclerView_patient_doctorspeciality = (RecyclerView) view.findViewById(R.id.recyclerview_patient_doctorspecciality);

    }
    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()

            {
                swiperefresh_patient_doctorspecciality.setRefreshing(true);
                getPatientDoctorSpecialityData();
            }
        }, second);
    }

    private void getPatientDoctorSpecialityData() {
        System.out.println("===in getPatientDoctorSpecialityData== ");
        PatientDoctorSpecialitySrvices srvices = new PatientDoctorSpecialitySrvices();
        srvices.getPatientDoctorSpecialityData(context.getApplicationContext(), fragmentActivity, title, recyclerView_patient_doctorspeciality,swiperefresh_patient_doctorspecciality);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Doctor List");
    }


}

