package com.project.docxp.bean;

/**
 * Created by Lenovo on 27/12/2017.
 */

public class BookAppointmentBean {
    private String patient_name,patient_email,patient_contact,patient_gender,patient_city,patient_doctor,
            patient_hospital,date,time,message,speciality;

    public String getMessage() {
        return message;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_email() {
        return patient_email;
    }

    public void setPatient_email(String patient_email) {
        this.patient_email = patient_email;
    }

    public String getPatient_contact() {
        return patient_contact;
    }

    public void setPatient_contact(String patient_contact) {
        this.patient_contact = patient_contact;
    }

    public String getPatient_gender() {
        return patient_gender;
    }

    public void setPatient_gender(String patient_gender) {
        this.patient_gender = patient_gender;
    }

    public String getPatient_city() {
        return patient_city;
    }

    public void setPatient_city(String patient_city) {
        this.patient_city = patient_city;
    }

    public String getPatient_doctor() {
        return patient_doctor;
    }

    public void setPatient_doctor(String patient_doctor) {
        this.patient_doctor = patient_doctor;
    }

    public String getPatient_hospital() {
        return patient_hospital;
    }

    public void setPatient_hospital(String patient_hospital) {
        this.patient_hospital = patient_hospital;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
