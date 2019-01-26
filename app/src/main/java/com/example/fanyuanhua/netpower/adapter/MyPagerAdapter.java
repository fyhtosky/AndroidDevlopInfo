package com.example.fanyuanhua.netpower.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private List<String >titleList;
    private List<Fragment>fragmentList;

    public MyPagerAdapter(FragmentManager fm, List<String> titleList, List<Fragment> fragmentList) {
        super(fm);
        this.titleList = titleList;
        this.fragmentList = fragmentList;
    }

    @Override
    public int getCount() {
        return fragmentList==null ?0:fragmentList.size();
    }

    @Override
    public Fragment getItem(int i) {
        assert fragmentList !=null;
        return fragmentList.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        assert titleList!=null;
        return titleList.get(position);
    }
}
