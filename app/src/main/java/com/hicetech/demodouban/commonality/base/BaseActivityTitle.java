package com.hicetech.demodouban.commonality.base;

import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.utils.SystemStatusManager;

import butterknife.ButterKnife;

public abstract class BaseActivityTitle extends FragmentActivity {
	private LinearLayout mLinearLayoutTitle;
	private TextView mRightText;
	private LinearLayout llContent;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_title_activity);
		LayoutInflater inflater = (LayoutInflater) getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(setView(), null);
		llContent =  ButterKnife.findById(this,R.id.llContent);
		llContent.addView(v, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ButterKnife.bind(this);
		initView();
		initData();
		initEvent();
	}

	/**
	 * 事件处理
	 */
	public abstract void initEvent();

	/**
	 * 逻辑代码处理
	 */
	public abstract void initData();

	/**v
	 * 控件的获取
	 */
	public abstract void initView();
	/**
	 * 设置主内容区域的layoutRes
	 * @param
	 */
	public abstract int setView();
	/**
	 * 设置左侧菜单图
	 */
	public void setLeftIMG(int icon){
		AppCompatImageView imageView = (AppCompatImageView) findViewById(R.id.iv_left_icon);
		imageView.setImageResource(icon);
	}
	/**
	 * 设置左侧菜单名称
	 */
	public void setLeftText(String text){
		TextView textView = (TextView) findViewById(R.id.tv_celebrity_name);
		textView.setText(text);
	}
	/**
	 * 设置左侧Top
	 */
	public void setLeftTopText(String text){
		TextView textView = (TextView) findViewById(R.id.tv_left_top);
		textView.setText(text);
	}

	/**
	 * 设置右侧菜单图
	 */
	public void setRightTitleDataIMG(int rightTitle){
		ImageView img=(ImageView) findViewById(R.id.iv_right_icon);
		img.setImageResource(rightTitle);
	}
	/**
	 * 设置右侧菜单动画
	 */
	public void setRightIMGAnimation(AnimatedVectorDrawable mAnimatedVectorDrawable){
		ImageView img=(ImageView) findViewById(R.id.iv_right_icon);
		img.setImageDrawable(mAnimatedVectorDrawable);
	}
	/**
	 * 更改标题颜色
	 * @param color
	 */
	protected void setChangeThemeColor(int color){
		findViewById(R.id.ll_title).setBackgroundResource(color);

	}
	/**
	 * 隐藏标题下划线
	 */
	protected void sethintThemeGone(){
		findViewById(R.id.view_list).setVisibility(View.GONE);

	}
	/**
	 * 隐藏标题下划线
	 */
	protected void setshowThemeGone(){
		findViewById(R.id.view_list).setVisibility(View.VISIBLE);

	}

	protected void upThemeStyle(int color){
		sethintThemeGone();
		setChangeThemeColor(color);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemStatusManager tintManager = new SystemStatusManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(color);//通知栏所需颜色
		}
	}



	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
}
