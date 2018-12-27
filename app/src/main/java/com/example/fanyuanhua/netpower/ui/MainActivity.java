package com.example.fanyuanhua.netpower.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.fanyuanhua.netpower.R;
import com.example.fanyuanhua.netpower.base.BaseActivity;
import com.example.fanyuanhua.netpower.dagger.CommonModule;
import com.example.fanyuanhua.netpower.dagger.DaggerCommonComponent;
import com.example.fanyuanhua.netpower.dagger.ICommonView;
import com.example.fanyuanhua.netpower.dagger.LoginPresenter;
import com.example.fanyuanhua.netpower.dagger.User;
import com.example.fanyuanhua.netpower.tool.ChannelUnit;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements ICommonView {

    @Nullable
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @Inject
    LoginPresenter presenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView(@Nullable Bundle bundle) {
        DaggerCommonComponent.builder()
                .commonModule(new CommonModule(this))
                .build()
                .inject(this);
        Logger.d("当前渠道的名称：" + ChannelUnit.getChannelName());
        tvInfo.setText("当前渠道的名称：" + ChannelUnit.getChannelName());
        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BasicActivity.class));
            }
        });




    }

    @Override
    protected void onEvent()  {
        presenter.login(new User());
    }


    @Override
    public Context getContext() {
        return this;
    }
}
