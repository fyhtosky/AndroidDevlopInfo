package com.example.fanyuanhua.netpower.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.fanyuanhua.netpower.R;
import com.example.fanyuanhua.netpower.base.BaseActivity;
import com.example.fanyuanhua.netpower.tool.ChannelUnit;
import com.orhanobut.logger.Logger;

import java.util.NoSuchElementException;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_info)
    TextView tvInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView(@Nullable Bundle bundle) {
        Logger.d("当前渠道的名称："+ ChannelUnit.getChannelName());
        tvInfo.setText("当前渠道的名称："+ ChannelUnit.getChannelName());
         forceCrash(tvInfo);
    }
    public void forceCrash(View view) {
        throw new NoSuchElementException("MainActivity This is a crash");
    }



}
