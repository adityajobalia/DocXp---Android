package com.project.docxp.services;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.project.docxp.bean.PateintHospitalDetailsBean;
import com.project.docxp.dao.PatientHospitalDetailsDao;

/**
 * Created by Devarshi on 26-01-2018.
 */

public class PatientHospitalDetailsServices {
    PatientHospitalDetailsDao dao = new PatientHospitalDetailsDao();
    PateintHospitalDetailsBean bean;
    public PateintHospitalDetailsBean getHospitalDetails(Context context, PateintHospitalDetailsBean hospitalDetailsBean,
                                                         RecyclerView recycler_pateint_hospiitaldetails,
                                                         FragmentManager fragmentManager,
                                                         TextView textview_hospital_title,
                                                         TextView textview_hospitaldetails_address,
                                                         TextView textview_hospitaldetails_contect,
                                                         TextView textview_hospitaldetails_website,
                                                         ImageView imageview_patient_hospital_details) {

        bean= dao.getHospitalData(context, hospitalDetailsBean,
                recycler_pateint_hospiitaldetails,
                fragmentManager, textview_hospital_title,
                textview_hospitaldetails_address,
                textview_hospitaldetails_contect,
                textview_hospitaldetails_website, imageview_patient_hospital_details);
        return bean;
    }

    public void getHospitalLatLong(PateintHospitalDetailsBean hospitalDetailsBean, GoogleMap myGoogleMap) {
          dao.getHospitalLatLongData(hospitalDetailsBean, myGoogleMap);

    }
}
