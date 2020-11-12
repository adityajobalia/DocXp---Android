package com.project.docxp.doctorfragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.project.docxp.R;
import com.project.docxp.bean.DoclistofappointmentcontentBean;
import com.project.docxp.services.DocListOfAppointmentServices;
import com.project.docxp.utility.ConnectivityReceiver;
import com.project.docxp.utility.SharedPreferenceData;

import java.util.Calendar;


public class DocListOfAppointmentsFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerview_doc_listofappointment;
    private RecyclerView.LayoutManager doc_lstapp_content_LayoutManager;
    private EditText edittext_doc_listofappoinrment;
    private Context context;
    private FragmentActivity fragmentActivity;
    private int year, month, dayOfMonth;
    private Button button_doc_listofappoinrments_date;
    private SwipeRefreshLayout swiperefresh_doc_listofappointment;
    private int second = 1000;
    SharedPreferences preferences;
    private boolean networkConnection;
    private DoclistofappointmentcontentBean doclistofappointmentcontentBean = new DoclistofappointmentcontentBean();
    private DocListOfAppointmentServices docListOfAppointmentServices;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doc_list_of_appointments, container, false);

        context = getContext();

        fragmentActivity = getActivity();
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        //get id
        getDocListOfAppointmentsID(view);

        button_doc_listofappoinrments_date.setOnClickListener(this);
        edittext_doc_listofappoinrment.setShowSoftInputOnFocus(false);
        edittext_doc_listofappoinrment.setOnClickListener(this);


        recyclerview_doc_listofappointment.setNestedScrollingEnabled(false);
        recyclerview_doc_listofappointment.setHasFixedSize(true);
        doc_lstapp_content_LayoutManager = new LinearLayoutManager(view.getContext());
        recyclerview_doc_listofappointment.setLayoutManager(doc_lstapp_content_LayoutManager);
        getListofAppointmentData();
        swiperefresh_doc_listofappointment.setOnRefreshListener(this);
        return view;
    }

    private void getDocListOfAppointmentsID(View view) {
        button_doc_listofappoinrments_date = (Button) view.findViewById(R.id.button_doc_listofappoinrments_date);

        edittext_doc_listofappoinrment = (EditText) view.findViewById(R.id.edittext_doc_listofappoinrment);

        // id of recycler
        recyclerview_doc_listofappointment = (RecyclerView) view.findViewById(R.id.recyclerview_doc_listofappointment);
        swiperefresh_doc_listofappointment = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_doc_listofappointment);
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()

            {
                swiperefresh_doc_listofappointment.setRefreshing(true);
                getListofAppointmentData();
            }
        }, second);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("List of Appointment");

    }


    public void getListofAppointmentData() {
        System.out.println("===========in getListofAppointmentData()");
        String docemail = preferences.getString(SharedPreferenceData.LOGIN_EMAIL, "");
        doclistofappointmentcontentBean.setDoc_email(docemail);
        System.out.println("====go to services===");

        docListOfAppointmentServices = new DocListOfAppointmentServices();
        docListOfAppointmentServices.getDocListofAppointment(context.getApplicationContext(), doclistofappointmentcontentBean, fragmentActivity, recyclerview_doc_listofappointment, swiperefresh_doc_listofappointment);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edittext_doc_listofappoinrment:
                final Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                System.out.println("====dayOfMonth==" + dayOfMonth + "/" + month + 1 + "/" + year);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int dayOfMonth1 = dayOfMonth;
                        int month1 = month + 1;
                        int year1 = year;
                        edittext_doc_listofappoinrment.setText(dayOfMonth1 + "/" + month1 + "/" + year1);

                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();

                break;
            case R.id.button_doc_listofappoinrments_date:
                if (networkConnection = checkConnection(context)) {
                    getSortedDataforListofAppointment();
                }
                break;
        }
    }

    public void getSortedDataforListofAppointment() {
        System.out.println("===========in getListofAppointmentDatasorting==");
        String docemail = preferences.getString(SharedPreferenceData.LOGIN_EMAIL, "");
        String date = edittext_doc_listofappoinrment.getText().toString();
        System.out.println("===date==" + date);
        doclistofappointmentcontentBean.setDoc_date(date);
        doclistofappointmentcontentBean.setDoc_email(docemail);
        System.out.println("====go to services===");
        docListOfAppointmentServices = new DocListOfAppointmentServices();
        docListOfAppointmentServices.getDocListofAppointmentbysorting(context.getApplicationContext(), doclistofappointmentcontentBean, fragmentActivity, recyclerview_doc_listofappointment);

    }

    private boolean checkConnection(Context applicationContext) {
        boolean isConnected = ConnectivityReceiver.isConnected(applicationContext);
        System.out.println("======isConnected=======" + isConnected);
        boolean networkStatus = showSnack(isConnected);
        System.out.println("======networkStatus======" + networkStatus);
        return networkStatus;
    }

    private boolean showSnack(boolean isConnected) {
        String message;
        if (isConnected) {
            message = "";
        } else {
            message = "No Internet Connection";
        }

        if (message.equals("")) {
            return true;
        } else {
            //snackbar.show();
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}