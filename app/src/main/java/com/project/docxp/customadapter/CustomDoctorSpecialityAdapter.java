package com.project.docxp.customadapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.docxp.R;
import com.project.docxp.bean.DoctorSpecialityDetailsBean;
import com.project.docxp.patientfragment.PatientBookAppointmentEdittextFragment;
import com.project.docxp.utility.ServerCrendentialsUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lenovo on 27/11/2017.
 */

public class CustomDoctorSpecialityAdapter extends RecyclerView.Adapter<CustomDoctorSpecialityAdapter.Patient_DoctorSpecialityViewHolder> {
    private ArrayList<DoctorSpecialityDetailsBean> doctorspecialitydetails;
    private Context context;
    private FragmentActivity fragmentActivity;
    private FragmentTransaction fragmentTransaction;
    private PatientBookAppointmentEdittextFragment bookAppointmentEdittextFragment;
    private Class<PatientBookAppointmentEdittextFragment> fragment;
    private DoctorSpecialityDetailsBean doctorSpecialityDetailsBean;
    String doctorname,city,hospitalname,speciality;

    public CustomDoctorSpecialityAdapter(ArrayList<DoctorSpecialityDetailsBean> doctorspecialitydetails, Context context, FragmentActivity fragmentManager) {
        this.doctorspecialitydetails = doctorspecialitydetails;
        this.context = context;
        this.fragmentActivity = fragmentManager;
    }

    @Override
    public Patient_DoctorSpecialityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_patient_doctorspeciality_details, parent, false);
        Patient_DoctorSpecialityViewHolder patient_doctorSpecialityViewHolder = new Patient_DoctorSpecialityViewHolder(view);

        return patient_doctorSpecialityViewHolder;
    }

    @Override
    public void onBindViewHolder(Patient_DoctorSpecialityViewHolder holder, final int position) {
        doctorSpecialityDetailsBean = doctorspecialitydetails.get(position);
        String path=doctorspecialitydetails.get(position).getDoc_profileimage();
        Picasso.with(context).load(ServerCrendentialsUtility.URL+path).placeholder(R.drawable.profile).error(R.drawable.profile).into(holder.circularimagview_pateint_doctorspeciality);
        holder.textview_pateint_doctorspeciality_doctorname.setText(doctorspecialitydetails.get(position).getTextview_pateint_doctorspeciality_doctorname());
        holder.textview_pateint_doctorspeciality_doctorspeciality.setText(doctorspecialitydetails.get(position).getTextview_pateint_doctorspeciality_doctorspeciality());
        holder.textview_pateint_doctorspeciality_doctorhospitalname.setText(doctorspecialitydetails.get(position).getTextview_pateint_doctorspeciality_doctorhospitalname());
        holder.textview_pateint_doctorspeciality_doctorcity.setText(doctorspecialitydetails.get(position).getTextview_pateint_doctorspeciality_doctorcity());
        holder.textview_pateint_doctorspeciality_doctorcontact.setText(doctorspecialitydetails.get(position).getTextview_pateint_doctorspeciality_doctorcontact());
        holder.textview_pateint_doctorspeciality_doctoremail.setText(doctorspecialitydetails.get(position).getTextview_pateint_doctorspeciality_doctoremail());

        holder.button_pateint_doctorspeciality_bookappintment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), doctorSpecialityDetailsBean.getTextview_pateint_doctorspeciality_doctorname(), Toast.LENGTH_SHORT).show();
                fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                bookAppointmentEdittextFragment = new PatientBookAppointmentEdittextFragment();
                fragment = PatientBookAppointmentEdittextFragment.class;

                doctorname = doctorspecialitydetails.get(position).getTextview_pateint_doctorspeciality_doctorname();
                speciality = doctorspecialitydetails.get(position).getTextview_pateint_doctorspeciality_doctorspeciality();
                city = doctorspecialitydetails.get(position).getTextview_pateint_doctorspeciality_doctorcity();
                hospitalname = doctorspecialitydetails.get(position).getTextview_pateint_doctorspeciality_doctorhospitalname();
                System.out.println("=======doctorname======"+doctorname);
                System.out.println("=======city======"+city);
                System.out.println("=======hospitalname======"+hospitalname);
                System.out.println("=========speciality========="+speciality);

                /*doctorSpecialityDetailsBean.setTextview_pateint_doctorspeciality_doctorcity(city);
                doctorSpecialityDetailsBean.setTextview_pateint_doctorspeciality_doctorname(doctorname);
                doctorSpecialityDetailsBean.setTextview_pateint_doctorspeciality_doctorhospitalname(hospitalname);
*/
                Bundle bundle = new Bundle();
                bundle.putString("city",city);
                bundle.putString("doctorname",doctorname);
                bundle.putString("hospitalname",hospitalname);
                bundle.putString("speciality",speciality);
                bookAppointmentEdittextFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.patient_content_display, bookAppointmentEdittextFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.show(bookAppointmentEdittextFragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });

    }


    @Override
    public int getItemCount() {
        return doctorspecialitydetails.size();
    }


    public class Patient_DoctorSpecialityViewHolder extends RecyclerView.ViewHolder {
        TextView textview_pateint_doctorspeciality_doctorname,
                textview_pateint_doctorspeciality_doctorspeciality,
                textview_pateint_doctorspeciality_doctorhospitalname,
                textview_pateint_doctorspeciality_doctorcity,
                textview_pateint_doctorspeciality_doctorcontact, textview_pateint_doctorspeciality_doctoremail;

        ImageView circularimagview_pateint_doctorspeciality;
        Button button_pateint_doctorspeciality_bookappintment;

        public Patient_DoctorSpecialityViewHolder(View itemView) {
            super(itemView);

            textview_pateint_doctorspeciality_doctorname = (TextView) itemView.findViewById(R.id.textview_pateint_doctorspeciality_doctorname);
            textview_pateint_doctorspeciality_doctorspeciality = (TextView) itemView.findViewById(R.id.textview_pateint_doctorspeciality_doctorspeciality);
            textview_pateint_doctorspeciality_doctorhospitalname = (TextView) itemView.findViewById(R.id.textview_pateint_doctorspeciality_doctorhospitalname);
            textview_pateint_doctorspeciality_doctorcity = (TextView) itemView.findViewById(R.id.textview_pateint_doctorspeciality_doctorcity);
            textview_pateint_doctorspeciality_doctoremail = (TextView) itemView.findViewById(R.id.textview_pateint_doctorspeciality_doctoremail);
            textview_pateint_doctorspeciality_doctorcontact=(TextView)itemView.findViewById(R.id. textview_pateint_doctorspeciality_doctorcontact) ;
            circularimagview_pateint_doctorspeciality = (ImageView) itemView.findViewById(R.id.circularimagview_pateint_doctorspeciality);
            button_pateint_doctorspeciality_bookappintment = (Button) itemView.findViewById(R.id.button_pateint_doctorspeciality_bookappintment);
        }
    }
}
