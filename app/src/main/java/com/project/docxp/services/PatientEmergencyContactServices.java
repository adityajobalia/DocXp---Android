package com.project.docxp.services;

import android.content.Context;
import android.widget.TextView;

import com.project.docxp.bean.PatientEmergencyContactBean;
import com.project.docxp.dao.PatientEmergencyContactDao;

/**
 * Created by Devarshi on 31-01-2018.
 */

public class PatientEmergencyContactServices {
    public void setContactData(PatientEmergencyContactBean contactData, Context context) {
        PatientEmergencyContactDao dao= new PatientEmergencyContactDao();
        dao.setContactDatainDB(contactData,context);
    }

    public void getEmergencyContactData(Context context, PatientEmergencyContactBean bean, TextView textview_emergencycontectlist_name, TextView textview_emergencycontectlist_contect_no, TextView textview_emergencycontectlist_email) {
        PatientEmergencyContactDao dao= new PatientEmergencyContactDao();
        dao.getEmergencyContactDataFromDB(context,bean,textview_emergencycontectlist_name,textview_emergencycontectlist_contect_no,textview_emergencycontectlist_email);
    }

   /* public void removeContact(Context context, PatientEmergencyContactBean bean) {
        PatientEmergencyContactDao dao= new PatientEmergencyContactDao();
        dao.removedata(context,bean);
    }*/
}
