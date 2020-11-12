package com.project.docxp.clicklistener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Devarshi on 23-11-2017.
 */

public class PateintHomeclickListener extends RecyclerView.ItemDecoration implements RecyclerView.OnItemTouchListener{
    private ClickListerner clickListerner;
    private GestureDetector gestureDetector;
    public PateintHomeclickListener(Context applicationContext, final RecyclerView recycler_pateint_home, final ClickListerner clickListerner) {
        this.clickListerner=clickListerner;
        gestureDetector=new GestureDetector(applicationContext,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child=recycler_pateint_home.findChildViewUnder(e.getX(),e.getY());
                if (child !=null && clickListerner != null)
                {
                    clickListerner.onLongClick(child,recycler_pateint_home.getChildLayoutPosition(child));
                }
            }
        });
    }



    public interface ClickListerner{
        public void onClick(View view,int position);
        public void onLongClick(View view, int position);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child=rv.findChildViewUnder(e.getX(),e.getY());
        if (child !=null && clickListerner !=null && gestureDetector.onTouchEvent(e) ){
            clickListerner.onClick(child,rv.getChildLayoutPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
