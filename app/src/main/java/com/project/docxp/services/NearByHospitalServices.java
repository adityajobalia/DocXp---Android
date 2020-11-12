package com.project.docxp.services;

import com.google.android.gms.maps.GoogleMap;
import com.project.docxp.bean.NearByHospitalBean;
import com.project.docxp.dao.NearByHospitalDao;

/**
 * Created by Devarshi on 13-02-2018.
 */

public class NearByHospitalServices  {
    public void getNearByHospitalMarkers(GoogleMap myGoogleMap, NearByHospitalBean bean) {
        NearByHospitalDao dao= new NearByHospitalDao();
        dao.getNearByHospitalMarker(myGoogleMap,bean);
    }
}
