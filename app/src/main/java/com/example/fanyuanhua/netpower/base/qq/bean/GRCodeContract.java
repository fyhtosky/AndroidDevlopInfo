package com.example.fanyuanhua.netpower.base.qq.bean;

import com.example.fanyuanhua.netpower.base.mvp.BaseModel;
import com.example.fanyuanhua.netpower.base.mvp.BasePresenter;
import com.example.fanyuanhua.netpower.base.mvp.BaseView;
import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeInfo;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import okhttp3.RequestBody;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public interface GRCodeContract {
    interface View extends BaseView {
        /**
         * 登录
         * @param grCodeInfo
         */
        void getGRCode(@NonNull GRCodeInfo grCodeInfo);

    }
    interface  Model extends BaseModel {

        Observable<GRCodeInfo > getGRCode();
    }
    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void  getGRCode();
    }
}
