package com.project.docxp.services;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.project.docxp.bean.PatientMyAppointmentBean;
import com.project.docxp.dao.PatientMyAppointmentDao;

/**
 * Created by Devarshi on 22-01-2018.
 */

public class PatientMyAppointmentServices {
    private PatientMyAppointmentDao dao;
    public void getPatientAppointment(Context applicationContext, PatientMyAppointmentBean bean, FragmentActivity fragmentActivity, RecyclerView recyclerView_patient_myappointment, SwipeRefreshLayout swiperefresh_pateint_myappointment) {

        dao= new PatientMyAppointmentDao();
        dao.getPatientAppointmentData(applicationContext,bean,fragmentActivity,recyclerView_patient_myappointment,swiperefresh_pateint_myappointment);
    }
}
