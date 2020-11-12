package com.project.docxp.utility;

import com.project.docxp.bean.PatientFindHospitalBean;

import java.util.Comparator;

/**
 * Created by Devarshi on 24-01-2018.
 */

public class BasedonHospitalnameComparision implements Comparator<PatientFindHospitalBean> {
    @Override
    public int compare(PatientFindHospitalBean o1, PatientFindHospitalBean o2) {
        return o1.getTextview_patient_findhospital_Hospitalname().compareTo(o2.getTextview_patient_findhospital_Hospitalname());
    }
}
