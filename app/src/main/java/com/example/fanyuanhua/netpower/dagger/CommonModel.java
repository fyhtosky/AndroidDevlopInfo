package com.example.fanyuanhua.netpower.dagger;

import com.example.fanyuanhua.netpower.base.Api;
import com.example.fanyuanhua.netpower.base.RetrofitUtils;
import com.example.fanyuanhua.netpower.base.mvp.DefaultTransformer;
import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeContract;
import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeInfo;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by fanyuanhua on 18/12/28.
 */

public class CommonModel  implements GRCodeContract.Model{
    @Inject
    public CommonModel() {
    }

    @Override
    public Observable<GRCodeInfo> getGRCode() {
        return  RetrofitUtils.getSingleton().create(Api.class)
                .getQQGRCode()
                .compose(new DefaultTransformer<GRCodeInfo>());
    }

}
