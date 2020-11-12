package com.project.docxp.services;

import android.support.v4.app.FragmentActivity;
import android.widget.Spinner;

import com.project.docxp.dao.SpinnerDao;

import java.util.ArrayList;

/**
 * Created by adity on 15-01-2018.
 */

public class SpinnerServices {
    private SpinnerDao spinnerDao;

    public void getSpinnerCityData(FragmentActivity activity, ArrayList<String> arrayList, int spinner_patient_bookappointment_city) {
        spinnerDao = new SpinnerDao();
        spinnerDao.getCityData(activity,arrayList,spinner_patient_bookappointment_city);
    }
}
