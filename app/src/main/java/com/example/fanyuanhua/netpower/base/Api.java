package com.example.fanyuanhua.netpower.base;


import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public interface Api {
     String API_ROOT = "https://mindcloud.camoryapps.com/";
    /**
     * OssService正式服务器
     * test测试服务器
     */
//     String ROOT = "test";
    String ROOT = "OssService";



    /**
     * 动态获取QQ群账号
     * @return
     */
    @GET(ROOT+"/custom/getQQGRCode")
    Observable<GRCodeInfo> getQQGRCode();
}
