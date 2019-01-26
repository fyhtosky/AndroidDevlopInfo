package com.example.fanyuanhua.netpower.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class CanvasOne extends View {
    private Paint mPaint = new Paint();
    private TextPaint textPaint=new TextPaint();
    private Path mPath=new Path();
    //屏幕
    private DisplayMetrics dm;
    private String TAG="CanvasOne";

    public CanvasOne(Context context) {
        this(context,null);
    }

    public CanvasOne(Context context,  @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
        mPath.reset();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOne(canvas);
    }

    private void drawOne(Canvas canvas) {
//        canvas.drawPoint(200, 200, mPaint);     //在坐标(200,200)位置绘制一个点
//        canvas.drawPoints(new float[]{          //绘制一组点，坐标位置由float数组指定
//                500,500,
//                500,600,
//                500,700
//        },mPaint);

        int width=dm.widthPixels;
        int height=dm.heightPixels;
        Log.i(TAG,"设备的宽高：宽："+width+";高："+height);
        int median_distance=(width-700)/2;
        canvas.translate(median_distance,100);
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(0,0,350,300,mPaint);    // 在坐标(300,300)(500,600)之间绘制一条直线
        canvas.drawLine(350,300,700,0,mPaint);
        canvas.translate(0,300);
//        canvas.drawRect(100,100,800,400,mPaint);
        //绘制圆角矩形
        mPaint.setColor(Color.YELLOW);
        RectF rectF = new RectF(0,0,700,500);
        canvas.drawRoundRect(rectF,30,30,mPaint);

        // 绘制椭圆
        mPaint.setColor(Color.BLUE);
        canvas.drawOval(rectF,mPaint);

        canvas.translate(0,-250);

        // 绘制圆弧
        mPaint.setColor(Color.MAGENTA);
        canvas.drawArc(rectF,180,180,true,mPaint);
        canvas.save();
        canvas.restore();
        //绘制圆
        mPaint.setColor(Color.RED);
        canvas.drawCircle(350,300,100,mPaint);

        canvas.translate(0,800);

        String text = "Hello HenCoder";
        //设置文字大小。
        mPaint.setTextSize(60);
        //设置字体。
        mPaint.setTypeface(Typeface.SERIF);
        //是否使用伪粗体。
        mPaint.setFakeBoldText(true);
        //是否加删除线。
        mPaint.setStrikeThruText(true);
        //是否加下划线。
        mPaint.setUnderlineText(true);
        //设置文字横向错切角度。其实就是文字倾斜度的啦
        mPaint.setTextSkewX(-0.5f);
        //设置文字横向放缩。也就是文字变胖变瘦。
        mPaint.setTextScaleX(0.8f);
        //绘制文字
        canvas.drawText(text, 0, 0, mPaint);
        //根据路径绘制文字
        mPath.moveTo(0, 0);
        for ( int i = 1; i <= 3; i++ ){
            mPath.lineTo(i * 150, (float)Math.random() * 600);
        }
        canvas.drawTextOnPath(text,mPath,50,50,mPaint);
        canvas.save();


        textPaint.setColor(Color.GREEN);       //设置画笔颜色
        textPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        textPaint.setStrokeWidth(10f);
        textPaint.setTextSize(60);
        //需要绘制多行的文字，自动换行
        String text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
        StaticLayout staticLayout1 = new StaticLayout(text1, textPaint, width-median_distance,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        String text2 = "a\nbc\ndefghi\njklm\nnopqrst\nuvwx\nyz";
        StaticLayout staticLayout2 = new StaticLayout(text2, textPaint, width-median_distance,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);

        canvas.save();
        canvas.translate(0, 100);
        staticLayout1.draw(canvas);
        canvas.translate(0, 300);
        staticLayout2.draw(canvas);
        canvas.restore();

       Log.i(TAG,"行间距："+textPaint.getFontSpacing());
      Paint.FontMetrics fontMetrics=textPaint.getFontMetrics();
        Log.i(TAG,"topLine："+fontMetrics.top);
        Log.i(TAG,"bottomLine："+fontMetrics.bottom);
        Log.i(TAG,"ascentLine："+fontMetrics.ascent);
        Log.i(TAG,"descentLine："+fontMetrics.descent);

        //获取文字的显示范围
        Rect bounds=new Rect();
        mPaint.setColor(Color.BLACK);
        mPaint.getTextBounds(text, 0, text.length(), bounds);
        int offsetX=0;
        int offsetY=0;
        bounds.left += offsetX;

        bounds.top += offsetY;
        bounds.right += offsetX;
        bounds.bottom += offsetY;
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(bounds, mPaint);
        //测量文字的宽度并返回
        Log.i(TAG,"文字的宽度："+mPaint.measureText(text));
        Log.i(TAG,"多行文字的宽度："+textPaint.measureText(text1));
        Log.i(TAG,"多行文字添加换行符的宽度："+textPaint.measureText(text1));
        //绘制光标

    }
}
