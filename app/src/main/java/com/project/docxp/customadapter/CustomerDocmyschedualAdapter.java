package com.project.docxp.customadapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.docxp.R;
import com.project.docxp.bean.DocmyschedualBean;
import com.project.docxp.services.DocListOfAppointmentServices;
import com.project.docxp.utility.ConnectivityReceiver;

import java.util.ArrayList;

/**
 * Created by Devarshi on 21-11-2017.
 */

public class CustomerDocmyschedualAdapter extends RecyclerView.Adapter<CustomerDocmyschedualAdapter.Doc_myschedual_Viewholder> {

    private ArrayList<DocmyschedualBean> docmyscheduallist;
    private Context context;
    private FragmentActivity fragmentActivity;
    private DocmyschedualBean bean;
    private boolean networkConnection;

    public CustomerDocmyschedualAdapter(ArrayList<DocmyschedualBean> dataset, Context context, FragmentActivity fragmentActivity) {
        this.docmyscheduallist = dataset;
        this.context = context;
        this.fragmentActivity=fragmentActivity;
    }


    @Override
    public Doc_myschedual_Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cardview_doc_myschedual, parent, false);
        Doc_myschedual_Viewholder doc_myschedual_viewholder = new Doc_myschedual_Viewholder(view);

        return doc_myschedual_viewholder;
    }

    @Override
    public void onBindViewHolder(Doc_myschedual_Viewholder holder, final int position) {
        bean=docmyscheduallist.get(position);
        holder.textview_doc_myschedula_Name.setText(docmyscheduallist.get(position).getTextview_doc_myschedula_Name());
        holder.textview_doc_myschedula_Contect.setText(docmyscheduallist.get(position).getTextview_doc_myschedula_Contect());
        holder.textview_doc_myschedula_Email.setText(docmyscheduallist.get(position).getTextview_doc_myschedula_Email());
        //holder.textview_doc_myschedula_Age.setText(docmyscheduallist.get(position).getTextview_doc_myschedula_Age());
        holder.textview_doc_myschedula_Gender.setText(docmyscheduallist.get(position).getTextview_doc_myschedula_Gender());
        holder.textview_doc_myschedula_Time.setText(docmyscheduallist.get(position).getTextview_doc_myschedula_Time());
        holder.textview_doc_myschedula_Date.setText(docmyscheduallist.get(position).getTextview_doc_myschedula_Date());
        holder.button_doc_myschedual_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context.getApplicationContext(), "reject"+docmyscheduallist.get(position).getTextview_doc_myschedula_Name(), Toast.LENGTH_SHORT).show();
                if (networkConnection = checkConnection(context)) {
                    String patient_name = docmyscheduallist.get(position).getTextview_doc_myschedula_Name();
                    System.out.println("====patient_name==" + patient_name);
                    bean.setTextview_doc_myschedula_Name(patient_name);
                    String patient_email = docmyscheduallist.get(position).getTextview_doc_myschedula_Email();
                    bean.setTextview_doc_myschedula_Email(patient_email);
                    String pateint_date = docmyscheduallist.get(position).getTextview_doc_myschedula_Date();
                    bean.setTextview_doc_myschedula_Date(pateint_date);
                    String pateint_time = docmyscheduallist.get(position).getTextview_doc_myschedula_Time();
                    bean.setTextview_doc_myschedula_Time(pateint_time);

                    docmyscheduallist.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, docmyscheduallist.size());
                    DocListOfAppointmentServices services = new DocListOfAppointmentServices();
                    System.out.println("====DocListOfAppointmentServices===");
                    services.completionOfAppointment(context, bean);
                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return docmyscheduallist.size();
    }

    public class Doc_myschedual_Viewholder extends RecyclerView.ViewHolder {
        TextView textview_doc_myschedula_Name, textview_doc_myschedula_Contect, textview_doc_myschedula_Email, textview_doc_myschedula_Age, textview_doc_myschedula_Gender,textview_doc_myschedula_Time,textview_doc_myschedula_Date;
        Button button_doc_myschedual_cancle;

        public Doc_myschedual_Viewholder(View itemView) {
            super(itemView);

            textview_doc_myschedula_Name = (TextView) itemView.findViewById(R.id.textview_doc_myschedula_Name);
            textview_doc_myschedula_Contect = (TextView) itemView.findViewById(R.id.textview_doc_myschedula_Contect);
            textview_doc_myschedula_Email = (TextView) itemView.findViewById(R.id.textview_doc_myschedula_Email);
            //textview_doc_myschedula_Age = (TextView) itemView.findViewById(R.id.textview_doc_myschedula_Age);
            textview_doc_myschedula_Gender = (TextView) itemView.findViewById(R.id.textview_doc_myschedula_Gender);
            textview_doc_myschedula_Date = (TextView) itemView.findViewById(R.id.textview_doc_myschedula_Date);
            textview_doc_myschedula_Time = (TextView) itemView.findViewById(R.id.textview_doc_myschedula_Time);
            button_doc_myschedual_cancle=(Button) itemView.findViewById(R.id.button_doc_myschedual_cancle);
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
