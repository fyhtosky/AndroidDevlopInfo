package com.example.fanyuanhua.netpower.dagger;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public class ClassA {
    private static final String TAG="ClassA";
    @Inject
    public ClassA() {
    }

    public void getClassName(){
        Log.i(TAG,"该类的名字是："+TAG);
    }
}
