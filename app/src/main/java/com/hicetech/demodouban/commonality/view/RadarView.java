package com.hicetech.demodouban.commonality.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Link on 2017/9/5.
 */

public class RadarView extends View {

    private int count = 6;
    private float angle = (float) (Math.PI*2/count);
    private float radius = 500;//网格最大半径
    private int centerX; //中心X
    private int centerY; //中心Y
    private String[] titles = {"a","b","c","d","e","f"};
    private double[] data = {100,60,60,60,100,50,10,20}; //各维度分值
    private float maxValue = 100;             //数据最大值
    private Paint mainPaint;                //雷达区画笔
    private Paint valuePaint;               //数据区画笔
    private Paint textPaint;                //文本画笔




    public RadarView(Context context) {
        super(context);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mainPaint = new Paint();
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setColor(Color.BLACK);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerX = w/2;
        centerY = h/2;

        postInvalidate();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
    }

    private void drawPolygon(Canvas canvas){
        Path path = new Path();
        float r = radius/(count - 1);
        for (int i = 1; i <count ; i++) {
            float curR = r*i;
            path.reset();

            for (int j = 0; j <count ; j++) {
                if (j == 0){
                    path.moveTo(centerX+curR,centerY);

                }else {
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
}
