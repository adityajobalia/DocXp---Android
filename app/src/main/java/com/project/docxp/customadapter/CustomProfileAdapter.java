package com.project.docxp.customadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.docxp.R;
import com.project.docxp.bean.ProfileBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devarshi on 25-10-2017.
 */

public class CustomProfileAdapter extends RecyclerView.Adapter<CustomProfileAdapter.ViewHolder>  {

    private List<ProfileBean> profileBeanList;
    private ArrayList<ProfileBean> profileBeanArrayList;

    public CustomProfileAdapter(List<ProfileBean> listProfileBeen) {
        this.profileBeanList=listProfileBeen;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycler_profile,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ProfileBean profileBean=profileBeanList.get(position);
        holder.details.setText(profileBean.getProdfile_details());
        holder.content.setText(profileBean.getProfile_content());
        holder.update_arrow.setImageResource(profileBean.getUpdate_icon());
        holder.icon.setImageResource(profileBean.getProfile_icon());

    }


    @Override
    public int getItemCount() {
        return profileBeanList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView details,content;
        public ImageView update_arrow,icon;
        public ViewHolder(View view) {
            super(view);

            details=(TextView)view.findViewById(R.id.profile_details);
            content=(TextView)view.findViewById(R.id.profile_content);
            update_arrow=(ImageView) view.findViewById(R.id.profile_update);
            icon=(ImageView)view.findViewById(R.id.profile_icon);

        }
    }

}
