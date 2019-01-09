package com.example.fanyuanhua.netpower.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.fanyuanhua.netpower.R;
import com.example.fanyuanhua.netpower.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    Handler handler;
    Intent intent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onInitView(@Nullable Bundle bundle) {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
           @Override
           public void run() {
               if (isFinishing()) {
                   return;
               }

               intent = new Intent(SplashActivity.this, MainActivity.class);
               startActivity(intent);
               finish();
           }
       },3000);
    }


}
