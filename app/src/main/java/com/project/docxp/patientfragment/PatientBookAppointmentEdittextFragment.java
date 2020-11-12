package com.project.docxp.patientfragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.docxp.R;
import com.project.docxp.bean.BookAppointmentBean;
import com.project.docxp.services.BookAppointmentServices;
import com.project.docxp.utility.ConnectivityReceiver;
import com.project.docxp.utility.SharedPreferenceData;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientBookAppointmentEdittextFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private ProgressDialog progressDialog;
    private EditText edittext_patient_bookappointment_edittext_name, edittext_patient_bookappointment_edittext_email, edittext_patient_bookappointment_edittext_contact;
    private String patient_name, patient_email, patient_contact, patient_gender, patient_city, patient_doctor,
            patient_hospital, date, time;
    private EditText editext_patient_bookappointment_edittext_city, editext_patient_bookappointment_edittext_doctor,
            editext_patient_bookappointment_edittext_hospital, edittext_patient_book_apppointment_edittext_date,
            edittext_patient_book_apppointment_edittext_time, editext_patient_bookappointment_edittext_speciality;
    private int year, month, dayOfMonth, hourOfDay, minute;
    private RadioGroup radiogroup_patient_bookappointment_edittext_gender;
    private RadioButton radiobtn_patient_bookappointment_edittext_gender_male, radiobtn_patient_bookappointment_edittext_gender_female;
    private Button register_button_register_bookappointment_edittext;
    private BookAppointmentBean bookAppointmentBean, bean;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private static String doctorname, speciality, doctor, cityname, hospitalname, doc_speciality;
    BookAppointmentServices appointmentServices = new BookAppointmentServices();
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private PatientHomeFragment patientHomeFragment;
    private Class<PatientHomeFragment> fragment;
    private FragmentActivity fragmentActivity;
    private boolean networkConnection;
    SharedPreferences preferences;

    public PatientBookAppointmentEdittextFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        preferences = context.getSharedPreferences(SharedPreferenceData.SHAREPREF, Context.MODE_PRIVATE);
        patient_email = preferences.getString(SharedPreferenceData.LOGIN_EMAIL, "");
        fragmentActivity = getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_book_appointment_edittext, container, false);
        getPatientBookAppointmentEdittextFragmentID(view);
        edittext_patient_bookappointment_edittext_email.setText(patient_email);
        edittext_patient_bookappointment_edittext_email.setEnabled(false);

        //to get the data from doctor speciality fragment when we have doctorname,city,hospital
        Bundle bundle1 = getArguments();
        cityname = bundle1.getString("city");
        doctor = bundle1.getString("doctorname");
        hospitalname = bundle1.getString("hospitalname");
        doc_speciality = bundle1.getString("speciality");

        //to get the data from find hospital fragment when we only have doctorname
        Bundle bundle = getArguments();
        doctorname = bundle.getString("doctorname");
        speciality = bundle.getString("speciality");

        System.out.println("=============================");
        System.out.println("=======doctorname======" + doctorname);
        System.out.println("=======speciality======" + speciality);
        if (doctorname != null && speciality != null) {
            System.out.println("===========inside if with one condition=================");

            editext_patient_bookappointment_edittext_doctor.setText(doctorname);
            editext_patient_bookappointment_edittext_doctor.setEnabled(false);
            editext_patient_bookappointment_edittext_speciality.setText(speciality);
            editext_patient_bookappointment_edittext_speciality.setEnabled(false);
            appointmentServices.getEdittextData(context, doctorname, speciality, editext_patient_bookappointment_edittext_city, editext_patient_bookappointment_edittext_hospital);
        } else if (cityname != null && doctor != null && hospitalname != null && doc_speciality != null) {
            System.out.println("==========inside if with 3 condition===========");
            editext_patient_bookappointment_edittext_city.setText(cityname);
            editext_patient_bookappointment_edittext_city.setEnabled(false);
            editext_patient_bookappointment_edittext_doctor.setText(doctor);
            editext_patient_bookappointment_edittext_doctor.setEnabled(false);
            editext_patient_bookappointment_edittext_hospital.setText(hospitalname);
            editext_patient_bookappointment_edittext_hospital.setEnabled(false);
            editext_patient_bookappointment_edittext_speciality.setText(doc_speciality);
            editext_patient_bookappointment_edittext_speciality.setEnabled(false);
            appointmentServices.setEdittextDoctorSpecialityData(context, bookAppointmentBean, cityname, doctor, hospitalname, doc_speciality, progressDialog);

        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Book Appointment");
    }


    private void getPatientBookAppointmentEdittextFragmentID(View view) {
        edittext_patient_bookappointment_edittext_name = (EditText) view.findViewById(R.id.edittext_patient_bookappointment_edittext_name);
        edittext_patient_bookappointment_edittext_email = (EditText) view.findViewById(R.id.edittext_patient_bookappointment_edittext_email);
        edittext_patient_bookappointment_edittext_contact = (EditText) view.findViewById(R.id.edittext_patient_bookappointment_edittext_contact);
        editext_patient_bookappointment_edittext_city = (EditText) view.findViewById(R.id.editext_patient_bookappointment_edittext_city);
        editext_patient_bookappointment_edittext_speciality = (EditText) view.findViewById(R.id.editext_patient_bookappointment_edittext_speciality);
        editext_patient_bookappointment_edittext_doctor = (EditText) view.findViewById(R.id.editext_patient_bookappointment_edittext_doctor);
        editext_patient_bookappointment_edittext_hospital = (EditText) view.findViewById(R.id.editext_patient_bookappointment_edittext_hospital);
        edittext_patient_book_apppointment_edittext_date = (EditText) view.findViewById(R.id.edittext_patient_book_apppointment_edittext_date);
        edittext_patient_book_apppointment_edittext_time = (EditText) view.findViewById(R.id.edittext_patient_book_apppointment_edittext_time);
        radiogroup_patient_bookappointment_edittext_gender = (RadioGroup) view.findViewById(R.id.radiogroup_patient_bookappointment_edittext_gender);
        radiobtn_patient_bookappointment_edittext_gender_male = (RadioButton) view.findViewById(R.id.radiobtn_patient_bookappointment_edittext_gender_male);
        radiobtn_patient_bookappointment_edittext_gender_female = (RadioButton) view.findViewById(R.id.radiobtn_patient_bookappointment_edittext_gender_female);

        register_button_register_bookappointment_edittext = (Button) view.findViewById(R.id.register_button_register_bookappointment_edittext);
        register_button_register_bookappointment_edittext.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            edittext_patient_book_apppointment_edittext_date.setShowSoftInputOnFocus(false);
            edittext_patient_book_apppointment_edittext_date.setCursorVisible(false);
        }
        edittext_patient_book_apppointment_edittext_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    final Calendar calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    System.out.println("====dayOfMonth==" + dayOfMonth + "/" + month + "/" + year);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            int dayOfMonth1=dayOfMonth;
                            int month1=month+1;
                            int year1=year;
                            edittext_patient_book_apppointment_edittext_date.setText(dayOfMonth1 + "/" + month1 + "/" + year1);


                        }
                    }, year, month , dayOfMonth);
                    datePickerDialog.show();
                } else {
                    edittext_patient_book_apppointment_edittext_date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Calendar calendar = Calendar.getInstance();
                            year = calendar.get(Calendar.YEAR);
                            month = calendar.get(Calendar.MONTH);
                            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                            System.out.println("====dayOfMonth==" + dayOfMonth + "/" + month + "/" + year);

                            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    int dayOfMonth1=dayOfMonth;
                                    int month1=month+1;
                                    int year1=year;
                                    edittext_patient_book_apppointment_edittext_date.setText(dayOfMonth1 + "/" + month1 + "/" + year1);


                                }
                            }, year, month , dayOfMonth);
                            datePickerDialog.show();
                        }
                    });
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            edittext_patient_book_apppointment_edittext_time.setShowSoftInputOnFocus(false);
            edittext_patient_book_apppointment_edittext_time.setCursorVisible(false);
        }
        //edittext_patient_book_apppointment_edittext_time.setOnClickListener(this);
        edittext_patient_book_apppointment_edittext_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar cal = Calendar.getInstance();
                    hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
                    minute = cal.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                         /*
                        * Logic for AM and PM
                        */

                            String AM_PM;
                            if (hourOfDay < 12) {
                                AM_PM = "AM";

                            } else {
                                AM_PM = "PM";
                                hourOfDay = hourOfDay - 12;
                            }

                            edittext_patient_book_apppointment_edittext_time.setText(hourOfDay + ":" + minute + "");
                        }
                    }, hourOfDay, minute, true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        timePickerDialog.create();
                    }
                    timePickerDialog.show();
                } else {
                    edittext_patient_book_apppointment_edittext_time.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar cal = Calendar.getInstance();
                            hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
                            minute = cal.get(Calendar.MINUTE);
                            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                         /*
                        * Logic for AM and PM
                        */

                                    String AM_PM;
                                    if (hourOfDay < 12) {
                                        AM_PM = "AM";

                                    } else {
                                        AM_PM = "PM";
                                        hourOfDay = hourOfDay - 12;
                                    }

                                    edittext_patient_book_apppointment_edittext_time.setText(hourOfDay + ":" + minute + "");
                                }
                            }, hourOfDay, minute, true);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                timePickerDialog.create();
                            }
                            timePickerDialog.show();
                        }
                    });
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.edittext_patient_book_apppointment_edittext_date:
                final Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH)+1;
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                System.out.println("====dayOfMonth==" + dayOfMonth + "/" + month + "/" + year);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edittext_patient_book_apppointment_edittext_date.setText(dayOfMonth + "/" + month + "/" + year);


                    }
                }, year, month , dayOfMonth);
                datePickerDialog.show();
                break;

            case R.id.edittext_patient_book_apppointment_edittext_time:
                Calendar cal = Calendar.getInstance();
                hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
                minute = cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                         *//*
                        * Logic for AM and PM
                        *//*

                        String AM_PM;
                        if (hourOfDay < 12) {
                            AM_PM = "AM";

                        } else {
                            AM_PM = "PM";
                            hourOfDay = hourOfDay - 12;
                        }

                        edittext_patient_book_apppointment_edittext_time.setText(hourOfDay + ":" + minute + " " + AM_PM);
                    }
                }, hourOfDay, minute, false);
                timePickerDialog.create();
                timePickerDialog.show();
                break;
*/
            case R.id.register_button_register_bookappointment_edittext:


                if (radiobtn_patient_bookappointment_edittext_gender_male.isChecked()) {
                    patient_gender = "Male";
                } else if (radiobtn_patient_bookappointment_edittext_gender_female.isChecked()) {
                    patient_gender = "Female";
                }
                System.out.println("======" + patient_gender);
                Toast.makeText(context, "" + patient_gender, Toast.LENGTH_SHORT).show();





                if (!TextUtils.isEmpty(edittext_patient_bookappointment_edittext_name.getText().toString()) && edittext_patient_bookappointment_edittext_name.getText().toString().matches("[a-zA-Z ]+")) {
                    patient_name = edittext_patient_bookappointment_edittext_name.getText().toString();
                    System.out.println("======" + patient_name);
                } else {
                    edittext_patient_bookappointment_edittext_name.setError("Enter valid name");
                }
                /*if (!TextUtils.isEmpty(edittext_patient_bookappointment_edittext_email.getText().toString()) && Patterns.EMAIL_ADDRESS.matcher(edittext_patient_bookappointment_edittext_email.getText().toString()).matches()) {
                    patient_email = edittext_patient_bookappointment_edittext_email.getText().toString();
                    System.out.println("======" + patient_email);
                } else {
                    edittext_patient_bookappointment_edittext_email.setError("Enter valid Email Address");
                }*/

                if (!TextUtils.isEmpty(edittext_patient_bookappointment_edittext_contact.getText().toString()) && Patterns.PHONE.matcher(edittext_patient_bookappointment_edittext_contact.getText().toString()).matches()) {

                    patient_contact = edittext_patient_bookappointment_edittext_contact.getText().toString();
                    System.out.println("======" + patient_contact);
                } else {
                    edittext_patient_bookappointment_edittext_contact.setError("Enter valid Mobile No");
                }
                if (!TextUtils.isEmpty(edittext_patient_book_apppointment_edittext_date.getText().toString())){
                    date = edittext_patient_book_apppointment_edittext_date.getText().toString();
                    System.out.println("======" + date);
                }else{
                    edittext_patient_book_apppointment_edittext_date.setError("Enter Date");
                }
                if (!TextUtils.isEmpty(edittext_patient_book_apppointment_edittext_time.getText().toString())){

                    time = edittext_patient_book_apppointment_edittext_time.getText().toString();
                    System.out.println("======" + time);

                }else{
                    edittext_patient_book_apppointment_edittext_time.setError("Enter Date");
                }
                bookAppointmentBean = new BookAppointmentBean();
                bookAppointmentBean.setPatient_name(patient_name);
                bookAppointmentBean.setPatient_email(patient_email);
                bookAppointmentBean.setPatient_contact(patient_contact);
                bookAppointmentBean.setPatient_gender(patient_gender);
                bookAppointmentBean.setDate(date);
                bookAppointmentBean.setTime(time);

                edittext_patient_bookappointment_edittext_name.setText("");
               // edittext_patient_bookappointment_edittext_email.setText(bookAppointmentBean.getPatient_email());
               // edittext_patient_bookappointment_edittext_email.setEnabled(false);
                edittext_patient_bookappointment_edittext_contact.setText("");
                edittext_patient_book_apppointment_edittext_time.setText("");
                edittext_patient_book_apppointment_edittext_date.setText("");
                radiogroup_patient_bookappointment_edittext_gender.clearCheck();

if (networkConnection = checkConnection(context)) {
                BookAppointmentServices bookAppointmentServices = new BookAppointmentServices();
                bookAppointmentServices.appointmentEdittextServices(bookAppointmentBean, getActivity().getApplicationContext(), progressDialog);

                /*fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                patientHomeFragment = new PatientHomeFragment();
                fragment = PatientHomeFragment.class;

                fragmentTransaction.replace(R.id.patient_content_display, patientHomeFragment);
                fragmentTransaction.show(patientHomeFragment);
                fragmentTransaction.commitAllowingStateLoss();
*/
}
                break;
        }

    }
private boolean checkConnection(Context applicationContext) {
        boolean isConnected = ConnectivityReceiver.isConnected(applicationContext);
        System.out.println("======isConnected======="+isConnected);
        boolean networkStatus = showSnack(isConnected);
        System.out.println("======networkStatus======"+networkStatus);
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
