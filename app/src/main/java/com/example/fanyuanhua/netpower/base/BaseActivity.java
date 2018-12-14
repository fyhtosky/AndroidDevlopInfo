package com.example.fanyuanhua.netpower.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fanyuanhua on 18/12/14.
 */

public  abstract class BaseActivity extends AppCompatActivity {
    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            unbinder=ButterKnife.bind(this);
            onInitView(savedInstanceState);
            onEvent();
        }


    }


    /**
     * 初始化事务
     */
    protected  void onEvent(){

    }


    /**
     * 获取布局资源文件
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutId();


    /**
     * 初始化数据
     * @param bundle
     */
    protected abstract void onInitView(@Nullable Bundle bundle);



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
