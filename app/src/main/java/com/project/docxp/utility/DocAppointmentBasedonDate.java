package com.project.docxp.utility;

import com.project.docxp.bean.DoclistofappointmentcontentBean;

import java.util.Comparator;

/**
 * Created by Devarshi on 23-01-2018.
 */

public class DocAppointmentBasedonDate implements Comparator<DoclistofappointmentcontentBean> {
    @Override
    public int compare(DoclistofappointmentcontentBean o1, DoclistofappointmentcontentBean o2) {
        return o2.getTextview_doc_listofappointment_Date().compareTo(o1.getTextview_doc_listofappointment_Date());
    }
}
