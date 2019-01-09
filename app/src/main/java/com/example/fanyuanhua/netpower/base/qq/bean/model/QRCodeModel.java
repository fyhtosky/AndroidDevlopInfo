package com.example.fanyuanhua.netpower.base.qq.bean.model;

import com.example.fanyuanhua.netpower.net.Api;
import com.example.fanyuanhua.netpower.net.RetrofitUtils;
import com.example.fanyuanhua.netpower.base.mvp.DefaultTransformer;
import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeContract;
import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeInfo;

import io.reactivex.Observable;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public class QRCodeModel implements GRCodeContract.Model {


    @Override
    public Observable<GRCodeInfo> getGRCode() {
        return  RetrofitUtils.getSingleton().create(Api.class)
                .getQQGRCode()
                .compose(new DefaultTransformer<GRCodeInfo>());
    }
}
