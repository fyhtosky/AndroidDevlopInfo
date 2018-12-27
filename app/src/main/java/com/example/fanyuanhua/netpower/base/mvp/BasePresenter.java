package com.example.fanyuanhua.netpower.base.mvp;

import android.content.Context;

/**
 * Created by fanyuanhua on 18/12/27.
 * 绑定view 和Model
 */

public class BasePresenter<V extends BaseView,M extends BaseModel> implements Presenter<V,M> {
    protected Context mContext;

    protected V mView;

    protected M mModel;


    @Override
    public void attachView(V v) {
        this.mView=v;
    }

    @Override
    public void attachModel(M m) {
       this.mModel=m;
    }

    @Override
    public void detachView() {
     this.mView=null;
    }

    @Override
    public void detachModel() {
     this.mModel=null;
    }

    public boolean isViewBind() {
        return mView!=null;
    }

    public V getmView() {
        return mView;
    }

    public M getmModel() {
        return mModel;
    }
}
