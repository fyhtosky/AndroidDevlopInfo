package com.example.fanyuanhua.netpower.base.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by fanyuanhua on 18/12/27.
 */

public  abstract class BaseMvpActivity<M extends BaseModel,P extends BasePresenter>  extends AppCompatActivity implements IBase{
    protected P mPresenter;
    private Unbinder unbinder;
    /**
     * 获取布局资源文件
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutId();


    /**
     * 获取抽取View对象
     * @return  BaseView
     */
    protected  abstract BaseView getViewImp();

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getLayoutId()!=0){
            setContentView(getLayoutId());
            //注解绑定
            unbinder = ButterKnife.bind(this);
            inject();//Dagger MVP架构初始化
            bindMVP();
            onInitView(savedInstanceState);
            onEvent();
        }
    }

    private void bindMVP() {
        if(mPresenter!=null&&!mPresenter.isViewBind()&&getViewImp()!=null) {
            mPresenter.mContext=this;
            mPresenter.attachView(getViewImp());
            mPresenter.attachModel(mPresenter.getmModel());
        }

    }



    private void onEvent() {

    }

    protected abstract void onInitView(Bundle savedInstanceState);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.detachModel();
            mPresenter.detachView();
            mPresenter = null;
        }
    }
}
