package com.iris.cada.newfragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.ProfitApplication;
import com.iris.cada.entity.OverviewCustomerInfo;
import com.iris.cada.entity.OverviewProfitInfo;
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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 概览 —盈利
 * 
 * @author LFJ
 *
 */
public class OverviewProfitFragment extends BaseFragment implements OnClickListener {

	private RelativeLayout layoutStartTime = null;
	private TextView txtStartTime = null;
	private RelativeLayout layoutEndTime = null;
	private TextView txtEndTime = null;
	private LinearLayout layoutCarMode = null;
	private TextView txtCarMode = null;
	private TextView txtGP1 = null;
	private TextView txtGP1Percent = null;
	private TextView txtGP2 = null;
	private TextView txtGP2Percent = null;
	private TextView txtTotalRevenue = null;
	private TextView txtGrossMargin = null;
	private TextView txtJiazhuangRate = null;
	private TextView txtProfitRate = null;
	private TextView txtProcedureFee = null;
	private TextView txtCarLoanPermeability = null;
	private TextView txtNewInsuranceIncome = null;
	private TextView txtNewInsurancePermeability = null;
	private TextView txtExtendedwarrantyIncome = null;
	private TextView txtExtendedwarrantyPermeability = null;
	private TextView txtTradeRebate = null;
	private TextView txtTradePermeability = null;

	private AddTodoPickerViewUtils addPickerViewUtils;
	private Date mStartDate;
	private Date mEndDate;
	private Date mCurrentDate;
	private List<String> mCarModeList;
	private List<String> mCarCodeList;
	private String mTimeFormat = null;

	private String mCarModeSelect = "All";

	private String title="All";


	private LocalBroadcastManager mLocalBroadcastManager = null;
	private BroadcastReceiver mBroadcastReceiver = null;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			super.handleMessage(msg);
			switch (msg.what) {
			case ProfitApplication.DATA_FAILED://数据请求成功
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				break;
			case ProfitApplication.DATA_SUC:
				dealProfitInfo(msg);
				break;
			case ProfitApplication.SERVER_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_net));
				break;
			case OVERVIEW_DATA_SUC://销售顾问数据成功
				handleCustomerMsg(msg);
				break;
			case OVERVIEW_DATA_FIN:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				break;
			}
		}
	};
	
	
	//解析销售顾问数据
		public void handleCustomerMsg(Message msg){
			try {
				String strMsg = (String) msg.obj;
				Gson gson = new Gson();
				List<OverviewCustomerInfo> mOverviewCustomerInfos = gson.fromJson(strMsg,new TypeToken<List<OverviewCustomerInfo>>(){}.getType()); 
				dealCustomerMsg(mOverviewCustomerInfos);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//处理销售顾问数据
		public void dealCustomerMsg(List<OverviewCustomerInfo> mOverviewCustomerInfos){
			if(null!=mOverviewCustomerInfos && mOverviewCustomerInfos.size()>0){
				//得到销售顾问数据
				for(int i=0;i<mOverviewCustomerInfos.size();i++){
					mCustomers.add(mOverviewCustomerInfos.get(i).getScName());
					mCode.add(mOverviewCustomerInfos.get(i).getScCode());
				}
			}
		}

	// 新增GP3
	private TextView txtGP3 = null;
	private TextView txtGP3Percent = null;
	private TextView txtCarLoanMaoLi;
	private TextView txtCarLoanMaoLiRate;
	private TextView txtNewInsuranceMaoLi;
	private TextView txtNewInsuranceMaoLiRate;
	private TextView txtYanBaoMaoLi;
	private TextView txtYanBaoMaoLiRate;
	private TextView txtShangPaiTotal;
	private TextView txtShangPaiShenTouRate;
	private TextView txtShangPaiMaoLi;
	private TextView txtShangPaiMaoLiRate;
	private TextView txtOtherTotal;
	private TextView txtOtherMaoLi;
	private TextView txtOtherMaoLiRate;
	private TextView text_car_mode;
	
	private String mManagerSelect = "All";
	private List<String> mCustomers;
	private List<String> mCode;//顾问模式编码
	public static final int OVERVIEW_DATA_SUC = 300;//请求销售顾问成功
	public static final int OVERVIEW_DATA_FIN = 401;
	
	private LinearLayout ll_xc,ll_jp,ll_cd,ll_xb,ll_yb,ll_sp,ll_qt,ll_zh,ll_gpf,ll_gps,ll_gpt;

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
					txtCarMode.setText("All");
//					if(ProfitApplication.isConsultantMode){//顾问模式
//						initCustomerData();//获取销售顾问
//					}
//						getProfitInfo();//请求数据
					mManagerSelect = "All";
					mCarModeSelect = "All";
					title="All";
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
		View view = inflater.inflate(R.layout.fragment_overview_profit3, container, false);
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
		//增加
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

		txtGP1 = (TextView) view.findViewById(R.id.fragment_overview_profit_gp1);
		txtGP1Percent = (TextView) view.findViewById(R.id.fragment_overview_profit_gp1_percent);
		txtGP2 = (TextView) view.findViewById(R.id.fragment_overview_profit_gp2);
		txtGP2Percent = (TextView) view.findViewById(R.id.fragment_overview_profit_gp2_percent);

		// 新增GP3
		txtGP3 = (TextView) view.findViewById(R.id.fragment_overview_profit_gp3);
		txtGP3Percent = (TextView) view.findViewById(R.id.fragment_overview_profit_gp3_percent);
		// 车贷总毛利
		txtCarLoanMaoLi = (TextView) view.findViewById(R.id.fragment_overview_profit_chedai_maoli);
		// 车贷毛利率
		txtCarLoanMaoLiRate = (TextView) view.findViewById(R.id.fragment_overview_chedai_maolilv);

		// 新保总毛利
		txtNewInsuranceMaoLi = (TextView) view.findViewById(R.id.fragment_overview_profit_xinbao_maoli);
		// 新保毛利率
		txtNewInsuranceMaoLiRate = (TextView) view.findViewById(R.id.fragment_overview_xinbao_maolilv);

		// 延保总毛利

		txtYanBaoMaoLi = (TextView) view.findViewById(R.id.fragment_overview_profit_yanbao_maoli);
		// 延保毛利率
		txtYanBaoMaoLiRate = (TextView) view.findViewById(R.id.fragment_overview_yanbao_maolilv);

		// 上牌总收入
		txtShangPaiTotal = (TextView) view.findViewById(R.id.fragment_overview_profit_shangpai_shouru);
		// 上牌渗透率
		txtShangPaiShenTouRate = (TextView) view.findViewById(R.id.fragment_overview_profit_shangpai_permeability);
		// 上牌总毛利
		txtShangPaiMaoLi = (TextView) view.findViewById(R.id.fragment_overview_profit_shangpai_maoli);
		// 上牌毛利率
		txtShangPaiMaoLiRate = (TextView) view.findViewById(R.id.fragment_overview_shangpai_maolilv);

		// 其它
		txtOtherTotal = (TextView) view.findViewById(R.id.fragment_overview_profit_other_shouru);
		txtOtherMaoLi = (TextView) view.findViewById(R.id.fragment_overview_profit_other_maoli);
		txtOtherMaoLiRate = (TextView) view.findViewById(R.id.fragment_overview_other_maolilv);

		txtTotalRevenue = (TextView) view.findViewById(R.id.fragment_overview_profit_total_revenue);
		txtGrossMargin = (TextView) view.findViewById(R.id.fragment_overview_profit_gross_margin);
		//精品总毛利
		txtJiazhuangRate = (TextView) view.findViewById(R.id.fragment_overview_profit_jiazhuang_rate);
		txtProfitRate = (TextView) view.findViewById(R.id.fragment_overview_profit_rate);
		// 车贷总收入
		txtProcedureFee = (TextView) view.findViewById(R.id.fragment_overview_profit_procedure_fee);
		// 车贷渗透率
		txtCarLoanPermeability = (TextView) view.findViewById(R.id.fragment_overview_profit_car_loan_permeability);
		txtNewInsuranceIncome = (TextView) view.findViewById(R.id.fragment_overview_profit_new_insurance_income);
		txtNewInsurancePermeability = (TextView) view
				.findViewById(R.id.fragment_overview_profit_new_insurance_permeability);
		txtExtendedwarrantyIncome = (TextView) view.findViewById(R.id.fragment_overview_profit_extendedwarranty_income);
		txtExtendedwarrantyPermeability = (TextView) view
				.findViewById(R.id.fragment_overview_profit_extendedwarranty_permeability);
		txtTradeRebate = (TextView) view.findViewById(R.id.fragment_overview_profit_trade_rebate);
		txtTradePermeability = (TextView) view.findViewById(R.id.fragment_overview_profit_trade_in_permeability);
        
//		ll_xc=(LinearLayout) view.findViewById(R.id.ll_xc);
		ll_gpf=(LinearLayout) view.findViewById(R.id.ll_gpf);
		ll_gps=(LinearLayout) view.findViewById(R.id.ll_gps);
		ll_gpt=(LinearLayout) view.findViewById(R.id.ll_gpt);
		ll_jp=(LinearLayout) view.findViewById(R.id.ll_jp);
		ll_cd=(LinearLayout) view.findViewById(R.id.ll_cd);
		ll_xb=(LinearLayout) view.findViewById(R.id.ll_xb);
		ll_yb=(LinearLayout) view.findViewById(R.id.ll_yb);
		ll_sp=(LinearLayout) view.findViewById(R.id.ll_sp);
		ll_qt=(LinearLayout) view.findViewById(R.id.ll_qt);
		ll_zh=(LinearLayout) view.findViewById(R.id.ll_zh);
		
//		ll_gpf.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				showdialog(0);
//				return true;
//			}
//		});
		ll_gpf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showdialog(0);
			}
		});
		
//		ll_gps.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				showdialog(8);
//				return true;
//			}
//		});
		ll_gps.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showdialog(8);
			}
		});
		
//		ll_gpt.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				showdialog(9);
//				return true;
//			}
//		});
		
		ll_gpt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showdialog(9);
			}
		});
		
		
//		ll_jp.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				showdialog(1);
//				return true;
//			}
//		});
		
		ll_jp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showdialog(1);
			}
		});
		
//		ll_cd.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				showdialog(2);
//				return true;
//			}
//		});
		
		ll_cd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showdialog(2);
			}
		});
		
//		ll_xb.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				showdialog(3);
//				return true;
//			}
//		});
		
		ll_xb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showdialog(3);
			}
		});
		
//		ll_yb.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				showdialog(4);
//				return true;
//			}
//		});
		ll_yb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showdialog(4);
			}
		});
		
//		ll_sp.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				showdialog(5);
//				return true;
//			}
//		});
		
		ll_sp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showdialog(5);
			}
		});
//		ll_qt.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				showdialog(6);
//				return true;
//			}
//		});
		
		ll_qt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showdialog(6);
			}
		});
//		ll_zh.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				showdialog(7);
//				return true;
//			}
//		});
		ll_zh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showdialog(7);
			}
		});
		
		initData();
	}
	
	protected void showdialog(int type) {// 彈出對話框
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		if (type == 0) {//新车
			list.add("GP1&=进销差=新车销售价格-采购发票价格");	
			list.add("GP1%&=GP1/新车销售收入*100%");					
		}else if(type == 8){
			list.add("GP2&=GP1+新车相关返利+二手车置换返利");
			list.add("GP2%&=GP2/新车销售收入*100%");
		}else if(type == 9){
			list.add("GP3&=GP2+衍生业务毛利");
			list.add("GP3%&=GP3/（新车销售收入+衍生业务收入）*100%");
		}
		else if (type == 1) {//精品
			list.add("总收入&精品加装收入总和");
			list.add("总毛利&精品加装毛利总和");
			list.add("加装率&精品加装量总和/销量*100%");
			list.add("毛利率&精品总收入/精品总毛利*100%");
		} else if (type == 2) {//车贷
			list.add("总收入&新车发生贷款银行返还的手续费总和");
			list.add("总毛利&新车车贷服务费总和(返还金额)-赠送成本总和(为促成车贷而赠送的成本)");
			list.add("加装率&新车车贷数量总和/销量*100%");
			list.add("毛利率&车贷总收入/车贷总毛利*100%");
		} else if (type == 3) {//新保
			list.add("总收入&新车发生保险销售时保险机构返还的手续费总和");
			list.add("总毛利&新车保险服务费总和(返还金额)-赠送成本总和(为促成保险而赠送的成本)");
			list.add("加装率&新车保险数量总和/销量*100%");
			list.add("毛利率&新保总收入/新保总毛利*100%");
		} else if (type == 4) {//延保
			list.add("总收入&延保销售时保险机构返还的手续费");
			list.add("总毛利&新车延保手续费总和(返还金额)-赠送成本总和(为促成延保而赠送的成本)");
			list.add("加装率&新车延保数量总和/销量*100%");
			list.add("毛利率&延保总收入/延保总毛利*100%");
		} else if (type == 5) {//上牌
			list.add("总收入&上牌业务收入总和");
			list.add("总毛利&上牌业务毛利总和");
			list.add("加装率&新车上牌量总和/销量*100%");
			list.add("毛利率&上牌总收入/上牌总毛利*100%");
		} else if (type == 6) {//其他
			list.add("业务收入&除新车及新车衍生业务外的其它收入总和（如划痕业务等）");
			list.add("业务毛利&除新车及新车衍生业务外的其它毛利总和（如划痕业务等）");		
			list.add("毛利率&其它业务总收入/其它业务总毛利*100%");
		} else if (type == 7) {//置换
			list.add("置换数&新车置换台次");
			list.add("置换率&新车置换台次/销量*100%");					
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
		initDate();//设置时间

		if(!ProfitApplication.isConsultantMode){//车型
			// 车型列表
			mCarModeList = new ArrayList<String>();
			mCarCodeList=new ArrayList<>();//车型编码
			mCarModeList.add("All");
			mCarCodeList.add("All");
			if(ProfitApplication.loginBackInfo.getModels()!=null&&ProfitApplication.loginBackInfo.getModels().size()>0) {
				mCarModeList.addAll(ProfitApplication.loginBackInfo.getModels());
				mCarCodeList.addAll(ProfitApplication.loginBackInfo.getCarCodeList());
			}

			txtCarMode.setText(title);
		}else{
			//请求销售顾问
			initCustomerData();
		}
		
		getProfitInfo();//请求数据
	}

	/**
	 * 获取顾问模式列表
	 */
	public void initCustomerData(){
		mCustomers = new ArrayList<String>();
		mCode= new ArrayList<>();
		mCustomers.add("All");
		mCode.add("All");//顾问模式
		txtCarMode.setText(title);
		ProfitApplication.profitNetService.getOverviewProfitServlet2(txtStartTime.getText().toString().trim(),
				txtEndTime.getText().toString().trim(), "", handler);
	}


	/**
	 * 设置时间
	 */
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
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.overview_top_start_time_layout:
			showDateDialog(txtStartTime, true);
			break;
		case R.id.overview_top_end_time_layout:
			showDateDialog(txtEndTime, false);
			break;
		case R.id.overview_top_car_mode_layout:
			if(!ProfitApplication.isConsultantMode){//车型
				showCarModeDialog();
			}else{
				//顾问
				showManagerDialog();
			}
			break;
		default:
			break;
		}
	}
	
	//显示销售顾问
		private void showManagerDialog() {
			EditDialogUtils.showSelectDialog(getContext(), txtCarMode, "请选择销售顾问", mCustomers)
					.setItemClick(new OnListViewItemClick() {

						@Override
						public void onClick(String str, int position) {
							txtCarMode.setText(str);
							title=str;
							mManagerSelect = mCode.get(position-1);
							getProfitInfo();
						}
					});
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
//					strDate = addPickerViewUtils.getDateNoDay(date) + "-01";
					strDate = addPickerViewUtils.getDate(date);//开始日期
					mStartDate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, strDate);
				} else {
//					strDate = DateAndTimeUtils.getLastDateOfMonth(date);
					strDate = addPickerViewUtils.getDate(date);//结束日期
					mEndDate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, strDate);
				}

				if (isStart) {
					tvTime.setText(strDate);
					String strEndDate = DateAndTimeUtils.getLastDateOfMonth(mStartDate);
					mEndDate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, strEndDate);

					txtEndTime.setText(strEndDate);
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
				// 时间刷新
				TimeRefreshUtils.sendTimeRefreshMsg(getContext(), mStartDate, mEndDate);

				getProfitInfo();
			}

			@Override
			public void onCancel() {

			}
		});
	}

	private void showCarModeDialog() {
		EditDialogUtils.showSelectDialog(getContext(), txtCarMode, "请选择车型", mCarModeList)
				.setItemClick(new OnListViewItemClick() {

					@Override
					public void onClick(String str, int position) {
						txtCarMode.setText(str);
						title=str;//车型
						mCarModeSelect = mCarCodeList.get(position-1);
						getProfitInfo();//请求数据
					}
				});
	}

	@Override
	public void refresh() {
	}

	/**
	 * 请求数据
	 */
	private void getProfitInfo() {
		progressDialog.show();
		if(!ProfitApplication.isConsultantMode){//车型模式
			ProfitApplication.profitNetService.getOverviewProfitServlet(txtStartTime.getText().toString().trim(),
					txtEndTime.getText().toString().trim(), mCarModeSelect, handler);
		}else{//销售顾问数据
			ProfitApplication.profitNetService.getOverviewProfitServlet(txtStartTime.getText().toString().trim(),
					txtEndTime.getText().toString().trim(), mManagerSelect, handler);
		}
		
	}

	private void dealProfitInfo(Message msg) {
		try {
			String strMsg = (String) msg.obj;

			Gson gson = new Gson();
			OverviewProfitInfo overviewProfitInfo = gson.fromJson(strMsg, OverviewProfitInfo.class);
			showProfitData(overviewProfitInfo);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showProfitData(OverviewProfitInfo overviewProfitInfo) {
		if (null != overviewProfitInfo) {
			txtGP1.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getGp1(), "元"));
//			txtGP1.setGravity(Gravity.RIGHT);
			txtGP1Percent.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getGp1rate(), "%"));
			txtGP2.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getGp2(), "元"));
			txtGP2Percent.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getGp2rate(), "%"));
			txtTotalRevenue.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getBountiqueIncome(), "元"));
			//精品总毛利
			txtJiazhuangRate.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getBountiqueGross(), "元"));
			//精品加装率
			txtGrossMargin.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getBountiqueAddrate(), "%"));
			txtProfitRate.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getBountiqueGrossrate(), "%"));
			txtProcedureFee.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getCarloanfees(), "元"));
			txtCarLoanPermeability
					.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getCarloanpermeaterate(), "%"));
			txtNewInsuranceIncome
					.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getXinInsuranceIncome(), "元"));
			txtNewInsurancePermeability
					.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getXinInsurancepermeaterate(), "%"));
			txtExtendedwarrantyIncome
					.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getLastInsuranceIncome(), "元"));
			txtExtendedwarrantyPermeability
					.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getLastInsurancepermeaterate(), "%"));
			txtTradeRebate.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getPermuterebate(), "台"));
			txtTradePermeability.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getPermuterate(), "%"));
			
			
			txtGP3.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getGp3(), "元"));
			txtGP3Percent.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getGp3rate(), "%"));
			//车贷
			txtCarLoanMaoLi.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getCarloanProfit(), "元"));
			txtCarLoanMaoLiRate.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getCarloanGrossrate(), "%"));
			//新保
			txtNewInsuranceMaoLi.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getXinInsuranceProfit(), "元"));
			txtNewInsuranceMaoLiRate.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getXinInsurancePermeaterate(), "%"));
			//延保
			txtYanBaoMaoLi.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getLastInsuranceProfit(), "元"));
			txtYanBaoMaoLiRate.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getLastInsurancePermeaterate(), "%"));
			//上牌
			txtShangPaiTotal.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getOnCardTotalIncome(), "元"));
			txtShangPaiShenTouRate.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getOnCardPermeaterate(), "%"));
			txtShangPaiMaoLi.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getOnCardTotalProfit(), "元"));
			txtShangPaiMaoLiRate.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getOnCardGrossrate(), "%"));
			//其它
			txtOtherTotal.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getOtherTotalIncome(), "元"));
			txtOtherMaoLi.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getOtherTotalProfit(), "元"));
			txtOtherMaoLiRate.setText(StringUtils.addUnit(getContext(), overviewProfitInfo.getOtherGrossrate(), "%"));
			
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

}
