package com.project.docxp.patientfragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.project.docxp.R;
import com.project.docxp.bean.BookAppointmentBean;
import com.project.docxp.dao.BookAppointmentSpinnerDao;
import com.project.docxp.services.BookAppointmentServices;
import com.project.docxp.utility.ConnectivityReceiver;
import com.project.docxp.utility.SharedPreferenceData;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientBookAppointmentFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private ProgressDialog progressDialog;
    private EditText editText_patient_name, edittext_patient_email, edittext_patient_contact;
    private static String patient_name, patient_email, patient_contact, patient_gender, patient_city, patient_doctor,
            patient_hospital, date, time, speciality;
    private EditText edittext_patient_book_apppointment_date, edittext_patient_book_apppointment_time;
    private int year, month, dayOfMonth, hourOfDay, minute;
    private RadioGroup radiogroup_patient_bookappointment_gender;
    private RadioButton radiobtn_patient_bookappointment_gender_male, radiobtn_patient_bookappointment_gender_female;
    private Button register_button_register;
    private static Spinner spinner_pateint_bookappointmenrt_city, spinner_pateint_bookappointmenrt_doctor,
            spinner_pateint_bookappointmenrt_hospital, spinner_pateint_bookappointmenrt_speciality;
    private AwesomeValidation awesomeValidation;
    private FragmentActivity fragmentActivity;
    private BookAppointmentBean bookAppointmentBean;
    private BookAppointmentSpinnerDao spinnerDao;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private PatientHomeFragment patientHomeFragment;
    private Class<PatientHomeFragment> fragment;
    private boolean networkConnection;
    SharedPreferences preferences;

    public PatientBookAppointmentFragment() {
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
        View view = inflater.inflate(R.layout.fragment_patient_book_appointment, container, false);
        getPatientBookAppointmentFragmentID(view);
        progressDialog = new ProgressDialog(context);
        edittext_patient_email.setText(patient_email);
        edittext_patient_email.setEnabled(false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Book Appointment");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            /*case R.id.edittext_patient_book_apppointment_date:
                final Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                System.out.println("====dayOfMonth==" + dayOfMonth + "/" + month + "/" + year);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edittext_patient_book_apppointment_date.setText(dayOfMonth + "/" + month + "/" + year);


                    }
                }, year, month + 1, dayOfMonth);
                datePickerDialog.show();
                break;

            case R.id.edittext_patient_book_apppointment_time:
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

                        edittext_patient_book_apppointment_time.setText(hourOfDay + ":" + minute + "");
                    }
                }, hourOfDay, minute, true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    timePickerDialog.create();
                }
                timePickerDialog.show();
                break;*/

            case R.id.register_button_register:

                if (radiobtn_patient_bookappointment_gender_male.isChecked()) {
                    patient_gender = "Male";
                } else if (radiobtn_patient_bookappointment_gender_female.isChecked()) {
                    patient_gender = "Female";
                }
                System.out.println("======" + patient_gender);
                Toast.makeText(context, "" + patient_gender, Toast.LENGTH_SHORT).show();
                patient_city = spinner_pateint_bookappointmenrt_city.getSelectedItem().toString();
                speciality = spinner_pateint_bookappointmenrt_speciality.getSelectedItem().toString();
                System.out.println("====" + speciality);
                patient_doctor = spinner_pateint_bookappointmenrt_doctor.getSelectedItem().toString();
                System.out.println("======" + patient_doctor);
                patient_hospital = spinner_pateint_bookappointmenrt_hospital.getSelectedItem().toString();
                System.out.println("======" + patient_hospital);


                if (!TextUtils.isEmpty(editText_patient_name.getText().toString()) && editText_patient_name.getText().toString().matches("[a-zA-Z ]+")) {
                    patient_name = editText_patient_name.getText().toString();
                    System.out.println("======" + patient_name);
                } else {
                    editText_patient_name.setError("Enter valid name");
                }
                if (!TextUtils.isEmpty(edittext_patient_email.getText().toString()) && Patterns.EMAIL_ADDRESS.matcher(edittext_patient_email.getText().toString()).matches()) {
                    patient_email = edittext_patient_email.getText().toString();
                    System.out.println("======" + patient_email);
                } else {
                    edittext_patient_email.setError("Enter valid Email Address");
                }

                if (!TextUtils.isEmpty(edittext_patient_contact.getText().toString()) && Patterns.PHONE.matcher(edittext_patient_contact.getText().toString()).matches()) {
                    patient_contact = edittext_patient_contact.getText().toString();
                    System.out.println("======" + patient_contact);
                } else {
                    edittext_patient_contact.setError("Enter valid Mobile No");
                }
                if (!TextUtils.isEmpty(edittext_patient_book_apppointment_date.getText().toString())) {
                    date = edittext_patient_book_apppointment_date.getText().toString();
                    System.out.println("======" + date);
                } else {
                    edittext_patient_book_apppointment_date.setError("Enter Date");
                }
                if (!TextUtils.isEmpty(edittext_patient_book_apppointment_time.getText().toString())) {
                    time = edittext_patient_book_apppointment_time.getText().toString();
                    System.out.println("======" + time);
                } else {
                    edittext_patient_book_apppointment_date.setError("Enter Date");
                }


                //set form data n bean
                bookAppointmentBean = new BookAppointmentBean();

                bookAppointmentBean.setPatient_name(patient_name);
                bookAppointmentBean.setPatient_email(patient_email);
                bookAppointmentBean.setPatient_contact(patient_contact);
                bookAppointmentBean.setPatient_gender(patient_gender);
                bookAppointmentBean.setPatient_city(patient_city);
                bookAppointmentBean.setSpeciality(speciality);
                bookAppointmentBean.setPatient_doctor(patient_doctor);
                bookAppointmentBean.setPatient_hospital(patient_hospital);
                bookAppointmentBean.setDate(date);
                bookAppointmentBean.setTime(time);
                if (networkConnection = checkConnection(context)) {
                    BookAppointmentServices bookAppointmentServices = new BookAppointmentServices();
                    bookAppointmentServices.appointmentServices(bookAppointmentBean, getActivity().getApplicationContext(), progressDialog);

                    editText_patient_name.setText("");
                   // edittext_patient_email.setText("");
                    edittext_patient_contact.setText("");
                    radiogroup_patient_bookappointment_gender.clearCheck();
                    edittext_patient_book_apppointment_date.setText("");
                    edittext_patient_book_apppointment_time.setText("");
                }

               /*fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                patientHomeFragment = new PatientHomeFragment();
                fragment = PatientHomeFragment.class;
                fragmentTransaction.replace(R.id.patient_content_display, patientHomeFragment);
                fragmentTransaction.show(patientHomeFragment);
                fragmentTransaction.commitAllowingStateLoss();*/

                break;
        }
    }

    public void getPatientBookAppointmentFragmentID(View view) {
        spinnerDao = new BookAppointmentSpinnerDao();

        editText_patient_name = (EditText) view.findViewById(R.id.edittext_patient_name);
        edittext_patient_email = (EditText) view.findViewById(R.id.edittext_patient_email);
        edittext_patient_contact = (EditText) view.findViewById(R.id.edittext_patient_contact);
        radiogroup_patient_bookappointment_gender = (RadioGroup) view.findViewById(R.id.radiogroup_patient_bookappointment_gender);
        edittext_patient_book_apppointment_date = (EditText) view.findViewById(R.id.edittext_patient_book_apppointment_date);
        edittext_patient_book_apppointment_time = (EditText) view.findViewById(R.id.edittext_patient_book_apppointment_time);
        radiobtn_patient_bookappointment_gender_male = (RadioButton) view.findViewById(R.id.radiobtn_patient_bookappointment_gender_male);
        radiobtn_patient_bookappointment_gender_female = (RadioButton) view.findViewById(R.id.radiobtn_patient_bookappointment_gender_female);
        spinner_pateint_bookappointmenrt_city = (Spinner) view.findViewById(R.id.spinner_pateint_bookappointmenrt_city);
        spinner_pateint_bookappointmenrt_doctor = (Spinner) view.findViewById(R.id.spinner_pateint_bookappointmenrt_doctor);
        spinner_pateint_bookappointmenrt_hospital = (Spinner) view.findViewById(R.id.spinner_pateint_bookappointmenrt_hospital);
        spinner_pateint_bookappointmenrt_speciality = (Spinner) view.findViewById(R.id.spinner_pateint_bookappointmenrt_speciality);

        System.out.println("====calling city spinner=====");
        spinner_pateint_bookappointmenrt_city = spinnerDao.getCitySpinnerData(context, bookAppointmentBean, spinner_pateint_bookappointmenrt_city);
        System.out.println("=========spinner_pateint_bookappointmenrt_city===========" + spinner_pateint_bookappointmenrt_city);
        // patient_city = spinner_pateint_bookappointmenrt_city.getSelectedItem().toString();
        //System.out.println("======patient_city========" + patient_city);
        spinner_pateint_bookappointmenrt_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String city = spinner_pateint_bookappointmenrt_city.getSelectedItem().toString();
                System.out.println("======after selection city======" + city);
                spinner_pateint_bookappointmenrt_speciality = spinnerDao.getSpecialitySpinnerData(context, bookAppointmentBean, spinner_pateint_bookappointmenrt_speciality, city);
                spinner_pateint_bookappointmenrt_speciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        speciality = spinner_pateint_bookappointmenrt_speciality.getSelectedItem().toString();
                        System.out.println("======after selection speciality========" + speciality);
                        spinner_pateint_bookappointmenrt_hospital = spinnerDao.getHospitalSpinnerData(context, bookAppointmentBean, spinner_pateint_bookappointmenrt_hospital, speciality, city);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_pateint_bookappointmenrt_hospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                patient_hospital = spinner_pateint_bookappointmenrt_hospital.getSelectedItem().toString();
                System.out.println("==========patient_hospital==========" + patient_hospital);
                spinnerDao.getDoctorSpinnerData(context, bookAppointmentBean, spinner_pateint_bookappointmenrt_doctor, patient_hospital, speciality);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        register_button_register = (Button) view.findViewById(R.id.register_button_register);
        register_button_register.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            edittext_patient_book_apppointment_date.setShowSoftInputOnFocus(false);
            edittext_patient_book_apppointment_date.setCursorVisible(false);
        }

        edittext_patient_book_apppointment_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

                            int dayOfMonth1 = dayOfMonth;
                            int month1 = month + 1;
                            int year1 = year;

                            edittext_patient_book_apppointment_date.setText(dayOfMonth1 + "/" + month1 + "/" + year1);


                        }
                    }, year, month, dayOfMonth);
                    datePickerDialog.show();

                } else {
                    edittext_patient_book_apppointment_date.setOnClickListener(new View.OnClickListener() {
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

                                    int dayOfMonth1 = dayOfMonth;
                                    int month1 = month + 1;
                                    int year1 = year;

                                    edittext_patient_book_apppointment_date.setText(dayOfMonth1 + "/" + month1 + "/" + year1);


                                }
                            }, year, month, dayOfMonth);
                            datePickerDialog.show();

                        }
                    });
                }

            }

        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            edittext_patient_book_apppointment_time.setShowSoftInputOnFocus(false);
            edittext_patient_book_apppointment_time.setCursorVisible(false);
        }
        edittext_patient_book_apppointment_time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

                            edittext_patient_book_apppointment_time.setText(hourOfDay + ":" + minute + "");
                        }
                    }, hourOfDay, minute, true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        timePickerDialog.create();
                    }
                    timePickerDialog.show();

                } else {
                    edittext_patient_book_apppointment_time.setOnClickListener(new View.OnClickListener() {
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

                                    edittext_patient_book_apppointment_time.setText(hourOfDay + ":" + minute + "");
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
        // edittext_patient_book_apppointment_time.setOnClickListener(this);

        //validations
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(getActivity(), R.id.edittext_patient_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(getActivity(), R.id.edittext_patient_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(getActivity(), R.id.edittext_patient_contact, "^[0-9]{10}$", R.string.mobileerror);

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
