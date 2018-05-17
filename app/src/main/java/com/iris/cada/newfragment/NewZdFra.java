package com.iris.cada.newfragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.FilterConsultantActivity;
import com.iris.cada.activity.NewSettingAct;
import com.iris.cada.activity.SeparateAct;
import com.iris.cada.activity.ShsxAct;
import com.iris.cada.adapter.Diagnosisadapter;
import com.iris.cada.adapter.MyGridViewAdapter;
import com.iris.cada.adapter.ZdDiagnosisadapter;
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
import com.iris.cada.comparator.ZdkcslComparatorAsc;
import com.iris.cada.comparator.ZdkcslComparatorDesc;
import com.iris.cada.entity.Check;
import com.iris.cada.entity.IViewSCBean;
import com.iris.cada.entity.ShzdBean;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.utils.DateAndTimeUtils;
import com.iris.cada.utils.StringUtils;
import com.iris.cada.utils.TimeRefreshUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.utils.WebUtils;
import com.iris.cada.utils.ZdCheckJieDaiComparatorAsc;
import com.iris.cada.utils.ZdCheckJieDaiComparatorDesc;
import com.iris.cada.utils.ZdCheckLookToBuyRateComparatorAsc;
import com.iris.cada.utils.ZdCheckLookToBuyRateComparatorDes;
import com.iris.cada.utils.ZdCheckXinPotentialCustomerComparatorAsc;
import com.iris.cada.utils.ZdCheckXinPotentialCustomerComparatorDesc;
import com.iris.cada.utils.ZdDeriComparatorAsc;
import com.iris.cada.utils.ZdDeriComparatorDesc;
import com.iris.cada.utils.ZdDiagEnglishAndNumCompactorAsc;
import com.iris.cada.utils.ZdDiagEnglishNumDesc;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.cada.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NewZdFra extends Fragment implements OnClickListener {
	private List<String> datas;
	private ListView lv;
	private ImageView setting_img;
	private ImageView img_last_data, img_next_data;
	private TextView star_data, fragment_xiaoshou;
	private AddTodoPickerViewUtils addPickerViewUtils;
	private MyGridViewAdapter myGridViewAdapter = null;
	private String timeFormat = null;
	private Gson gson = null;
	private List<ShzdBean> Diagnosislist = new ArrayList<ShzdBean>();
	private List<ShzdBean> Diagnosislistdatas = new ArrayList<ShzdBean>();
	private TextView item_detail_name, XinPotential_tv, LookToBuy_tv, DerivativesBusiness_tv, fine_reposhow;
	private String mModeSelect = "All";
	private boolean isMonth = true;
	private AddTodoPickerViewUtils addTodoPickerViewUtils;
	private Date mStartDate;
	private Date mEndDate;
	private LinearLayout report_awy_linner;
	private ZdDiagnosisadapter diagnosisadapter;
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

	// 车型筛选
	public static final int KEY_CAR_OK = 101;
	public static final String KEY_DATA_CAR = "KEY_DATA_CAR";

	public static final int REQUSET = 1;
	public ArrayList<String> checkedList = new ArrayList<String>();// 被选中的的集合
	public ArrayList<IViewSCBean> sclist = new ArrayList<IViewSCBean>();

	private RelativeLayout layoutStartTime;
	private TextView txtStartTime;
	private RelativeLayout layoutEndTime;
	private TextView txtEndTime;
	private LinearLayout jiedai_linner;
	private ImageView up_image5;
	private ImageView down_image5;
	private TextView jieDai_avg;

	private TextView tv_pjzdkcsl;// 平均那列的库存数量

	private ImageView iv_zdkcslup, iv_zdkcsldown;
	private LinearLayout ll_zdkcsl;

	private TextView tv_jd, tv_xz, tv_cj, tv_dc;
	private LinearLayout ll_bottem;

	private String customerName;// 顾问模式要传递的值
	private String carType;// 车型模式要传递的值
	
	private String stime,etime;//开始时间，结束时间
	private Date startdate,enddate;//开始时间Date格式，结束时间Date格式

	Handler handler = new MyHandler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case ProfitApplication.DATA_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				Diagnosislist.clear();
				Diagnosislistdatas.clear();
				diagnosisadapter = new ZdDiagnosisadapter(getActivity(), Diagnosislistdatas,Diagnosislist,txtStartTime.getText().toString(), txtEndTime.getText().toString());
				diagnosisadapter.notifyDataSetChanged();
				lv.setAdapter(diagnosisadapter);
				XinPotential_tv.setText("0");
				LookToBuy_tv.setText("0");
				DerivativesBusiness_tv.setText("0");
				jieDai_avg.setText("0");// 接待总数
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

	};

	/**
	 * 解析并设置在lv上
	 * 
	 * @param msg
	 */
	private void dealChannelInfo(Message msg) {
		if (Diagnosislist != null) {// 后台返回的数据
			Diagnosislist.clear();
		}
		try {
			String strMsg = (String) msg.obj;
			Diagnosislist = gson.fromJson(new JSONObject(strMsg).getJSONArray("data").toString(),
					new TypeToken<List<ShzdBean>>() {
					}.getType());

			showOperativeInfo(Diagnosislist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showOperativeInfo(List<ShzdBean> diagnosislist) {
		if (diagnosislist != null && diagnosislist.size() != 0) {
			Diagnosislistdatas.clear();
			// 遍历集合 拿到除去第一条的 其他几条数据。
			for (int i = 0; i < diagnosislist.size(); i++) {
				if (i == 0) {
					continue;
				} else {
					Diagnosislistdatas.add(diagnosislist.get(i));// 拿去除掉第一条的数据
				}
			}
		}
		Log.e("除掉第一条的数据", Diagnosislistdatas.toString());
		Log.e("原始数据", diagnosislist.toString());
		diagnosisadapter = new ZdDiagnosisadapter(getActivity(), Diagnosislistdatas,diagnosislist,txtStartTime.getText().toString(), txtEndTime.getText().toString());
		Collections.sort(Diagnosislistdatas, new ZdDiagEnglishNumDesc(ProfitApplication.isConsultantMode));
		Log.e("监控数据", Diagnosislistdatas.toString());
		diagnosisadapter.notifyDataSetChanged();
		lv.setAdapter(diagnosisadapter);// 设置数据

		XinPotential_tv.setText(diagnosislist.get(0).get合计收入());
		LookToBuy_tv.setText(diagnosislist.get(0).get合计毛利());
		DerivativesBusiness_tv.setText(diagnosislist.get(0).get平均单车收入());
		jieDai_avg.setText(diagnosislist.get(0).get维修台次());// 接待总数

	}

//	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
//		View localView = paramLayoutInflater.inflate(R.layout.fragment_diagnosis, paramViewGroup, false);
//		initView(localView);
//		return localView;
//
//	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		return super.onCreateView(inflater, container, savedInstanceState);
		View localView=inflater.inflate(R.layout.fragment_diagnosis, container, false);
		initView(localView);
		return localView;
	}

	/**
	 * 初始化控件
	 * 
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

		tv_jd = (TextView) paramView.findViewById(R.id.tv_jd);// 接待
		tv_xz = (TextView) paramView.findViewById(R.id.tv_xz);// 新增

		tv_cj = (TextView) paramView.findViewById(R.id.fragment_report_fine_extended_text_up);
		tv_dc = (TextView) paramView.findViewById(R.id.fragment_report_fine_extended_infiltration_text_down);

		ll_bottem=(LinearLayout) paramView.findViewById(R.id.ll_bottem);
		this.lv = ((ListView) paramView.findViewById(R.id.fragment_reort_carlon_lv));
		income_text_up = (TextView) paramView.findViewById(R.id.fragment_report_fine_extended_income_text_up);
		fine_reposhow = (TextView) paramView.findViewById(R.id.fragment_report_fine_reposhow);
		report_awy_linner = (LinearLayout) paramView.findViewById(R.id.fragment_report_awy_linner);
		setting_img = (ImageView) paramView.findViewById(R.id.fragment_diagno_title_setting);
		item_detail_name = (TextView) paramView.findViewById(R.id.diadeal_name);
		XinPotential_tv = (TextView) paramView.findViewById(R.id.item_diadeal_name1);
		LookToBuy_tv = (TextView) paramView.findViewById(R.id.item_diadeal_name2);
		DerivativesBusiness_tv = (TextView) paramView.findViewById(R.id.item_diadeal_name3);
		up_image1 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_awy_image_up);// 新增潜客对应的箭头
		down_img1 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_awy_image_down);
		up_image2 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_extended_image_up);// 成交率对应的箭头
		down_img2 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_extended_image_down);
		up_image3 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_extended_infiltration_image_up);// 衍生业务对应的箭头
		down_img3 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_extended_infiltration_image_down);
		up_image4 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_extended_income_image_up);// 个人诊断对应的箭头
		down_img4 = (ImageView) paramView.findViewById(R.id.fragment_report_fine_extended_income_image_down);
		iv_zdkcslup = (ImageView) paramView.findViewById(R.id.iv_zdkcslup);
		iv_zdkcsldown = (ImageView) paramView.findViewById(R.id.iv_zdkcsldown);
		ll_zdkcsl = (LinearLayout) paramView.findViewById(R.id.ll_zdkcsl);
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
		tv_pjzdkcsl = (TextView) paramView.findViewById(R.id.tv_pjzdkcsl);

		// 新需求：默认按第一列排序
		first_column_linner = (LinearLayout) paramView.findViewById(R.id.fragment_report_first_column_linner);
		first_column_linner.setOnClickListener(this);
		image_up0 = (ImageView) paramView.findViewById(R.id.fragment_report_first_column_image_up);
		image_down0 = (ImageView) paramView.findViewById(R.id.fragment_report_first_column_image_down);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		ll_bottem.setVisibility(View.GONE);
		timeFormat = "yyyy-MM-dd";
		addPickerViewUtils = new AddTodoPickerViewUtils(getActivity());
		gson = new Gson();
		txtStartTime.setText(WebUtils.getStart());
		txtEndTime.setText(WebUtils.getend());

		customerName = "";// 默认什么都不传
		carType = "";

		if (ProfitApplication.isConsultantMode == true) {// 顾问模式车型模式给空字符串
			getGwInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "gw", customerName);// 先获取数据拼接到h5界面
		} else if (ProfitApplication.isConsultantMode == false) {// 车型模式
			getCarInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "cx", carType);
		}

		bindata();// 根据不同情况显示不同界面

	}

	/** 顾问模式 **/
	private void getGwInfo(String startDate, String endDate, String type, String content) {
		// TODO Auto-generated method stub
		ProfitApplication.profitNetService.getShzdinfo(startDate, endDate, type, content, handler);
	}

	/** 车型模式 **/
	private void getCarInfo(String startDate, String endDate, String type, String content) {
		// TODO Auto-generated method stub
		ProfitApplication.profitNetService.getShzdinfo(startDate, endDate, type, content, handler);
	}

	// 根据不同情况改变界面显示的
	private void bindata() {
		if (!ProfitApplication.isConsultantMode) {// 车型模式
			fine_reposhow.setText("车型");
			item_detail_name.setText("车型平均");
			income_text_up.setText("车型诊断");
			fragment_xiaoshou.setText("车型");

			mFilter.setVisibility(View.VISIBLE);// 筛选
			ll_zdkcsl.setVisibility(View.GONE);
			tv_pjzdkcsl.setVisibility(View.GONE);

		} else {
			fine_reposhow.setText("服务顾问");
			item_detail_name.setText("团队平均");
			income_text_up.setText("个人诊断");
			fragment_xiaoshou.setText("服务顾问");
			mFilter.setVisibility(View.VISIBLE);// 筛选
			ll_zdkcsl.setVisibility(View.GONE);
			tv_pjzdkcsl.setVisibility(View.GONE);

		}

		tv_jd.setText("维修台次");
		tv_xz.setText("收入");
		tv_cj.setText("毛利");
		tv_dc.setText("   平均\n单车收入");

	}

	// 监听
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fragment_diagno_title_setting:
			// 跳转侧滑设置页面
			// ((MainActivity) getActivity()).getSlidingMenu().toggle();
			Intent intent1 = new Intent(getActivity(), NewSettingAct.class);
			startActivity(intent1);
			break;
		case R.id.fragment_detail_star_adc_rela:
//			showDateDialog(txtStartTime, true);
			showDateDialog(true);//选择开始时间
			break;
		case R.id.fragment_detail_end_adc_rela:
//			showDateDialog(txtEndTime, false);
        	showDateDialog(false);//选择开始时间
			break;
		case R.id.fragment_report_first_column_linner:// 第一列
			firstColumnCompactor(bS, ProfitApplication.isConsultantMode);
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
			Intent intent = new Intent(getActivity(), ShsxAct.class);
			intent.putExtra("startTime", txtStartTime.getText().toString().trim());
			intent.putExtra("endTime", txtEndTime.getText().toString().trim());
			intent.putExtra("date", (Serializable) Diagnosislistdatas);
			startActivityForResult(intent, REQUSET);
			break;

		case R.id.ll_zdkcsl:// 库存那列
			// kucSlCompara(bS);

			break;
		}
	}

//	/** 显示日期选择器 */
//	private void showDateDialog(final TextView tvTime, final boolean isStart) {
//		TimePickerViewDialog pvTimeDialog = addPickerViewUtils.initDatePickerView(isStart ? "请选择开始时间" : "请选择结束时间")
//				.getPvTime();
//		pvTimeDialog.getDialog().show();
//
//		pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {
//
//			@Override
//			public void onTimeSelect(Date date) {
//				String strDate = null;
//				if (isStart) {
//					// strDate = addPickerViewUtils.getDateNoDay(date) + "-01";
//					strDate = addPickerViewUtils.getDate(date);// 开始日期
//					mStartDate = DateAndTimeUtils.getDateTimeForStr(timeFormat, strDate);
//				} else {
//					// strDate = DateAndTimeUtils.getLastDateOfMonth(date);
//					strDate = addPickerViewUtils.getDate(date);// 结束日期
//					mEndDate = DateAndTimeUtils.getDateTimeForStr(timeFormat, strDate);
//				}
//
//				if (isStart) {
//					tvTime.setText(strDate);
//					String strEndDate = DateAndTimeUtils.getLastDateOfMonth(mStartDate);
//					mEndDate = DateAndTimeUtils.getDateTimeForStr(timeFormat, strEndDate);
//					txtEndTime.setText(strEndDate);
//
//				} else {
//					if (DateAndTimeUtils.isEqual(mStartDate, mEndDate)) {
//						tvTime.setText(strDate);
//					} else if (!DateAndTimeUtils.isBefore(mStartDate, mEndDate)) {
//						ToastUtils.showMyToast(getActivity(), "“结束时间”不可小于“开始时间”");
//						return;
//					} else {
//						tvTime.setText(strDate);
//					}
//				}
//
//				ProfitApplication.mStartDate = mStartDate;
//				ProfitApplication.mEndDate = mEndDate;
//
//				if (ProfitApplication.isConsultantMode == true) {// 顾问模式车型模式给空字符串
//					getGwInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "gw", customerName);// 先获取数据拼接到h5界面
//				} else if (ProfitApplication.isConsultantMode == false) {// 车型模式
//					getCarInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "cx", carType);
//				}
//
//			}
//
//			@Override
//			public void onCancel() {
//
//			}
//		});
//	}

	/****
     * 
     * @param b
     */
	private void showDateDialog(final boolean b) {
		// TODO Auto-generated method stub
		TimePickerViewDialog pvTimeDialog = addPickerViewUtils.initDatePickerView(b ? "请选择开始时间" : "请选择结束时间")
				.getPvTime();
		pvTimeDialog.getDialog().show();
		pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {
			
			@Override
			public void onTimeSelect(Date date) {
				// TODO Auto-generated method stub
				if(b){			
					if(!DateAndTimeUtils.isBefore(date, DateAndTimeUtils.getCurrentDate(timeFormat))){//开始时间比当前时间大
						ToastUtils.showMyToast(getActivity(), "开始时间不可大于当前时间");
						return;
					}else {//开始时间比当前时间小
						stime=addPickerViewUtils.getDate(date);//开始时间
						txtStartTime.setText(stime);
						
						startdate=DateAndTimeUtils.getDateTimeForStr(timeFormat, stime);//开始时间锉
						ProfitApplication.mStartDate=startdate;
						etime=DateAndTimeUtils.getLastDateOfMonth(startdate);
						enddate=DateAndTimeUtils.getDateTimeForStr(timeFormat, etime);
						
						if(!DateAndTimeUtils.isBefore(enddate, DateAndTimeUtils.getCurrentDate(timeFormat))){
							txtEndTime.setText(DateAndTimeUtils.getCurrentTime(timeFormat));//当前时间
							ProfitApplication.mEndDate=DateAndTimeUtils.getCurrentDate(timeFormat);
						}else{
							txtEndTime.setText(etime);
							ProfitApplication.mEndDate=enddate;
						}
						if (ProfitApplication.isConsultantMode == true) {// 顾问模式车型模式给空字符串
							getGwInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "gw", customerName);// 先获取数据拼接到h5界面
						} else if (ProfitApplication.isConsultantMode == false) {// 车型模式
							getCarInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "cx", carType);
						}
					}
				
				}else{
					if(!DateAndTimeUtils.isBefore(date, DateAndTimeUtils.getCurrentDate(timeFormat))){//结束时间比当前时间大	
						ToastUtils.showMyToast(getActivity(), "结束时间不可大于当前时间");
						return;
					}else{
						etime=addPickerViewUtils.getDate(date);//结束时间
						enddate=DateAndTimeUtils.getDateTimeForStr(timeFormat, etime);//结束时间锉
						if (DateAndTimeUtils.isEqual(startdate, enddate)) {//结束时间等于开始时间
							txtEndTime.setText(etime);
							ProfitApplication.mEndDate=enddate;
							if (ProfitApplication.isConsultantMode == true) {// 顾问模式车型模式给空字符串
								getGwInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "gw", customerName);// 先获取数据拼接到h5界面
							} else if (ProfitApplication.isConsultantMode == false) {// 车型模式
								getCarInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "cx", carType);
							}
						}else if (!DateAndTimeUtils.isBefore(startdate, enddate)) {//结束时间小于开始时间
							ToastUtils.showMyToast(getActivity(), "“结束时间”不可小于“开始时间”");
							return;
						}else {//结束时间大于开始时间小于当前时间
							txtEndTime.setText(etime);
							ProfitApplication.mEndDate=enddate;
							if (ProfitApplication.isConsultantMode == true) {// 顾问模式车型模式给空字符串
								getGwInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "gw", customerName);// 先获取数据拼接到h5界面
							} else if (ProfitApplication.isConsultantMode == false) {// 车型模式
								getCarInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "cx", carType);
							}
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


	
	private LinearLayout first_column_linner;
	private ImageView image_up0;
	private ImageView image_down0;

	private void LookTobyCompara(boolean b) {
		if (Diagnosislistdatas != null && Diagnosislistdatas.size() != 0) {
			ShutImage(2);
			if (b) {
				// Collections.sort(Diagnosislistdatas, new
				// CheckLookToBuyRateComparatorAsc());
				Collections.sort(Diagnosislistdatas, new ZdCheckLookToBuyRateComparatorAsc());
				diagnosisadapter.notifyDataSetChanged();
				bS = false;
				showimge(b, up_image2, down_img2);
			} else {
				// Collections.sort(Diagnosislistdatas, new
				// CheckLookToBuyRateComparatorDes());
				Collections.sort(Diagnosislistdatas, new ZdCheckLookToBuyRateComparatorDes());
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
				// Collections.sort(Diagnosislistdatas, new
				// CheckDerivativesBusinessAverageCarProfitsComparatorAsc());
				Collections.sort(Diagnosislistdatas, new ZdDeriComparatorAsc());
				diagnosisadapter.notifyDataSetChanged();
				bS = false;
				showimge(b, up_image3, down_img3);
			} else {
				// Collections.sort(Diagnosislistdatas, new
				// CheckDerivativesBusinessAverageCarProfitsComparatorDesc());
				Collections.sort(Diagnosislistdatas, new ZdDeriComparatorDesc());
				diagnosisadapter.notifyDataSetChanged();
				bS = true;
				showimge(b, up_image3, down_img3);
			}
		}
	}

	// 第一列
	public void firstColumnCompactor(boolean b, boolean isConsultantMode) {
		if (Diagnosislistdatas != null && Diagnosislistdatas.size() != 0) {
			ShutImage(0);
			if (b) {
				// Collections.sort(Diagnosislistdatas, new
				// DiagEnglishAndNumCompactorAsc(isConsultantMode));
				Collections.sort(Diagnosislistdatas, new ZdDiagEnglishAndNumCompactorAsc(isConsultantMode));
				diagnosisadapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up0, image_down0);
			} else {
				// Collections.sort(Diagnosislistdatas, new
				// DiagEnglishAndNumCompactorDesc(isConsultantMode));
				Collections.sort(Diagnosislistdatas, new ZdDiagEnglishNumDesc(ProfitApplication.isConsultantMode));
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
				// Collections.sort(Diagnosislistdatas, new
				// CheckJieDaiComparatorAsc());
				Collections.sort(Diagnosislistdatas, new ZdCheckJieDaiComparatorAsc());
				diagnosisadapter.notifyDataSetChanged();
				bS = false;
				showimge(b, up_image5, down_image5);
			} else {
				// Collections.sort(Diagnosislistdatas, new
				// CheckJieDaiComparatorDesc());
				Collections.sort(Diagnosislistdatas, new ZdCheckJieDaiComparatorDesc());
				diagnosisadapter.notifyDataSetChanged();
				bS = true;
				showimge(b, up_image5, down_image5);
			}
		}
	}

	public void Compara(boolean b) {
		if (Diagnosislistdatas != null && Diagnosislistdatas.size() != 0) {
			ShutImage(1);
			if (b) {

				Collections.sort(Diagnosislistdatas, new ZdCheckXinPotentialCustomerComparatorAsc());
				diagnosisadapter.notifyDataSetChanged();
				bS = false;
				showimge(b, up_image1, down_img1);
			} else {

				Collections.sort(Diagnosislistdatas, new ZdCheckXinPotentialCustomerComparatorDesc());
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

	public void ShutImage(int image_index) {
		switch (image_index) {// 4对应的是个人诊断，这个箭头没有，这一列不需要排序
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		checkedList.clear();// -----------------------------------------
		if (resultCode == KEY_DATA_OK) {
			String datem = data.getStringExtra("KEY_DATA_MODUE");
			if (TextUtils.isEmpty(datem)) {
				customerName = "";
			} else {
				customerName = datem;
			}
			getGwInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "gw", customerName);// 先获取数据拼接到h5界面

		} else if (resultCode == KEY_CAR_OK) {// 车型筛选
			String datec = data.getStringExtra(KEY_DATA_CAR);
			if (TextUtils.isEmpty(datec)) {
				carType = "";

			} else {
				carType = datec;
			}
			getCarInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "cx", carType);// 先获取数据拼接到h5界面
		}
	}

	public void reflush() {
		bindata();
		// TODO Auto-generated method stub
		txtStartTime.setText(WebUtils.getStart());
		txtEndTime.setText(WebUtils.getend());

		if (ProfitApplication.isConsultantMode == true) {// 顾问模式车型模式给空字符串
			getGwInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "gw", customerName);// 先获取数据拼接到h5界面
		} else if (ProfitApplication.isConsultantMode == false) {// 车型模式
			getCarInfo(txtStartTime.getText().toString(), txtEndTime.getText().toString(), "cx", carType);
		}
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if(hidden){
			
		}else{
			reflush();
		}
	}

	

}
