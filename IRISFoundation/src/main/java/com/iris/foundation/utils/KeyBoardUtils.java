package com.iris.foundation.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘工具类
 */
public class KeyBoardUtils {

	private static InputMethodManager manager;

	/**
	 * 打开输入法
	 * 
	 * @param view
	 * @param context
	 */
	public static void openKeyBoard(View view, Context context) {
		manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		manager.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
		manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 关闭输入法
	 * 
	 * @param view
	 * @param context
	 */
	public static void closeKeyBoard(View view, Context context) {
		manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 切换输入法
	 * 
	 * @param view
	 * @param context
	 */
	public static void blockedOutInput(View view, Context context) {
		manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		manager.toggleSoftInputFromWindow(view.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
