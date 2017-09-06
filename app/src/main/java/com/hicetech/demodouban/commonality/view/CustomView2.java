package com.hicetech.demodouban.commonality.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Link on 2017/9/5.
 */

public class CustomView2 extends View {
    private Paint mPaint = new Paint();
    //宽高
    private int mWidth,mHeight ;
    public CustomView2(Context context) {
        super(context);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6f);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        canvas.translate(mWidth/2,mHeight/2);
        /**
         * 图一
         */
        /*RectF rect = new RectF(-400,-400,400,400);

        for (int i = 0; i <20 ; i++) {
            canvas.drawRect(rect,mPaint);
            canvas.scale(0.9f,0.9f);

        }
        */
        /**
         * 图二
         */
        canvas.drawCircle(0,0,400,mPaint);
        canvas.drawCircle(0,0,380,mPaint);

        for (int i = 0; i <=360 ; i+=10) {
            canvas.drawLine(0,380,0,400,mPaint);
            canvas.rotate(10);
        }

    }


}
