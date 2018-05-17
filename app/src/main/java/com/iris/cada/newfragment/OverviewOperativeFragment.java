package com.iris.cada.newfragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.BaseActivity;
import com.iris.cada.entity.OverviewCustomerInfo;
import com.iris.cada.entity.OverviewOperativeInfo;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.utils.DateAndTimeUtils;
import com.iris.cada.utils.StringUtils;
import com.iris.cada.utils.TimeRefreshUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.view.EditDialogUtils;
import com.iris.cada.view.KpiDialog;
import com.iris.cada.view.CustomSheetDialog.OnListViewItemClick;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.cada.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 概览-运营
 * 
 * @author LFJ
 *原来是基础basefragment
 */
public class OverviewOperativeFragment extends BaseFragment implements OnClickListener {

	private RelativeLayout layoutStartTime = null;
	private TextView txtStartTime = null;
	private RelativeLayout layoutEndTime = null;
	private TextView txtEndTime = null;
	private LinearLayout layoutCarMode = null;
	private TextView txtCarMode = null;

	private CheckBox chkContrastMode = null;
	private TextView txtPercent = null;
	private TextView txtReducedValue = null;
	private TextView txtNewCustomer = null;
	private TextView txtFirstInTryNum = null;
	private TextView txtQuoteNum = null;
	private TextView txtDealNum = null;

	private TextView txtReenterNum = null;
	private TextView txtDanYueZaiCiNum = null;
	private TextView txtDormantCusNum = null;
	private TextView txtActiveCusNum = null;

	private TextView txtFirstInReducedValue = null;
	private TextView txtFirstInTryPercent = null;
	private TextView txtReenterReducedValue = null;
	private TextView txtReenterPercent = null;

	private TextView txtBottomDes = null;

	private AddTodoPickerViewUtils addPickerViewUtils;
	private boolean isContrast = false;
	private Date mStartDate;
	private Date mEndDate;
	private Date mCurrentDate;

	private List<String> mCarModeList;
	private String mCarModeSelect = "All";

	private List<String>carCodelist;

	private String mTimeFormat = null;

	private LocalBroadcastManager mLocalBroadcastManager = null;
	private BroadcastReceiver mBroadcastReceiver = null;

	// 新需求
	private TextView text_car_mode;// 车型（销售顾问）文本
	private List<String> mManagerList;
	private String mManagerSelect = "All";
	private List<String> mCustomers;
	private List<String> mCustCodelist;//顾问模式的code
	public static final int OVERVIEW_DATA_SUC = 300;// 请求销售顾问成功
	public static final int OVERVIEW_DATA_FIN = 401;

	private LinearLayout ll_zc, ll_sc;
	private ImageView iv_xssc;

	private String title="All";
	
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			super.handleMessage(msg);
			switch (msg.what) {
			case ProfitApplication.DATA_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				break;
			case ProfitApplication.DATA_SUC:
				dealOperativeInfo(msg);
				break;
			case OVERVIEW_DATA_FIN:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				break;
			case OVERVIEW_DATA_SUC:
				handleCustomerMsg(msg);
				break;
			case ProfitApplication.SERVER_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_net));			
			    break;
			}
		}
	};

	// 解析销售顾问数据
	public void handleCustomerMsg(Message msg) {
		try {
			String strMsg = (String) msg.obj;
			Gson gson = new Gson();
			List<OverviewCustomerInfo> mOverviewCustomerInfos = gson.fromJson(strMsg,
					new TypeToken<List<OverviewCustomerInfo>>() {
					}.getType());
			dealCustomerMsg(mOverviewCustomerInfos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 处理销售顾问数据
	public void dealCustomerMsg(List<OverviewCustomerInfo> mOverviewCustomerInfos) {
		if (null != mOverviewCustomerInfos && mOverviewCustomerInfos.size() > 0) {
			// 得到销售顾问数据
			for (int i = 0; i < mOverviewCustomerInfos.size(); i++) {
				mCustomers.add(mOverviewCustomerInfos.get(i).getScName());
				mCustCodelist.add(mOverviewCustomerInfos.get(i).getScCode());
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ProfitApplication.BROADCAST_MESSAGE);
		intentFilter.addAction(ProfitApplication.TIME_REFRESH_MESSAGE);
		mBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(ProfitApplication.BROADCAST_MESSAGE)) {
					text_car_mode.setText(ProfitApplication.isConsultantMode ? "销售顾问" : "车型");
//					txtCarMode.setText("All");
//					if (ProfitApplication.isConsultantMode) {
//						initCustomerData();
//					}
//					getOperativeInfo();
					mCarModeSelect="All";
					mManagerSelect="All";
					title="All";
					chkContrastMode.setChecked(false);//切换模式后把选中的清空
					initData();
				}

				if (action.equals(ProfitApplication.TIME_REFRESH_MESSAGE)) {
					initData();
				}
			}
		};
		mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_overview_operative, container, false);
		initView(view);
		bindata();
		return view;
	}

	private void bindata() {
		if (!ProfitApplication.isConsultantMode) {
			text_car_mode.setText("车型");
			
		} else {		
			text_car_mode.setText("销售顾问");
		}
	}

	private void initView(View view) {
		// 车型对应的TextView
		text_car_mode = (TextView) view.findViewById(R.id.text_car_mode);

		layoutStartTime = (RelativeLayout) view.findViewById(R.id.overview_top_start_time_layout);
		layoutStartTime.setOnClickListener(this);

		txtStartTime = (TextView) view.findViewById(R.id.overview_top_start_time);

		layoutEndTime = (RelativeLayout) view.findViewById(R.id.overview_top_end_time_layout);
		layoutEndTime.setOnClickListener(this);

		txtEndTime = (TextView) view.findViewById(R.id.overview_top_end_time);

		layoutCarMode = (LinearLayout) view.findViewById(R.id.overview_top_car_mode_layout);
		layoutCarMode.setOnClickListener(this);

		txtCarMode = (TextView) view.findViewById(R.id.overview_top_car_mode);

		txtPercent = (TextView) view.findViewById(R.id.overview_operative_percent);
		txtReducedValue = (TextView) view.findViewById(R.id.overview_operative_reduced_value);
		txtReducedValue.setVisibility(isContrast ? View.VISIBLE : View.GONE);

		txtNewCustomer = (TextView) view.findViewById(R.id.overview_operative_new);
		txtFirstInTryNum = (TextView) view.findViewById(R.id.overview_operative_first_in_num);
		txtQuoteNum = (TextView) view.findViewById(R.id.overview_operative_quote_num);
		txtDealNum = (TextView) view.findViewById(R.id.overview_operative_deal_num);

		txtReenterNum = (TextView) view.findViewById(R.id.overview_operative_reenter_num);
		txtDanYueZaiCiNum = (TextView) view.findViewById(R.id.overview_operative_dangyuezaici);
		txtActiveCusNum = (TextView) view.findViewById(R.id.overview_operative_active_customer);
		txtDormantCusNum = (TextView) view.findViewById(R.id.overview_operative_dormant_customer);

		txtFirstInReducedValue = (TextView) view.findViewById(R.id.overview_operative_first_in_try_reduced_value);
		txtFirstInReducedValue.setVisibility(isContrast ? View.VISIBLE : View.GONE);
		txtFirstInTryPercent = (TextView) view.findViewById(R.id.overview_operative_first_in_try_percent);

		txtReenterReducedValue = (TextView) view.findViewById(R.id.overview_operative_reenter_reduced_value);
		txtReenterReducedValue.setVisibility(isContrast ? View.VISIBLE : View.GONE);
		txtReenterPercent = (TextView) view.findViewById(R.id.overview_operative_reenter_percent);

		txtBottomDes = (TextView) view.findViewById(R.id.overview_operative_bottom_description);

		chkContrastMode = (CheckBox) view.findViewById(R.id.overview_operative_contrast_mode);
		chkContrastMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isContrast = isChecked;
				if (isChecked) {
					txtReducedValue.setVisibility(View.VISIBLE);
					txtFirstInReducedValue.setVisibility(View.VISIBLE);
					txtReenterReducedValue.setVisibility(View.VISIBLE);
				} else {
					txtReducedValue.setVisibility(View.GONE);
					txtFirstInReducedValue.setVisibility(View.GONE);
					txtReenterReducedValue.setVisibility(View.GONE);
				}
			}
		});
		chkContrastMode.setChecked(false);

		iv_xssc=(ImageView) view.findViewById(R.id.iv_xssc);
		ll_zc = (LinearLayout) view.findViewById(R.id.ll_zc);
		ll_sc = (LinearLayout) view.findViewById(R.id.ll_sc);

//		iv_xssc.setOnLongClickListener(new OnLongClickListener() {
//
//			@Override
//			public boolean onLongClick(View v) {
//				showdialog(0);
//				return true;
//			}
//		});
		
		iv_xssc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showdialog(0);
			}
		});

//		ll_zc.setOnLongClickListener(new OnLongClickListener() {
//
//			@Override
//			public boolean onLongClick(View v) {
//				showdialog(1);
//				return true;
//			}
//		});
		
		ll_zc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showdialog(1);
			}
		});

//		ll_sc.setOnLongClickListener(new OnLongClickListener() {
//
//			@Override
//			public boolean onLongClick(View v) {
//				showdialog(2);
//				return true;
//			}
//		});
		ll_sc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showdialog(2);
			}
		});
		initData();
	}

	protected void showdialog(int type) {// 彈出對話框
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		if (type == 0) {
			list.add("新增潜客&当期新增潜在客户量");
			list.add("首次进店试乘试驾&当期首次进店试乘试驾量");
			list.add("首次进店报价数&=当期首次进店报价量");
			list.add("成交数&当期新增订单量");
		} else if (type == 1) {
			list.add("再次进店&当期再次进店潜在客户量");
			list.add("当月再次(当月客户再次进店)&当期新增潜客再次进店数量");
			list.add("活跃再次(活跃客户再次进店)&N-3到N-1月进店客户（新增+再次）,在N月再次进店数量");
			list.add("休眠再次(休眠客户再次进店)&N-4月以上进店客户（新增+再次），N-3到N-1未进店，在N月再次进店数量");
		} else if (type == 2) {
			list.add("首次进店试乘试驾率&当期首次进店试乘试驾量/ 当期新增潜在客户量*100%");
			list.add("再次进店率&当期再次进店潜在客户量/（当期进店潜在客户量- 当期首次进店成交客户量）*100%");
		} 
		KpiDialog kpidialog = new KpiDialog(getContext(), list).builder();
		kpidialog.show();
		kpidialog.setCanceledOnTouchOutside(true);

	}

	private void initData() {
		mTimeFormat = "yyyy-MM-dd";
		addPickerViewUtils = new AddTodoPickerViewUtils(getActivity());

		// 日期默认设置为本月日期
		mCurrentDate = DateAndTimeUtils.getCurrentDate(mTimeFormat);
		initDate();

		if (!ProfitApplication.isConsultantMode) {// 车型
			// 车型列表
			mCarModeList = new ArrayList<String>();
			mCarModeList.add("All");
			mCarModeList.addAll(ProfitApplication.loginBackInfo.getModels());
			txtCarMode.setText(title);
			carCodelist=new ArrayList<>();
			carCodelist.add("All");
			carCodelist.addAll(ProfitApplication.loginBackInfo.getCarCodeList());
			if(title.equals("All")) {
				chkContrastMode.setVisibility(View.GONE); // 车型为“全部”时，“对比模式”不显示
			}else {
				chkContrastMode.setVisibility(View.VISIBLE);
			}
			
		} else {
			// 顾问
			// 请求销售顾问数据,原来是从登录数据中取得的

			initCustomerData();
		}

		getOperativeInfo();
	}

	public void initCustomerData() {
		mCustomers = new ArrayList<String>();
		mCustCodelist=new ArrayList<>();
		mCustomers.add("All");
		mCustCodelist.add("All");
		txtCarMode.setText(title);
		if(title.equals("All")) {
			chkContrastMode.setVisibility(View.GONE); // 车型为“全部”时，“对比模式”不显示
		}else {
			chkContrastMode.setVisibility(View.VISIBLE);
		}
		ProfitApplication.profitNetService.getOverviewOperativeServlet2(txtStartTime.getText().toString().trim(),
				txtEndTime.getText().toString().trim(), "", handler);
	}

	private void initDate() {
		if (null == ProfitApplication.mStartDate) {
			mStartDate = DateAndTimeUtils.getCurrentMonthOfFirstDay();
		} else {
			mStartDate = ProfitApplication.mStartDate;
		}
		txtStartTime.setText(DateAndTimeUtils.getTimeForDate(mTimeFormat, mStartDate));

		if (null == ProfitApplication.mEndDate) {
			mEndDate = DateAndTimeUtils.getCurrentDate(mTimeFormat);
			txtEndTime.setText(DateAndTimeUtils.getCurrentTime(mTimeFormat));
		} else {
			mEndDate = ProfitApplication.mEndDate;
			txtEndTime.setText(DateAndTimeUtils.getTimeForDate(mTimeFormat, mEndDate));
		}
	}

	@Override
	public void refresh() {
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.overview_top_start_time_layout:
			showDateDialog(txtStartTime, true);
			break;
		case R.id.overview_top_end_time_layout:
			showDateDialog(txtEndTime, false);
			break;
		case R.id.overview_top_car_mode_layout:
			if (!ProfitApplication.isConsultantMode) {// 车型
				showCarModeDialog();
			} else {
				// 顾问
				showManagerDialog();
			}
			break;
		default:
			break;
		}
	}

	/** 显示日期选择器 */
	private void showDateDialog(final TextView tvTime, final boolean isStart) {
		TimePickerViewDialog pvTimeDialog = addPickerViewUtils.initDatePickerView(isStart ? "请选择开始时间" : "请选择结束时间")
				.getPvTime();
		pvTimeDialog.getDialog().show();

		pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				String strDate = null;
				if (isStart) {
					// strDate = addPickerViewUtils.getDateNoDay(date) + "-01";
					strDate = addPickerViewUtils.getDate(date);// 开始日期
					mStartDate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, strDate);
				} else {
					// strDate = DateAndTimeUtils.getLastDateOfMonth(date);
					strDate = addPickerViewUtils.getDate(date);// 结束日期
					mEndDate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, strDate);
				}

				if (isStart) {
					tvTime.setText(strDate);
					String strEndDate = DateAndTimeUtils.getLastDateOfMonth(mStartDate);
					mEndDate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, strEndDate);

					txtEndTime.setText(strEndDate);
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
				getOperativeInfo();
			}

			@Override
			public void onCancel() {

			}
		});
	}

	// 显示车型
	private void showCarModeDialog() {
		
		EditDialogUtils.showSelectDialog(getContext(), txtCarMode, "请选择车型", mCarModeList)
				.setItemClick(new OnListViewItemClick() {

					@Override
					public void onClick(String str, int position) {
						txtCarMode.setText(str);
                        title=str;
						if (str.equals("All")) {
							chkContrastMode.setVisibility(View.GONE);
							isContrast = false;
						} else {
							chkContrastMode.setVisibility(View.VISIBLE);
							chkContrastMode.setChecked(isContrast);
						}
						refreshView();

						mCarModeSelect = carCodelist.get(position-1);
						getOperativeInfo();
					}
				});
	}

	// 显示销售顾问
	private void showManagerDialog() {
		EditDialogUtils.showSelectDialog(getContext(), txtCarMode, "请选择销售顾问", mCustomers)
				.setItemClick(new OnListViewItemClick() {

					@Override
					public void onClick(String str, int position) {
						txtCarMode.setText(str);
						title=str;
						if (str.equals("All")) {
							chkContrastMode.setVisibility(View.GONE);
							isContrast = false;
						} else {
							chkContrastMode.setVisibility(View.VISIBLE);
							chkContrastMode.setChecked(isContrast);
						}
						refreshView();

						mManagerSelect = mCustCodelist.get(position-1);
						getOperativeInfo();
					}
				});
	}

	private void refreshView() {
		txtReducedValue.setVisibility(isContrast ? View.VISIBLE : View.GONE);
		txtFirstInReducedValue.setVisibility(isContrast ? View.VISIBLE : View.GONE);
		txtReenterReducedValue.setVisibility(isContrast ? View.VISIBLE : View.GONE);
	}

	private void getOperativeInfo() {
		progressDialog.show();
		if (!ProfitApplication.isConsultantMode) {// 车型模式
			ProfitApplication.profitNetService.getOverviewOperativeServlet(txtStartTime.getText().toString().trim(),
					txtEndTime.getText().toString().trim(), mCarModeSelect, handler);
		} else {// 销售顾问
			ProfitApplication.profitNetService.getOverviewOperativeServlet(txtStartTime.getText().toString().trim(),
					txtEndTime.getText().toString().trim(), mManagerSelect, handler);
		}

	}

	private void dealOperativeInfo(Message msg) {
		try {
			String strMsg = (String) msg.obj;

			Gson gson = new Gson();
			OverviewOperativeInfo overviewOperativeInfo = gson.fromJson(strMsg, OverviewOperativeInfo.class);

			showOperativeInfo(overviewOperativeInfo);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showOperativeInfo(OverviewOperativeInfo overviewOperativeInfo) {
		if (null != overviewOperativeInfo) {
			txtPercent.setText(StringUtils.FormatString(overviewOperativeInfo.getCloserate()) + "%");

			String contrast = overviewOperativeInfo.getContrast();
			if (Float.compare(Float.valueOf(contrast), 0f) <= 0) {
				txtReducedValue.setTextColor(Color.parseColor("#ff4400"));
				txtReducedValue.setText(StringUtils.FormatString(contrast) + "%");
			} else {
				txtReducedValue.setTextColor(Color.parseColor("#a7d859"));
				txtReducedValue.setText("+" + StringUtils.FormatString(contrast) + "%");
			}

			txtNewCustomer.setText(StringUtils.addUnit(getContext(), overviewOperativeInfo.getXinpotential(), "人"));
			txtFirstInTryNum.setText(StringUtils.addUnit(getContext(), overviewOperativeInfo.getFirstDrive(), "人"));
			txtQuoteNum.setText(StringUtils.addUnit(getContext(), overviewOperativeInfo.getQuotation(), "人"));
			txtDealNum.setText(StringUtils.addUnit(getContext(), overviewOperativeInfo.getIndent(), "人"));

			txtReenterNum.setText(overviewOperativeInfo.getAgain());
			txtDanYueZaiCiNum.setText(StringUtils.addUnit(getContext(), overviewOperativeInfo.getMonth(), "人"));
			txtActiveCusNum.setText(StringUtils.addUnit(getContext(), overviewOperativeInfo.getActive(), "人"));
			txtDormantCusNum.setText(StringUtils.addUnit(getContext(), overviewOperativeInfo.getDormant(), "人"));

			String firstContrast = overviewOperativeInfo.getFirstcontrast();
			if(!TextUtils.isEmpty(firstContrast)) {
				if (Float.compare(Float.valueOf(firstContrast), 0f) <= 0) {
					txtFirstInReducedValue.setTextColor(Color.parseColor("#ff4400"));
					txtFirstInReducedValue.setText(StringUtils.FormatString(firstContrast) + "%");
				} else if (Float.compare(Float.valueOf(firstContrast), 0f) > 0) {
					txtFirstInReducedValue.setTextColor(Color.parseColor("#a7d859"));
					txtFirstInReducedValue.setText("+" + StringUtils.FormatString(firstContrast) + "%");
				}
			}
			txtFirstInTryPercent.setText(StringUtils.FormatString(overviewOperativeInfo.getFirstDriverate()) + "%");
//			txtFirstInTryPercent.setText(overviewOperativeInfo.getFirstDriverate() + "%");

			String againContrast = overviewOperativeInfo.getAgaincontrast();
			if (Float.compare(Float.valueOf(againContrast), 0f) <= 0) {
				txtReenterReducedValue.setTextColor(Color.parseColor("#ff4400"));
				txtReenterReducedValue.setText(StringUtils.FormatString(againContrast) + "%");
			} else if (Float.compare(Float.valueOf(againContrast), 0f) > 0) {
				txtReenterReducedValue.setTextColor(Color.parseColor("#a7d859"));
				txtReenterReducedValue.setText("+" + StringUtils.FormatString(againContrast) + "%");
			}
			txtReenterPercent.setText(StringUtils.FormatString(overviewOperativeInfo.getAgainrate()) + "%");
//			txtReenterPercent.setText(overviewOperativeInfo.getAgainrate() + "%");

			StringBuffer desStr = new StringBuffer();
			desStr.append("总交车数（");
			desStr.append(overviewOperativeInfo.getTotal());
			desStr.append("）= 展厅（");
			desStr.append(overviewOperativeInfo.getExhibition());
			desStr.append("）+ ADC（");
			desStr.append(overviewOperativeInfo.getAdc());
			desStr.append("）+ 大客户（");
			desStr.append(overviewOperativeInfo.getBigcustomer());
			
			desStr.append("）+ 批售（");
			desStr.append(overviewOperativeInfo.getWholesale());
			
			desStr.append("）+ 车展（");
			desStr.append(overviewOperativeInfo.getAutoshow());
			desStr.append("）");
			txtBottomDes.setText(desStr.toString());
		}
	}
	
	
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

}
