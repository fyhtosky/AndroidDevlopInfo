package com.example.fanyuanhua.netpower.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CanvasThree extends View {
    private  Paint mPaint = new Paint();
    private Path path = new Path(); // 初始化 Path 对象
    public CanvasThree(Context context) {
        this(context,null);
    }

    public CanvasThree(Context context,  @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasThree(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        mPaint.setStyle(Paint.Style.FILL);//填充模式
        mPaint.setColor(Color.GREEN);//画笔的颜色
        mPaint.setTextSize(60);//设置字体的大小


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制一个圆
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(20);//设置画笔的宽度
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(false);//设置是否抗锯齿
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);

        // 在坐标原点绘制一个蓝色圆形
        mPaint.setStrokeWidth(10);//设置画笔的宽度
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);//设置是否抗锯齿
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);
        //保存
        canvas.save();
        //绘制长度为200 角度为30
        canvas.translate(200,0);
        canvas.rotate(30);

        mPaint.setColor(Color.RED);
        canvas.drawLine(0,0,200,0,mPaint);
        //还原
//        canvas.save();
        canvas.restore();
        canvas.translate(-500,100);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(100, 100, 500, 500, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(700, 100, 1100, 500, mPaint);
        //绘制点
        mPaint.setColor(Color.MAGENTA);
        canvas.translate(100,0);
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(50, 50, mPaint);

        canvas.translate(150,0);
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(50, 50, mPaint);
        canvas.translate(100,0);
        float[] points = {20, 20, 120, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
        canvas.drawLines(points, mPaint);
        canvas.translate(0,500);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(100, 100, 500, 300, 50, 50, mPaint);
        }
        mPaint.setStyle(Paint.Style.FILL); // 填充模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(200, 100, 800, 500, -110, 100, true, mPaint); // 绘制扇形
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(200, 100, 800, 500, 20, 140, false, mPaint); // 绘制弧形
        }
        mPaint.setStyle(Paint.Style.STROKE); // 画线模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(200, 100, 800, 500, 180, 60, false, mPaint); // 绘制不封口的弧形
        }
        canvas.translate(-400,0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            path.addArc(200, 200, 400, 400, -225, 225);
            path.arcTo(400, 200, 600, 400, -180, 225, false);
        }
        path.lineTo(400, 542);
        canvas.drawPath(path, mPaint);
    }
}
