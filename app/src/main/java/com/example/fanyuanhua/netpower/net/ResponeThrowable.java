package com.example.fanyuanhua.netpower.net;

import android.support.annotation.NonNull;

/**
 * Created by fanyuanhua on 18/12/27.
 */

public class ResponeThrowable extends Exception {
    public int code;
    public String message;

    public ResponeThrowable(@NonNull Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
