package com.iris.cada.newfragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.print.pdf.PrintedPdfDocument;
import android.support.v4.os.IResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.bigkoo.pickerview.TimePickerViewDialog.OnTimeSelectListener;
import com.google.gson.Gson;
import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.MainActivity;
import com.iris.cada.activity.NewSettingAct;
import com.iris.cada.entity.IViewFirst;
import com.iris.cada.entity.OverviewOperativeInfo;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.service.IRISService;
import com.iris.cada.utils.DateAndTimeUtils;
import com.iris.cada.utils.StringUtils;
import com.iris.cada.utils.TimeRefreshUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.view.CProgressDialog;
import com.iris.cada.view.KpiDialog;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.cada.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import slidingmenu.lib.SlidingMenu;

/** 首页 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener {
	private List<String> datas;
	private RelativeLayout end_data;
	private TextView end_time;
	private ImageView img_setting;
	private ListView lv;
	private RelativeLayout star_data;
	private TextView start_time;
	private TextView tv_home_title;
	private Dialog progressDialog;
	private String star_tm, end_tm;
	private TextView chengjiao_tv, zongjiao_tv, awy_tv, maoli_tv;
	private TextView turnover_time_tv, retail_time_tv, gross_time_tv, revenue_time_tv;
	private AddTodoPickerViewUtils addTodoPickerViewUtils;
	private Date mStartDate;
	private Date mEndDate;
	private LinearLayout ll_cj, ll_jc, ll_sr, ll_ml;

	private void getProfitInfo() {
		progressDialog.show();
		// Toast.makeText(getActivity(),
		// start_time.getText().toString().trim()+end_time.getText().toString().trim(),
		// Toast.LENGTH_SHORT).show();
		ProfitApplication.profitNetService.getiviewFirstHomeServlet(start_time.getText().toString().trim(),
				end_time.getText().toString().trim(), handler);
	}

	private void initView(View paramView) {

		// this.addTodoPickerViewUtils = new
		// AddTodoPickerViewUtils(getActivity());
		this.img_setting = ((ImageView) paramView.findViewById(R.id.home_title_setting));
		this.tv_home_title = ((TextView) paramView.findViewById(R.id.home_title_name));
		this.star_data = ((RelativeLayout) paramView.findViewById(R.id.fragment_home_star_data_rela));
		this.end_data = ((RelativeLayout) paramView.findViewById(R.id.fragment_home_end_data_rela));
		this.start_time = ((TextView) paramView.findViewById(R.id.overview_top_start_time));
		this.end_time = ((TextView) paramView.findViewById(R.id.overview_top_end_time));

		ll_cj = (LinearLayout) paramView.findViewById(R.id.ll_cj);
		ll_jc = (LinearLayout) paramView.findViewById(R.id.ll_jc);
		ll_sr = (LinearLayout) paramView.findViewById(R.id.ll_sr);
		ll_ml = (LinearLayout) paramView.findViewById(R.id.ll_ml);

		chengjiao_tv = (TextView) paramView.findViewById(R.id.fragment_home_chengjiao_tv);
		zongjiao_tv = (TextView) paramView.findViewById(R.id.fragment_home_zongjiao_tv);
		awy_tv = (TextView) paramView.findViewById(R.id.fragment_home_awy_tv);// 销售综合收入
		maoli_tv = (TextView) paramView.findViewById(R.id.fragment_home_maoli_tv);// 销售综合毛利
		turnover_time_tv = (TextView) paramView.findViewById(R.id.home_timeTurnover);
		retail_time_tv = (TextView) paramView.findViewById(R.id.home_timeRetail);
		gross_time_tv = (TextView) paramView.findViewById(R.id.home_timeGross);
		revenue_time_tv = (TextView) paramView.findViewById(R.id.home_timeRevenue);
		star_tm = start_time.getText().toString();
		end_tm = end_time.getText().toString();
		progressDialog = CProgressDialog.createLoadingDialog(getActivity());

		this.star_data.setOnClickListener(this);
		this.end_data.setOnClickListener(this);
		this.img_setting.setOnClickListener(this);
		ll_cj.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				showdialog(0);
				return true;// 不加短的動作
			}

		});

		ll_jc.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				showdialog(1);
				return true;// 不加短的動作
			}

		});

		ll_sr.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				showdialog(2);
				return true;// 不加短的動作
			}

		});

		ll_ml.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				showdialog(3);
				return true;// 不加短的動作
			}

		});
	}

	protected void showdialog(int type) {// 彈出對話框
		// TODO Auto-generated method stub
		List<String>list=new ArrayList<String>();
		if(type==0){
			list.add("成交数&当期新增加定单量");
		}else if(type==1){
			list.add("总交车数&当期交车总数");
		}else if(type==2){
			list.add("销售综合收入&=当期开票价格总额+衍生业务收入总计");
		}else if(type==3){
			list.add("销售综合毛利&=进销差(GP1)+销售返利总计+衍生业务毛利总计");
		}
		KpiDialog kpidialog = new KpiDialog(getContext(),list).builder();
		kpidialog.show();
		kpidialog.setCanceledOnTouchOutside(true);

	}

	/** 显示日期选择器 */
	private void showDateDialog(final TextView tvTime, final boolean isStart) {//
		TimePickerViewDialog pvTimeDialog = addTodoPickerViewUtils.initDatePickerView(isStart ? "请选择开始时间" : "请选择结束时间")
				.getPvTime();
		pvTimeDialog.getDialog().show();// initDateNoDayPickerView

		pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				String strDate = null;
				if (isStart) {
					// strDate = addTodoPickerViewUtils.getDateNoDay(date) +
					// "-01";
					strDate = addTodoPickerViewUtils.getDate(date);// 开始日期
					mStartDate = DateAndTimeUtils.getDateTimeForStr("yyyy-MM-dd", strDate);
				} else {
					// strDate = DateAndTimeUtils.getLastDateOfMonth(date);
					strDate = addTodoPickerViewUtils.getDate(date);// 结束日期
					mEndDate = DateAndTimeUtils.getDateTimeForStr("yyyy-MM-dd", strDate);
				}

				if (isStart) {
					tvTime.setText(strDate);
					String strEndDate = DateAndTimeUtils.getLastDateOfMonth(mStartDate);
					mEndDate = DateAndTimeUtils.getDateTimeForStr("yyyy-MM-dd", strEndDate);
					end_time.setText(strEndDate);
				} else {
					if (DateAndTimeUtils.isEqual(mStartDate, mEndDate)) {
						tvTime.setText(strDate);
					} else if (!DateAndTimeUtils.isBefore(mStartDate, mEndDate)) {
						ToastUtils.showMyToast(getActivity(), "“结束时间”不可小于“开始时间”");
						return;
					} else {
						tvTime.setText(strDate);
					}
				}
				TimeRefreshUtils.sendTimeRefreshMsg(getContext(), mStartDate, mEndDate);
				getProfitInfo();
			}

			@Override
			public void onCancel() {

			}
		});
	}

	private void initData() {
		addTodoPickerViewUtils = new AddTodoPickerViewUtils(getActivity());

		// 日期默认设置为本月日期
		if (null == ProfitApplication.mStartDate) {
			mStartDate = DateAndTimeUtils.getCurrentMonthOfFirstDay();
		} else {
			mStartDate = ProfitApplication.mStartDate;
		}
		start_time.setText(DateAndTimeUtils.getTimeForDate("yyyy-MM-dd", mStartDate));

		if (null == ProfitApplication.mEndDate) {
			mEndDate = DateAndTimeUtils.getDateTimeForStr("yyyy-MM-dd", DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"));
			end_time.setText(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"));
		} else {
			mEndDate = ProfitApplication.mEndDate;
			end_time.setText(DateAndTimeUtils.getTimeForDate("yyyy-MM-dd", mEndDate));
		}

		// // Car mode demo data
		// mCarModeList = new ArrayList<String>();
		// mCarModeList.add("All");
		// mCarModeList.add("A3 SportBack e-tron");
		//
		// txtCarMode.setText(mCarModeList.get(0));

		getProfitInfo();
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.fragment_home_star_data_rela:
			showDateDialog(start_time, true);
			break;
		case R.id.fragment_home_end_data_rela:
			showDateDialog(end_time, false);
			break;
		case R.id.home_title_setting:
			// 跳转侧滑设置页面
//			((MainActivity) getActivity()).getSlidingMenu().toggle();
			  Intent intent=new Intent(getActivity(),NewSettingAct.class);
			  startActivity(intent);
			break;

		}

	}

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.fragment_home_pager, paramViewGroup, false);
		initView(localView);
		initData();
		return localView;
	}

	public void refresh() {
	}

	Handler handler = new MyHandler(this) {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
			case ProfitApplication.DATA_SUC:
				showhomedata(msg);
				break;
			case ProfitApplication.DATA_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				break;
			case ProfitApplication.SERVER_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_net));
				break;
			}
		}

	};

	private void showhomedata(Message msg) {
		try {
			String strMsg = (String) msg.obj;
			IViewFirst iViewFirstdata = new Gson().fromJson(strMsg, IViewFirst.class);
			showOperativeInfo(iViewFirstdata);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 数据展示。
	private void showOperativeInfo(IViewFirst iViewFirstdata) {
		if (iViewFirstdata != null) {
			// Toast.makeText(getActivity(), iViewFirstdata.getGross(),
			// Toast.LENGTH_SHORT).show();
			if (!TextUtils.isEmpty(ProfitApplication.loginBackInfo.getJXSName())) {
				tv_home_title.setText(ProfitApplication.loginBackInfo.getJXSName());
			}

			chengjiao_tv.setText(iViewFirstdata.getTurnover());
			zongjiao_tv.setText(iViewFirstdata.getRetail());
			String premission = ProfitApplication.loginBackInfo.getPermission();
			if (!TextUtils.isEmpty(premission) && "仅运营权限".equals(premission)) {
				awy_tv.setText("-");
				maoli_tv.setText("-");
			} else {
				awy_tv.setText(StringUtils.intDivTo0(iViewFirstdata.getGross()));// 销售综合收入
				maoli_tv.setText(StringUtils.intDivTo0(iViewFirstdata.getRevenue()));// 销售综合毛利
			}
			turnover_time_tv.setText("更新于" + iViewFirstdata.getTimeTurnover());// 成交数更新时间
			retail_time_tv.setText("更新于" + iViewFirstdata.getTimeRetail());
			gross_time_tv.setText("更新于" + iViewFirstdata.getTimeGross());
			revenue_time_tv.setText("更新于" + iViewFirstdata.getTimeRevenue());
		}
	}
}
