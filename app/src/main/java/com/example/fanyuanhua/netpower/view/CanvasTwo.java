package com.example.fanyuanhua.netpower.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class CanvasTwo extends View {
    private Paint mPaint = new Paint();
    private Path mPath=new Path();
    //屏幕
    private DisplayMetrics dm;
    private String TAG="CanvasTwo";
    public CanvasTwo(Context context) {
        super(context);
        initPaint();
    }

    public CanvasTwo(Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CanvasTwo(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }
    // 2.初始化画笔
    private void initPaint() {
        //屏幕
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.STROKE);  //设置画笔模式为填充
        mPaint.setStrokeWidth(2f);         //设置画笔宽度为10px
        mPath.reset();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTwo(canvas);
    }

    private void drawTwo(Canvas canvas) {
        canvas.translate(100,100);
        String text = "Hello HenCoder";
        //设置文字大小。
        mPaint.setTextSize(60);
        //设置字体。
        mPaint.setTypeface(Typeface.SERIF);

        //设置文字横向错切角度。其实就是文字倾斜度的啦
        mPaint.setTextSkewX(-0.5f);
        //设置文字横向放缩。也就是文字变胖变瘦。
        mPaint.setTextScaleX(0.8f);
        //绘制文字
        canvas.drawText(text, 0, 0, mPaint);
        canvas.translate(0,100);
        //绘制光标
        mPaint.setColor(Color.GREEN);
        int length = text.length();
        int offsetX=0;
        int offsetY=0;
        float advance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            advance = mPaint.getRunAdvance(text, 0, length, 0, length, false, length);
        }
        canvas.drawText(text, offsetX, offsetY, mPaint);
        canvas.drawLine(offsetX + advance, offsetY - 50, offsetX + advance, offsetY + 10, mPaint);
        canvas.translate(0,80);
        String text1 = "Hello HenCoder \uD83C\uDDE8\uD83C\uDDF3"; // "Hello HenCoder 🇨🇳"
        canvas.drawText(text1, offsetX, offsetY, mPaint);
        float advance4 = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
             advance4 = mPaint.getRunAdvance(text, 0, length, 0, length, false, length - 3);
        }
        canvas.drawLine(offsetX + advance4, offsetY - 50, offsetX + advance4, offsetY + 10, mPaint);



    }


}
