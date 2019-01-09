package com.example.fanyuanhua.netpower.dagger;

import com.example.fanyuanhua.netpower.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by fanyuanhua on 18/12/27.
 */
@ActivityScope
@Component(modules = CommonModule.class)
public interface CommonComponent {
    void inject(MainActivity activity);
}
