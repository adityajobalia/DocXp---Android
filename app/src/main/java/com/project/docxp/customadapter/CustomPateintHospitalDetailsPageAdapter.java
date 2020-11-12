package com.project.docxp.customadapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.docxp.R;
import com.project.docxp.bean.PateintHospitalDetailsBean;
import com.project.docxp.patientfragment.PatientHospitalDetails;

import java.util.List;

/**
 * Created by Devarshi on 26-11-2017.
 */

public class CustomPateintHospitalDetailsPageAdapter extends PagerAdapter {
    private Context context;
    private int[] viewpagerimg = {R.drawable.viewpager1, R.drawable.viewpager2, R.drawable.viewpager3};

    public CustomPateintHospitalDetailsPageAdapter(Context applicationContext) {
        this.context = applicationContext;
    }


    @Override
    public int getCount() {
        return viewpagerimg.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        LayoutInflater layoutInflate = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflate.inflate(R.layout.custom_viewpager_pateint_hospitaldetails, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.custom_viewpage_img);
        imageView.setImageResource(viewpagerimg[position]);
        container.addView(view);
        return view;
    }
}
