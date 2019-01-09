package com.example.fanyuanhua.netpower.dagger;

import com.example.fanyuanhua.netpower.net.ResponeThrowable;
import com.example.fanyuanhua.netpower.net.RxObserver;
import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeContract;
import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeInfo;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public class LoginPresenter extends GRCodeContract.Presenter {

    @Inject
    CommonModel mModel;

    @Inject
    public LoginPresenter(GRCodeContract.View mView) {
        this.mView = mView;
        this.attachModel(mModel);

    }

    @Override
    public void getGRCode() {
        mModel.getGRCode().subscribe(new RxObserver<GRCodeInfo>() {
            @Override
            public void onSuccess(@NonNull GRCodeInfo grCodeInfo) {
                mView.getGRCode(grCodeInfo);
            }
            @Override
            protected void onError(@NonNull ResponeThrowable ex) {
                mView.showErrorWithStatus(ex.message);
            }
        });

    }
}
