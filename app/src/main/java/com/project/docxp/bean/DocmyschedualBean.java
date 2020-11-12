package com.project.docxp.bean;

import java.io.Serializable;

/**
 * Created by Devarshi on 21-11-2017.
 */

public class DocmyschedualBean implements Serializable {
    private String textview_doc_myschedula_Name,
            textview_doc_myschedula_Contect,
            textview_doc_myschedula_Email,
            textview_doc_myschedula_Age,textview_doc_myschedula_Gender,
            textview_doc_myschedula_Date,textview_doc_myschedula_Time,status;
    private String doc_email;
    public DocmyschedualBean() {
    }

    public DocmyschedualBean(String textview_doc_myschedula_Name,
                             String textview_doc_myschedula_Gender,
                             String textview_doc_myschedula_Email,
                             String textview_doc_myschedula_Contect,
                             String textview_doc_myschedula_Date,
                             String textview_doc_myschedula_Time, String status) {
        this.textview_doc_myschedula_Name = textview_doc_myschedula_Name;
        this.textview_doc_myschedula_Contect = textview_doc_myschedula_Contect;
        this.textview_doc_myschedula_Email = textview_doc_myschedula_Email;
        this.textview_doc_myschedula_Gender = textview_doc_myschedula_Gender;
        this.textview_doc_myschedula_Date = textview_doc_myschedula_Date;
        this.textview_doc_myschedula_Time = textview_doc_myschedula_Time;
        this.status=status;
    }

    public String getDoc_email() {
        return doc_email;
    }

    public void setDoc_email(String doc_email) {
        this.doc_email = doc_email;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTextview_doc_myschedula_Name() {
        return textview_doc_myschedula_Name;
    }

    public void setTextview_doc_myschedula_Name(String textview_doc_myschedula_Name) {
        this.textview_doc_myschedula_Name = textview_doc_myschedula_Name;
    }

    public String getTextview_doc_myschedula_Contect() {
        return textview_doc_myschedula_Contect;
    }

    public void setTextview_doc_myschedula_Contect(String textview_doc_myschedula_Contect) {
        this.textview_doc_myschedula_Contect = textview_doc_myschedula_Contect;
    }

    public String getTextview_doc_myschedula_Email() {
        return textview_doc_myschedula_Email;
    }

    public void setTextview_doc_myschedula_Email(String textview_doc_myschedula_Email) {
        this.textview_doc_myschedula_Email = textview_doc_myschedula_Email;
    }

    public String getTextview_doc_myschedula_Age() {
        return textview_doc_myschedula_Age;
    }

    public void setTextview_doc_myschedula_Age(String textview_doc_myschedula_Age) {
        this.textview_doc_myschedula_Age = textview_doc_myschedula_Age;
    }

    public String getTextview_doc_myschedula_Gender() {
        return textview_doc_myschedula_Gender;
    }

    public void setTextview_doc_myschedula_Gender(String textview_doc_myschedula_Gender) {
        this.textview_doc_myschedula_Gender = textview_doc_myschedula_Gender;
    }

    public String getTextview_doc_myschedula_Date() {
        return textview_doc_myschedula_Date;
    }

    public void setTextview_doc_myschedula_Date(String textview_doc_myschedula_Date) {
        this.textview_doc_myschedula_Date = textview_doc_myschedula_Date;
    }

    public String getTextview_doc_myschedula_Time() {
        return textview_doc_myschedula_Time;
    }

    public void setTextview_doc_myschedula_Time(String textview_doc_myschedula_Time) {
        this.textview_doc_myschedula_Time = textview_doc_myschedula_Time;
    }
}
