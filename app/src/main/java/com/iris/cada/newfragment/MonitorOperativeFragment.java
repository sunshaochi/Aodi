package com.iris.cada.newfragment;

import java.util.Arrays;
import java.util.List;

import com.iris.cada.adapter.FindTabAdapter;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.R;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 监控-运营
 * 
 * @author LFJ
 *
 */ 

public class MonitorOperativeFragment extends BaseFragment implements OnTabSelectedListener{
	
	private TabLayout mTabLayout = null;
	private ViewPager mViewPager = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_monitor_child_layout, container, false);
		initView(view);
		return view;
	}
	
	private void initView(View view){
		mTabLayout = (TabLayout) view.findViewById(R.id.monitor_child_tablayout);
		mViewPager = (ViewPager) view.findViewById(R.id.monitor_child_viewpager);
		
		List<String> tabTitleList = Arrays.asList(new String[] { "潜客渠道", "过程监控" });
		List<Fragment> fragmentList = Arrays.asList(new Fragment[] { new MonitorOperativeChannelFragment(), new MonitorOperativeProcessFragment() });

		FindTabAdapter adapter = new FindTabAdapter(getChildFragmentManager(), fragmentList, tabTitleList);
		mViewPager.setAdapter(adapter);
		mTabLayout.setTabMode(TabLayout.MODE_FIXED);
		mTabLayout.setupWithViewPager(mViewPager);
		// 当滑动到 线索池 时显示加号
		mTabLayout.setOnTabSelectedListener(this);
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0) {
		// TODO Auto-generated method stub
		
	}

}
