package com.example.fanyuanhua.netpower.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.fanyuanhua.netpower.R;
import com.example.fanyuanhua.netpower.tool.DP;

public class CanvasSix extends View {

    // 淡白色
    private static final int WHITE_COLOR = 0xfffde399;
    // 橙色
    private static final int ORANGE_COLOR = 0xffffa800;
    private float mCurrentProgressPosition;
    private float mProgressWidth;
    private  float mProgress;

    // 总进度
    private static final int TOTAL_PROGRESS = 100;
    // 用于控制绘制的进度条距离左／上／下的距离
    private static final int LEFT_MARGIN = 9;
    // 用于控制绘制的进度条距离右的距离
    private static final int RIGHT_MARGIN = 25;

    private int mLeftMargin, mRightMargin;

    private Bitmap mLeafBitmap;
    private int mLeafWidth, mLeafHeight;

    private Bitmap mOuterBitmap;
    private int mOuterWidth, mOuterHeight;

    private Rect mOuterSrcRect, mOuterDestRect;

    private Paint mBitmapPaint, mWhitePaint, mOrangePaint;

    private int mTotalWidth, mTotalHeight;
    // 弧形的半径
    private int mArcRadius;

    private RectF mWhiteRectF, mOrangeRectF, mArcRectF;
    // arc的右上角的x坐标，也是矩形x坐标的起始点
    private int mArcRightLocation;

    public CanvasSix(Context context) {
        this(context,null);
    }

    public CanvasSix(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasSix(Context context,  @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context ) {
        mLeftMargin = DP.dp2px(context, LEFT_MARGIN);
        mRightMargin = DP.dp2px(context, RIGHT_MARGIN);
        mLeafBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.leaf)).getBitmap();
        mLeafWidth = mLeafBitmap.getWidth();
        mLeafHeight = mLeafBitmap.getHeight();

        mOuterBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.leaf_kuang)).getBitmap();
        mOuterWidth = mOuterBitmap.getWidth();
        mOuterHeight = mOuterBitmap.getHeight();

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);

        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setColor(WHITE_COLOR);

        mOrangePaint = new Paint();
        mOrangePaint.setAntiAlias(true);
        mOrangePaint.setColor(ORANGE_COLOR);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mProgressWidth = mTotalWidth - mLeftMargin - mRightMargin;
        mArcRadius = (mTotalHeight - 2 * mLeftMargin) / 2;

        mOuterSrcRect = new Rect(0, 0, mOuterWidth, mOuterHeight);
        mOuterDestRect = new Rect(0, 0, mTotalWidth, mTotalHeight);

        mWhiteRectF = new RectF(mLeftMargin + mCurrentProgressPosition, mLeftMargin, mTotalWidth
                - mRightMargin,
                mTotalHeight - mLeftMargin);
        mOrangeRectF = new RectF(mLeftMargin + mArcRadius, mLeftMargin,
                mCurrentProgressPosition
                , mTotalHeight - mLeftMargin);

        mArcRectF = new RectF(mLeftMargin, mLeftMargin, mLeftMargin + 2 * mArcRadius,
                mTotalHeight - mLeftMargin);
        mArcRightLocation = mLeftMargin + mArcRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawProgressAndLeafs(canvas);
        canvas.drawBitmap(mOuterBitmap, mOuterSrcRect, mOuterDestRect, mBitmapPaint);

        postInvalidate();
    }

    private void drawProgressAndLeafs(Canvas canvas) {
        if (mProgress >= TOTAL_PROGRESS) {
            mProgress = 0;
        }
        mProgress=5;
        mCurrentProgressPosition = mProgressWidth * mProgress / TOTAL_PROGRESS;
        if (mCurrentProgressPosition < mArcRadius) {
            // 1.绘制白色ARC，绘制orange ARC
            // 2.绘制白色矩形

            // 1.绘制白色ARC
            canvas.drawArc(mArcRectF, 90, 180, false, mWhitePaint);

            // 2.绘制白色矩形
            mWhiteRectF.left = mArcRightLocation;
            canvas.drawRect(mWhiteRectF, mWhitePaint);



            // 3.绘制棕色 ARC
            // 单边角度
            int angle = (int) Math.toDegrees(Math.acos((mArcRadius - mCurrentProgressPosition)
                    / (float) mArcRadius));
            // 起始的位置
            int startAngle = 180 - angle;
            // 扫过的角度
            int sweepAngle = 2 * angle;

            canvas.drawArc(mArcRectF, startAngle, sweepAngle, false, mOrangePaint);
        }else {
            // 1.绘制white RECT
            mWhiteRectF.left = mCurrentProgressPosition;
            canvas.drawRect(mWhiteRectF, mWhitePaint);
            // 绘制叶子

            // 2.绘制Orange ARC
            canvas.drawArc(mArcRectF, 90, 180, false, mOrangePaint);
            // 3.绘制orange RECT
            mOrangeRectF.left = mArcRightLocation;
            mOrangeRectF.right = mCurrentProgressPosition;
        }
    }
}
