package com.example.fanyuanhua.netpower.adapter.drag;

import android.view.View;

/**
 * RecycleView  触发事件的回调
 */
public interface RecycleCallBack {
    //item的点击事件
    void itemOnClick(int position, View view);

    void onMove(int from, int to);
}
