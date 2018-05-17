package com.iris.cada.activity;

import com.iris.cada.utils.SharedPreferencesUtils;
import com.iris.cada.view.CProgressDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * 各Activity的基类，用于页面的通用设置。
 * 
 * @author jiahaoGuo
 * @version 2015-11-5 11:42:00 1.0
 *  原来是继承 SlidingFragmentActivity 来实现侧滑但是现在再继承SlidingFragmentActivity会无响应
 */
public class BaseActivity extends FragmentActivity implements OnClickListener {

	protected SharedPreferencesUtils service;
	protected Dialog progressDialog;

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		service = new SharedPreferencesUtils(this);
		progressDialog = CProgressDialog.createLoadingDialog(this);
	}

	@Override
	public void onClick(View v) {
	}

	/**
	 * 判断当前是否车型模式
	 * 
	 * @param tv
	 * @param carModel
	 */
	public void setListViewTitle(TextView tv, String carModel) {
		if (carModel.equals("是")) {
			tv.setText("车型");
		} else {
			tv.setText("顾问");
		}
	}
}
