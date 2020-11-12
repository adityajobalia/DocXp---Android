package com.project.docxp.bean;

import java.io.Serializable;

/**
 * Created by Devarshi on 20-11-2017.
 */

public class DoclistofappointmentcontentBean implements Serializable {
    private String textview_doc_listofappointment_Name,
            textview_doc_listofappointment_Gender,
            textview_doc_listofappointment_Email,
            textview_doc_listofappointment_Contact,
            textview_doc_listofappointment_Date,
            textview_doc_listofappointment_Time,status;
    private String doc_email;
    private String doc_date;
    /*private String patient_nm;
    private String patient_email;
    private String patient_date,status;*/
    public DoclistofappointmentcontentBean() {
    }

    public DoclistofappointmentcontentBean(DoclistofappointmentcontentBean doclistofappointmentcontentBean) {
    }

    public DoclistofappointmentcontentBean(String textview_doc_listofappointment_Name,
                                           String textview_doc_listofappointment_Gender,
                                           String textview_doc_listofappointment_Email,
                                           String textview_doc_listofappointment_Contact,
                                           String textview_doc_listofappointment_Date,
                                           String textview_doc_listofappointment_Time,
                                           String status) {
        this.textview_doc_listofappointment_Name = textview_doc_listofappointment_Name;
        this.textview_doc_listofappointment_Contact = textview_doc_listofappointment_Contact;
        this.textview_doc_listofappointment_Email = textview_doc_listofappointment_Email;
        this.textview_doc_listofappointment_Gender = textview_doc_listofappointment_Gender;
        this.textview_doc_listofappointment_Date = textview_doc_listofappointment_Date;
        this.textview_doc_listofappointment_Time = textview_doc_listofappointment_Time;
        this.status=status;
    }


     /*  public String getTextview_doc_listofappointment_Age() {
        return textview_doc_listofappointment_Age;
    }

    public void setTextview_doc_listofappointment_Age(String textview_doc_listofappointment_Age) {
        this.textview_doc_listofappointment_Age = textview_doc_listofappointment_Age;
    }*/

   /* public String getPatient_date() {
        return patient_date;
    }

    public void setPatient_date(String patient_date) {
        this.patient_date = patient_date;
    }

    public String getPatient_time() {
        return patient_time;
    }

    public void setPatient_time(String patient_time) {
        this.patient_time = patient_time;
    }*/

    /*private String patient_time;
*/


   /* public String getPatient_nm() {
        return patient_nm;
    }

    public void setPatient_nm(String patient_nm) {
        this.patient_nm = patient_nm;
    }

    public String getPatient_email() {
        return patient_email;
    }

    public void setPatient_email(String patient_email) {
        this.patient_email = patient_email;
    }*/

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   public String getTextview_doc_listofappointment_Contact() {
       return textview_doc_listofappointment_Contact;
   }

    public void setTextview_doc_listofappointment_Contact(String textview_doc_listofappointment_Contact) {
        this.textview_doc_listofappointment_Contact = textview_doc_listofappointment_Contact;
    }
    public String getDoc_date() {
        return doc_date;
    }

    public void setDoc_date(String doc_date) {
        this.doc_date = doc_date;
    }



    public String getTextview_doc_listofappointment_Name() {
        return textview_doc_listofappointment_Name;
    }

    public String getDoc_email() {
        return doc_email;
    }

    public void setDoc_email(String doc_email) {
        this.doc_email = doc_email;
    }

    public void setTextview_doc_listofappointment_Name(String textview_doc_listofappointment_Name) {
        this.textview_doc_listofappointment_Name = textview_doc_listofappointment_Name;
    }


    public String getTextview_doc_listofappointment_Email() {
        return textview_doc_listofappointment_Email;
    }

    public void setTextview_doc_listofappointment_Email(String textview_doc_listofappointment_Email) {
        this.textview_doc_listofappointment_Email = textview_doc_listofappointment_Email;
    }



    public String getTextview_doc_listofappointment_Gender() {
        return textview_doc_listofappointment_Gender;
    }

    public void setTextview_doc_listofappointment_Gender(String textview_doc_listofappointment_Gender) {
        this.textview_doc_listofappointment_Gender = textview_doc_listofappointment_Gender;
    }

    public String getTextview_doc_listofappointment_Date() {
        return textview_doc_listofappointment_Date;
    }

    public void setTextview_doc_listofappointment_Date(String textview_doc_listofappointment_Date) {
        this.textview_doc_listofappointment_Date = textview_doc_listofappointment_Date;
    }

    public String getTextview_doc_listofappointment_Time() {
        return textview_doc_listofappointment_Time;
    }

    public void setTextview_doc_listofappointment_Time(String textview_doc_listofappointment_Time) {
        this.textview_doc_listofappointment_Time = textview_doc_listofappointment_Time;
    }
}
