package com.project.docxp.bean;

import java.io.Serializable;

/**
 * Created by Devarshi on 25-10-2017.
 */

public class ProfileBean implements Serializable {
    private String prodfile_details;
    private String profile_content;
    private String profile_image;

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    private int update_icon,profile_icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    private String name,email,password,mobile,speciality;


    public ProfileBean(String prodfile_details, String profile_content, int update_icon, int profile_icon){
        this.prodfile_details=prodfile_details;
        this.profile_content=profile_content;
        this.update_icon=update_icon;
        this.profile_icon=profile_icon;
    }
    public ProfileBean(){

    }

    public int getProfile_icon() {
        return profile_icon;
    }

    public void setProfile_icon(int profile_icon) {
        this.profile_icon = profile_icon;
    }

    public String getProdfile_details() {
        return prodfile_details;
    }

    public void setProdfile_details(String prodfile_details) {
        this.prodfile_details = prodfile_details;
    }

    public String getProfile_content() {
        return profile_content;
    }

    public void setProfile_content(String profile_content) {
        this.profile_content = profile_content;
    }

    public int getUpdate_icon() {
        return update_icon;
    }

    public void setUpdate_icon(int update_icon) {
        this.update_icon = update_icon;
    }



}
