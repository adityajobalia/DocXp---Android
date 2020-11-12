package com.project.docxp.customadapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.docxp.R;
import com.project.docxp.bean.PateintHospitalDetailsBean;
import com.project.docxp.patientfragment.PatientBookAppointmentEdittextFragment;
import com.project.docxp.utility.ServerCrendentialsUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Devarshi on 02-12-2017.
 */

public class CustomerPateintHospitalDetailsRecyclerAdapter extends RecyclerView.Adapter<CustomerPateintHospitalDetailsRecyclerAdapter.CustomPateintHospitalDetailsViewHolder>  {
   private ArrayList<PateintHospitalDetailsBean> arrayList;
    String doctorname,speciality;

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private Context context;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private PatientBookAppointmentEdittextFragment bookAppointmentEdittextFragment;
    private Class fragment;
    private PateintHospitalDetailsBean bean;

    public CustomerPateintHospitalDetailsRecyclerAdapter(ArrayList<PateintHospitalDetailsBean> arrayList, Context applicationContext, FragmentManager fragmentManager) {
        this.arrayList = arrayList;
        this.context = applicationContext;
        this.fragmentManager = fragmentManager;
    }



    public class CustomPateintHospitalDetailsViewHolder extends RecyclerView.ViewHolder {


        TextView doctor_name,textview_hospitaldetails_doctorspeciality;
        CircleImageView doctor_speciality_image;
        Button button_hospitaldetails_bookapppointment;

        public CustomPateintHospitalDetailsViewHolder(View itemView) {
            super(itemView);
            doctor_name = (TextView) itemView.findViewById(R.id.textview_hospitaldetails_doctorname);
            doctor_speciality_image = (CircleImageView) itemView.findViewById(R.id.imageview_hospitaldetails_logo);
            textview_hospitaldetails_doctorspeciality=(TextView)itemView.findViewById(R.id.textview_hospitaldetails_doctorspeciality);
            button_hospitaldetails_bookapppointment = (Button) itemView.findViewById(R.id.button_hospitaldetails_bookapppointment);


        }
    }


    @Override
    public CustomPateintHospitalDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cardview_patient_hospital_details, parent, false);
       CustomPateintHospitalDetailsViewHolder holder = new CustomPateintHospitalDetailsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomPateintHospitalDetailsViewHolder holder, final int position) {
       // bean=arrayList.get(position);
        String path=arrayList.get(position).getDoctor_image();
        Picasso.with(context).load(ServerCrendentialsUtility.URL+path).placeholder(R.drawable.profile).error(R.drawable.profile).into(holder.doctor_speciality_image);
        holder.doctor_name.setText(arrayList.get(position).getDoctor_name());

        System.out.println("=======doctorname======"+doctorname);
        System.out.println("========list.get(position).getDoctorname()======="+arrayList.get(position).getDoctor_name());
        holder.textview_hospitaldetails_doctorspeciality.setText(arrayList.get(position).getDoctor_speciality());
        System.out.println("=====list.get(position).getDoctor_speciality()======"+arrayList.get(position).getDoctor_speciality());
        holder.button_hospitaldetails_bookapppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("=============================================================");
                Toast.makeText(context.getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
                fragmentTransaction = fragmentManager.beginTransaction();
                bookAppointmentEdittextFragment = new PatientBookAppointmentEdittextFragment();
                fragment = PatientBookAppointmentEdittextFragment.class;
                doctorname = arrayList.get(position).getDoctor_name();
                speciality = arrayList.get(position).getDoctor_speciality();
                System.out.println("=======doctorname======"+doctorname);
                System.out.println("=======speciality======"+speciality);
                Bundle bundle = new Bundle();
                bundle.putString("doctorname",doctorname);
                bundle.putString("speciality",speciality);
                bookAppointmentEdittextFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.patient_content_display, bookAppointmentEdittextFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.show(bookAppointmentEdittextFragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });


    }



}
