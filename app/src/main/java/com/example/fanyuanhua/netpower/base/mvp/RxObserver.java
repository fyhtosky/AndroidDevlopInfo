package com.example.fanyuanhua.netpower.base.mvp;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public  abstract class RxObserver<T> extends ErrorObserver<T> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    public abstract  void onSuccess(@NonNull  T t);
}
