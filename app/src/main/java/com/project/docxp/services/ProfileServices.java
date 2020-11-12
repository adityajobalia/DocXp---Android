package com.project.docxp.services;

import android.app.ProgressDialog;

import com.project.docxp.DocProfileActivity;
import com.project.docxp.DocProfileImageActivity;
import com.project.docxp.PatientProfileActivity;
import com.project.docxp.PatientProfileImageActivity;
import com.project.docxp.bean.ProfileBean;
import com.project.docxp.dao.ProfileDao;

public class ProfileServices {
    private ProfileDao profileDao;

    public void getPatientProfile(PatientProfileActivity patientProfileActivity, ProfileBean profileBean, ProgressDialog progressDialog) {
        profileDao = new ProfileDao();
        profileDao.getPatientData(patientProfileActivity, profileBean, progressDialog);
    }

    public void updatePatientName(ProgressDialog progressDialog, ProfileBean profileBean, String field, PatientProfileActivity patientProfileActivity) {
        profileDao = new ProfileDao();
        profileDao.updatePatientData(progressDialog,profileBean,field,patientProfileActivity);
    }

    public void getDoctorProfile(DocProfileActivity docProfileActivity, ProfileBean profileBean, ProgressDialog progressDialog) {
        profileDao = new ProfileDao();
        System.out.println("===== go to get doc data==");
        profileDao.getDoctorData(docProfileActivity,profileBean,progressDialog);
    }

    public void updateDoctorName(ProgressDialog progressDialog, ProfileBean profileBean, String field, DocProfileActivity docProfileActivity, int position) {
        profileDao = new ProfileDao();
        profileDao.updateDoctorData(progressDialog,profileBean,field,docProfileActivity);


    }

    public void updatePateintImage(ProgressDialog progressDialog, ProfileBean profileBean, String field, PatientProfileActivity profileActivity) {
        profileDao = new ProfileDao();
        profileDao.updatePatientImage(progressDialog,profileBean,field, profileActivity);
    }


    public void updateDocImage(ProgressDialog progressDialog, ProfileBean profileBean, String field, DocProfileActivity docProfileActivity) {
        profileDao = new ProfileDao();
        profileDao.updateDocImage(progressDialog,profileBean,field, docProfileActivity);
    }
}
