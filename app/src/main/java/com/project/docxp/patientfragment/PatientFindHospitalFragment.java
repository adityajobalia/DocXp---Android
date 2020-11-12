package com.project.docxp.patientfragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.docxp.R;
import com.project.docxp.bean.PatientFindHospitalBean;
import com.project.docxp.services.PatientFindHospitalServices;


public class PatientFindHospitalFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener /*implements SearchView.OnQueryTextListener*/ {
    private ActionBar actionBar;
    private EditText edittext_search_edittext;
    //private SearchView searchview_pateint_find_hospital;
    private RecyclerView recyclerView_patient_findhospital;
    private RecyclerView.LayoutManager layoutManager_patient_findhospital;
    private Context context;
    private FragmentActivity fragmentActivity;
    PatientFindHospitalServices services;
    private int second = 1000;
    PatientFindHospitalBean bean = new PatientFindHospitalBean();
    private SwipeRefreshLayout swiperefresh_patient_findhospital;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_find_hospital, container, false);
        context = getContext();
        fragmentActivity = getActivity();
        edittext_search_edittext = (EditText) view.findViewById(R.id.edittext_search_edittext);
        edittext_search_edittext.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event != null &&
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event == null || !event.isShiftPressed()) {
                        String city = edittext_search_edittext.getText().toString();
                        Toast.makeText(context, city, Toast.LENGTH_SHORT).show();
                        services = new PatientFindHospitalServices();
                        services.searchByCity(context.getApplicationContext(), city, fragmentActivity, recyclerView_patient_findhospital, swiperefresh_patient_findhospital);
                        edittext_search_edittext.setText("");
                        return true; // consume.
                    }
                }

                return false;
            }
        });

        swiperefresh_patient_findhospital = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_patient_findhospital);

        // to set the list in recycler
        recyclerView_patient_findhospital = (RecyclerView) view.findViewById(R.id.recyclerview_patient_findhospital);
        recyclerView_patient_findhospital.setHasFixedSize(true);
        recyclerView_patient_findhospital.setNestedScrollingEnabled(false);
        layoutManager_patient_findhospital = new LinearLayoutManager(view.getContext());
        recyclerView_patient_findhospital.setLayoutManager(layoutManager_patient_findhospital);

        getPatientFindHospitalData();
        swiperefresh_patient_findhospital.setOnRefreshListener(this);
        System.out.println("recycler set=============");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Find Hospital");
    }


    public void getPatientFindHospitalData() {
        System.out.println("======getPatientFindHospitalData===");
        services = new PatientFindHospitalServices();
        System.out.println("====== go to services===getPatientFindHospitalData===");
        services.getPatientFinHospitalList(context.getApplicationContext(), bean, fragmentActivity, recyclerView_patient_findhospital, swiperefresh_patient_findhospital);
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()

            {
                swiperefresh_patient_findhospital.setRefreshing(true);
                getPatientFindHospitalData();
            }
        }, second);
    }


}
