package com.iris.foundation.activity;

import com.iris.foundation.utils.LogIRISUtilS;
import com.iris.foundation.view.CProgressDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 各子Fragment的基类，用于页面通用的设置。
 * 
 */
public abstract class FoundationFragment extends Fragment {

	protected FoundationActivity context;
	protected Dialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogIRISUtilS.d("onCreatefragment", "onCreate = "+this.getClass().getSimpleName());
		context = (FoundationActivity) getActivity();
		progressDialog = CProgressDialog.createLoadingDialog(context);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			LogIRISUtilS.d("onDestroyfragment", "对话框强制关闭，"+this.getClass().getSimpleName());
		}
		super.onDestroy();
		LogIRISUtilS.d("onDestroyfragment", "onDestroy = "+this.getClass().getSimpleName());
	}
}
