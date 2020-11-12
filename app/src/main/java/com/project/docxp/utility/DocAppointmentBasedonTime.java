package com.project.docxp.utility;

import com.project.docxp.bean.DoclistofappointmentcontentBean;
import com.project.docxp.dao.DocListofAppointmnetDao;

import java.util.Comparator;

/**
 * Created by Devarshi on 19-01-2018.
 */

public class DocAppointmentBasedonTime implements Comparator<DoclistofappointmentcontentBean> {

    @Override
    public int compare(DoclistofappointmentcontentBean o1, DoclistofappointmentcontentBean o2) {

        return o1.getTextview_doc_listofappointment_Time().compareTo(o2.getTextview_doc_listofappointment_Time())  ;
    }
}
