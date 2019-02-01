package com.example.fanyuanhua.netpower.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CanvasEight extends View {
    private Paint paint=new Paint();
    private Path path=new Path();
    private int mWidth,mHeight;
    public CanvasEight(Context context) {
        this(context,null);
    }

    public CanvasEight(Context context,  @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasEight(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(200);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth=w;
        this.mHeight=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心

                       // 创建Path

        path.lineTo(200, 200);                      // lineTo

        path.moveTo(200,100);                       // moveTo

        path.lineTo(200,0);                         // lineTo

        canvas.drawPath(path, paint);              // 绘制Path
    }
}
