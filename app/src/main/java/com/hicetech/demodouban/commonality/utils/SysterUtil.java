package com.hicetech.demodouban.commonality.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

/**
 * 系统设置
 * @author Administrator
 *
 */
public class SysterUtil {
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = info.versionName;
			return  "v"+version;
		} catch (Exception e) {
			e.printStackTrace();
			return "失败";
		}
	}
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public static String getVersion1(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = info.versionName;
			return  version.replace(".", "");
		} catch (Exception e) {
			e.printStackTrace();
			return "失败";
		}
	}
	
	/**@param bMute 值为true时为关闭背景音乐。  音乐焦点获取*/  
	@TargetApi(Build.VERSION_CODES.FROYO)  
	public static boolean muteAudioFocus(Context context, boolean bMute) {  
	    if(context == null){  
	        Log.d("ANDROID_LAB", "context is null.");  
	        return false;  
	    }  
//	    if(!VersionUtils.isrFroyo()){  
//	        // 2.1以下的版本不支持下面的API：requestAudioFocus和abandonAudioFocus  
//	        Log.d("ANDROID_LAB", "Android 2.1 and below can not stop music");  
//	        return false;  
//	    }  
	    boolean bool = false;  
	    AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);  
	    if(bMute){  
	        int result = am.requestAudioFocus(null,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);  
	        bool = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;  
	    }else{  
	        int result = am.abandonAudioFocus(null);  
	        bool = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;  
	    }  
	    Log.d("ANDROID_LAB", "pauseMusic bMute="+bMute +" result="+bool);  
	    return bool;  
	}  
}
