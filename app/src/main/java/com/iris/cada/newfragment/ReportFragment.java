package com.iris.cada.newfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.MainActivity;
import com.iris.cada.activity.NewSettingAct;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.R;
/**
 * 
 * @author CK
 *  报表页面的主fragment 
 */
public class ReportFragment extends BaseFragment implements View.OnClickListener {
	private boolean isOperative = true;
	private ImageView mFunctionImg = null;
	private ImageView mSettingImg = null;

	public static void FragmentShowHide(int paramInt, Fragment paramFragment, FragmentManager paramFragmentManager) {
		FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
		if (!paramFragment.isAdded()) {
			localFragmentTransaction.add(paramInt, paramFragment).commit();
			return;
		}
		if (paramFragment.isHidden()) {
			localFragmentTransaction.show(paramFragment).commit();
			return;
		}
		localFragmentTransaction.hide(paramFragment).commit();
	}

	private void initData() {
	}

	private void initView(View paramView) {
		mSettingImg = (ImageView) paramView.findViewById(R.id.overview_title_setting);
		mSettingImg.setOnClickListener(this);
		mFunctionImg = (ImageView) paramView.findViewById(R.id.overview_title_function_icon);
		mFunctionImg.setOnClickListener(this);
		ReportOperateFragment localReportOperateFragment = new ReportOperateFragment();
		new OverviewOperativeFragment();
		FragmentShowHide(R.id.overview_fragment_layout , localReportOperateFragment, getChildFragmentManager());
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.overview_title_setting:
			//跳转侧滑设置页面
//			((MainActivity) getActivity()).getSlidingMenu().toggle();
			Intent intent=new Intent(getActivity(),NewSettingAct.class);
			  startActivity(intent);
		break;
		case R.id.overview_title_function_icon:
			if (this.isOperative) {
				
				String premission = ProfitApplication.loginBackInfo.getPermission();
				if(!TextUtils.isEmpty(premission) && "仅运营权限".equals(premission)){
					Toast.makeText(getActivity(), "没有足够权限", Toast.LENGTH_LONG).show();
					return;
				}
				
				this.isOperative = false;
				this.mFunctionImg.setImageResource(R.drawable.btn_yingli);
				FragmentShowHide(R.id.overview_fragment_layout , new ReportProfitFragment(), getChildFragmentManager());
				return;
			}
			this.isOperative = true;
			this.mFunctionImg.setImageResource(R.drawable.btn_yunying);
			FragmentShowHide(R.id.overview_fragment_layout , new ReportOperateFragment(), getChildFragmentManager());
			break;
		}
		
	}

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.fragment_over, paramViewGroup, false);
		initView(localView);
		initData();
		return localView;
	}

	public void refresh() {
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\反编译\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name: com.iris.cada.newfragment.ReportFragment JD-Core Version:
 * 0.6.2
 */