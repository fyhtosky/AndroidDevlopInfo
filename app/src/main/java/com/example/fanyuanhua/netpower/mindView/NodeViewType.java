package com.example.fanyuanhua.netpower.mindView;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface NodeViewType {

     int TOPTOBOTTOM = 0;
     int LEFTTORIGHT = 1;
     int INTOOUT = 2;
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TOPTOBOTTOM, LEFTTORIGHT, INTOOUT})
    @interface NodeLayoutMode {}


    @NodeLayoutMode
    public int getLayoutType() ;

    public void setLayoutType(@NodeLayoutMode int layoutType);



    //lineType
     int ZHI_XIAN  = 0;
     int ZHE_XIAN  = 1;
     int QU_XIAN   = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ZHI_XIAN, ZHE_XIAN, QU_XIAN})
    @interface NodeLinetMode {}



    @NodeLinetMode
    public int getLineType() ;

    public void setLineType(@NodeLinetMode int lineType);
}
