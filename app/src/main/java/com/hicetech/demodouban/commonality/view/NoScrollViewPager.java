package com.hicetech.demodouban.commonality.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/6/24.
 */

public class NoScrollViewPager extends ViewPager{
    private boolean isCanScroll;
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            //允许滑动则应该调用父类的方法
            return super.onTouchEvent(ev);
        } else {
            //禁止滑动则不做任何操作，直接返回true即可
            return false;
        }
    }

    @Override
    public void scrollTo(int x, int y) {

        /**
         *必须这样重写，否则会出现画面"一半，一半"的现象，根据Debug来分析可能是虽然onTouchEvent方法返回了true
         *但是依然在返回true前，viewpager开始调用了scrollTo方法，导致画面"拖出来一点"
         */
        super.scrollTo(x, y);

    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isCanScroll) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }
    }

    //设置是否允许滑动，true是可以滑动，false是禁止滑动
    public void setIsCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }
}
