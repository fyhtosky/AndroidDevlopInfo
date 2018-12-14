package com.example.fanyuanhua.netpower.tool;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.fanyuanhua.netpower.base.App;

/**
 * Created by fanyuanhua on 18/12/14.
 */

public class ChannelUnit {
    public static final String UMENG_CHANNEL="UMENG_CHANNEL";
    /**
     * 获取渠道名
     * @return 如果没有获取成功，那么返回值为空
     */
    public   static String getChannelName() {
        String channelName = "UNKNOWN";
        try {
            PackageManager packageManager = App.getSharedApplication().getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.
                        getApplicationInfo(App.getSharedApplication().getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = String.valueOf(applicationInfo.metaData.get(UMENG_CHANNEL));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("UMENG_CHANNEL","当前渠道名："+channelName);
        return channelName;
    }

    /**
     * 获取版本号
     * @return
     */
    public static String getAppVersion(){
        PackageManager manager = App.getSharedApplication().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(App.getSharedApplication().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final String appVersion = info.versionName; // 版本名
        final int currentVersionCode = info.versionCode; // 版本号

        return appVersion;
    }
}
