package com.project.docxp.doctorfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.docxp.R;
import com.project.docxp.bean.DocmyschedualBean;
import com.project.docxp.customadapter.CustomerDocmyschedualAdapter;
import com.project.docxp.services.DocMyScheduleServices;
import com.project.docxp.utility.SharedPreferenceData;

public class DocMyScheduleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recycler_doc_myschedule;
    private RecyclerView.LayoutManager doc_myschedual_LayoutManager;
    private CustomerDocmyschedualAdapter customer_Docmyschedual_Adapter;
    private Context context;
    private int second = 1000;
    private FragmentActivity fragmentActivity;
    private SwipeRefreshLayout  swiperefresh_doc_myschedual;
    SharedPreferences preferences;
    DocmyschedualBean bean= new DocmyschedualBean();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doc_my_schedule, container, false);
        context = getContext();
        fragmentActivity=getActivity();
        preferences=context.getSharedPreferences(SharedPreferenceData.SHAREPREF,Context.MODE_PRIVATE);
        getDocMyScheduleID(view);

        recycler_doc_myschedule.setHasFixedSize(true);
        recycler_doc_myschedule.setNestedScrollingEnabled(false);
        doc_myschedual_LayoutManager = new LinearLayoutManager(view.getContext());
        recycler_doc_myschedule.setLayoutManager(doc_myschedual_LayoutManager);
        getDocMyScheduleData();
        swiperefresh_doc_myschedual.setOnRefreshListener(this);


        return view;
    }

    private void getDocMyScheduleID(View view) {
        swiperefresh_doc_myschedual=(SwipeRefreshLayout)view.findViewById(R.id.swiperefresh_doc_myschedual);
        recycler_doc_myschedule = (RecyclerView) view.findViewById(R.id.recycler_doc_myschedule);
    }
    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()

            {
                swiperefresh_doc_myschedual.setRefreshing(true);
                getDocMyScheduleData();
            }
        }, second);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("My Schedule");

    }



    public void getDocMyScheduleData() {
        System.out.println("====in getDocMyScheduleData=== ");
        String docemail = preferences.getString(SharedPreferenceData.LOGIN_EMAIL, "");
        bean.setDoc_email(docemail);
        DocMyScheduleServices services= new DocMyScheduleServices();
        services.getConfirmAppointmentData(context,fragmentActivity,recycler_doc_myschedule,bean,swiperefresh_doc_myschedual);
    }
}
