package com.project.docxp.utility;

import com.project.docxp.bean.DoctorSpecialityDetailsBean;

import java.util.Comparator;

/**
 * Created by Devarshi on 16-12-2017.
 */

public class BasedonDoctornameComparision implements Comparator<DoctorSpecialityDetailsBean> {
    @Override
    public int compare(DoctorSpecialityDetailsBean o1, DoctorSpecialityDetailsBean o2) {
        return o1.getTextview_pateint_doctorspeciality_doctorname().compareTo(o2.getTextview_pateint_doctorspeciality_doctorname());
    }
}
