package com.project.docxp.customadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.docxp.R;
import com.project.docxp.bean.PatientMyAppointmentBean;

import java.util.ArrayList;

/**
 * Created by Lenovo on 13/12/2017.
 */

public class CustomMyAppointmentAdapter extends RecyclerView.Adapter<CustomMyAppointmentAdapter.PatientMyAppointmentViewHolder> {
    private ArrayList<PatientMyAppointmentBean> myappointment;
    private Context context;

    public CustomMyAppointmentAdapter(ArrayList<PatientMyAppointmentBean> myappointment, Context context) {
        this.myappointment = myappointment;
        this.context = context;
    }

    @Override
    public PatientMyAppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cardview_patient_my_appointment, parent, false);
        PatientMyAppointmentViewHolder patientMyAppointmentViewHolder = new PatientMyAppointmentViewHolder(view);
        return patientMyAppointmentViewHolder;
    }

    @Override
    public void onBindViewHolder(PatientMyAppointmentViewHolder holder, final int position) {
        holder.textview_patient_myappointment_doctorname.setText(myappointment.get(position).getTextview_patient_myappointment_doctorname());
        holder.textview_patient_myappointment_hospitalname.setText(myappointment.get(position).getTextview_patient_myappointment_hospitalname());
        holder.textview_patient_myappointment_appointmentDate.setText(myappointment.get(position).getTextview_patient_myappointment_appointmentDate());
        holder.textview_patient_myappointment_appointmentTime.setText(myappointment.get(position).getTextview_patient_myappointment_appointmentTime());
        holder.textview_patient_myappointment_city.setText(myappointment.get(position).getTextview_patient_myappointment_city());
        holder.textview_patient_myappointment_patient_nm.setText(myappointment.get(position).getTextview_patient_myappointment_patient_nm());

    }


    @Override
    public int getItemCount() {
        return myappointment.size();
    }

    public class PatientMyAppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView textview_patient_myappointment_doctorname;
        TextView textview_patient_myappointment_hospitalname;
        TextView textview_patient_myappointment_appointmentDate;
        TextView textview_patient_myappointment_appointmentTime;
        TextView textview_patient_myappointment_city,textview_patient_myappointment_patient_nm;

        public PatientMyAppointmentViewHolder(View itemView) {
            super(itemView);
            textview_patient_myappointment_doctorname = (TextView) itemView.findViewById(R.id.textview_patient_myappointment_doctorname);
            textview_patient_myappointment_hospitalname = (TextView) itemView.findViewById(R.id.textview_patient_myappointment_hospitalname);
            textview_patient_myappointment_appointmentDate = (TextView) itemView.findViewById(R.id.textview_patient_myappointment_appointmentDate);
            textview_patient_myappointment_appointmentTime = (TextView) itemView.findViewById(R.id.textview_patient_myappointment_appointmentTime);
            textview_patient_myappointment_city = (TextView) itemView.findViewById(R.id.textview_patient_myappointment_city);
            textview_patient_myappointment_patient_nm=(TextView)itemView.findViewById(R.id.textview_patient_myappointment_patient_nm);
        }
    }
}
