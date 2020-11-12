package com.project.docxp.doctorfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.docxp.R;
import com.project.docxp.utility.ConnectivityReceiver;


public class DocHomeFragment extends Fragment {

    Context context;
    CardView cardview_doc_home_listofappointment, cardview_doc_home_myschedule;
    private boolean networkConnection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_doc_home, container, false);

        cardview_doc_home_listofappointment = (CardView) view.findViewById(R.id.cardview_doc_home_listofappointment);
        cardview_doc_home_listofappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkConnection = checkConnection(context)) {
                    Fragment doc_listofappointment = new DocListOfAppointmentsFragment();
                    FragmentManager fragmentManager_doc_listofappointment = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction_doc_listofappointment = fragmentManager_doc_listofappointment.beginTransaction();
                    fragmentTransaction_doc_listofappointment.replace(R.id.doc_content_display, doc_listofappointment);
                    fragmentTransaction_doc_listofappointment.addToBackStack(null);
                    fragmentTransaction_doc_listofappointment.commit();
                }
            }
        });
        cardview_doc_home_myschedule=(CardView)view.findViewById(R.id.cardview_doc_home_myschedule);
        cardview_doc_home_myschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkConnection = checkConnection(context)) {
                    Fragment doc_myschedual = new DocMyScheduleFragment();
                    FragmentManager fragmentManager_doc_myschedual = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction_doc_myschedual = fragmentManager_doc_myschedual.beginTransaction();
                    fragmentTransaction_doc_myschedual.replace(R.id.doc_content_display, doc_myschedual);
                    fragmentTransaction_doc_myschedual.addToBackStack(null);
                    fragmentTransaction_doc_myschedual.commit();
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
