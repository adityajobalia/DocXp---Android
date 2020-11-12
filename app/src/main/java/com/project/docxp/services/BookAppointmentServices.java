package com.project.docxp.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.EditText;

import com.project.docxp.bean.BookAppointmentBean;
import com.project.docxp.dao.AppointmentDao;

/**
 * Created by Lenovo on 27/12/2017.
 */

public class BookAppointmentServices {
    Context context;
    AppointmentDao appointmentDao = new AppointmentDao();
    BookAppointmentBean appointmentBean;

    public void appointmentEdittextServices(BookAppointmentBean bookAppointmentBean, Context applicationContext, ProgressDialog progressDialog){
        this.context = applicationContext;
        appointmentDao.setBookAppointmentEdittext(bookAppointmentBean,context,progressDialog);
    }

    public void getEdittextData(Context context, String doctorname, String speciality, EditText editext_patient_bookappointment_edittext_city, EditText editext_patient_bookappointment_edittext_hospital) {
        this.context = context;
        appointmentDao.getBookAppointmentEdittext(context,doctorname,speciality,editext_patient_bookappointment_edittext_city,editext_patient_bookappointment_edittext_hospital);

    }


    public void appointmentServices(BookAppointmentBean bookAppointmentBean, Context applicationContext, ProgressDialog progressDialog) {
        this.context = applicationContext;
        appointmentDao.bookAppointment(bookAppointmentBean, applicationContext, progressDialog);

    }

    public void setEdittextDoctorSpecialityData(Context context, BookAppointmentBean bookAppointmentBean, String cityname, String doctor, String hospitalname, String doc_speciality, ProgressDialog progressDialog) {
        this.context = context;
        appointmentDao.setBookAppointmentDoctorSpeciality(context,bookAppointmentBean,cityname,doctor,hospitalname,doc_speciality,progressDialog);
    }
}
