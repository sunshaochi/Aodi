package com.iris.cada.fragment;

import com.iris.cada.activity.BaseActivity;
import com.iris.cada.activity.NewBaseAct;
import com.iris.cada.utils.SharedPreferencesUtils;
import com.iris.cada.view.CProgressDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 各子Fragment的基类，用于页面通用的设置。
 * 
 * @author jiahaoGuo
 * @version 2015-11-5 13:59:09
 */
public abstract class BaseFragment extends Fragment {

	protected SharedPreferencesUtils service;
//	protected BaseActivity activity;
	protected NewBaseAct activity;
	protected Dialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		service = new SharedPreferencesUtils(getActivity());
		activity = (NewBaseAct) getActivity();
		progressDialog = CProgressDialog.createLoadingDialog(getActivity());
	}

	public abstract void refresh();

	@Override
	public void onStart() {
		super.onStart();
	}
}
