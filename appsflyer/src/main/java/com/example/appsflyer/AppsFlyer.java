package com.example.appsflyer;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;

/**
 * Created by fanyuanhua on 18/12/13.
 * 用于快速集成AppsFlyer移动统计代码
 */

public class AppsFlyer implements Application.ActivityLifecycleCallbacks {
    private static final String AF_DEV_KEY = "";

    private Application application;

    public AppsFlyer(Application application) {
        this.application = application;
    }

    public static void init(Application application) {
        AppsFlyerConversionListener appsFlyerConversionListener=new AppsFlyerConversionListener() {
            @Override
            public void onInstallConversionDataLoaded(Map<String, String> map) {

            }

            @Override
            public void onInstallConversionFailure(String s) {

            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {

            }

            @Override
            public void onAttributionFailure(String s) {

            }
        };
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, appsFlyerConversionListener, application);
        AppsFlyerLib.getInstance().startTracking(application);

        AppsFlyer appsFlyer=new AppsFlyer(application);
        application.registerActivityLifecycleCallbacks(appsFlyer);
        AppsFlyerLib.getInstance().setDebugLog(appsFlyer.isApkDebugable());
    }
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        AppsFlyerLib.getInstance().sendDeepLinkData(activity);
        AppsFlyerLib.getInstance().reportTrackSession(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {


    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private boolean isApkDebugable() {
        try {
            ApplicationInfo info = application.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
