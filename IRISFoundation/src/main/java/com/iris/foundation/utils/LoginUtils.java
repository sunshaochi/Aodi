package com.iris.foundation.utils;

import java.io.File;

import com.iris.foundation.activity.IRISApplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings.Secure;

/**
 * 登陆时用到的一些方法汇总
 * 
 * @author CK
 *
 */
public class LoginUtils {
	private Activity context;
	/**
	 * 更新APK在本地存放的位置
	 */
	public File APK_DIR;
	private static LoginUtils loginUtils;

	private LoginUtils(Activity context) {
		this.context = context;

		if (APK_DIR == null) {
			APK_DIR = new File(Environment.getExternalStorageDirectory(), "/iris/update");
			if (!APK_DIR.exists()) {
				APK_DIR.mkdirs();
			}
		}
	}

	public static LoginUtils getInstance(Activity context) {
		if (loginUtils == null) {
			loginUtils = new LoginUtils(context);
		}
		return loginUtils;
	}

	/**
	 * 安装apk
	 * 
	 * @param file
	 *            要安装的apk的目录
	 */
	public void install(File file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		context.finish();
		context.startActivity(intent);
	}

	// -------------------------------一些系统信息----------------------
	/**
	 * 获取设备id
	 * 
	 * @return
	 */
	public String getDeviceId() {
		return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}

	/**
	 * 获取版本号
	 * 
	 * @return
	 */
	public String getVersion() {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionCode + "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			IRISApplication.showToast("版本号未知");
		}
		return null;
	}
}
