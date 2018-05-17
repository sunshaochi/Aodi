package com.iris.foundation.utils;

import android.util.Log;

/**
 * 控制log打印，建议统一使用该类，便于控制log打印
 * @author ChuanJing
 * @date 2016年8月12日 下午5:41:08
 */
public class LogIRISUtilS {

	/** 控制log的开关 */
	private static final boolean IS_SHOW_LOG = false;

	public static void v(String msg) {
		if (IS_SHOW_LOG) {
			Log.w("--vaerbose--", msg);
		}
	}
	
	public static void v(String tag, String msg){
		if (IS_SHOW_LOG) {
			Log.w(tag, msg);
		}
	}
	
	public static void d(String msg) {
		if (IS_SHOW_LOG) {
			Log.d("--debug--", msg);
		}
	}
	
	public static void d(String tag, String msg){
		if (IS_SHOW_LOG) {
			Log.d(tag, msg);
		}
	}
	public static void i(String msg) {
		if (IS_SHOW_LOG) {
			Log.i("--info--", msg);
		}
	}
	
	public static void i(String tag, String msg){
		if (IS_SHOW_LOG) {
			Log.i(tag, msg);
		}
	}
	public static void w(String msg) {
		if (IS_SHOW_LOG) {
			Log.v("--vaerbose--", msg);
		}
	}
	
	public static void w(String tag, String msg){
		if (IS_SHOW_LOG) {
			Log.v(tag, msg);
		}
	}
	
	public static void w(String tag, String msg, Throwable tr) {
		if (IS_SHOW_LOG) {
			Log.e(tag, msg, tr);
		}
	}

	public static void e(String msg) {
		if (IS_SHOW_LOG) {
			Log.e("--error--", msg);
		}
	}
	
	public static void e(String tag, String msg){
		if (IS_SHOW_LOG) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (IS_SHOW_LOG) {
			Log.e(tag, msg, tr);
		}
	}


	/**
	 * 打印异常堆栈
	 * @param exception
	 */
	public static void logException(Throwable e) {
		if (IS_SHOW_LOG) {
			if (e != null) {
				e.printStackTrace();
			}
		}
	}
}
