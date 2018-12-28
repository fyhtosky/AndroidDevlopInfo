package com.example.fanyuanhua.netpower.base.qq.bean.model;

import com.example.fanyuanhua.netpower.base.mvp.BaseView;
import com.example.fanyuanhua.netpower.base.mvp.ResponeThrowable;
import com.example.fanyuanhua.netpower.base.mvp.RxObserver;
import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeContract;
import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeInfo;

import io.reactivex.annotations.NonNull;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public class QRCodePresenter extends GRCodeContract.Presenter {



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
