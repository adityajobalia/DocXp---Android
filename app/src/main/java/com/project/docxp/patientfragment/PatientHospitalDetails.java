package com.project.docxp.patientfragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.docxp.HospitalMap;
import com.project.docxp.R;
import com.project.docxp.bean.PateintHospitalDetailsBean;
import com.project.docxp.bean.PatientFindHospitalBean;
import com.project.docxp.services.PatientHospitalDetailsServices;
import com.project.docxp.utility.ConnectivityReceiver;
import com.project.docxp.utility.DocXpPermissions;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientHospitalDetails extends Fragment implements View.OnClickListener {
    private TextView textview_hospital_title, textview_hospitaldetails_address, textview_hospitaldetails_contect, textview_hospitaldetails_website;
    private FloatingActionButton button_hospitaldetails_call, fabbutton_hospitaldetails_direction, fabbutton_hospitaldetails_share;
    private FragmentManager fragmentManager;
    private ViewPager viewpager_pateint_hospitaldetails;
    private ImageView imageview_patient_hospital_details;
    private int currentpage = 0;
    private long numpage = 0;
    private Timer timer;
    private CirclePageIndicator circlePageIndicator;
    PatientFindHospitalBean bean;
    PateintHospitalDetailsBean hospitalDetailsBean;
    PatientHospitalDetailsServices services = new PatientHospitalDetailsServices();
    String hospitalname,hospitallocality;
private boolean networkConnection;
    RecyclerView recycler_pateint_hospiitaldetails;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentManager = getActivity().getSupportFragmentManager();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_hospital_details, container, false);
        context = getContext();

        getPatientHospitalDetailsID(view);

       /* final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentpage == numpage) {
                    currentpage = 0;
                }
                viewpager_pateint_hospitaldetails.setCurrentItem(currentpage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);

            }
        }, 500, 3000);

        //set viewPager
        CustomPateintHospitalDetailsPageAdapter customPateintHospitalDetailsPageAdapter = new CustomPateintHospitalDetailsPageAdapter(getActivity().getApplicationContext());
        viewpager_pateint_hospitaldetails.setAdapter(customPateintHospitalDetailsPageAdapter);
        circlePageIndicator.setViewPager(viewpager_pateint_hospitaldetails);*/

        recycler_pateint_hospiitaldetails.setHasFixedSize(true);
        recycler_pateint_hospiitaldetails.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        recycler_pateint_hospiitaldetails.setLayoutManager(manager);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_pateint_hospiitaldetails.setLayoutManager(layoutManager);
        recycler_pateint_hospiitaldetails.setItemAnimator(new DefaultItemAnimator());

        fabbutton_hospitaldetails_direction.setOnClickListener(this);
        Bundle bundle = getArguments();
        bean = (PatientFindHospitalBean) bundle.getSerializable("hospital_obj");
        hospitalname = bean.getTextview_patient_findhospital_Hospitalname();
        hospitallocality=bean.getTextview_patient_findhospital_Hospitalcontect();

        System.out.println("==hospitalname====" + hospitalname);
        System.out.println("=======hospital locality==" + bean.getTextview_patient_findhospital_Hospitalcontect());
        hospitalDetailsBean = new PateintHospitalDetailsBean();
        hospitalDetailsBean.setHospital_name(hospitalname);
        hospitalDetailsBean.setHospital_locality(hospitallocality);

        hospitalDetailsBean=services.getHospitalDetails(context, hospitalDetailsBean, recycler_pateint_hospiitaldetails,
                fragmentManager, textview_hospital_title,
                textview_hospitaldetails_address, textview_hospitaldetails_contect,
                textview_hospitaldetails_website, imageview_patient_hospital_details);


        return view;
    }

   /* private List<PateintHospitalDetailsBean> prepareData() {
        hospitalDetailsBeanlist = new ArrayList<PateintHospitalDetailsBean>();

        for (int i = 0; i < image.length; i++) {
            hospitalDetailsBeanlist.add(new PateintHospitalDetailsBean("Doctor Name", image[i]));
        }
        return hospitalDetailsBeanlist;
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Hospital Details");
    }

    public void getPatientHospitalDetailsID(View view) {
        imageview_patient_hospital_details = (ImageView) view.findViewById(R.id.imageview_patient_hospital_details);
        textview_hospital_title = (TextView) view.findViewById(R.id.textview_hospital_title);

        //set textview for address contect and website
        textview_hospitaldetails_address = (TextView) view.findViewById(R.id.textview_hospitaldetails_address);

        textview_hospitaldetails_contect = (TextView) view.findViewById(R.id.textview_hospitaldetails_contect);

        textview_hospitaldetails_website = (TextView) view.findViewById(R.id.textview_hospitaldetails_website);

        fabbutton_hospitaldetails_direction = (FloatingActionButton) view.findViewById(R.id.fabbutton_hospitaldetails_direction);

        recycler_pateint_hospiitaldetails = (RecyclerView) view.findViewById(R.id.recycler_pateint_hospital_details);


      /*  //set viewpager
        viewpager_pateint_hospitaldetails = (ViewPager) view.findViewById(R.id.viewpager_pateint_hospitaldetails);
        circlePageIndicator = (CirclePageIndicator) view.findViewById(R.id.pageindicator);*/

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fabbutton_hospitaldetails_direction:
                boolean locationPermissions = DocXpPermissions.checkPermissionsArray(DocXpPermissions.LOCATION_PERMISSIONS, context);
networkConnection = checkConnection(context);
                if (locationPermissions && networkConnection) {
                    Toast.makeText(context.getApplicationContext(), "direction", Toast.LENGTH_SHORT).show();
                    Intent mapintent = new Intent(getActivity().getApplicationContext(), HospitalMap.class);
                    Bundle  bundle=new Bundle();
                    bundle.putSerializable("hospital_obj",hospitalDetailsBean);
                    System.out.println("========@@@lat========"+hospitalDetailsBean.getHospital_lat()+hospitalDetailsBean.getHospital_long());
                    mapintent.putExtras(bundle);
                    startActivity(mapintent);



                } else {
                    DocXpPermissions.verifyLocationPermission(DocXpPermissions.LOCATION_PERMISSIONS, context);
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
