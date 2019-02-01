package com.example.fanyuanhua.netpower.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.Logger;

import org.scilab.forge.jlatexmath.core.AjLatexMath;

/**
 * Created by fanyuanhua on 18/12/14.
 */

public class App extends MultiDexApplication {
    private static App sharedApplication;

    public static App getSharedApplication() {
        return sharedApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AjLatexMath.init(this);
        sharedApplication = this;
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });  //logger
//        Fabric.with(this, new Crashlytics());

//        final Fabric fabric = new Fabric.Builder(this)
//                .kits(new Crashlytics())
//                .debuggable(true)           // Enables Crashlytics debugger
//                .build();
//        Fabric.with(fabric);

    }
}
