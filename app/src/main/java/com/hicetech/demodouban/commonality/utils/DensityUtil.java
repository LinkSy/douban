package com.hicetech.demodouban.commonality.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DensityUtil {
	/**
	 * dip转像素  
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float SCALE = context.getResources().getDisplayMetrics().density;

		float valueDips = dpValue;
		int valuePixels = (int) (valueDips * SCALE + 0.5f);

		return valuePixels;
	}

	/**
	 * 像素转dip
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float SCALE = context.getResources().getDisplayMetrics().density;

		float dips = pxValue / SCALE;

		return (int) dips;
	}

	/**
	 * 获取屏幕的宽度
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	/**
	 * 获取屏幕的高度  
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 动态设置ListView的高度
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		if(listView == null) return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//		params.height = 2000;
		listView.setLayoutParams(params);
	}

}
