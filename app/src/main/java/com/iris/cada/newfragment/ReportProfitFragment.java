package com.iris.cada.newfragment;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.ReportSecondActivity;
import com.iris.cada.adapter.FindTabAdapter;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.utils.DateAndTimeUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.cada.R;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 盈利界面2017/8/7 16:18修改
 * 
 * @ClassName: ReportFragment
 * @Description: TODO()
 * @author iris-gjh
 * @param <FindTabAdapter>
 * @date 2016年10月10日 下午5:46:41 报表盈利页面
 */

public class ReportProfitFragment extends BaseFragment implements OnClickListener {
	private FindTabAdapter adapter;
	private DiagDetailsFragment diagdetailsfragment;
	private LinearLayout star_time_linner, end_time_linner;
	private TextView star_data, end_data;
	private Date mStartDate;
	private Date mEndDate;
	private AddTodoPickerViewUtils addTodoPickerViewUtils;
	private ReportFineFragment reportFineFragment;
	private ReportCarloanFragment reportcarloanFragment;
	private ReportNewInsuranceFragment reportnewinFragment;
	private ReportExtendedWarrantyFragment reportextendFragment;
	private ReportSubstitutionFragment reportsubstiFragment;
	//新增上牌界面
	private ReportShangPaiFragment reportShangPaiFragment;

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_report, container, false);
		initView(view);
		bindata();
		initData();
		// datas=new ArrayList<String>();
		// datas.add("111");
		// datas.add("123");
		// fragment_diag_lv.setAdapter(new
		// DiagDetailsAdapter(getActivity(),datas));
		return view;
	}

	private void bindata() {

	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_findfragment_title);
		final ViewPager vp = (ViewPager) view.findViewById(R.id.vp_FindFragment_pager);
		star_time_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_star_time_linner);
		end_time_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_end_time_linner);
		star_data = (TextView) view.findViewById(R.id.fragment_report_fine_starname);
		end_data = (TextView) view.findViewById(R.id.fragment_report_fine_enddata);
		star_time_linner.setOnClickListener(this);
		end_time_linner.setOnClickListener(this);
		List<String> strings = Arrays.asList(new String[] { "精品", "车贷", "新保", "延保", "置换" ,"上牌"});
		reportFineFragment = new ReportFineFragment();
		reportcarloanFragment = new ReportCarloanFragment();
		reportnewinFragment = new ReportNewInsuranceFragment();
		reportextendFragment = new ReportExtendedWarrantyFragment();
		reportsubstiFragment = new ReportSubstitutionFragment();
		//上牌界面
		reportShangPaiFragment = new ReportShangPaiFragment();
		List<Fragment> frags = Arrays.asList(new Fragment[] { reportFineFragment, reportcarloanFragment,
				reportnewinFragment, reportextendFragment, reportsubstiFragment ,reportShangPaiFragment});
		adapter = new FindTabAdapter(getChildFragmentManager(), frags, strings);
		vp.setAdapter(adapter);
		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
		tabLayout.setupWithViewPager(vp);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_report_fine_star_time_linner:
			//showDateDialog(star_data, true);
			break;
		case R.id.fragment_report_fine_end_time_linner:
			//showDateDialog(end_data, false);
			break;

		default:
			break;
		}

	}

	/** 显示日期选择器 */
	private void showDateDialog(final TextView tvTime, final boolean isStart) {
		TimePickerViewDialog pvTimeDialog = addTodoPickerViewUtils
				.initDateNoDayPickerView(isStart ? "请选择开始时间" : "请选择结束时间").getPvTime();
		pvTimeDialog.getDialog().show();

		pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				String strDate = null;
				if (isStart) {
					strDate = addTodoPickerViewUtils.getDateNoDay(date) + "-01";
					mStartDate = DateAndTimeUtils.getDateTimeForStr("yyyy-MM-dd", strDate);
				} else {
					strDate = DateAndTimeUtils.getLastDateOfMonth(date);
					mEndDate = DateAndTimeUtils.getDateTimeForStr("yyyy-MM-dd", strDate);
				}

				if (isStart) {
					tvTime.setText(strDate);
					String strEndDate = DateAndTimeUtils.getLastDateOfMonth(mStartDate);
					mEndDate = DateAndTimeUtils.getDateTimeForStr("yyyy-MM-dd", strEndDate);
					end_data.setText(strEndDate);
				} else {
					if(DateAndTimeUtils.isEqual(mStartDate, mEndDate)){
						tvTime.setText(strDate);
					}else if (!DateAndTimeUtils.isBefore(mStartDate, mEndDate)) {
						ToastUtils.showMyToast(getActivity(), "“结束时间”不可小于“开始时间”");
						return;
					} else {
						tvTime.setText(strDate);
					}
				}
				// getProfitInfo();

			}

			@Override
			public void onCancel() {

			}
		});
	}

	private void initData() {
		addTodoPickerViewUtils = new AddTodoPickerViewUtils(getActivity());

		// 日期默认设置为本月日期
		mStartDate = DateAndTimeUtils.getCurrentMonthOfFirstDay();
		star_data.setText(DateAndTimeUtils.getTimeForDate("yyyy-MM-dd", mStartDate));

		mEndDate = DateAndTimeUtils.getDateTimeForStr("yyyy-MM-dd", DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"));
		end_data.setText(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"));

		// // Car mode demo data
		// mCarModeList = new ArrayList<String>();
		// mCarModeList.add("All");
		// mCarModeList.add("A3 SportBack e-tron");
		//
		// txtCarMode.setText(mCarModeList.get(0));

		// getProfitInfo();
	}
}
