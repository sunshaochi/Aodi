package com.iris.cada.newfragment;

import java.util.Arrays;
import java.util.List;

import com.iris.cada.adapter.ViewPagerAdapter;
import com.iris.cada.fragment.BaseFragment;
import com.iris.foundation.view.ViewPagerIndex;
import com.iris.cada.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
/**
 * 
 * @author CK
 *  运营报表里面的展厅页面
 */

public class ExhibitionHallFragment extends BaseFragment{
	private ListView lv;
	private List<String> datas;
	private ViewPagerIndex vp_index;

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_oper_population, container, false);
		initView(view);
		bindata();
		return view;
	}

	private void bindata() {
		// TODO Auto-generated method stub
		
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
	    ViewPager vp = (ViewPager) view.findViewById(R.id.vp_FindFragment_pager);
		List<Fragment> frags = Arrays.asList(new Fragment[] { new OperatePopulationDataFragment("展厅"),new ExhibitionHallDataFragment("展厅"),new AdcDataFragment("展厅")});
		vp_index = (ViewPagerIndex)view.findViewById(R.id.vp_FindFragment_pager_index);
		vp_index.setData(new int[frags.size()], "#DDDDDD", "#ffcc0000");
		vp_index.setSelect(0);
		vp.setAdapter(new ViewPagerAdapter(getChildFragmentManager(),frags));
		vp.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				vp_index.setSelect(position);
			}
			
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
}
