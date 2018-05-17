package com.iris.cada.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyViewPagerAdapter extends PagerAdapter {

	private List<View> viewList;

	public MyViewPagerAdapter(List<View> viewList, List<String> titleList) {
		this.viewList = viewList;
	}

	public void setData(List<View> viewList) {
		this.viewList = viewList;
		this.notifyDataSetChanged();
	}

	// 返回所有视图的数量
	@Override
	public int getCount() {
		return viewList.size();
	}

	// 判断视图是否由对象产生
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	// 实例化页面
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(viewList.get(position));
		return viewList.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (getCount() != 2) {
			container.removeView(viewList.get(position));
		}
	}
}
