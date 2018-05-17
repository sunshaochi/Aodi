package com.iris.cada.newfragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iris.cada.ProfitApplication;
import com.iris.cada.Statcode;
import com.iris.cada.activity.SeparateAct;
import com.iris.cada.adapter.ViewPagerAdapter;
import com.iris.cada.fragment.NewBaseFrag;
import com.iris.cada.utils.DateAndTimeUtils;
import com.iris.cada.utils.TimeRefreshUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.utils.WebUtils;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.foundation.view.ViewPagerIndex;
import com.iris.cada.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReprotKcFrag extends NewBaseFrag {
	@ViewInject(R.id.rl_select)
	private RelativeLayout rl_select;
	@ViewInject(R.id.vp)
	private ViewPager vp;
	@ViewInject(R.id.vpdex)
	private ViewPagerIndex vpdex;
	@ViewInject(R.id.overview_top_start_time)
	private TextView tv_start;// 开始时间
	@ViewInject(R.id.overview_top_end_time)
	private TextView tv_end;// 结束时间
	@ViewInject(R.id.tv_udtime)
	private TextView tv_udtime;

	private AddTodoPickerViewUtils addPickerViewUtils;
	private String mTimeFormat;
	private String stime;// 选择的开始时间
	private Date startdate;// 选择的开始时间锉求结束时间
	private String etime;// 计算出的结束时间
	private Date enddate;// 结束时间锉和当前时间比较
	private Gson gson;
	private List<Fragment>frags;
	private RepKcfFra repkcffra;
	private RepKcsFra repkcsfra;
	
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {			
			super.handleMessage(msg);
			switch (msg.what) {
			case ProfitApplication.DATA_SUC://获取更新时间成功
				showUdtime(msg);
				break;		
			case ProfitApplication.DATA_FAILED://获取数据失败
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				break;		

			case ProfitApplication.SERVER_FAILED://连接服务器失败
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_net));
				break;
			
			}
		}

		
	};
	
	private void showUdtime(Message msg) {
		// TODO Auto-generated method stub
		String mess=(String) msg.obj;
		gson=new Gson();
		try {
			String time=gson.fromJson(new JSONObject(mess).getString("data"), String.class);
			if(!TextUtils.isEmpty(time)){
				tv_udtime.setText("*库存数据更新于："+time);
			}
		}		
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.fra_reprotkc, null);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rl_select.setVisibility(View.GONE);
		mTimeFormat = "yyyy-MM-dd";
		addPickerViewUtils = new AddTodoPickerViewUtils(getActivity());// 工具内时间弹出框
		tv_start.setText(WebUtils.getStart());
		tv_end.setText(WebUtils.getend());
		startdate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, tv_start.getText().toString());// 赋值后的开始时间
        
		getUdtime();//获取更新时间
		frags=new ArrayList<Fragment>();
		repkcffra=new RepKcfFra();
		repkcsfra=new RepKcsFra();
		frags.add(repkcffra);
		frags.add(repkcsfra);
		
//		List<Fragment> frags = Arrays.asList(new Fragment[] { new RepKcfFra(), new RepKcsFra() });
		
		vpdex.setData(new int[frags.size()], "#DDDDDD", "#ffcc0000");
		vpdex.setSelect(0);
		vp.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), frags));

		vp.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				vpdex.setSelect(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
    /**获取更新时间**/
	private void getUdtime() {
		// TODO Auto-generated method stub
		ProfitApplication.profitNetService.getUdtime(handler);				
	}

	@OnClick({ R.id.overview_top_start_time_layout, R.id.overview_top_end_time_layout })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.overview_top_start_time_layout:// 开始时间
			showDateDialog(true);// 选择开始时间
			break;
		case R.id.overview_top_end_time_layout:// 结束时间
			showDateDialog(false);// 选择开始时间
			break;

		}
	}

	private void showDateDialog(final boolean b) {
		// TODO Auto-generated method stub
		TimePickerViewDialog pvTimeDialog = addPickerViewUtils.initDatePickerView(b ? "请选择开始时间" : "请选择结束时间")
				.getPvTime();
		pvTimeDialog.getDialog().show();
		pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				// TODO Auto-generated method stub
				if (b) {
					if (!DateAndTimeUtils.isBefore(date, DateAndTimeUtils.getCurrentDate(mTimeFormat))) {// 开始时间比当前时间大
						ToastUtils.showMyToast(getActivity(), "开始时间不可大于当前时间");
						return;
					} else {// 开始时间比当前时间小
						stime = addPickerViewUtils.getDate(date);// 开始时间
						tv_start.setText(stime);

						startdate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, stime);// 开始时间锉
						ProfitApplication.mStartDate = startdate;
						etime = DateAndTimeUtils.getLastDateOfMonth(startdate);
						enddate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, etime);

						if (!DateAndTimeUtils.isBefore(enddate, DateAndTimeUtils.getCurrentDate(mTimeFormat))) {
							tv_end.setText(DateAndTimeUtils.getCurrentTime(mTimeFormat));// 当前时间
							// ProfitApplication.mEndDate=DateAndTimeUtils.getCurrentDate(mTimeFormat);
							TimeRefreshUtils.sendTimeRefreshMsg(getContext(), startdate,
									DateAndTimeUtils.getCurrentDate(mTimeFormat));
							repkcffra.update();//通知更新界面
							repkcsfra.update();//通知更新界界面
						} else {
							tv_end.setText(etime);
							// ProfitApplication.mEndDate=enddate;
							TimeRefreshUtils.sendTimeRefreshMsg(getContext(), startdate, enddate);
							repkcffra.update();//通知更新界面
							repkcsfra.update();//通知更新界界面
						}
					}

				} else {
					if (!DateAndTimeUtils.isBefore(date, DateAndTimeUtils.getCurrentDate(mTimeFormat))) {// 结束时间比当前时间大
						return;
					} else {
						etime = addPickerViewUtils.getDate(date);// 结束时间
						enddate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, etime);// 结束时间锉
						if (DateAndTimeUtils.isEqual(startdate, enddate)) {// 结束时间等于开始时间
							tv_end.setText(etime);
							// ProfitApplication.mEndDate=enddate;
							TimeRefreshUtils.sendTimeRefreshMsg(getContext(), startdate, enddate);
							repkcffra.update();//通知更新界面
							repkcsfra.update();//通知更新界界面
						} else if (!DateAndTimeUtils.isBefore(startdate, enddate)) {// 结束时间小于开始时间
							ToastUtils.showMyToast(getActivity(), "“结束时间”不可小于“开始时间”");
							return;
						} else {// 结束时间大于开始时间小于当前时间
							tv_end.setText(etime);
							// ProfitApplication.mEndDate=enddate;
							TimeRefreshUtils.sendTimeRefreshMsg(getContext(), startdate, enddate);
							repkcffra.update();//通知更新界面
							repkcsfra.update();//通知更新界界面
						}
					}

				}

			}

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub

			}
		});

	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if(hidden){
			
		}else {//重新可见
			upData();
		}
	}

	public void upData() {
		if (tv_start.getText().equals(WebUtils.getStart()) && tv_end.getText().equals(WebUtils.getend())) {
           //如果开始时间和结束时间都没变化什么操作都没做，否则通知两个子fragment刷新界面
		} else {
			tv_start.setText(WebUtils.getStart());
			tv_end.setText(WebUtils.getend());
			getUdtime();//获取更新时间
			repkcffra.update();
			repkcsfra.update();
		}

	}

}
