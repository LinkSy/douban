package com.hicetech.demodouban.commonality.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 *
 * @author 界面的工具类
 * @name  jiesen
 * @time 2016-11-4下午2:37:21
 * @data
 */
public class ViewUtils {
	/**
	 * 更改视图的宽度为屏幕的宽度
	 * @param context
	 * @param v
	 */
	public static void setViewWide(Activity context,View v){
		LayoutParams para;
		para = v.getLayoutParams();
		para.height =  DensityUtil.getScreenWidth(context);
		para.width =  DensityUtil.getScreenWidth(context);
		v.setLayoutParams(para);
	}
	/**
	 * 字体加粗
	 * @param v
	 */
	public static void setTypefaceOverstriking(TextView v){
		TextPaint tp = v.getPaint();
		tp.setFakeBoldText(true);
	}
	/**
	 * 更改视图的宽度为屏幕的宽度一半
	 * @param context
	 * @param v
	 */
	public static void setBunWide(Activity context,View v){
		LayoutParams para;
		para = v.getLayoutParams();
		para.height =  DensityUtil.getScreenWidth(context)/2;
		para.width =  DensityUtil.getScreenWidth(context);
		v.setLayoutParams(para);
	}

	public static void setLayoutX(View view,int x)
	{
		MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams());
		margin.setMargins(x,margin.topMargin, x+margin.width, margin.bottomMargin);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
		view.setLayoutParams(layoutParams);
	}

	/* 
	 * 设置控件所在的位置Y，并且不改变宽高， 
	 * Y为绝对位置，此时X可能归0 
	 */
	public static void setLayoutY(View view,int y)
	{
		MarginLayoutParams margin=new MarginLayoutParams(view.getLayoutParams());
		margin.setMargins(margin.leftMargin,y, margin.rightMargin, y+margin.height);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
		view.setLayoutParams(layoutParams);
	}

	/**
	 * 自定义控件的高度
	 * @param context
	 * @param v
	 */
	public static void setDayBunHeight(Activity context,View v,int h){
		LayoutParams para;
		para = v.getLayoutParams();
		para.height =  h;
		para.width =  DensityUtil.getScreenWidth(context);
		v.setLayoutParams(para);
	}
	/**
	 * 更改视图高度
	 * @param context
	 * @param v
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static void settingHeight(Activity context, View v, int h){
		LayoutParams para;
		para = v.getLayoutParams();
		para.height =  h;
		v.setLayoutParams(para);

	}
	/**
	 * 更改视图宽度
	 * @param context
	 * @param v
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static void settingWidth(Activity context, View v, int h){
		LayoutParams para;
		para = v.getLayoutParams();
		para.width =  h;
		v.setLayoutParams(para);

	}

	/**
	 * 获取打气筒
	 * @param context
	 * @return
     */
	public static LayoutInflater getInflater(Context context){
		return ((LayoutInflater )context.getSystemService(context.LAYOUT_INFLATER_SERVICE)).from(context);
	}


}
