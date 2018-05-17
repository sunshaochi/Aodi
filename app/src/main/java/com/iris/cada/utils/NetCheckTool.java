package com.iris.cada.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

public class NetCheckTool {
	/*
	 * public static boolean isNetworkAvailable(Context context) { //获得网络状态管理器
	 * ConnectivityManager connectivityManager = (ConnectivityManager)
	 * context.getSystemService(Context.CONNECTIVITY_SERVICE);
	 * if(connectivityManager == null){ return false; }else{ NetworkInfo[] info
	 * = connectivityManager.getAllNetworkInfo(); if(info != null){
	 * for(NetworkInfo network : info){ if(network.getState() ==
	 * NetworkInfo.State.CONNECTED){ return true; } } } } return false;
	 * 
	 * }
	 */

	public static boolean isNetworkAvailable(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			Log.v("error", e.toString());
		}
		return false;
	}

	public static void checkNetwork(final Context context) {
		if (!isNetworkAvailable(context)) {
			TextView msg = new TextView(context);
			msg.setText("当前没有可用网络，请设置网络！");
			msg.setTextSize(25);
			new AlertDialog.Builder(context).setTitle("网络状态提示：").setView(msg)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 跳转到设置界面
							context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
						}
					}).create().show();

		}
	}

}
