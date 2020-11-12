package com.project.docxp.utility;

import com.project.docxp.bean.PatientMyAppointmentBean;

import java.util.Comparator;

/**
 * Created by Devarshi on 22-01-2018.
 */

public class PatientAppointmentBasedonTime implements Comparator<PatientMyAppointmentBean> {

    @Override
    public int compare(PatientMyAppointmentBean o1, PatientMyAppointmentBean o2) {
        return o1.getTextview_patient_myappointment_appointmentDate().compareTo(o2.getTextview_patient_myappointment_appointmentDate());
    }
}
