package com.example.fanyuanhua.netpower.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.fanyuanhua.netpower.bean.PieData;

import java.util.ArrayList;

import static java.lang.Math.PI;

public class PieView extends View {
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    private Paint mPaint = new Paint();
    private Paint mTextPaint = new Paint();
    // 宽高
    private int mWidth, mHeight;
    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    // 数据
    private ArrayList<PieData> mData;
    public PieView(Context context) {
        this(context,null);
    }

    public PieView(Context context,  @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieView(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPieView();
    }

    private void initPieView() {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
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
        if (null == mData)
            return;

        float currentStartAngle = mStartAngle;
        //将画布移动到中心位置
        canvas.translate(mWidth / 2, mHeight / 2);
        //设置饼状半径
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);

        //设置绘制的范围
        RectF rect = new RectF(-r, -r, r, r);
        float dx = 0, dy = 0;
        mTextPaint.setTextSize(50);
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);
            mPaint.setColor(pie.getColor());
            //绘制圆弧
            canvas.drawArc(rect, currentStartAngle, pie.getAngle(), true, mPaint);
              //绘制分数
            if(currentStartAngle>0 && currentStartAngle<=90){//第四象限
                dx = (float) ( r*0.8  * Math.cos(2 * PI / 360 * currentStartAngle));//注意Math.sin(x)中x为弧度值，并非数学中的角度，所以需要将角度转换为弧度
                dy = (float) ( r*0.8   * Math.sin(2 * PI / 360 * currentStartAngle));

            }else if(currentStartAngle > 90f && currentStartAngle <= 180f){//第三象限
                dx = (float) (- r*0.8   * Math.cos(2 * PI / 360 * (180f - currentStartAngle)));
                dy = (float) ( r *0.8  * Math.sin(2 * PI / 360 * (180f - currentStartAngle)));

            }else if(currentStartAngle > 180f && currentStartAngle <= 270f){//第二象限
                dx = (float) ( - r*0.8   * Math.cos(2 * PI / 360 * (270f - currentStartAngle)));
                dy = (float) ( -r*0.8   * Math.sin(2 * PI / 360 * (270f - currentStartAngle)));
            }else {//第一象限
                dx = (float) ( r*0.8   * Math.cos(2 * PI / 360 * (360f - currentStartAngle)));
                dy = (float) ( -r *0.8  * Math.sin(2 * PI / 360 * (360f - currentStartAngle)));
            }
            canvas.drawText(pie.getPercentage() *100+ "%", dx, dy, mTextPaint);
            currentStartAngle += pie.getAngle();
        }
    }


    // 设置起始角度
    public void setStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
        invalidate();   // 刷新
    }

    // 设置数据
    public void setData(ArrayList<PieData> mData) {
        this.mData = mData;
        initData(mData);
        invalidate();   // 刷新
    }

    // 初始化数据
    private void initData(ArrayList<PieData> mData) {
        if (null == mData || mData.size() == 0)   // 数据有问题 直接返回
            return;

        float sumValue = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            sumValue += pie.getValue();       //计算数值和

            int j = i % mColors.length;       //设置颜色
            pie.setColor(mColors[j]);
        }

        float sumAngle = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            float percentage = pie.getValue() / sumValue;   // 百分比
            float angle = percentage * 360;                 // 对应的角度

            pie.setPercentage(percentage);                  // 记录百分比
            pie.setAngle(angle);                            // 记录角度大小
            sumAngle += angle;

            Log.i("angle", "" + pie.getAngle());
        }
    }
}
