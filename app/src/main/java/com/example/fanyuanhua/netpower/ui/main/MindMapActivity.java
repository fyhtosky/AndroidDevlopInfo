package com.example.fanyuanhua.netpower.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.fanyuanhua.netpower.R;
import com.example.fanyuanhua.netpower.adapter.MyPagerAdapter;
import com.example.fanyuanhua.netpower.base.BaseActivity;
import com.example.fanyuanhua.netpower.fragment.DrawPageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MindMapActivity extends BaseActivity {

    final String TAG = "MindMap";
//    @BindView(R.id.tabLayout)
//    TabLayout tabLayout;
//    @BindView(R.id.vp)
//    ViewPager vp;





    @Override
    protected int getLayoutId() {
        return R.layout.activity_mind_map;
    }

    @Override
    protected void onInitView(@Nullable Bundle bundle) {
        List<String>titleList=new ArrayList<>();
        titleList.add("drawColor");
        titleList.add("drawCircle");
        titleList.add("drawRect");
        titleList.add("drawArc");
        titleList.add("drawPath");
        titleList.add("直方图");
        titleList.add("饼状图");
        List<Fragment>fragmentList=new ArrayList<>();
        for (int i=0;i<titleList.size();i++){
            fragmentList.add(DrawPageFragment.newInstance(R.layout.activity_main));
        }
        MyPagerAdapter pagerAdapter=new MyPagerAdapter(getSupportFragmentManager(),titleList,fragmentList);
//        vp.setAdapter(pagerAdapter);
//        vp.setOffscreenPageLimit(fragmentList.size());
//        tabLayout.setupWithViewPager(vp);
    }
}
