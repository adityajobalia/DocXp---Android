package com.project.docxp.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.docxp.R;
import com.project.docxp.bean.PatientHomeBean;

import java.util.ArrayList;

/**
 * Created by Lenovo on 16/12/2017.
 */

public class CustomPatientHomeAdapter extends BaseAdapter {
    ArrayList<PatientHomeBean> doctorspeciality;
    Context context;

    public CustomPatientHomeAdapter(ArrayList<PatientHomeBean> doctorspeciality, Context context) {
        this.doctorspeciality = doctorspeciality;
        this.context = context;
    }

    @Override
    public int getCount() {
        return doctorspeciality.size();
    }

    @Override
    public Object getItem(int position) {
        return doctorspeciality.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PatientHomeViewHolder patientHomeViewHolder = new PatientHomeViewHolder();
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.custom_patient_home,null,false);
            patientHomeViewHolder.speciality_img = (ImageView)convertView.findViewById(R.id.patient_fab_home);
            patientHomeViewHolder.speciality_title = (TextView)convertView.findViewById(R.id.patient_textview_home);
            convertView.setTag(patientHomeViewHolder);
        }
        else {
            patientHomeViewHolder = (PatientHomeViewHolder) convertView.getTag();
        }
        patientHomeViewHolder.speciality_img.setImageResource(doctorspeciality.get(position).getImageview_speciality());
        patientHomeViewHolder.speciality_title.setText(doctorspeciality.get(position).getTextview_title());
        return convertView;
    }

    public class PatientHomeViewHolder{
        ImageView speciality_img;
        TextView speciality_title;
    }

}
