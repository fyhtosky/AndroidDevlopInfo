package com.example.appsflyer;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;

/**
 * Created by fanyuanhua on 18/12/13.
 * 用于快速集成AppsFlyer移动统计代码
 */

public class AppsFlyer implements Application.ActivityLifecycleCallbacks {
//    "DbiPJy5Z3rBxwa2pJPGUmj"
    private static final String TAG="AppsFlyer";

    private static final String  AF_DEV_KEY = "uWDevKNCFta9W2tPK3CMfP";


    public static void init(Application application) {
        boolean isDebug=(application.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        Log.i(TAG, "当前版本编译类型:"+isDebug);
        application.registerActivityLifecycleCallbacks(new AppsFlyer());

        AppsFlyerConversionListener appsFlyerConversionListener=new AppsFlyerConversionListener() {
            @Override
            public void onInstallConversionDataLoaded(Map<String, String> conversionData) {
                Log.i(TAG, "onInstallConversionDataLoaded:" + conversionData);
            }

            @Override
            public void onInstallConversionFailure(String errorMessage) {
                Log.i(TAG, "onInstallConversionFailure:" + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
                Log.i(TAG, "onAppOpenAttribution:" + attributionData);
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.i(TAG, "onAttributionFailure:" + errorMessage);
            }
        };

        AppsFlyerLib.getInstance().init(AF_DEV_KEY,appsFlyerConversionListener,application);
        AppsFlyerLib.getInstance().startTracking(application);
    }
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

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
}
