package com.hicetech.demodouban.commonality.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Stack;

/**
 *
 * @author  所有界面必须继承此类，此类为所有Activity的基类
 *
 */
public class BaseActivity extends FragmentActivity {

    /** 用来保存所有已打开的Activity */
    private static Stack<Activity> listActivity = new Stack<Activity>();
    protected Gson gson=new Gson();
    /** 提示信息 **/
    private Toast mToast;

    /** 记录上次点击按钮的时间 **/
    private long lastClickTime;
    /** 按钮连续点击最低间隔时间 单位：毫秒 **/
    public final static int CLICK_TIME = 500;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	 /********************** activity跳转 **********************************/
    public void openActivity(Class<?> targetActivityClass) {
        openActivity(targetActivityClass, null);
    }
    public void openActivity(Class<?> targetActivityClass,int code) {
        openActivity(targetActivityClass,null,code);
    }
    public void openActivity(Class<?> targetActivityClass,Bundle bundle,int code) {
        Intent intent = new Intent(this, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,code);//;Activity(intent);
//        overridePendingTransition(R.anim.zoomout,R.anim.zoomin);
    }

    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(this, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
//        overridePendingTransition(R.anim.zoomout,R.anim.zoomin);
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass) {
        openActivity(targetActivityClass);
        this.finish();
    }

    public void openActivityAndCloseThis(Class<?> targetActivityClass, Bundle bundle) {
        openActivity(targetActivityClass, bundle);
        this.finish();
    }

    /***************************************************************/

    /** 验证上次点击按钮时间间隔，防止重复点击 */
    public boolean verifyClickTime() {
        if (System.currentTimeMillis() - lastClickTime <= CLICK_TIME) {
            return false;
        }
        lastClickTime = System.currentTimeMillis();
        return true;
    }

    /** 收起键盘 */
    public void closeInputMethod() {
        // 收起键盘
        View view = getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
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

    /**
     * 关闭所有(前台、后台)Activity,注意：请已BaseActivity为父类
     */
    protected static void finishAll() {
        int len = listActivity.size();
        for (int i = 0; i < len; i++) {
            Activity activity = listActivity.pop();
            activity.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 从栈中移除当前activity
        if (listActivity.contains(this)) {
            listActivity.remove(this);
        }

    }
    /**
     * 发送handler
     * @param hamdler
     * @param what
     * @param obj
     */
    public void sendHandler(Handler hamdler, int what, Object obj){
        Message message = new Message();
        message.what = what;
        if(null!=obj){
            message.obj=obj;
        }
        hamdler.sendMessage(message);
    }
    /**
     * 发送handler
     * @param hamdler
     * @param what
     * @param obj
     */
    public void sendHandler(Handler hamdler, int what,int code, Object obj){
        Message message = new Message();
        message.what = what;
        message.arg1=code;
        if(null!=obj){
            message.obj=obj;
        }
        hamdler.sendMessage(message);
    }

    protected void toast(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }
}
