package com.iris.cada.newfragment;

import java.util.Arrays;
import java.util.List;

import com.iris.cada.adapter.FindTabAdapter;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.view.CustomViewPager;
import com.iris.cada.R;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 *报表运营主页面(总体，展示，ADC)
 */
public class ReportOperateFragment extends BaseFragment {
	private FindTabAdapter tabadapter;
	private ViewPager vp;

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_report_operat, container, false);
		initView(view);

		// fragment_diag_lv.setAdapter(new
		// DiagDetailsAdapter(getActivity(),datas));
		bindata();
		return view;
	}

	private void bindata() {
		// TODO Auto-generated method stub

	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_findfragment_title);
		List<String> strings = Arrays.asList(new String[] { "总体", "展厅", "ADC" });
		 vp = (ViewPager) view.findViewById(R.id.vp_report_fragment_pager);
		//添加fragment到viewpager
		List<Fragment> frags = Arrays.asList(new Fragment[] { new OperatePopulationFragement(),new ExhibitionHallFragment(),new AdcFragment()});
		tabadapter = new FindTabAdapter(getChildFragmentManager(), frags, strings);
		vp.setAdapter(tabadapter);
	    //vp.setOffscreenPageLimit(2);
		//vp.setScanScroll(false);
		tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
		tabLayout.setTabMode(TabLayout.MODE_FIXED);
		tabLayout.setupWithViewPager(vp);
	}

}
