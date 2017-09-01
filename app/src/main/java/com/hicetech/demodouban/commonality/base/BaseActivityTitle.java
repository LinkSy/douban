package com.hicetech.demodouban.commonality.base;

import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hicetech.demodouban.R;
import com.hicetech.demodouban.commonality.utils.SystemStatusManager;
import com.hicetech.demodouban.commonality.utils.ViewUtils;

import butterknife.ButterKnife;

public abstract class BaseActivityTitle extends FragmentActivity {
	private RelativeLayout mRightTitle;
	private TextView mRightText;
	private LinearLayout llContent;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_title_activity);
		LayoutInflater inflater = (LayoutInflater) getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		upThemeStyle();
		View v = inflater.inflate(setView(), null);
		llContent =  ButterKnife.findById(this,R.id.llContent);
		llContent.addView(v, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ButterKnife.bind(this);
		initView();
		initData();
		initEvent();
		setLeftImageClick();
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
	 * 隐藏左侧按钮
	 */
	public void alabHideRelativeLayoutLeft(boolean bSetHide) {
		RelativeLayout bt = alabGetLeft();
		if (null != bt) {
			if (bSetHide){
				bt.setVisibility(View.INVISIBLE);
			}else {
				bt.setVisibility(View.VISIBLE);
			}
		}
	}
	/**
	 * 隐藏右侧按钮
	 */
	public void alabHideRelativeLayoutRight(boolean bSetHide) {
		RelativeLayout bt = alabGetRight();
		if (null != bt) {
			if (bSetHide) bt.setVisibility(View.INVISIBLE);
			else bt.setVisibility(View.VISIBLE);
		}
	}
	/**
	 * 得到模板上导航栏的左侧按钮，一般为[返回]
	 * @return 成功则返回ImageView对象，失败则返回null。
	 */
	public RelativeLayout alabGetLeft() {
		RelativeLayout relativeLayout=(RelativeLayout) findViewById(R.id.rl_left_title);
		relativeLayout.setVisibility(View.VISIBLE);
		return relativeLayout;
	}
	/**
	 * 得到模板上导航栏的右侧按钮，一般为[刷新]
	 * @return 成功则返回Button对象，失败则返回null。
	 */
	public RelativeLayout alabGetRight() {
		if(null==mRightTitle){
			mRightTitle = (RelativeLayout) findViewById(R.id.rl_right_title);
		}
		return mRightTitle;
	}
	/**
	 * 设置模板上导航栏中间的标题文字
	 * @param titleText
	 * @return 修改成功返回true，失败返回false
	 */
	public boolean alabSetBarTitleText(int titleText) {
		TextView tv = (TextView) findViewById(R.id.tv_centre_title);
		if (null != tv) {

			com.hicetech.demodouban.commonality.utils.ViewUtils.setTypefaceOverstriking(tv);
			tv.setText(getResources().getString(titleText));
			return true;
		}
		return false;
	}
	/**
	 * 设置模板上导航栏中间的标题文字
	 * @param titleText
	 * @return 修改成功返回true，失败返回false
	 */
	public boolean alabSetBarTitleText(String titleText) {
		TextView tv = (TextView) findViewById(R.id.tv_centre_title);
		if (null != tv) {
			ViewUtils.setTypefaceOverstriking(tv);
			tv.setText(titleText);
			return true;
		}
		return false;
	}
	/**
	 *
	 * @return 右侧文本框
	 */
	public TextView getRightTextView(){
		if(null==mRightText){
			mRightText = (TextView) findViewById(R.id.tv_right_title);
		}
		return mRightText;

	}


	/**
	 * 设置左侧菜单图片
	 * @param leftTitle
	 */
	public void setLeftTitleData(int leftTitle){
		ImageView img=(ImageView) findViewById(R.id.iv_left_title);
		img.setImageResource(leftTitle);
	}
	/**
	 * 设置右侧菜单图
	 */
	public void setRightTitleDataIMG(int rightTitle){
		ImageView img=(ImageView) findViewById(R.id.iv_right_title);
		img.setImageResource(rightTitle);
	}

	/**
	 * 设置右侧焦点
	 * @param is
	 */
	public void setmRightTitleFocus(boolean is){
		alabGetRight().setEnabled(is);
	}
	/**
	 * 设置右侧菜单文本
	 */
	public boolean setRightTitleDataTXT(int rightTitle){

		TextView text=(TextView) findViewById(R.id.tv_right_title);
		if(null!=getRightTextView()){
			ViewUtils.setTypefaceOverstriking(getRightTextView());
			getRightTextView().setText(getResources().getString(rightTitle));
			return true;
		}
		return false;

	}
	/**
	 * 设置右侧菜单文本
	 */
	public boolean setRightTitleDataTXT(String rightTitle){

		TextView text=(TextView) findViewById(R.id.tv_right_title);
		if(null!=getRightTextView()){
			ViewUtils.setTypefaceOverstriking(getRightTextView());
			getRightTextView().setText(rightTitle);
			return true;
		}
		return false;

	}
	/**
	 * 子类实现此方法覆盖父类的点击事件
	 * @param left
	 */
	protected void finishLeft(RelativeLayout left){
		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	/**
	 * 更改标题颜色
	 * @param color
	 */
	protected void setChangeThemeColos(int color){
		findViewById(R.id.rl_headline).setBackgroundResource(color);

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
	protected void upThemeStyle(){
		//((ImageView) findViewById(R.id.iv_left_title)).setImageResource(R.mipmap.b_back);
		//((TextView)findViewById(R.id.tv_centre_title)).setTextColor(getResources().getColor(R.color.white));
		//setChangeThemeColos(R.color.login_btn_color);
		sethintThemeGone();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemStatusManager tintManager = new SystemStatusManager(this);
			tintManager.setStatusBarTintEnabled(true);
			//tintManager.setStatusBarTintResource(R.color.login_theme);//通知栏所需颜色
		}
		//getRightTextView().setTextColor(getResources().getColor(R.color.white));
	}

	protected void upThemeStyle(int color){
		sethintThemeGone();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
			SystemStatusManager tintManager = new SystemStatusManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setStatusBarTintResource(color);//通知栏所需颜色
		}
	}

	protected void setLeftImageClick(){
		findViewById(R.id.rl_left_title).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
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
