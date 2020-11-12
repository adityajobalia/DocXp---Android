package com.project.docxp.services;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.project.docxp.dao.PatientDoctorSpecialityDao;

/**
 * Created by Devarshi on 24-01-2018.
 */

public class PatientDoctorSpecialitySrvices {
   private PatientDoctorSpecialityDao dao;

    public void getPatientDoctorSpecialityData(Context applicationContext, FragmentActivity fragmentActivity, String title, RecyclerView recyclerView_patient_doctorspeciality, SwipeRefreshLayout swiperefresh_patient_doctorspecciality) {
        dao = new PatientDoctorSpecialityDao();
        dao.getPatientDoctorSpecialityList(applicationContext, fragmentActivity, title, recyclerView_patient_doctorspeciality,swiperefresh_patient_doctorspecciality);
    }
}
