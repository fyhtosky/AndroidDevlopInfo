package com.example.fanyuanhua.netpower.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fanyuanhua on 18/12/27.
 */
@Module
public class CommonModule {
    private ICommonView iView;
    public CommonModule(ICommonView iView){
        this.iView = iView;
    }


    @Provides
    @ActivityScope
    public ICommonView provideIcommonView(){
        return this.iView;
    }


}
