package com.project.docxp.customadapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.docxp.R;
import com.project.docxp.bean.DoclistofappointmentcontentBean;
import com.project.docxp.services.DocListOfAppointmentServices;
import com.project.docxp.utility.ConnectivityReceiver;

import java.util.List;

/**
 * Created by Devarshi on 20-11-2017.
 */

public class CustomDocListofAppointmentAdapter extends RecyclerView.Adapter<CustomDocListofAppointmentAdapter.Doc_lstapp_ViewHolder> {

    private List<DoclistofappointmentcontentBean> lst;
    private Context context;
    private FragmentActivity fragmentActivity;
    private DoclistofappointmentcontentBean doclistofappointmentcontentBean;
    private boolean networkConnection;

    public CustomDocListofAppointmentAdapter(List<DoclistofappointmentcontentBean> appointmentData, Context context, FragmentActivity fragmentActivity) {
        this.lst = appointmentData;
        this.context = context;
        this.fragmentActivity = fragmentActivity;
    }


    @Override
    public Doc_lstapp_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cardview_doc_listofappointment, parent, false);
        Doc_lstapp_ViewHolder doc_lstapp_viewHolder = new Doc_lstapp_ViewHolder(view);

        return doc_lstapp_viewHolder;
    }

    @Override
    public void onBindViewHolder(final Doc_lstapp_ViewHolder holder, final int position) {
        int i = lst.size();
        System.out.println("###===list size==" + i);
        doclistofappointmentcontentBean = lst.get(position);
        holder.textview_doc_listofappointment_Name.setText(lst.get(position).getTextview_doc_listofappointment_Name());
        holder.textview_doc_listofappointment_Gender.setText(lst.get(position).getTextview_doc_listofappointment_Gender());
        holder.textview_doc_listofappointment_Email.setText(lst.get(position).getTextview_doc_listofappointment_Email());
        holder.textview_doc_listofappointment_Contact.setText(lst.get(position).getTextview_doc_listofappointment_Contact());
        holder.textview_doc_listofappointment_Date.setText(lst.get(position).getTextview_doc_listofappointment_Date());
        holder.textview_doc_listofappointment_Time.setText(lst.get(position).getTextview_doc_listofappointment_Time());

        holder.button_doc_listofappointment_conform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert);
                builder.setMessage("Do you want to confirm?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText(context.getApplicationContext(), "yes", Toast.LENGTH_SHORT).show();
                        if (networkConnection = checkConnection(context)) {
                            String status = "confirm";
                            String patient_name = lst.get(position).getTextview_doc_listofappointment_Name();
                            doclistofappointmentcontentBean.setTextview_doc_listofappointment_Name(patient_name);
                            String patient_email = lst.get(position).getTextview_doc_listofappointment_Email();
                            doclistofappointmentcontentBean.setTextview_doc_listofappointment_Email(patient_email);
                            String pateint_date = lst.get(position).getTextview_doc_listofappointment_Date();
                            doclistofappointmentcontentBean.setTextview_doc_listofappointment_Date(pateint_date);
                            String pateint_time = lst.get(position).getTextview_doc_listofappointment_Time();
                            doclistofappointmentcontentBean.setTextview_doc_listofappointment_Time(pateint_time);
                            doclistofappointmentcontentBean.setStatus(status);
                            lst.remove(position);
                            //notifyItemRemoved(position);
                            //notifyItemRangeChanged(position, lst.size());
                            notifyDataSetChanged();
                            //notifyItemChanged(position);

                            DocListOfAppointmentServices services = new DocListOfAppointmentServices();
                            services.conformDatafromAppointment(context, doclistofappointmentcontentBean);
                        }

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context.getApplicationContext(), "no", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create();
                builder.show();

            }
        });
        holder.imagview_doc_listofappointment_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert);
                builder.setMessage("Do you want to reject?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (networkConnection = checkConnection(context)) {
                            // Toast.makeText(context.getApplicationContext(), "yes", Toast.LENGTH_SHORT).show();
                            String patient_name = lst.get(position).getTextview_doc_listofappointment_Name();
                            doclistofappointmentcontentBean.setTextview_doc_listofappointment_Name(patient_name);
                            String patient_email = lst.get(position).getTextview_doc_listofappointment_Email();
                            doclistofappointmentcontentBean.setTextview_doc_listofappointment_Email(patient_email);
                            String pateint_date = lst.get(position).getTextview_doc_listofappointment_Date();
                            doclistofappointmentcontentBean.setTextview_doc_listofappointment_Date(pateint_date);
                            String pateint_time = lst.get(position).getTextview_doc_listofappointment_Time();
                            doclistofappointmentcontentBean.setTextview_doc_listofappointment_Time(pateint_time);

                            lst.remove(position);
                            notifyItemRemoved(position);
                            //notifyItemRangeChanged(position, lst.size());
                            notifyDataSetChanged();
                            notifyItemChanged(position);

                            DocListOfAppointmentServices services = new DocListOfAppointmentServices();
                            services.rejectDatafromAppointment(context, doclistofappointmentcontentBean);

                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (networkConnection = checkConnection(context)) {
                            // Toast.makeText(context.getApplicationContext(), "no", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.create();
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {

        return lst.size();
    }


    public class Doc_lstapp_ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
        TextView textview_doc_listofappointment_Name, textview_doc_listofappointment_Contact, textview_doc_listofappointment_Email,
                textview_doc_listofappointment_Gender, textview_doc_listofappointment_Date, textview_doc_listofappointment_Time;
        Button button_doc_listofappointment_conform;
        ImageView imagview_doc_listofappointment_reject;


        public Doc_lstapp_ViewHolder(View itemView) {
            super(itemView);

            textview_doc_listofappointment_Name = (TextView) itemView.findViewById(R.id.textview_doc_listofappointment_Name);
            textview_doc_listofappointment_Contact = (TextView) itemView.findViewById(R.id.textview_doc_listofappointment_Contact);
            textview_doc_listofappointment_Email = (TextView) itemView.findViewById(R.id.textview_doc_listofappointment_Email);
            textview_doc_listofappointment_Gender = (TextView) itemView.findViewById(R.id.textview_doc_listofappointment_Gender);
            textview_doc_listofappointment_Date = (TextView) itemView.findViewById(R.id.textview_doc_listofappointment_Date);
            textview_doc_listofappointment_Time = (TextView) itemView.findViewById(R.id.textview_doc_listofappointment_Time);
            button_doc_listofappointment_conform = (Button) itemView.findViewById(R.id.button_doc_listofappointment_conform);
            imagview_doc_listofappointment_reject = (ImageView) itemView.findViewById(R.id.imagview_doc_listofappointment_reject);

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
