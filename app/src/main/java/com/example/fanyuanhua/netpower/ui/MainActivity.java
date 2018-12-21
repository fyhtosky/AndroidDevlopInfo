package com.example.fanyuanhua.netpower.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.fanyuanhua.netpower.R;
import com.example.fanyuanhua.netpower.base.BaseActivity;
import com.example.fanyuanhua.netpower.flashView.ShineButton;
import com.example.fanyuanhua.netpower.tool.ChannelUnit;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_info)
    TextView tvInfo;
//    @BindView(R.id.po_image2)
    ShineButton shineButton;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
    protected void onEvent()  {



    }

    @Override
    protected void onResume() {
        super.onResume();
        shineButton.create(this);
    }
}
