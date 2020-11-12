package com.project.docxp.patientfragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.project.docxp.R;
import com.project.docxp.bean.PatientEmergencyContactBean;
import com.project.docxp.services.PatientEmergencyContactServices;
import com.project.docxp.utility.SharedPreferenceData;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientEmergencyContactFormFragment extends Fragment implements View.OnClickListener{

    private EditText edittext_patient_emergency_contact_name, edittext_patient_emergency_contact_mobile, edittextpatient_emergency_contact_email;
    private Button button_patient_emergency_contact_save;
    private Context context;
    SharedPreferences preferences;

    public PatientEmergencyContactFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_patient_emergency_contact_form, container, false);
        context=getContext();
        preferences=context.getSharedPreferences(SharedPreferenceData.SHAREPREF,Context.MODE_PRIVATE);
        getPatientEmergencyContactFormFragmentID(view);
        button_patient_emergency_contact_save.setOnClickListener(this);


        return view;
    }

    private void getPatientEmergencyContactFormFragmentID(View view) {
        edittext_patient_emergency_contact_name=(EditText)view.findViewById(R.id.edittext_patient_emergency_contact_name);
        edittext_patient_emergency_contact_mobile=(EditText)view.findViewById(R.id.edittext_patient_emergency_contact_mobile);
        edittextpatient_emergency_contact_email=(EditText)view.findViewById(R.id.edittextpatient_emergency_contact_email);
        button_patient_emergency_contact_save=(Button)view.findViewById(R.id.button_patient_emergency_contact_save);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Contact");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_patient_emergency_contact_save:
                getEmergencyContactData();
                break;
        }
    }

    public void getEmergencyContactData() {
        String name=edittext_patient_emergency_contact_name.getText().toString();
            String email=edittextpatient_emergency_contact_email.getText().toString();
        String mobile=edittext_patient_emergency_contact_mobile.getText().toString();
        System.out.println("=============="+name+"========="+email+"======="+mobile);
        String patientemail = preferences.getString(SharedPreferenceData.LOGIN_EMAIL, "");
        PatientEmergencyContactBean patientEmergencyContactBean=new PatientEmergencyContactBean();
        patientEmergencyContactBean.setContact_name(name);
        patientEmergencyContactBean.setContact_email(email);
        patientEmergencyContactBean.setContact_mobile(mobile);
        patientEmergencyContactBean.setLogin_email(patientemail);

        PatientEmergencyContactServices services =new PatientEmergencyContactServices();
        services.setContactData(patientEmergencyContactBean,context);

    }
}
