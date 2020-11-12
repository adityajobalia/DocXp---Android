package com.project.docxp.bean;

import java.io.Serializable;

/**
 * Created by Lenovo on 24/11/2017.
 */

public class PatientFindHospitalBean implements Serializable {

    private  String textview_patient_findhospital_Hospitalname,textview_patient_findhospital_Hospitalcontect;


    public PatientFindHospitalBean() {
    }


    public PatientFindHospitalBean(String textview_patient_findhospital_Hospitalname,
                                   String textview_patient_findhospital_Hospitalcontect) {
        this.textview_patient_findhospital_Hospitalname = textview_patient_findhospital_Hospitalname;
        this.textview_patient_findhospital_Hospitalcontect = textview_patient_findhospital_Hospitalcontect;
    }

    public String getTextview_patient_findhospital_Hospitalname() {
        return textview_patient_findhospital_Hospitalname;
    }

    public void setTextview_patient_findhospital_Hospitalname(String textview_patient_findhospital_Hospitalname) {
        this.textview_patient_findhospital_Hospitalname = textview_patient_findhospital_Hospitalname;
    }

    public String getTextview_patient_findhospital_Hospitalcontect() {
        return textview_patient_findhospital_Hospitalcontect;
    }

    public void setTextview_patient_findhospital_Hospitalcontect(String textview_patient_findhospital_Hospitalcontect) {
        this.textview_patient_findhospital_Hospitalcontect = textview_patient_findhospital_Hospitalcontect;
    }


}
