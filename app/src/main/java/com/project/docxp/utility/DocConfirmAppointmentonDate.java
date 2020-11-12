package com.project.docxp.utility;

import com.project.docxp.bean.DocmyschedualBean;

import java.util.Comparator;

/**
 * Created by Devarshi on 25-01-2018.
 */

public class DocConfirmAppointmentonDate implements Comparator<DocmyschedualBean>{

    @Override
    public int compare(DocmyschedualBean o1, DocmyschedualBean o2) {
        return o2.getTextview_doc_myschedula_Time().compareTo(o1.getTextview_doc_myschedula_Time());
    }
}
