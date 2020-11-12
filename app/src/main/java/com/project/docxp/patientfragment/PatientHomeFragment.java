package com.project.docxp.patientfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.project.docxp.R;
import com.project.docxp.bean.PatientHomeBean;
import com.project.docxp.customadapter.CustomPatientHomeAdapter;

import java.util.ArrayList;
import java.util.List;


public class PatientHomeFragment extends Fragment {

    private Class fragment;
    private PatientHomeFragment patient_homeFragment;
    private PatientFindHospitalFragment patient_findhospital;
    private int visibility;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private List<PatientHomeBean> patientHomeBeanList;
    private CustomPatientHomeAdapter customPatientHomeAdapter;
    private GridView gridView_patient_home;
    private PatientDoctorSpecialityFragment patient_doDoctorSpecialityFragment;
    private Context context;
    private int[] image = {R.drawable.cardiologist,R.drawable.dentist,R.drawable.ent,R.drawable.eyespecialist,  R.drawable.neurosurgeon, R.drawable.nutrition, R.drawable.orthopedic,
            R.drawable.pediatrician,R.drawable.physician };
    private String[] title = {"Cardiologist","Dentist","ENT","Eyespecialist","Neurosurgeon" ,"Nutritianist",
            "Orthopedic","Pediatrician", "Physician"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getContext();

       //getActivity().startService(new Intent(getContext(), ServicesManager.class));
        // for set recyclerview
        View view = inflater.inflate(R.layout.fragment_patient_home, container, false);
        gridView_patient_home = (GridView)view.findViewById(R.id.gridview_patient_home);
        customPatientHomeAdapter = new CustomPatientHomeAdapter(getDataset(),context);
        gridView_patient_home.setAdapter(customPatientHomeAdapter);
        gridView_patient_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        onItemClickMethod(title[0]);
                        break;

                    case 1:
                        onItemClickMethod(title[1]);
                        break;

                    case 2:
                        onItemClickMethod(title[2]);
                        break;

                    case 3:
                        onItemClickMethod(title[3]);
                        break;

                    case 4:
                        onItemClickMethod(title[4]);
                        break;

                    case 5:
                        onItemClickMethod(title[5]);
                        break;

                    case 6:
                        onItemClickMethod(title[6]);
                        break;

                    case 7:
                        onItemClickMethod(title[7]);
                        break;
                    case 8:
                        onItemClickMethod(title[8]);
                        break;

                }
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");
    }
    public ArrayList<PatientHomeBean> getDataset() {
        ArrayList<PatientHomeBean> result = new ArrayList<PatientHomeBean>();
        for (int i = 0; i < title.length; i++) {
            PatientHomeBean patientHomeBean = new PatientHomeBean(image[i],title[i]);
            result.add(i, patientHomeBean);
        }
        return result;
    }

    public void onItemClickMethod(String title){
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        patient_doDoctorSpecialityFragment = new PatientDoctorSpecialityFragment();
        fragment = PatientDoctorSpecialityFragment.class;
        Bundle bundle= new Bundle();
        bundle.putString("title",title);
        patient_doDoctorSpecialityFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.patient_content_display,patient_doDoctorSpecialityFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.show(patient_doDoctorSpecialityFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

}
