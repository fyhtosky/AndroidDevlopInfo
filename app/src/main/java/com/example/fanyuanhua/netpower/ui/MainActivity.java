package com.example.fanyuanhua.netpower.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.fanyuanhua.netpower.R;
import com.example.fanyuanhua.netpower.base.mvp.BaseMvpActivity;
import com.example.fanyuanhua.netpower.base.mvp.BaseView;
import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeContract;
import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeInfo;
import com.example.fanyuanhua.netpower.dagger.CommonModel;
import com.example.fanyuanhua.netpower.dagger.CommonModule;
import com.example.fanyuanhua.netpower.dagger.DaggerCommonComponent;
import com.example.fanyuanhua.netpower.dagger.LoginPresenter;
import com.example.fanyuanhua.netpower.tool.ChannelUnit;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

public class MainActivity extends BaseMvpActivity<CommonModel,LoginPresenter> implements GRCodeContract.View {

    @Nullable
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @Inject
    LoginPresenter mPresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected BaseView getViewImp() {
        return null;
    }

    @Override
    protected void onInitView(@Nullable Bundle bundle) {
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
    protected void onEvent() {
       mPresenter.getGRCode();
    }

    @Override
    public void getGRCode(@NonNull GRCodeInfo grCodeInfo) {
        tvInfo.setText("内容：" + grCodeInfo.toString());
    }

    @Override
    public void showErrorWithStatus(String msg) {

    }

    @Override
    public void inject() {
        DaggerCommonComponent.builder()
                .commonModule(new CommonModule(this))
                .build()
                .inject(this);
    }
}
