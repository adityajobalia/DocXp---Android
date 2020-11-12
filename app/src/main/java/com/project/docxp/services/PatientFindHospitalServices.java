package com.project.docxp.services;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.project.docxp.bean.PatientFindHospitalBean;
import com.project.docxp.dao.PatientFindHospitalDao;

/**
 * Created by Devarshi on 24-01-2018.
 */

public class PatientFindHospitalServices {
    private PatientFindHospitalDao dao;

    public void getPatientFinHospitalList(Context applicationContext, PatientFindHospitalBean bean, FragmentActivity fragmentActivity, RecyclerView recyclerView_patient_findhospital, SwipeRefreshLayout swiperefresh_patient_findhospital) {
        System.out.println("===in PatientFindHospitalServices==");
        dao = new PatientFindHospitalDao();
        System.out.println("===go to dao of PatientFindHospital");
        dao.getPatientFinHospitalListData(applicationContext, bean, fragmentActivity, recyclerView_patient_findhospital,swiperefresh_patient_findhospital);


    }

    public void searchByCity(Context applicationContext, String city, FragmentActivity fragmentActivity, RecyclerView recyclerView_patient_findhospital, SwipeRefreshLayout swiperefresh_patient_findhospital) {
        System.out.println("===in PatientFindHospitalServices==");
        dao = new PatientFindHospitalDao();
        System.out.println("===go to dao of PatientFindHospital");
        dao.getHospitalByCity(applicationContext, city, fragmentActivity, recyclerView_patient_findhospital,swiperefresh_patient_findhospital);

    }
}
