package com.project.docxp.services;

import android.widget.TextView;

import com.project.docxp.DocNavigationActivity;
import com.project.docxp.PatientNavigationActivity;
import com.project.docxp.dao.NavigationDrawerDataDao;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Devarshi on 21-02-2018.
 */

public class NavigationDrawerDataServices {

    public void getDocNavigationDrawerData(DocNavigationActivity docNavigationActivity, String email, TextView textview_doc_name, CircleImageView imageView_doctor_nav_header) {
        NavigationDrawerDataDao dao = new NavigationDrawerDataDao();
        dao.getDocDrawrData(docNavigationActivity, email, textview_doc_name, imageView_doctor_nav_header);

    }

    public void getPatientNavigationDrawerData(PatientNavigationActivity patientNavigationActivity, String email, TextView textview_patient_name, CircleImageView imageView_patient_nav_header) {
        NavigationDrawerDataDao dao = new NavigationDrawerDataDao();
        dao.getPatientDrawrData(patientNavigationActivity, email, textview_patient_name, imageView_patient_nav_header);
    }
}
