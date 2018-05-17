package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.R;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by cg on 2015/9/26.
 */
public class FindTabAdapter extends FragmentPagerAdapter {
	private List<Fragment> list_fragment; // fragment列表
	private List<String> list_Title; // tab名的列表
	private Context mContext;

	public FindTabAdapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title) {
		super(fm);
		this.list_fragment = list_fragment;
		this.list_Title = list_Title;
	}

	@Override
	public Fragment getItem(int position) {
		return list_fragment.get(position);
	}

	@Override
	public int getCount() {
		return list_Title.size();
	}

	// 此方法用来显示tab上的名字
	@Override
	public CharSequence getPageTitle(int position) {
		 return list_Title.get(position % list_Title.size());
	}

	/**
	 * TabLayout 子项布局
	 * 
	 * @param context
	 * @param position
	 *            tab子项id
	 * @param alarmValue
	 *            tab子项对应的提示数值
	 * @return
	 */
	public View getTabItemView(Context context, int position, int alarmValue) {

		View view = LayoutInflater.from(context).inflate(R.layout.item_tab_layout, null);
//
		TextView textView = (TextView) view.findViewById(R.id.tab_layout_title);
		textView.setText(list_Title.get(position));

		// if( alarmValue > 0 ){
		// BadgeView badgeView = new BadgeView(context);
		// badgeView.setBadgeCount(alarmValue);
		// badgeView.setTargetView(textView);
		// badgeView.setGravity(Gravity.RIGHT | Gravity.TOP);
		// }

		return null;
	}
}