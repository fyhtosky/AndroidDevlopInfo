package com.example.fanyuanhua.netpower.dagger;

import com.example.fanyuanhua.netpower.ui.BasicActivity;

import dagger.Component;

/**
 * Created by fanyuanhua on 18/12/27.
 */
@Component
public interface AComponent {
    void inject(BasicActivity activity);
}
