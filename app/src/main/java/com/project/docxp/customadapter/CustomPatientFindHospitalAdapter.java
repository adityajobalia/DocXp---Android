package com.project.docxp.customadapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.docxp.R;
import com.project.docxp.bean.PatientFindHospitalBean;
import com.project.docxp.patientfragment.PatientHospitalDetails;

import java.util.ArrayList;

/**
 * Created by Lenovo on 24/11/2017.
 */

public class CustomPatientFindHospitalAdapter extends RecyclerView.Adapter<CustomPatientFindHospitalAdapter.PatientFindHospitalViewHolder> {
    private ArrayList<PatientFindHospitalBean> hospitallist;
    private Context context;
    private int visibility;
    public FragmentManager fragmentManager;
    private FragmentActivity fragmentActivity;
    private FragmentTransaction fragmentTransaction;
    private PatientHospitalDetails patientHospitalDetails;
    private Class fragment;;

    public CustomPatientFindHospitalAdapter(ArrayList<PatientFindHospitalBean> hospitallist, Context context, FragmentActivity fragmentActivity) {
        this.hospitallist = hospitallist;
        this.context = context;
        this.fragmentActivity=fragmentActivity;
    }

    @Override
    public PatientFindHospitalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cardview_patient_findhospital, parent, false);
        PatientFindHospitalViewHolder hospitalViewHolder = new PatientFindHospitalViewHolder(view);

        return hospitalViewHolder;
    }

    @Override
    public void onBindViewHolder(PatientFindHospitalViewHolder holder, final int position) {

        holder.textview_patient_findhospital_Hospitalname.setText(hospitallist.get(position).getTextview_patient_findhospital_Hospitalname());
        holder.textview_patient_findhospital_Hospitallocality.setText(hospitallist.get(position).getTextview_patient_findhospital_Hospitalcontect());
        holder.imageview_patient_hospitaldetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("==============toast");
                fragmentManager = fragmentActivity.getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                patientHospitalDetails = new PatientHospitalDetails();
                fragment = PatientHospitalDetails.class;
                PatientFindHospitalBean bean= new PatientFindHospitalBean();

                String hospital_name=hospitallist.get(position).getTextview_patient_findhospital_Hospitalname();
                bean.setTextview_patient_findhospital_Hospitalname(hospital_name);
                System.out.println("===hospital_name=="+hospital_name);
                String hospital_contact=hospitallist.get(position).getTextview_patient_findhospital_Hospitalcontect();
                bean.setTextview_patient_findhospital_Hospitalcontect(hospital_contact);
                System.out.println("===hospital_contact=="+hospital_contact);
                Toast.makeText(context.getApplicationContext(), position+hospital_name, Toast.LENGTH_SHORT).show();

                Bundle  bundle=new Bundle();
                bundle.putSerializable("hospital_obj",bean);
                patientHospitalDetails.setArguments(bundle);
                fragmentTransaction.replace(R.id.patient_content_display, patientHospitalDetails);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.show(patientHospitalDetails);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return hospitallist.size();
    }

    public class PatientFindHospitalViewHolder extends RecyclerView.ViewHolder {
        TextView textview_patient_findhospital_Hospitalname, textview_patient_findhospital_Hospitallocality;
        ImageView imageview_patient_hospitaldetails;


        public PatientFindHospitalViewHolder(View itemView) {
            super(itemView);
            textview_patient_findhospital_Hospitalname = (TextView) itemView.findViewById(R.id.textview_patient_findhospital_Hospitalname);
            textview_patient_findhospital_Hospitallocality = (TextView) itemView.findViewById(R.id.textview_patient_findhospital_Hospitallocality);
            imageview_patient_hospitaldetails = (ImageView) itemView.findViewById(R.id.imageview_patient_hospitaldetails);


        }
    }


}
