package com.iris.foundation.activity;

import com.google.gson.Gson;
import com.iris.foundation.utils.CrashHandler;
import com.iris.foundation.utils.ToastIRISUtils;
import com.lidroid.xutils.HttpUtils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class IRISApplication extends Application {
	private static IRISApplication application;
	public static HttpUtils hu;
	public static Gson gson;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		CrashHandler handler = CrashHandler.getInstance();
		handler.init(getApplicationContext()); 
		if (hu == null) {
			hu = new HttpUtils();
			hu.configCurrentHttpCacheExpiry(1000);
			hu.configSoTimeout(60000);
		}
		if (gson == null) {
			gson = new Gson();
		}
	}

	public static Application getApplication() {
		return application;
	}

	/**
	 * 网络请求出错
	 * 
	 */
	public static void showNetErrorToast() {
		ToastIRISUtils.showToastLong(application, "请检查网络");
	}

	/**
	 * 网络请求数据出错
	 * 
	 */
	public static void showDataErrorToast() {
		ToastIRISUtils.showToastLong(application, "服务器请求失败");
	}

	/**
	 * 弹出toast
	 * 
	 * @param str
	 */
	public static void showToast(String str) {
		ToastIRISUtils.showToast(application, str);
	}
	/**
	 * 弹出toast(长时间)
	 * 
	 * @param str
	 */
	public static void showToastLong(String str) {
		ToastIRISUtils.showToastLong(application, str);
	}

	/**
	 * 常量100-199用户页面内部消息发送,200-250 表示获取数据成功,251-299表示上传数据成功
	 * ,400-450表示获取数据失败,450-499表示上传数据失败
	 */
	public static final int DATA_SUC = 200, DATA_FAILED = 400, SERVER_FAILED = 404, UP_SUC = 250, UP_FAILED = 450,NO_DATE=203;

	/**
	 * 调用系统打电话的方法
	 * 
	 * @param context
	 * @param phone
	 */
	public static void doCallPhone(Context context, String phone) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
		context.startActivity(intent);
	}

	/**
	 * 调起系统发短信功能
	 * 
	 * @param phoneNumber
	 * @param message
	 */
	public static void doSendSMSTo(Context context, String phoneNumber) {
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
		intent.putExtra("sms_body", "");
		context.startActivity(intent);
	}
}
