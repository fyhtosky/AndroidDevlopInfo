package com.example.fanyuanhua.netpower.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.Logger;

import io.fabric.sdk.android.Fabric;

/**
 * Created by fanyuanhua on 18/12/14.
 */

public class App extends Application {
    private static App sharedApplication;

    public static App getSharedApplication() {
        return sharedApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedApplication = this;
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });  //logger
//        Fabric.with(this, new Crashlytics());

        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)           // Enables Crashlytics debugger
                .build();
        Fabric.with(fabric);



    }
}
