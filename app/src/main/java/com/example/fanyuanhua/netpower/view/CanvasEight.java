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
    private Paint mPaint=new Paint();
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
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setTextSize(30);
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
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
//        path.lineTo(400, 400);                      // lineTo
//        path.moveTo(400,200);                       // moveTo
//        path.lineTo(400,0);                         // lineTo
//        canvas.drawPath(path, paint);              // 绘制Path


//        Path path = new Path();
//        path.addRect(-200,-200,200,200, Path.Direction. CCW);
//        path.setLastPoint(-100,100);
//        canvas.drawPath(path,paint);


        canvas.scale(1,-1);                         // <-- 注意 翻转y坐标轴

//        Path path = new Path();
//        Path src = new Path();
//
//        path.addRect(-200,-200,200,200, Path.Direction.CW);
//        src.addCircle(0,0,100, Path.Direction.CW);
//
//        path.addPath(src,0,200);
//
//        mPaint.setColor(Color.BLACK);           // 绘制合并后的路径
//        canvas.drawPath(path,mPaint);
//        canvas.drawText("草你麻痹的 了垃圾神挖宝图啊是啊",0,400,mPaint);


        Path path = new Path();                     // path中添加一个圆形(圆心在坐标原点)
        path.addCircle(0,0,100, Path.Direction.CW);

        Path dst = new Path();                      // dst中添加一个矩形
        dst.addRect(-200,-200,200,200, Path.Direction.CW);

        path.offset(300,0,null);                     // 平移

        canvas.drawPath(path,mPaint);               // 绘制path

        mPaint.setColor(Color.BLUE);                // 更改画笔颜色

        canvas.drawPath(dst,mPaint);
    }
}
