package com.iris.foundation.activity;

import com.iris.foundation.utils.LogIRISUtilS;
import com.iris.foundation.utils.SystemBarTintManager;
import com.iris.foundation.view.CProgressDialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 各Activity的基类，用于页面的通用设置。
 * 
 */
public class FoundationActivity extends FragmentActivity implements OnClickListener {

	private SystemBarTintManager mTintManager;
	protected Activity context;
	protected Dialog progressDialog;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		LogIRISUtilS.d("onCreateActivity", "onCreate = "+this.getClass().getSimpleName());
		context = this;
		mTintManager = new SystemBarTintManager(context);
		progressDialog= CProgressDialog.createLoadingDialog(context);
	}

	// 设置系统状态栏与应用程序自定义title一体化
	public void setSysStatusBar(int color) {
		mTintManager.setTintColor(color);
	}

	@Override
	public void onClick(View v) {
	}
	/**
	 * 切换Fragment
	 * 
	 * @param fragment
	 */
	public void switchConent(Fragment fragment,int id) {
		getSupportFragmentManager().beginTransaction().replace(id, fragment).commitAllowingStateLoss();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			LogIRISUtilS.d("onDestroyActivity", "对话框强制关闭"+this.getClass().getSimpleName());
		}
		LogIRISUtilS.d("onDestroyActivity", "onCreate = "+this.getClass().getSimpleName());
		super.onDestroy();
	}
}
