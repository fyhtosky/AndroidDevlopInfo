package com.example.fanyuanhua.netpower.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.fanyuanhua.netpower.R;
import com.example.fanyuanhua.netpower.base.BaseActivity;
import com.example.fanyuanhua.netpower.dagger.ClassA;
import com.example.fanyuanhua.netpower.dagger.DaggerAComponent;
import com.example.fanyuanhua.netpower.dagger.DaggerCommonComponent;

import javax.inject.Inject;

public class BasicActivity extends BaseActivity {

     @Inject
    ClassA classA;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic;
    }

    @Override
    protected void onInitView(@Nullable Bundle bundle) {
        DaggerAComponent.builder().build().inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(BasicActivity.this,NavigationDrawerActivity.class));
            }
        });
    }

    @Override
    protected void onEvent() {
        classA.getClassName();
    }
}
