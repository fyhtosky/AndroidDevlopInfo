package com.example.fanyuanhua.netpower.base;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.webkit.WebSettings;

import com.example.fanyuanhua.netpower.app.App;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public class RetrofitUtils {
    private volatile static Retrofit retrofit;
    public static Gson gson = new Gson();

    public static Retrofit getSingleton(){
        if (retrofit == null) synchronized (RetrofitUtils.class) {
            if (retrofit == null) {
                OkHttpClient httpClient = new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request()
                                        .newBuilder()
                                        .removeHeader("User-Agent")//移除旧的
                                        .addHeader("User-Agent",
                                                getUserAgent())
                                        .addHeader("device-code",getDeviceCode())
                                        .addHeader("edition","android"+getEdition())
                                        .addHeader("app-type","free")
                                        .addHeader("request-id",String.valueOf(UUID.randomUUID()))
                                        .build();
                                Logger.i(request.headers().toString());
                                return chain.proceed(request);
                            }
                        })
                        .addInterceptor(new MyInterceptor())
                        .build();

                retrofit = new Retrofit.Builder()
                        .baseUrl(Api.API_ROOT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(httpClient)
                        .build();

            }
        }

        return retrofit;
    }

    /**
     * 获取版本号
     * @return
     */
    private static String getEdition(){
        PackageManager manager = App.getSharedApplication().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(App.getSharedApplication().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final String appVersion = info.versionName; // 版本名
        return appVersion;
    }

    /**
     * 获取设备的标识符
     * @return
     */
    private static String getDeviceCode(){
        return Settings.System.getString(App.getSharedApplication().getContentResolver(), Settings.System.ANDROID_ID);
    }

    /**
     * 获取UserAgent
     * @return
     */
    private static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(App.getSharedApplication());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static RequestBody getRequestBody(Object o){
        String requst = gson.toJson(o);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),requst);
    }

    /**
     * node节点有些字段不能用到，序列化会陷入死循环，采用过滤的gson
     * @param o
     * @return
     */
    public static RequestBody getRequestBodyOld(Object o){
        String requst = gson.toJson(o);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),requst);
    }
}
