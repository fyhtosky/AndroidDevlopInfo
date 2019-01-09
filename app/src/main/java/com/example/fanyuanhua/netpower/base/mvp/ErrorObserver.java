package com.example.fanyuanhua.netpower.base.mvp;

import android.util.Log;

import com.example.fanyuanhua.netpower.net.ResponeThrowable;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public abstract class ErrorObserver <T> implements Observer<T> {


    @Override
    public void onError(@NonNull Throwable e) {
        Log.e("ErrorOberver","错误信息:"+e.getMessage());
        if(e instanceof ResponeThrowable){
            onError((ResponeThrowable)e);
        }else{
            onError(new ResponeThrowable(e,1000));
        }
    }

    /**
     * 错误回调
     */
    protected abstract void onError(@NonNull ResponeThrowable ex);
}
