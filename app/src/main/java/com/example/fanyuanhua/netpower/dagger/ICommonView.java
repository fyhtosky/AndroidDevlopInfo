package com.example.fanyuanhua.netpower.dagger;

import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeInfo;

import io.reactivex.annotations.NonNull;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public interface ICommonView {
    /**
     * 登录
     * @param grCodeInfo
     */
    void getGRCode(@NonNull GRCodeInfo grCodeInfo);

    //    提示错误消息
    void showErrorWithStatus(String msg);
}
