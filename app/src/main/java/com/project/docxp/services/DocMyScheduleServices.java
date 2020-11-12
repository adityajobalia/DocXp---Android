package com.project.docxp.services;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.project.docxp.bean.DocmyschedualBean;
import com.project.docxp.dao.DocMyScheduleDB;

/**
 * Created by Devarshi on 25-01-2018.
 */

public class DocMyScheduleServices {
    DocMyScheduleDB db;
    public void getConfirmAppointmentData(Context context, FragmentActivity fragmentActivity, RecyclerView recycler_doc_myschedule, DocmyschedualBean bean, SwipeRefreshLayout swiperefresh_doc_myschedual) {
    db=new DocMyScheduleDB();
    db.getConfirmAppointmentList(context,fragmentActivity,recycler_doc_myschedule,bean,swiperefresh_doc_myschedual);
    }
}
