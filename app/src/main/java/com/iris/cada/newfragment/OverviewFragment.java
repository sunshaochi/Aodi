package com.iris.cada.newfragment;

import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.MainActivity;
import com.iris.cada.activity.NewSettingAct;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.utils.FragmentUtils;
import com.iris.cada.R;

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
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 概览
 * 
 * @author LFJ
 *
 */
public class OverviewFragment extends BaseFragment implements OnClickListener {

	private ImageView mSettingImg = null;
	private ImageView mFunctionImg = null;
	
	private boolean isOperative = true;
	
	private OverviewOperativeFragment overviewOperativeFragment = null;
	private OverviewProfitFragment overviewprofitFragment = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_over, container, false);

		initView(view);
		initData();
		return view;
	}

	private void initView(View view) {
		overviewOperativeFragment = new OverviewOperativeFragment();
		overviewprofitFragment = new OverviewProfitFragment();
		
		mSettingImg = (ImageView) view.findViewById(R.id.overview_title_setting);
		mSettingImg.setOnClickListener(this);
		
		

		mFunctionImg = (ImageView) view.findViewById(R.id.overview_title_function_icon);
		mFunctionImg.setOnClickListener(this);

		FragmentUtils.FragmentShowHide(R.id.overview_fragment_layout, null , overviewOperativeFragment, getChildFragmentManager());
	}

	private void initData() {
	}

	@Override
	public void refresh() {
		
	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.overview_title_setting:
			// 跳转侧滑设置页面
//			((MainActivity) getActivity()).getSlidingMenu().toggle();
			Intent intent=new Intent(getActivity(),NewSettingAct.class);
			  startActivity(intent);
			break;
		case R.id.overview_title_function_icon:
			if (isOperative) {
				
				String premission = ProfitApplication.loginBackInfo.getPermission();
				if(!TextUtils.isEmpty(premission) && "仅运营权限".equals(premission)){
					Toast.makeText(getActivity(), "没有足够权限", Toast.LENGTH_LONG).show();
					return;
				}
				
				isOperative = false;
				mFunctionImg.setImageResource(R.drawable.btn_yingli);//盈利
				FragmentUtils.FragmentShowHide(R.id.overview_fragment_layout, overviewOperativeFragment , overviewprofitFragment, getChildFragmentManager());
			} else {
				isOperative = true;

				mFunctionImg.setImageResource(R.drawable.btn_yunying);
				FragmentUtils.FragmentShowHide(R.id.overview_fragment_layout, overviewprofitFragment , overviewOperativeFragment, getChildFragmentManager());
			}
			break;
			
		default:
			break;
		}
	}

}