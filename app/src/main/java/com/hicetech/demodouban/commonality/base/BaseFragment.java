package com.hicetech.demodouban.commonality.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;

import es.dmoral.toasty.Toasty;

/**
 * 基类fragment
 */
public abstract class BaseFragment extends Fragment {
	private View contentView=null;//
	 boolean isVisible;

	protected Gson gson=new Gson();

	@Override
	public View onCreateView(LayoutInflater inflater,
							  ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(null==contentView) {
			this.contentView=inflater.inflate(getLayout(), null);
		}
		ViewGroup parent = (ViewGroup) contentView.getParent();
		if (parent != null) {
			parent.removeView(contentView);
		} 
		//initView(contentView);
		
		
		initData();
		initEvent();
		return contentView;

	}
	/**
	 * 事件处理
	 */
	public void initEvent() {
		// TODO Auto-generated method stub

	}
	/**
	 * 数据处理
	 */
	public void initData() {
		// TODO Auto-generated method stub

	}

	/**
	 * 获取视图
	 * @return
	 */
	public abstract int getLayout();
	/**
	 * 初始化控件
	 * @param contentView		SS
	 */
	//public abstract void initView(View contentView);
	/**
	 * handler
	 * @param handler
	 * @param type
	 * @param object
	 */
	public void sendHandlerMessage(Handler handler, int type, Object object) {
		Message message = handler.obtainMessage();
		message.what = type;
		message.obj = object;
		message.sendToTarget();
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		
		if(getUserVisibleHint()) {  
	            isVisible = true;  
	            onVisible();  
	        } else {  
	            isVisible = false;  
	            onInvisible();  
	        }  
	}
	/**
	 * 不可见的状态
	 */
	public void onInvisible() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 可见状态下
	 */
	public void onVisible() {
		// TODO Auto-generated method stub
		
	}

	/********************** activity跳转 **********************************/
	public void openActivity(Class<?> targetActivityClass) {
		openActivity(targetActivityClass, null);
	}
	public void openActivity(Class<?> targetActivityClass,int code) {
		openActivity(targetActivityClass,null,code);
	}
	public void openActivity(Class<?> targetActivityClass,Bundle bundle,int code) {
		Intent intent = new Intent(getActivity(), targetActivityClass);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent,code);//;Activity(intent);
//		getActivity().overridePendingTransition(R.anim.zoomout,R.anim.zoomin);
	}

	public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
		Intent intent = new Intent(getActivity(), targetActivityClass);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
//		getActivity().overridePendingTransition(R.anim.zoomout,R.anim.zoomin);
	}

	public void openActivityAndCloseThis(Class<?> targetActivityClass) {
		openActivity(targetActivityClass);
	}

	public void openActivityAndCloseThis(Class<?> targetActivityClass, Bundle bundle) {
		openActivity(targetActivityClass, bundle);
	}

	/***************************************************************/


	/** 收起键盘 */
	public void closeInputMethod() {
		// 收起键盘
		View view = getActivity().getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 获取string
	 *
	 * @param mRid
	 * @return
	 */
	public String getStringMethod(int mRid) {
		return this.getResources().getString(mRid);
	}

	/**
	 * 获取demin
	 *
	 * @param mRid
	 * @return
	 */
	protected int getDemonIntegerMethod(int mRid) {
		return (int) this.getResources().getDimension(mRid);
	}


	protected void toast(String text,String type){
		switch (type){
			case "info":
				Toasty.info(getActivity(),text,Toast.LENGTH_SHORT,true).show();
				break;
			case "error":
				Toasty.error(getActivity(),text,Toast.LENGTH_SHORT,true).show();
				break;
			case "success":
				Toasty.success(getActivity(),text,Toast.LENGTH_SHORT,true).show();
				break;
		}



	}



}
