package com.iris.cada.newfragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.DiagnosisProductActivity;
import com.iris.cada.activity.FilterConsultantActivity;
import com.iris.cada.activity.MainActivity;
import com.iris.cada.activity.NewSettingAct;
import com.iris.cada.adapter.Diagnosisadapter;
import com.iris.cada.adapter.MyGridViewAdapter;
import com.iris.cada.comparator.CheckDerivativesBusinessAverageCarProfitsComparatorAsc;
import com.iris.cada.comparator.CheckDerivativesBusinessAverageCarProfitsComparatorDesc;
import com.iris.cada.comparator.CheckJieDaiComparatorAsc;
import com.iris.cada.comparator.CheckJieDaiComparatorDesc;
import com.iris.cada.comparator.CheckLookToBuyRateComparatorAsc;
import com.iris.cada.comparator.CheckLookToBuyRateComparatorDes;
import com.iris.cada.comparator.CheckXinPotentialCustomerComparatorAsc;
import com.iris.cada.comparator.CheckXinPotentialCustomerComparatorDesc;
import com.iris.cada.comparator.DiagEnglishAndNumCompactorAsc;
import com.iris.cada.comparator.DiagEnglishAndNumCompactorDesc;
import com.iris.cada.comparator.EnglishAndNumCompactorAsc;
import com.iris.cada.comparator.EnglishAndNumCompactorDesc;
import com.iris.cada.comparator.ZdkcslComparatorAsc;
import com.iris.cada.comparator.ZdkcslComparatorDesc;
import com.iris.cada.entity.Check;
import com.iris.cada.entity.IViewSCBean;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.utils.DateAndTimeUtils;
import com.iris.cada.utils.StringUtils;
import com.iris.cada.utils.TimeRefreshUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.cada.R;

import android.R.string;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DiagnosisFragment extends BaseFragment implements OnClickListener {
	private List<String> datas;
	private ListView lv;
	private ImageView setting_img;
	private ImageView img_last_data, img_next_data;
	private TextView star_data, fragment_xiaoshou;
	private AddTodoPickerViewUtils addPickerViewUtils;
	private Date mCurrentDate = null;
	private MyGridViewAdapter myGridViewAdapter = null;
	private String timeFormat = null;
	private Gson gson = null;
	private List<Check> Diagnosislist = new ArrayList<Check>();
	private List<Check> Diagnosislistdatas = new ArrayList<Check>();
	private TextView item_detail_name, XinPotential_tv, LookToBuy_tv, DerivativesBusiness_tv, fine_reposhow;
	private String mModeSelect = "All";
	private boolean isMonth = true;
	private AddTodoPickerViewUtils addTodoPickerViewUtils;
	private Date mStartDate;
	private Date mEndDate;
	private LinearLayout report_awy_linner;
	private Diagnosisadapter diagnosisadapter;
	private ImageView up_image1, down_img1, up_image2, down_img2, up_image3, down_img3, up_image4, down_img4;
	private boolean bS = true;
	private LinearLayout look_to_buy_extended_linner, fragment_report_fine_extended_infiltration_linner;
	private String id;
	private LocalBroadcastManager mLocalBroadcastManager = null;
	private BroadcastReceiver mBroadcastReceiver = null;
	private TextView income_text_up;
	private TextView mFilter = null;
	public static final String KEY_DATA_USER = "KEY_DATA_USER";
	public static final int KEY_DATA_OK = 100;
	
	//车型筛选
	public static final int KEY_CAR_OK = 101;
	public static final String KEY_DATA_CAR = "KEY_DATA_CAR";
	
	
	public static final int REQUSET = 1;
	public ArrayList<String> checkedList = new ArrayList<String>();//被选中的的集合
	public ArrayList<IViewSCBean> sclist = new ArrayList<IViewSCBean>();

	private RelativeLayout layoutStartTime;
	private TextView txtStartTime;
	private RelativeLayout layoutEndTime;
	private TextView txtEndTime;
	private LinearLayout jiedai_linner;
	private ImageView up_image5;
	private ImageView down_image5;
	private TextView jieDai_avg;
	
	
	
	private TextView tv_pjzdkcsl;//平均那列的库存数量
	
	private ImageView iv_zdkcslup,iv_zdkcsldown;
	private LinearLayout ll_zdkcsl;

	private String premission; 
	
	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.fragment_diagnosis, paramViewGroup, false);
		initView(localView);
		initData();//加载时间后获取监控数据
		bindata();//根据不同情况显示不同界面
		return localView;
	}

	public void refresh() {
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		premission = ProfitApplication.loginBackInfo.getPermission();
		if(TextUtils.isEmpty(premission)){
			premission="总经理";
		}
		mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ProfitApplication.BROADCAST_MESSAGE);
		mBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(ProfitApplication.BROADCAST_MESSAGE)) {
					fine_reposhow.setText(ProfitApplication.isConsultantMode ? "顾问" : "车型");
					getMonitorOperativeChannelInfo();
				}
				if (action.equals(ProfitApplication.TIME_REFRESH_MESSAGE)) {
					initDate();
					getMonitorOperativeChannelInfo();
				}
				bindata();
			}
		};
		mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);	   
	
	}

	
	/**初始化控件
	 * @param paramView
	 */
	private void initView(View paramView) {
		// 新添加
		layoutStartTime = (RelativeLayout) paramView.findViewById(R.id.fragment_detail_star_adc_rela);
		layoutStartTime.setOnClickListener(this);
		txtStartTime = (TextView) paramView.findViewById(R.id.fragment_detail_adc_start_time);

		layoutEndTime = (RelativeLayout) paramView.findViewById(R.id.fragment_detail_end_adc_rela);
		layoutEndTime.setOnClickListener(this);
		txtEndTime = (TextView) paramView.findViewById(R.id.fragment_detail_adc_end_time);

		
		this.lv = ((ListView) paramView.findViewById(R.id.fragment_reort_carlon_lv));
		income_text_up = (TextView) paramView.findViewById(R.id.fragment_report_fine_extended_income_text_up);
		fine_reposhow = (TextView) paramView.findViewById(R.id.fragment_report_fine_reposhow);
		report_awy_linner = (LinearLayout) paramView.findViewById(R.id.fragment_report_awy_linner);
		setting_img = (ImageView) paramView.findViewById(R.id.fragment_diagno_title_setting);
		item_detail_name = (TextView) paramView.findViewById(R.id.diadeal_name);
		XinPotential_tv = (TextView) paramView.findViewById(R.id.item_diadeal_name1);
		LookToBuy_tv = (TextView) paramView.findViewById(R.id.item_diadeal_name2);
		DerivativesBusiness_tv = (TextView) paramView.findViewById(R.id.item_diadeal_name3);
		up_image1 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_awy_image_up);//新增潜客对应的箭头
		down_img1 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_awy_image_down);
		up_image2 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_extended_image_up);//成交率对应的箭头
		down_img2 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_extended_image_down);
		up_image3 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_extended_infiltration_image_up);//衍生业务对应的箭头
		down_img3 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_extended_infiltration_image_down);
		up_image4 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_extended_income_image_up);//个人诊断对应的箭头
		down_img4 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_extended_income_image_down);
		iv_zdkcslup=(ImageView) paramView.findViewById(R.id.iv_zdkcslup);
		iv_zdkcsldown=(ImageView) paramView.findViewById(R.id.iv_zdkcsldown);
		ll_zdkcsl=(LinearLayout) paramView.findViewById(R.id.ll_zdkcsl);
		ll_zdkcsl.setOnClickListener(this);
		look_to_buy_extended_linner = (LinearLayout) paramView.findViewById(R.id.fragment_report_fine_extended_linner);
		fragment_report_fine_extended_infiltration_linner = (LinearLayout) paramView
				.findViewById(R.id.fragment_report_fine_extended_infiltration_linner);
		fragment_xiaoshou = (TextView) paramView.findViewById(R.id.fragment_xiaoshou);
		setting_img.setOnClickListener(this);
		report_awy_linner.setOnClickListener(this);
		look_to_buy_extended_linner.setOnClickListener(this);
		fragment_report_fine_extended_infiltration_linner.setOnClickListener(this);

		mFilter = (TextView) paramView.findViewById(R.id.overview_title_function_filter);
		mFilter.setOnClickListener(this);

		// 新增 接待总数
		jiedai_linner = (LinearLayout) paramView.findViewById(R.id.fragment_report_jiedai_linner);
		jiedai_linner.setOnClickListener(this);
		up_image5 = (ImageView) paramView.findViewById(R.id.fragment_report_jiedai_awy_image_up);
		down_image5 = (ImageView) paramView.findViewById(R.id.fragment_report_jiedai_awy_image_down);
		jieDai_avg = (TextView) paramView.findViewById(R.id.item_diadeal_name0);// 接待总数对应的团队平均
		tv_pjzdkcsl=(TextView) paramView.findViewById(R.id.tv_pjzdkcsl);

		// 新需求：默认按第一列排序
		first_column_linner = (LinearLayout) paramView.findViewById(R.id.fragment_report_first_column_linner);
		first_column_linner.setOnClickListener(this);
		image_up0 = (ImageView) paramView.findViewById(R.id.fragment_report_first_column_image_up);
		image_down0 = (ImageView) paramView.findViewById(R.id.fragment_report_first_column_image_down);
	}
	
	/**
	 * 加载时间
	 */
	private void initData() {
		timeFormat = "yyyy-MM-dd";
		addPickerViewUtils = new AddTodoPickerViewUtils(getActivity());
		gson = new Gson();
		if (null == ProfitApplication.mStartDate) {
			// 默认显示当前月
			mCurrentDate = DateAndTimeUtils.getCurrentDate(timeFormat);
		} else {
			mCurrentDate = ProfitApplication.mStartDate;
		}
		initDate();//初始化时间

		getMonitorOperativeChannelInfo();//获取监控数据
	}
	// 根据不同情况改变界面显示的
		private void bindata() {
			if (!ProfitApplication.isConsultantMode) {//费顾问模式
				fine_reposhow.setText("车型");
				item_detail_name.setText("车型平均");
				income_text_up.setText("车型诊断");
				fragment_xiaoshou.setText("车型");
				mFilter.setVisibility(View.VISIBLE);
				ll_zdkcsl.setVisibility(View.VISIBLE);
				tv_pjzdkcsl.setVisibility(View.VISIBLE);
			} else {
				fine_reposhow.setText("销售顾问");
				item_detail_name.setText("团队平均");
				income_text_up.setText("个人诊断");
				fragment_xiaoshou.setText("销售顾问");
				mFilter.setVisibility(View.VISIBLE);
				ll_zdkcsl.setVisibility(View.GONE);
				tv_pjzdkcsl.setVisibility(View.GONE);
			}
		}
		
		private void initDate() {
			if (null == ProfitApplication.mStartDate) {
				mStartDate = DateAndTimeUtils.getCurrentMonthOfFirstDay();
			} else {
				mStartDate = ProfitApplication.mStartDate;
			}
			txtStartTime.setText(DateAndTimeUtils.getTimeForDate(timeFormat, mStartDate));

			if (null == ProfitApplication.mEndDate) {
				mEndDate = DateAndTimeUtils.getCurrentDate(timeFormat);
				txtEndTime.setText(DateAndTimeUtils.getCurrentTime(timeFormat));
			} else {
				mEndDate = ProfitApplication.mEndDate;
				txtEndTime.setText(DateAndTimeUtils.getTimeForDate(timeFormat, mEndDate));
			}
		}

    //监听
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fragment_diagno_title_setting:
			// 跳转侧滑设置页面
//			((MainActivity) getActivity()).getSlidingMenu().toggle();
			  Intent intent1=new Intent(getActivity(),NewSettingAct.class);
			  startActivity(intent1);
			break;
		case R.id.fragment_detail_star_adc_rela:
			showDateDialog(txtStartTime, true);
			break;
		case R.id.fragment_detail_end_adc_rela:
			showDateDialog(txtEndTime, false);
			break;
		case R.id.fragment_report_first_column_linner://第一列
			firstColumnCompactor(bS,ProfitApplication.isConsultantMode);
			break;
		case R.id.fragment_report_jiedai_linner:// 接待总数
			jieDaiCompara(bS);
			break;
		case R.id.fragment_report_awy_linner:
			Compara(bS);
			break;
		case R.id.fragment_report_fine_extended_linner:
			LookTobyCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_infiltration_linner:
			DerivativesBusinessAverageCarProfits(bS);
			break;
		case R.id.overview_title_function_filter:
			Intent intent = new Intent(getActivity(), FilterConsultantActivity.class);
			intent.putExtra("startTime", txtStartTime.getText().toString().trim());
			intent.putExtra("endTime", txtEndTime.getText().toString().trim());			
			intent.putExtra("date", (Serializable)Diagnosislistdatas);	
			startActivityForResult(intent, REQUSET);
			break;
			
		case R.id.ll_zdkcsl://库存那列
			kucSlCompara(bS);
			
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
					mStartDate = DateAndTimeUtils.getDateTimeForStr(timeFormat, strDate);
				} else {
					// strDate = DateAndTimeUtils.getLastDateOfMonth(date);
					strDate = addPickerViewUtils.getDate(date);// 结束日期
					mEndDate = DateAndTimeUtils.getDateTimeForStr(timeFormat, strDate);
				}

				if (isStart) {
					tvTime.setText(strDate);
					String strEndDate = DateAndTimeUtils.getLastDateOfMonth(mStartDate);
					mEndDate = DateAndTimeUtils.getDateTimeForStr(timeFormat, strEndDate);

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
				// 时间刷新
				TimeRefreshUtils.sendTimeRefreshMsg(getContext(), mStartDate, mEndDate);

				getMonitorOperativeChannelInfo();//获取监控数据
			}

			@Override
			public void onCancel() {

			}
		});
	}

	private void refreshDate(boolean isLeft) {
		String strCurrentDate = DateAndTimeUtils.getTimeForDate(timeFormat, mCurrentDate);
		long currentLongTime;

		if (isLeft) {
			currentLongTime = DateAndTimeUtils
					.lastMonth(DateAndTimeUtils.getLongTimeForStr(timeFormat, strCurrentDate));
			Date lastDate = DateAndTimeUtils.getDateTimeForStr(timeFormat,
					DateAndTimeUtils.getTimeForLongTime(timeFormat, currentLongTime));

			mCurrentDate = lastDate;

		} else {
			boolean isCurrentMonth = DateAndTimeUtils
					.orCurrentMonth(DateAndTimeUtils.getLongTimeForStr(timeFormat, strCurrentDate));
			if (isCurrentMonth) {
				return;
			}

			currentLongTime = DateAndTimeUtils
					.nextMonth(DateAndTimeUtils.getLongTimeForStr(timeFormat, strCurrentDate));
			Date nextDate = DateAndTimeUtils.getDateTimeForStr(timeFormat,
					DateAndTimeUtils.getTimeForLongTime(timeFormat, currentLongTime));

			if (!DateAndTimeUtils.isAfter(nextDate, DateAndTimeUtils.getCurrentDate(timeFormat))) {
				mCurrentDate = nextDate;
			}
		}

		String strDate = DateAndTimeUtils.getFirstAndLastDayOfMonth(mCurrentDate);
		// star_data.setText(addPickerViewUtils.getDateNoDay(mCurrentDate));---------------------
		TimeRefreshUtils.savePublicDate(getContext(), mCurrentDate);
		getMonitorOperativeChannelInfo();
	}

	/**
	 * 获取监控数据
	 */
	private void getMonitorOperativeChannelInfo() {
		progressDialog.show();
		// Toast.makeText(getActivity(), star_data.getText().toString().trim(),
		// Toast.LENGTH_SHORT).show();	
		ProfitApplication.profitNetService.getDiagnosisData(txtStartTime.getText().toString().trim(),
				txtEndTime.getText().toString().trim(), sclist, handler);
		
	}

	Handler handler = new MyHandler() {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
			case ProfitApplication.DATA_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				Diagnosislist.clear();
				diagnosisadapter = new Diagnosisadapter(getActivity(), Diagnosislistdatas, mCurrentDate, Diagnosislist);
				diagnosisadapter.notifyDataSetChanged();
				lv.setAdapter(diagnosisadapter);
				XinPotential_tv.setText("0");
				LookToBuy_tv.setText("0");
				DerivativesBusiness_tv.setText("-");
				jieDai_avg.setText("0");// 接待总数
				tv_pjzdkcsl.setText("0");//库存数量
				break;
			case ProfitApplication.DATA_SUC:
				// ToastUtils.showMyToast(getActivity(), "succc-------------");
				dealChannelInfo(msg);
				break;
			case ProfitApplication.SERVER_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_net));
				break;
			}
		}

		/**解析并设置在lv上
		 * @param msg
		 */
		private void dealChannelInfo(Message msg) {
			if (Diagnosislist != null) {//后台返回的数据
				Diagnosislist.clear();
			}
			try {
				String strMsg = (String) msg.obj;
				Diagnosislist = new Gson().fromJson(strMsg, new TypeToken<ArrayList<Check>>() {
				}.getType());
				
				showOperativeInfo(Diagnosislist);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void showOperativeInfo(List<Check> diagnosislist) {
			if (diagnosislist != null && diagnosislist.size() != 0) {
				Diagnosislistdatas.clear();
				// 遍历集合 拿到除去第一条的 其他几条数据。
				for (int i = 0; i < diagnosislist.size(); i++) {
					if (i == 0) {
						continue;
					} else {
						Diagnosislistdatas.add(diagnosislist.get(i));//拿去除掉第一条的数据
					}
				}
				Log.e("diaz", Diagnosislistdatas.get(0).getSalesConsultant() + ""+Diagnosislistdatas.get(0).getCarType());
			}
			Log.e("除掉第一条的数据",Diagnosislistdatas.toString());
			Log.e("原始数据",diagnosislist.toString());
			diagnosisadapter = new Diagnosisadapter(getActivity(), Diagnosislistdatas, mCurrentDate, diagnosislist);
//			Collections.sort(Diagnosislistdatas, new CheckXinPotentialCustomerComparatorDesc());
			Collections.sort(Diagnosislistdatas, new DiagEnglishAndNumCompactorDesc(ProfitApplication.isConsultantMode));
			Log.e("监控数据", Diagnosislistdatas.toString());
			diagnosisadapter.notifyDataSetChanged();
			lv.setAdapter(diagnosisadapter);//设置数据
			XinPotential_tv.setText(diagnosislist.get(0).getXinPotentialCustomer());
			LookToBuy_tv.setText(diagnosislist.get(0).getLookToBuyRate() + "%");
			if("销售半权限".equals(premission)||"仅运营权限".equals(premission)){
			DerivativesBusiness_tv.setText("-");
			}else {
				DerivativesBusiness_tv.setText(StringUtils.intDivTo0(diagnosislist.get(0).getDerivativesBusinessAverageCarProfits()));
			}
			jieDai_avg.setText(diagnosislist.get(0).getReceptionTotal());// 接待总数
			tv_pjzdkcsl.setText(diagnosislist.get(0).getStockNum());//库存数量
			
		}
	};
	private LinearLayout first_column_linner;
	private ImageView image_up0;
	private ImageView image_down0;

	private void LookTobyCompara(boolean b) {
		if (Diagnosislistdatas != null && Diagnosislistdatas.size() != 0) {
			ShutImage(2);
			if (b) {
				Collections.sort(Diagnosislistdatas, new CheckLookToBuyRateComparatorAsc());
				diagnosisadapter.notifyDataSetChanged();
				bS = false;
				showimge(b, up_image2, down_img2);
			} else {
				Collections.sort(Diagnosislistdatas, new CheckLookToBuyRateComparatorDes());
				diagnosisadapter.notifyDataSetChanged();
				bS = true;
				showimge(b, up_image2, down_img2);
			}
		}

	}

	private void DerivativesBusinessAverageCarProfits(boolean b) {
		if (Diagnosislistdatas != null && Diagnosislistdatas.size() != 0) {
			ShutImage(3);
			if (b) {
				Collections.sort(Diagnosislistdatas, new CheckDerivativesBusinessAverageCarProfitsComparatorAsc());
				diagnosisadapter.notifyDataSetChanged();
				bS = false;
				showimge(b, up_image3, down_img3);
			} else {
				Collections.sort(Diagnosislistdatas, new CheckDerivativesBusinessAverageCarProfitsComparatorDesc());
				diagnosisadapter.notifyDataSetChanged();
				bS = true;
				showimge(b, up_image3, down_img3);
			}
		}
	}
	
	//第一列
	public void firstColumnCompactor(boolean b,boolean isConsultantMode) {
		if (Diagnosislistdatas != null && Diagnosislistdatas.size() != 0) {
			ShutImage(0);
			if (b) {
				Collections.sort(Diagnosislistdatas, new DiagEnglishAndNumCompactorAsc(isConsultantMode));
				diagnosisadapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up0, image_down0);
			} else {
				Collections.sort(Diagnosislistdatas, new DiagEnglishAndNumCompactorDesc(isConsultantMode));
				diagnosisadapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up0, image_down0);
			}
		}
	}
	

	// 接待总数比较
	public void jieDaiCompara(boolean b) {
		if (Diagnosislistdatas != null && Diagnosislistdatas.size() != 0) {
			ShutImage(5);
			if (b) {
				Collections.sort(Diagnosislistdatas, new CheckJieDaiComparatorAsc());
				diagnosisadapter.notifyDataSetChanged();
				bS = false;
				showimge(b, up_image5, down_image5);
			} else {
				Collections.sort(Diagnosislistdatas, new CheckJieDaiComparatorDesc());
				diagnosisadapter.notifyDataSetChanged();
				bS = true;
				showimge(b, up_image5, down_image5);
			}
		}
	}
	//库存数量
	private void kucSlCompara(boolean b) {
		// TODO Auto-generated method stub
		if (Diagnosislistdatas != null && Diagnosislistdatas.size() != 0) {
			ShutImage(6);
			if (b) {
				Collections.sort(Diagnosislistdatas, new ZdkcslComparatorAsc());
				diagnosisadapter.notifyDataSetChanged();
				bS = false;
				showimge(b, iv_zdkcslup, iv_zdkcsldown);
			} else {
				Collections.sort(Diagnosislistdatas, new  ZdkcslComparatorDesc());
				diagnosisadapter.notifyDataSetChanged();
				bS = true;
				showimge(b, iv_zdkcslup, iv_zdkcsldown);
			}
		}
	}

	public void Compara(boolean b) {
		if (Diagnosislistdatas != null && Diagnosislistdatas.size() != 0) {
			ShutImage(1);
			if (b) {
				Collections.sort(Diagnosislistdatas, new CheckXinPotentialCustomerComparatorAsc());
				diagnosisadapter.notifyDataSetChanged();
				bS = false;
				showimge(b, up_image1, down_img1);
			} else {
				Collections.sort(Diagnosislistdatas, new CheckXinPotentialCustomerComparatorDesc());
				diagnosisadapter.notifyDataSetChanged();
				bS = true;
				showimge(b, up_image1, down_img1);
			}
		}
	}

	public void showimge(boolean b, ImageView up, ImageView down) {
		if (b) {
			up.setVisibility(View.VISIBLE);
			down.setVisibility(View.INVISIBLE);
		} else {
			down.setVisibility(View.VISIBLE);
			up.setVisibility(View.INVISIBLE);
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

	public void ShutImage(int image_index) {
		switch (image_index) {//4对应的是个人诊断，这个箭头没有，这一列不需要排序
		case 0:
			up_image1.setVisibility(View.INVISIBLE);
			down_img1.setVisibility(View.INVISIBLE);
			up_image2.setVisibility(View.INVISIBLE);
			down_img2.setVisibility(View.INVISIBLE);
			up_image3.setVisibility(View.INVISIBLE);
			down_img3.setVisibility(View.INVISIBLE);
			up_image5.setVisibility(View.INVISIBLE);
			down_image5.setVisibility(View.INVISIBLE);
			iv_zdkcslup.setVisibility(View.INVISIBLE);
			iv_zdkcsldown.setVisibility(View.INVISIBLE);
			break;
		case 1:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			up_image2.setVisibility(View.INVISIBLE);
			down_img2.setVisibility(View.INVISIBLE);
			up_image3.setVisibility(View.INVISIBLE);
			down_img3.setVisibility(View.INVISIBLE);
			up_image5.setVisibility(View.INVISIBLE);
			down_image5.setVisibility(View.INVISIBLE);
			iv_zdkcslup.setVisibility(View.INVISIBLE);
			iv_zdkcsldown.setVisibility(View.INVISIBLE);
			break;
		case 2:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			up_image1.setVisibility(View.INVISIBLE);
			down_img1.setVisibility(View.INVISIBLE);
			up_image3.setVisibility(View.INVISIBLE);
			down_img3.setVisibility(View.INVISIBLE);
			up_image5.setVisibility(View.INVISIBLE);
			down_image5.setVisibility(View.INVISIBLE);
			iv_zdkcslup.setVisibility(View.INVISIBLE);
			iv_zdkcsldown.setVisibility(View.INVISIBLE);
			break;
		case 3:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			up_image1.setVisibility(View.INVISIBLE);
			down_img1.setVisibility(View.INVISIBLE);
			up_image2.setVisibility(View.INVISIBLE);
			down_img2.setVisibility(View.INVISIBLE);
			up_image4.setVisibility(View.INVISIBLE);
			down_img4.setVisibility(View.INVISIBLE);
			up_image5.setVisibility(View.INVISIBLE);
			down_image5.setVisibility(View.INVISIBLE);
			iv_zdkcslup.setVisibility(View.INVISIBLE);
			iv_zdkcsldown.setVisibility(View.INVISIBLE);
			break;
		case 5:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			up_image1.setVisibility(View.INVISIBLE);
			down_img1.setVisibility(View.INVISIBLE);
			up_image2.setVisibility(View.INVISIBLE);
			down_img2.setVisibility(View.INVISIBLE);
			up_image3.setVisibility(View.INVISIBLE);
			down_img3.setVisibility(View.INVISIBLE);
			iv_zdkcslup.setVisibility(View.INVISIBLE);
			iv_zdkcsldown.setVisibility(View.INVISIBLE);
			break;
			
		case 6:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			up_image1.setVisibility(View.INVISIBLE);
			down_img1.setVisibility(View.INVISIBLE);
			up_image2.setVisibility(View.INVISIBLE);
			down_img2.setVisibility(View.INVISIBLE);
			up_image3.setVisibility(View.INVISIBLE);
			down_img3.setVisibility(View.INVISIBLE);
			iv_zdkcslup.setVisibility(View.INVISIBLE);
			iv_zdkcsldown.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}

	}

	
	/**
	 * 筛选的用户
	 * @param checked_List
	 */
	public void getIviewdata(ArrayList<String> checked_List) {
		if (checked_List.size() != 0) {
			sclist.clear();
			for (int i = 0; i < checked_List.size(); i++) {
				IViewSCBean ivViewSCBean = new IViewSCBean();
				ivViewSCBean.setScCode(checked_List.get(i));
				sclist.add(ivViewSCBean);
			}
			getMonitorOperativeChannelInfo();
			Log.e("筛选过来的经销商", sclist.toString());
		}
	}
	
	
	/**
	 * 车型筛选
	 * @param carType
	 */
	public void getCarData(String carType) {
		if (!TextUtils.isEmpty(carType)) {
			ProfitApplication.profitNetService.getDiagnosisData2(txtStartTime.getText().toString().trim(),
					txtEndTime.getText().toString().trim(), sclist, handler,carType);
		 Log.e("筛选过来的车型", sclist.toString());
		}
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		checkedList.clear();//-----------------------------------------
		if (resultCode == KEY_DATA_OK) {
			checkedList = data.getStringArrayListExtra(KEY_DATA_USER);
			getIviewdata(checkedList);//经销商删选的用户
		}else if(resultCode == KEY_CAR_OK){//车型筛选
//			checkedList = data.getStringArrayListExtra(KEY_DATA_CAR);
			String checkCar = data.getStringExtra(KEY_DATA_CAR);
//			Log.e("车型筛选", checkCar.toString());
			getCarData(checkCar);//车型筛选
		}
	}

	
}
