package com.hicetech.demodouban.commonality.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hicetech.demodouban.R;

/**
 * Created by Link on 2017/9/4.
 *
 */

public class PopupWindowUtility {

    /**刚进入页面的加载dilog**/
    public static Dialog loading(Activity activity){
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Dialog dialog = new Dialog(activity, R.style.AlertDialogStyle);
        View layout = inflater.inflate(R.layout.dialog_loading, null);
        dialog.setContentView(layout);
        Window window = dialog.getWindow();

        WindowManager.LayoutParams lp = window.getAttributes(); // 获取对话框当前的参数值
        window.setGravity(Gravity.CENTER);
        layout.measure(0, 0);
        lp.width = layout.getMeasuredWidth();
        lp.height = layout.getMeasuredHeight();
        window.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(false);
        if (!dialog.isShowing()){
            dialog.show();
        }

        return dialog;

    }

}
