package com.project.docxp.services;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.project.docxp.bean.DoclistofappointmentcontentBean;
import com.project.docxp.bean.DocmyschedualBean;
import com.project.docxp.dao.DocListofAppointmnetDao;

/**
 * Created by Devarshi on 12-01-2018.
 */

public class DocListOfAppointmentServices {
private DocListofAppointmnetDao   appointmnetDao;

    public void getDocListofAppointment(Context applicationContext, DoclistofappointmentcontentBean doclistofappointmentcontentBean, FragmentActivity fragmentActivity, RecyclerView recyclerview_doc_listofappointment, SwipeRefreshLayout swiperefresh_doc_listofappointment) {
        appointmnetDao= new DocListofAppointmnetDao();
        System.out.println("===in doc service ====");
        appointmnetDao.getDocListofAppointmentData(applicationContext,doclistofappointmentcontentBean,fragmentActivity,recyclerview_doc_listofappointment,swiperefresh_doc_listofappointment);

    }

    public void getDocListofAppointmentbysorting(Context applicationContext, DoclistofappointmentcontentBean doclistofappointmentcontentBean, FragmentActivity fragmentActivity, RecyclerView recyclerview_doc_listofappointment) {
    appointmnetDao= new DocListofAppointmnetDao();
        System.out.println("=====in doc services==");
        appointmnetDao.getDocListofAppointmentDatabysorting(applicationContext,doclistofappointmentcontentBean,fragmentActivity,recyclerview_doc_listofappointment);
    }

    public void rejectDatafromAppointment(Context context, DoclistofappointmentcontentBean doclistofappointmentcontentBean) {
    appointmnetDao= new DocListofAppointmnetDao();
    appointmnetDao.removeDataFromAppointment(context,doclistofappointmentcontentBean);
        sendNotificationToPatientServices(context,doclistofappointmentcontentBean);

    }

    public void conformDatafromAppointment(Context context, DoclistofappointmentcontentBean doclistofappointmentcontentBean) {
        appointmnetDao= new DocListofAppointmnetDao();
        appointmnetDao.conformAppointmentData(context,doclistofappointmentcontentBean);
        sendNotificationToPatientServices(context, doclistofappointmentcontentBean);

    }

    public void completionOfAppointment(Context context, DocmyschedualBean bean) {
        appointmnetDao= new DocListofAppointmnetDao();
        System.out.println("==DocListofAppointmnetDao==");
        appointmnetDao.completionAppointmentByDoctor(context,bean);
    }

    public void sendNotificationToPatientServices(Context context, DoclistofappointmentcontentBean doclistofappointmentcontentBean){
        appointmnetDao = new DocListofAppointmnetDao();
        appointmnetDao.sendNotificationToPatient(context,doclistofappointmentcontentBean);
    }
}

