package com.iris.foundation.utils;

import com.iris.foundation.activity.IRISApplication;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 工具类
 * 
 * @author Chenlipeng
 * @date 2017年5月27日 上午11:01:44
 */
public class ToastIRISUtils {

	private static Toast mToast;

	public static void showToast(Context context, int id) {
		if (mToast == null) {
			mToast = Toast.makeText(IRISApplication.getApplication(), "", Toast.LENGTH_SHORT);
		}
		mToast.setText(id);
		mToast.setDuration(Toast.LENGTH_SHORT);
		mToast.show();
	}

	public static void showToast(Context context, String string) {
		if (mToast == null) {
			mToast = Toast.makeText(IRISApplication.getApplication(), "", Toast.LENGTH_SHORT);
		}
		mToast.setText(string);
		mToast.setDuration(Toast.LENGTH_SHORT);
		mToast.show();
	}
	public static void showToastLong(Context context, int id) {
		if (mToast == null) {
			mToast = Toast.makeText(IRISApplication.getApplication(), "", Toast.LENGTH_SHORT);
		}
		mToast.setText(id);
		mToast.setDuration(Toast.LENGTH_LONG);
		mToast.show();
	}
	
	public static void showToastLong(Context context, String string) {
		if (mToast == null) {
			mToast = Toast.makeText(IRISApplication.getApplication(), "", Toast.LENGTH_SHORT);
		}
		mToast.setText(string);
		mToast.setDuration(Toast.LENGTH_LONG);
		mToast.show();
	}

}
