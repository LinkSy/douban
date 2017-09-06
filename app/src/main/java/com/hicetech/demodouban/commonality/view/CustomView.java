package com.hicetech.demodouban.commonality.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hicetech.demodouban.forum.PieData;

import java.util.List;

/**
 * Created by Link on 2017/9/5.
 * 自定义View
 */

public class CustomView extends View {
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    //初始绘制角度
    private float mStartAngle = 0;
    //数据
    private List<PieData> mData ;
    //宽高
    private int mWidth,mHeight ;
    //画笔
    private Paint mPaint = new Paint();



    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

        canvas.translate(mWidth/2,mHeight/2);

        float r = (float) (Math.min(mWidth,mHeight)/2*0.8);

        RectF rectF = new RectF(-r,-r,r,r);

        for (int i = 0; i <mData.size() ; i++) {
            PieData pie = mData.get(i);
            mPaint.setColor(mColors[i]);
            canvas.drawArc(rectF,currentStartAngle,pie.getAngle(),true,mPaint);
            currentStartAngle += pie.getAngle();


        }

    }

    // 设置起始角度
    public void setStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
        invalidate();   // 刷新
    }

    // 设置数据
    public void setData(List<PieData> mData) {
        this.mData = mData;
        initData(mData);
        invalidate();   // 刷新
    }

    private void initData(List<PieData> mData) {
        if (mData == null || mData.size() == 0){
            return;
        }
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


        }

    }

}
