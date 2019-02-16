package com.example.fanyuanhua.netpower.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class RadarView extends View {
    private int count = 6;                //数据个数
    private float angle = (float) (Math.PI*2/count);
    private float radius;                   //网格最大半径
    private int centerX;                  //中心X
    private int centerY;
    private double[] data = {100,60,60,60,100,50,10,20}; //各维度分值
    private String[] titles = {"富强","民主","自由","和谐","互爱","平等"};
    private float maxValue = 100;             //数据最大值
    private Paint mainPaint=new Paint();                //雷达区画笔
    private Path path = new Path();
    private Paint valuePaint=new Paint();               //数据区画笔
    private Paint textPaint=new TextPaint();                //文本画笔
    public RadarView(Context context) {
        this(context,null);
    }

    public RadarView(Context context,  @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadarView(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRadarView();
    }

    private void initRadarView() {
        mainPaint.setColor(Color.BLACK);       //设置画笔颜色
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setStrokeWidth(4f);

        valuePaint.setColor(Color.GREEN);       //设置画笔颜色
        valuePaint.setStyle(Paint.Style.STROKE);
        valuePaint.setStrokeWidth(2f);

        textPaint.setColor(Color.RED);       //设置画笔颜色
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeWidth(2f);
        textPaint.setTextSize(40);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius=Math.min(w,h)/2*0.7f;
        //中心坐标
        centerX = w/2;
        centerY = h/2;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawLines(canvas);
        drawRegion(canvas);
        drawText(canvas);
    }

    /**
     * 画雷达图
     * @param canvas
     */
    private void drawPolygon(Canvas canvas){
        float r = radius/(count-1);//r是蜘蛛丝之间的间距
        for(int i=1;i<count;i++){//中心点不用绘制
            float curR = r*i;//当前半径
            path.reset();
            for(int j=0;j<count;j++){
                if(j==0){
                    path.moveTo(centerX+curR,centerY);
                }else{
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x = (float) (centerX+curR*Math.cos(angle*j));
                    float y = (float) (centerY+curR*Math.sin(angle*j));
                    path.lineTo(x,y);
                }
            }
            path.close();//闭合路径
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * 画原点到各个重点的直线
     * @param canvas
     */
    private void drawLines(Canvas canvas){
        for(int i=0;i<count;i++){
            path.reset();
            path.moveTo(centerX, centerY);
            float x = (float) (centerX+radius*Math.cos(angle*i));
            float y = (float) (centerY+radius*Math.sin(angle*i));
            path.lineTo(x, y);
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * 绘制区域
     * @param canvas
     */
    private void drawRegion(Canvas canvas){
        path.reset();
        valuePaint.setAlpha(255);
        for(int i=0;i<count;i++){
            double percent = data[i]/maxValue;
            float x = (float) (centerX+radius*Math.cos(angle*i)*percent);
            float y = (float) (centerY+radius*Math.sin(angle*i)*percent);
            if(i==0){
                path.moveTo(x, centerY);
            }else{
                path.lineTo(x,y);
            }
            //绘制小圆点
            canvas.drawCircle(x,y,2,valuePaint);
        }
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);
        valuePaint.setAlpha(127);
        //绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
    }
    /**
     * 绘制文字
     * @param canvas
     */
    private void drawText(Canvas canvas){
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for(int i=0;i<count;i++){
            float x = (float) (centerX+(radius+fontHeight/2)*Math.cos(angle*i));
            float y = (float) (centerY+(radius+fontHeight/2)*Math.sin(angle*i));
            if(angle*i>=0&&angle*i<=Math.PI/2){//第4象限
                canvas.drawText(titles[i], x,y,textPaint);
            }else if(angle*i>=3*Math.PI/2&&angle*i<=Math.PI*2){//第3象限
                canvas.drawText(titles[i], x,y,textPaint);
            }else if(angle*i>Math.PI/2&&angle*i<=Math.PI){//第2象限
                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x-dis,y,textPaint);
            }else if(angle*i>=Math.PI&&angle*i<3*Math.PI/2){//第1象限
                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x-dis,y,textPaint);
            }
        }
    }


}
