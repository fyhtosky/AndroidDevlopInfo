package com.example.fanyuanhua.netpower.mindView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class NodeRelativeLayout extends RelativeLayout implements GestureDetector.OnGestureListener {
    String TAG="NodeRelativeLayout";
    private GestureDetector gestureDetector;
    public NodeRelativeLayout(Context context) {
        this(context,null);
    }

    public NodeRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NodeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
      gestureDetector=new GestureDetector(context,this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.i(TAG,"事件单击触发");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(TAG,"onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(TAG,"onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(TAG,"事件滑动触发onScroll");
        float xlen = Math.abs( e1.getX() - e2.getX());
        float ylen = Math.abs( e1.getY() - e2.getY());
        double nLenEnd = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);
        if(nLenEnd > 10){
            Log.i(TAG,"事件滑动触发");
            return false;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(TAG,"事件长按触发");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG,"onFling");
        return false;
    }
}
