package com.project.docxp.bean;

import java.io.Serializable;

/**
 * Created by Lenovo on 13/12/2017.
 */

public class PatientMyAppointmentBean implements Serializable {

    private String textview_patient_myappointment_doctorname;
    private String textview_patient_myappointment_hospitalname;
    private String textview_patient_myappointment_appointmentDate;
    private String textview_patient_myappointment_appointmentTime;

    private String patient_email,textview_patient_myappointment_city,textview_patient_myappointment_patient_nm;

    public PatientMyAppointmentBean() {
    }

    public PatientMyAppointmentBean(String patient_nm, String doc_name,
                                    String hospital_name, String city, String date, String time) {
        this.textview_patient_myappointment_patient_nm=patient_nm;
        this.textview_patient_myappointment_doctorname=doc_name;
        this.textview_patient_myappointment_hospitalname=hospital_name;
        this.textview_patient_myappointment_city=city;
        this.textview_patient_myappointment_appointmentDate=date;
        this.textview_patient_myappointment_appointmentTime=time;

    }

    public String getTextview_patient_myappointment_patient_nm() {
        return textview_patient_myappointment_patient_nm;
    }

    public void setTextview_patient_myappointment_patient_nm(String textview_patient_myappointment_patient_nm) {
        this.textview_patient_myappointment_patient_nm = textview_patient_myappointment_patient_nm;
    }

    public String getTextview_patient_myappointment_city() {
        return textview_patient_myappointment_city;
    }

    public void setTextview_patient_myappointment_city(String textview_patient_myappointment_city) {
        this.textview_patient_myappointment_city = textview_patient_myappointment_city;
    }

    public String getTextview_patient_myappointment_appointmentTime() {
        return textview_patient_myappointment_appointmentTime;
    }

    public void setTextview_patient_myappointment_appointmentTime(String textview_patient_myappointment_appointmentTime) {
        this.textview_patient_myappointment_appointmentTime = textview_patient_myappointment_appointmentTime;
    }

    public String getPatient_email() {
        return patient_email;
    }

    public void setPatient_email(String patient_email) {
        this.patient_email = patient_email;
    }

    public String getTextview_patient_myappointment_doctorname() {
        return textview_patient_myappointment_doctorname;
    }

    public void setTextview_patient_myappointment_doctorname(String textview_patient_myappointment_doctorname) {
        this.textview_patient_myappointment_doctorname = textview_patient_myappointment_doctorname;
    }

    public String getTextview_patient_myappointment_hospitalname() {
        return textview_patient_myappointment_hospitalname;
    }

    public void setTextview_patient_myappointment_hospitalname(String textview_patient_myappointment_hospitalname) {
        this.textview_patient_myappointment_hospitalname = textview_patient_myappointment_hospitalname;
    }

    public String getTextview_patient_myappointment_appointmentDate() {
        return textview_patient_myappointment_appointmentDate;
    }

    public void setTextview_patient_myappointment_appointmentDate(String textview_patient_myappointment_appointmentDate) {
        this.textview_patient_myappointment_appointmentDate = textview_patient_myappointment_appointmentDate;
    }


}
