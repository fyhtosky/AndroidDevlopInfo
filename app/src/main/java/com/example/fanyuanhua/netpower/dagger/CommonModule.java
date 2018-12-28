package com.example.fanyuanhua.netpower.dagger;

import com.example.fanyuanhua.netpower.base.qq.bean.GRCodeContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fanyuanhua on 18/12/27.
 */
@Module
public class CommonModule {
    private GRCodeContract.View mView;

    public CommonModule(GRCodeContract.View mView){
        this.mView = mView;

    }

    @Provides
    @ActivityScope
    public GRCodeContract.View provideBaseView(){
        return this.mView;
    }




}
