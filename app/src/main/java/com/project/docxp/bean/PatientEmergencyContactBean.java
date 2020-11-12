package com.project.docxp.bean;

import java.io.Serializable;

/**
 * Created by Devarshi on 31-01-2018.
 */

public class PatientEmergencyContactBean implements Serializable
{

    private String contact_name,contact_mobile,contact_email,Login_email;

    public PatientEmergencyContactBean() {
    }

    public PatientEmergencyContactBean(String name, String mobile, String email) {
        this.contact_name=name;
        this.contact_email=email;
        this.contact_mobile=mobile;
    }

    public String getContact_name() {
        return contact_name;
    }

    public String getLogin_email() {
        return Login_email;
    }

    public void setLogin_email(String login_email) {
        Login_email = login_email;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_mobile() {
        return contact_mobile;
    }

    public void setContact_mobile(String contact_mobile) {
        this.contact_mobile = contact_mobile;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }
}
