package com.project.docxp.bean;

import java.io.Serializable;

/**
 * Created by Devarshi on 02-12-2017.
 */

public class PateintHospitalDetailsBean implements Serializable {
    private String doctor_name, doctor_image, doctor_speciality;
    private String hospital_name;
    private String hospital_image;
    private String hospital_address,hospital_locality;
    private String hospital_contact;
    private String hospital_website;

    public String getHospital_locality() {
        return hospital_locality;
    }

    public void setHospital_locality(String hospital_locality) {
        this.hospital_locality = hospital_locality;
    }

    public double hospital_lat;
    public double hospital_long;

    public PateintHospitalDetailsBean(String doctor_name, String doctor_speciality, String doctor_image) {
        this.doctor_name = doctor_name;
        this.doctor_speciality = doctor_speciality;
        this.doctor_image = doctor_image;
    }

    public PateintHospitalDetailsBean() {
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_image() {
        return doctor_image;
    }

    public void setDoctor_image(String doctor_image) {
        this.doctor_image = doctor_image;
    }

    public String getDoctor_speciality() {
        return doctor_speciality;
    }

    public void setDoctor_speciality(String doctor_speciality) {
        this.doctor_speciality = doctor_speciality;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getHospital_image() {
        return hospital_image;
    }

    public void setHospital_image(String hospital_image) {
        this.hospital_image = hospital_image;
    }

    public String getHospital_address() {
        return hospital_address;
    }

    public void setHospital_address(String hospital_address) {
        this.hospital_address = hospital_address;
    }

    public String getHospital_contact() {
        return hospital_contact;
    }

    public void setHospital_contact(String hospital_contact) {
        this.hospital_contact = hospital_contact;
    }

    public String getHospital_website() {
        return hospital_website;
    }

    public void setHospital_website(String hospital_website) {
        this.hospital_website = hospital_website;
    }

    public double getHospital_lat() {
        return hospital_lat;
    }

    public void setHospital_lat(double hospital_lat) {
        this.hospital_lat = hospital_lat;
    }

    public double getHospital_long() {
        return hospital_long;
    }

    public void setHospital_long(double hospital_long) {
        this.hospital_long = hospital_long;
    }


}
