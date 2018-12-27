package com.example.fanyuanhua.netpower.event;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by fanyuanhua on 18/12/24.
 */

public class Display {
    public static final int DISPLAY_USE_LOGO = 0;
    public static final int DISPLAY_SHOW_HOME = 1;
    public static final int DISPLAY_HOME_AS_UP = 2;
    public static final int DISPLAY_SHOW_TITLE = 3;
    public static final int DISPLAY_SHOW_CUSTOM = 4;
    @IntDef(flag=true, value={
            DISPLAY_USE_LOGO,
            DISPLAY_SHOW_HOME,
            DISPLAY_HOME_AS_UP,
            DISPLAY_SHOW_TITLE,
            DISPLAY_SHOW_CUSTOM
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayOptions {}
}
