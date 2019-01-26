package com.example.fanyuanhua.netpower.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CanvasFour extends View {
    private Paint mPaint = new Paint();
    private Path path = new Path(); // 初始化 Path 对象
    // 宽高
    private int mWidth, mHeight;
    public CanvasFour(Context context) {
        this(context,null);
    }

    public CanvasFour(Context context,  @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasFour(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }
    private void initViews() {
        mPaint.setStyle(Paint.Style.STROKE);//填充模式
        mPaint.setColor(Color.GREEN);//画笔的颜色
        mPaint.setTextSize(60);//设置字体的大小
        mPaint.setStrokeWidth(20);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        RectF rectF = new RectF(0,0,200,200);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            path.addArc(200, 200, 400, 400, -225, 225);
////            path.arcTo(400, 200, 600, 400, -180, 225, false);
////            path.lineTo(400, 542);
////            canvas.drawPath(path,mPaint);
//            canvas.translate(100,100);
//
//            path.addArc(rectF,0,180);
//            path.addOval(rectF,Path.Direction.CW);
//            path.addCircle(200,200,100,Path.Direction.CW);
////            path.addRect(rectF,Path.Direction.CW);
//            path.addRoundRect(rectF,50,50,Path.Direction.CW);
//            canvas.drawPath(path, mPaint);
//        }
//        canvas.translate(100,300);
//        path.reset();
//        mPaint.setStyle(Paint.Style.STROKE);
////        path.lineTo(100, 100); // 由当前位置 (0, 0) 向 (100, 100) 画一条直线
////        path.rLineTo(100, 0); // 由当前位置 (100, 100) 向正右方 100 像素的位置画一条直
////        canvas.drawPath(path, mPaint);
//        path.reset();
//        path.lineTo(100,100);
//        path.cubicTo(100,100,100,200,300,400);
////        path.moveTo(300,300);
//        path.arcTo(rectF,30,120,false);
//        path.close();
//        canvas.drawPath(path, mPaint);
        //设置饼状半径
        float radius = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);

        //将画布移动到中心位置
        canvas.translate(mWidth / 4, mHeight / 4);


        canvas.drawPath(regularStarPath(5,radius),mPaint);


    }
    /**
     * n角星路径
     *
     * @param num 几角星
     * @param R   外接圆半径
     * @param r   内接圆半径
     * @return n角星路径
     */
    public static Path nStarPath(int num, float R, float r) {
        Path path = new Path();
        float perDeg = 360 / num;
        float degA = perDeg / 2 / 2;
        float degB = 360 / (num - 1) / 2 - degA / 2 + degA;
        path.moveTo(
                (float) (Math.cos(rad(degA + perDeg * 0)) * R + R * Math.cos(rad(degA))),
                (float) (-Math.sin(rad(degA + perDeg * 0)) * R + R));
        for (int i = 0; i < num; i++) {
            path.lineTo(
                    (float) (Math.cos(rad(degA + perDeg * i)) * R + R * Math.cos(rad(degA))),
                    (float) (-Math.sin(rad(degA + perDeg * i)) * R + R));
            path.lineTo(
                    (float) (Math.cos(rad(degB + perDeg * i)) * r + R * Math.cos(rad(degA))),
                    (float) (-Math.sin(rad(degB + perDeg * i)) * r + R));
        }
        path.close();
        return path;
    }

    /**
     * 画正n角星的路径:
     *
     * @param num 角数
     * @param R   外接圆半径
     * @return 画正n角星的路径
     */
    public static Path regularStarPath(int num, float R) {
        float degA, degB;
        if (num % 2 == 1) {//奇数和偶数角区别对待
            degA = 360 / num / 2 / 2;
            degB = 180 - degA - 360 / num / 2;
        } else {
            degA = 360 / num / 2;
            degB = 180 - degA - 360 / num / 2;
        }
        float r = (float) (R * Math.sin(rad(degA)) / Math.sin(rad(degB)));
        return nStarPath(num, R, r);
    }
    /**
     * 角度制化为弧度制
     *
     * @param deg 角度
     * @return 弧度
     */
    public static float rad(float deg) {
        return (float) (deg * Math.PI / 180);
    }
}
