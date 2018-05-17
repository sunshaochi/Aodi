package com.iris.cada.newfragment;

import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.MainActivity;
import com.iris.cada.activity.NewSettingAct;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.utils.FragmentUtils;
import com.iris.cada.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 监控
 * 
 * @author LFJ
 *
 */
public class MonitorFragment extends BaseFragment implements OnClickListener {

	private ImageView mSettingImg = null;
	private ImageView mFunctionImg = null;

	private boolean isOperative = true;
	private String premission;
	private MonitorOperativeFragment monitorOperativeFragment = null;
	private MonitorProfitFragment monitorProfitFragment = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_over, container, false);
		initView(view);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		premission = ProfitApplication.loginBackInfo.getPermission();
		if(TextUtils.isEmpty(premission)){
			premission="总经理";
		}
		FragmentUtils.FragmentShowHide(R.id.overview_fragment_layout, null, monitorOperativeFragment,
				getChildFragmentManager());
	}

	private void initView(View view) {
		monitorOperativeFragment = new MonitorOperativeFragment();
		monitorProfitFragment = new MonitorProfitFragment();

		mSettingImg = (ImageView) view.findViewById(R.id.overview_title_setting);
		mSettingImg.setOnClickListener(this);

		mFunctionImg = (ImageView) view.findViewById(R.id.overview_title_function_icon);
		mFunctionImg.setOnClickListener(this);

//		FragmentUtils.FragmentShowHide(R.id.overview_fragment_layout, null, monitorOperativeFragment,
//				getChildFragmentManager());
	}

	private void initData() {
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

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
				
//				String premission = ProfitApplication.loginBackInfo.getPermission();
				if("仅运营权限".equals(premission)||"销售半权限".equals(premission)){
					Toast.makeText(getActivity(), "没有足够权限", Toast.LENGTH_LONG).show();
					return;
				}
				
				isOperative = false;

				mFunctionImg.setImageResource(R.drawable.btn_yingli);
				FragmentUtils.FragmentShowHide(R.id.overview_fragment_layout, monitorOperativeFragment,
						monitorProfitFragment, getChildFragmentManager());
			} else {
				isOperative = true;

				mFunctionImg.setImageResource(R.drawable.btn_yunying);
				FragmentUtils.FragmentShowHide(R.id.overview_fragment_layout, monitorProfitFragment,
						monitorOperativeFragment, getChildFragmentManager());
			}
			break;
		default:
			break;
		}
	}

}
