package com.project.docxp.patientfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.docxp.R;
import com.project.docxp.bean.PatientEmergencyContactBean;
import com.project.docxp.services.PatientEmergencyContactServices;
import com.project.docxp.utility.SharedPreferenceData;


public class PatientEmergencyContactListFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private Button button_emergency_contact_list_add;
    private TextView textview_emergencycontectlist_name,
            textview_emergencycontectlist_contect_no,
            textview_emergencycontectlist_email;
    private ImageView imageview_emergencycontect_reject;
    private CardView cardview_pateint_emergency_contectlist;
    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_emergency_contact_list, container, false);
        preferences=context.getSharedPreferences(SharedPreferenceData.SHAREPREF,Context.MODE_PRIVATE);

        //getPatientEmergencyContactListID(view);
        button_emergency_contact_list_add = (Button) view.findViewById(R.id.button_emergency_contact_list_add);
        textview_emergencycontectlist_name=(TextView)view.findViewById(R.id.textview_emergencycontectlist_name);
        textview_emergencycontectlist_contect_no=(TextView)view.findViewById(R.id.textview_emergencycontectlist_contect_no);
        textview_emergencycontectlist_email=(TextView)view.findViewById(R.id.textview_emergencycontectlist_email);
       /* imageview_emergencycontect_reject=(ImageView)view.findViewById(R.id.imageview_emergencycontect_reject);
        imageview_emergencycontect_reject.setOnClickListener(this);*/
        button_emergency_contact_list_add.setOnClickListener(this);



        getEmergencyContactData();
        return view;
    }
/*
    private void getPatientEmergencyContactListID(View view) {

    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Emergency Contact List");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_emergency_contact_list_add:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PatientEmergencyContactFormFragment fragment = new PatientEmergencyContactFormFragment();
                fragmentTransaction.replace(R.id.patient_content_display, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.show(fragment);
                fragmentTransaction.commitAllowingStateLoss();
                break;

           /* case R.id.imageview_emergencycontect_reject:
                PatientEmergencyContactBean bean= new PatientEmergencyContactBean();
                PatientEmergencyContactServices services= new PatientEmergencyContactServices();
                String patientemail = preferences.getString(SharedPreferenceData.LOGIN_EMAIL, "");
                bean.setContact_name(textview_emergencycontectlist_name.getText().toString());
                bean.setLogin_email(patientemail);
                cardview_pateint_emergency_contectlist.removeViewAt(0);
                services.removeContact(context,bean);
                break;*/
        }

    }

    public void getEmergencyContactData() {
        PatientEmergencyContactServices services= new PatientEmergencyContactServices();
        String patientemail = preferences.getString(SharedPreferenceData.LOGIN_EMAIL, "");
        PatientEmergencyContactBean bean= new PatientEmergencyContactBean();
        bean.setLogin_email(patientemail);
        services.getEmergencyContactData(context,bean,textview_emergencycontectlist_name,textview_emergencycontectlist_contect_no,textview_emergencycontectlist_email);

    }
}
