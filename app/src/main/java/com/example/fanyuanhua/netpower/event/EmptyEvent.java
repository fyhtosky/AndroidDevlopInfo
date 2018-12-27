package com.example.fanyuanhua.netpower.event;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by fanyuanhua on 18/12/24.
 */

public abstract class EmptyEvent {

    /**
     * @Retention(RetentionPolicy.SOURCE) 注解可以告知编译器不将枚举的注解数据存储在 .class 文件中。
     */

    public static final int NAVIGATION_MODE_STANDARD = 0;
    public static final int NAVIGATION_MODE_LIST = 1;
    public static final int NAVIGATION_MODE_TABS = 2;
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NAVIGATION_MODE_STANDARD,NAVIGATION_MODE_LIST,NAVIGATION_MODE_TABS})
    public @interface  EventModel{};



    @EventModel
    public abstract int getEventModel();

    public abstract void setEventModel(@EventModel int model);
}
