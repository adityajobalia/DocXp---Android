package com.project.docxp.bean;

import java.io.Serializable;

/**
 * Created by Devarshi on 22-11-2017.
 */

public class PatientHomeBean implements Serializable {
    private int imageview_speciality;
    private  String textview_title;
    public PatientHomeBean(){}
    public PatientHomeBean(int imageview_speciality, String textview_title) {
        this.imageview_speciality = imageview_speciality;
        this.textview_title = textview_title;
    }

    public int getImageview_speciality() {
        return imageview_speciality;
    }

    public void setImageview_speciality(int imageview_speciality) {
        this.imageview_speciality = imageview_speciality;
    }

    public String getTextview_title() {
        return textview_title;
    }

    public void setTextview_title(String textview_title) {
        this.textview_title = textview_title;
    }
}
