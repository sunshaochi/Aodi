package com.iris.cada.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> list_fragment; // fragment列表
	private Context mContext;

	public ViewPagerAdapter(FragmentManager fm, List<Fragment> list_fragment) {
		super(fm);
		this.list_fragment = list_fragment;
	
	}
	@Override
	public Fragment getItem(int arg0) {
		return list_fragment.get(arg0);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_fragment.size();
	}
}