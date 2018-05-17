package com.iris.cada.newfragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.adapter.NewinSuranceAdapter;
import com.iris.cada.adapter.SubstitutionAdapter;
import com.iris.cada.adapter.YbAdapter;
import com.iris.cada.adapter.ZhAdapter;
import com.iris.cada.comparator.AvgpermuteGrossComparatorAsc;
import com.iris.cada.comparator.AvgpermuteGrossComparatorDesc;
import com.iris.cada.comparator.PermuteSaleNumComparatorAsc;
import com.iris.cada.comparator.PermuteSaleNumComparatorDesc;
import com.iris.cada.comparator.PermuterateComparatorAsc;
import com.iris.cada.comparator.PermuterateComparatorDesc;
import com.iris.cada.comparator.PermuterebateComparatorAsc;
import com.iris.cada.comparator.PermuterebateComparatorDesc;
import com.iris.cada.comparator.ReportEnglishAndNumComparatorAsc;
import com.iris.cada.comparator.ReportEnglishAndNumComparatorDesc;
import com.iris.cada.comparator.TurnoverComparatorAsc;
import com.iris.cada.comparator.TurnoverComparatorDesc;
import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.utils.DateAndTimeUtils;
import com.iris.cada.utils.TimeRefreshUtils;
import com.iris.cada.utils.ToastUtils;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/* 置换
 * 报表盈利 的置换页面
 */
public class ReportSubstitutionFragment extends BaseFragment implements OnClickListener {
	private ListView lv;
	private ListView plv_zh;//总计和平均
	private List<IViewProfitReport> zjlist=new ArrayList<IViewProfitReport>();//总计集合
	private List<IViewProfitReport> pjlist=new ArrayList<IViewProfitReport>();//平均集合
	private ZhAdapter adapter;//平均总计适配器 
	private List<String> datas;
	private boolean bS = true;
	private AddTodoPickerViewUtils addTodoPickerViewUtils;
	private List<IViewProfitReport> FineProfitdata;
	private Date mStartDate;
	private Date mEndDate;
	private TextView star_data, end_data, fine_reposhow;
	private LinearLayout star_data_linner, end_data_linner;
	private ImageView image_up1, image_down1, image_up2, image_down2, image_up3, image_down3, image_up4, image_down4,
			image_up5, image_down5;
	private LinearLayout awy_linner, extended_linner, extended_infiltration_linner, extended_income_linner,
			extended_carlon_linner;
	private SubstitutionAdapter SubstitutionAdapter;
	private LocalBroadcastManager mLocalBroadcastManager = null;
	private BroadcastReceiver mBroadcastReceiver = null;

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

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
					fine_reposhow.setText(ProfitApplication.isConsultantMode ? "顾问" : "车型");
					getProfitInfo();
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
		View view = inflater.inflate(R.layout.fragment_substitution, container, false);
		initView(view);
		bindata();
		initData();
		return view;
	}

	private void bindata() {
		if (!ProfitApplication.isConsultantMode) {
			fine_reposhow.setText("车型");
		} else {
			fine_reposhow.setText("销售顾问");
		}

	}

	private void initView(View view) {
		lv = (ListView) view.findViewById(R.id.fragment_reort_carlon_lv);
		plv_zh=(ListView) view.findViewById(R.id.plv_zh);
		fine_reposhow = (TextView) view.findViewById(R.id.fragment_report_fine_reposhow);
		star_data = (TextView) view.findViewById(R.id.fragment_report_sub_stardata);
		end_data = (TextView) view.findViewById(R.id.fragment_report_sub_enddata);
		star_data_linner = (LinearLayout) view.findViewById(R.id.fragment_report_sub_stardata_linner);
		end_data_linner = (LinearLayout) view.findViewById(R.id.fragment_report_sub_enddata_linner);
		image_up1 = (ImageView) view.findViewById(R.id.fragment_report_fine_awy_image_up);
		image_down1 = (ImageView) view.findViewById(R.id.fragment_report_fine_awy_image_down);
		image_up2 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_image_up);
		image_down2 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_image_down);
		image_up3 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_infiltration_image_up);
		image_down3 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_infiltration_image_down);
		image_up4 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_income_image_up);
		image_down4 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_income_image_down);
		image_up5 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_carlon_image_up);
		image_down5 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_carlon_image_down);

		awy_linner = (LinearLayout) view.findViewById(R.id.fragment_report_awy_linner);
		extended_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_linner);
		extended_infiltration_linner = (LinearLayout) view
				.findViewById(R.id.fragment_report_fine_extended_infiltration_linner);
		extended_income_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_income_linner);
		extended_carlon_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_carlon_linner);

		star_data_linner.setOnClickListener(this);
		end_data_linner.setOnClickListener(this);
		awy_linner.setOnClickListener(this);
		extended_linner.setOnClickListener(this);
		extended_infiltration_linner.setOnClickListener(this);
		extended_income_linner.setOnClickListener(this);
		extended_carlon_linner.setOnClickListener(this);
		
		//新需求
		first_column_linner = (LinearLayout) view.findViewById(R.id.fragment_report_first_column_linner);
		first_column_linner.setOnClickListener(this);
		image_up0 = (ImageView) view.findViewById(R.id.fragment_report_first_column_image_up);
		image_down0 = (ImageView) view.findViewById(R.id.fragment_report_first_column_image_down);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_report_sub_stardata_linner:
			showDateDialog(star_data, true);
			break;
		case R.id.fragment_report_sub_enddata_linner:
			showDateDialog(end_data, false);
			break;
		case R.id.fragment_report_first_column_linner:
			firstColumnCompactor(bS,ProfitApplication.isConsultantMode);
			break;
		case R.id.fragment_report_awy_linner:
			TurnoverCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_linner:
			PermuterebatenumCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_infiltration_linner:
			PermuterenumCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_income_linner:
			PermuterebateCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_carlon_linner:
			avgPermuterenumCompara(bS);
			break;
		}

	}

	/** 显示日期选择器 */
	private void showDateDialog(final TextView tvTime, final boolean isStart) {
		TimePickerViewDialog pvTimeDialog = addTodoPickerViewUtils
				.initDatePickerView(isStart ? "请选择开始时间" : "请选择结束时间").getPvTime();
		pvTimeDialog.getDialog().show();

		pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				String strDate = null;
				if (isStart) {
//					strDate = addTodoPickerViewUtils.getDateNoDay(date) + "-01";
					strDate = addTodoPickerViewUtils.getDate(date);//开始日期
					mStartDate = DateAndTimeUtils.getDateTimeForStr("yyyy-MM-dd", strDate);
				} else {
//					strDate = DateAndTimeUtils.getLastDateOfMonth(date);
					strDate = addTodoPickerViewUtils.getDate(date);//结束日期
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

				TimeRefreshUtils.sendTimeRefreshMsg(getContext(), mStartDate, mEndDate);
				getProfitInfo();//获取数据
			}

			@Override
			public void onCancel() {

			}
		});
	}

	/**
	 * 获取数据
	 */
	private void getProfitInfo() {
		progressDialog.show();
		// Toast.makeText(getActivity(),
		// start_time.getText().toString().trim()+end_time.getText().toString().trim(),
		// Toast.LENGTH_SHORT).show();
		ProfitApplication.profitNetService.getiviewProfitServlet(star_data.getText().toString().trim(),
				end_data.getText().toString().trim(), "置换", "IViewProfitReportPermuteServlet", handler);
	}

	Handler handler = new MyHandler(this) {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
			case ProfitApplication.DATA_SUC:
				showdata(msg);
				// ToastUtils.showMyToast(getActivity(),
				// getString(R.string.our_income));
				break;
			case ProfitApplication.DATA_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				if (FineProfitdata != null) {
					FineProfitdata.clear();
					showOperativeInfo(FineProfitdata);
				}
				zjlist.clear();
				pjlist.clear();
				ZhAdapter exadpter=new ZhAdapter(getActivity(), zjlist, pjlist);
                plv_zh.setAdapter(exadpter);
				break;
			case ProfitApplication.SERVER_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_net));
				break;
			}
		}
	};
	private LinearLayout first_column_linner;
	private ImageView image_up0;
	private ImageView image_down0;

	private void showdata(Message msg) {
		FineProfitdata = new ArrayList<IViewProfitReport>();
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			FineProfitdata.clear();
		}
		if(zjlist!=null){
			zjlist.clear();
		}
		if(pjlist!=null){
			pjlist.clear();
		}
		try {
			String strMsg = (String) msg.obj;
			FineProfitdata = new Gson().fromJson(strMsg, new TypeToken<ArrayList<IViewProfitReport>>() {
			}.getType());
			/**新加总计和平均(先把总计和平均的放到另外一个集合)**/		
	 	       
        	for(int i=0;i<FineProfitdata.size();i++){
        		if(FineProfitdata.get(i).getModels().equals("总计")){	        		  
        			if(FineProfitdata.get(i).getModels().equals("总计")){
        			      zjlist.add(FineProfitdata.get(i));
        			      FineProfitdata.remove(i);
        			}
        			if(FineProfitdata.get(i).getModels().equals("平均")){
        				  pjlist.add(FineProfitdata.get(i));
        				  FineProfitdata.remove(i);
        			}
        			
        		}
        	
        	}
        	adapter=new ZhAdapter(getActivity(), zjlist, pjlist);	       
        	plv_zh.setAdapter(adapter);
			showOperativeInfo(FineProfitdata);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showOperativeInfo(List<IViewProfitReport> fineProfitdata) {
		SubstitutionAdapter = new SubstitutionAdapter(getActivity(), fineProfitdata);
//		TurnoverCompara(false);
		firstColumnCompactor(false,ProfitApplication.isConsultantMode);
		lv.setAdapter(SubstitutionAdapter);
	}

	private void initData() {
		addTodoPickerViewUtils = new AddTodoPickerViewUtils(getActivity());

		// 日期默认设置为本月日期
		if (null == ProfitApplication.mStartDate) {
			mStartDate = DateAndTimeUtils.getCurrentMonthOfFirstDay();
		} else {
			mStartDate = ProfitApplication.mStartDate;
		}
		star_data.setText(DateAndTimeUtils.getTimeForDate("yyyy-MM-dd", mStartDate));

		if (null == ProfitApplication.mEndDate) {
			mEndDate = DateAndTimeUtils.getCurrentDate("yyyy-MM-dd");
			end_data.setText(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"));
		} else {
			mEndDate = ProfitApplication.mEndDate;
			end_data.setText(DateAndTimeUtils.getTimeForDate("yyyy-MM-dd", mEndDate));
		}

		getProfitInfo();
	}
	
	
	//第一列比较器
		public void firstColumnCompactor(boolean b,boolean isConsultantMode) {
				if (FineProfitdata != null && FineProfitdata.size() != 0) {
					ShutImage(0);
					if (b) {
						Collections.sort(FineProfitdata, new ReportEnglishAndNumComparatorAsc(isConsultantMode));
						SubstitutionAdapter.notifyDataSetChanged();
						bS = false;
						showimge(b, image_up0, image_down0);
					} else {
						Collections.sort(FineProfitdata, new ReportEnglishAndNumComparatorDesc(isConsultantMode));
						SubstitutionAdapter.notifyDataSetChanged();
						bS = true;
						showimge(b, image_up0, image_down0);
					}
				}
		}
	

	public void TurnoverCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(1);
			if (b) {
				Collections.sort(FineProfitdata, new TurnoverComparatorAsc());
				SubstitutionAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up1, image_down1);
			} else {
				Collections.sort(FineProfitdata, new TurnoverComparatorDesc());
				SubstitutionAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up1, image_down1);
			}
		}
	}

	// 置换反利
	public void PermuterebateCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(4);
			if (b) {
				Collections.sort(FineProfitdata, new PermuterebateComparatorAsc());
				SubstitutionAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up4, image_down4);
			} else {
				Collections.sort(FineProfitdata, new PermuterebateComparatorDesc());
				SubstitutionAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up4, image_down4);
			}
		}
	}

	// 置换数
	public void PermuterebatenumCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(2);
			if (b) {
				Collections.sort(FineProfitdata, new PermuteSaleNumComparatorAsc());
				SubstitutionAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up2, image_down2);
			} else {
				Collections.sort(FineProfitdata, new PermuteSaleNumComparatorDesc());
				SubstitutionAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up2, image_down2);
			}
		}
	}

	// PermuterateComparatorAsc
	// 置换率
	public void PermuterenumCompara(boolean b) {
		ShutImage(3);
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			if (b) {
				Collections.sort(FineProfitdata, new PermuterateComparatorAsc());
				SubstitutionAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up3, image_down3);
			} else {
				Collections.sort(FineProfitdata, new PermuterateComparatorDesc());
				SubstitutionAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up3, image_down3);
			}
		}
	}

	// 置换率
	public void avgPermuterenumCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(5);
			if (b) {
				Collections.sort(FineProfitdata, new AvgpermuteGrossComparatorAsc());
				SubstitutionAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up5, image_down5);
			} else {
				Collections.sort(FineProfitdata, new AvgpermuteGrossComparatorDesc());
				SubstitutionAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up5, image_down5);
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
		switch (image_index) {
		case 0:
			image_up1.setVisibility(View.INVISIBLE);
			image_down1.setVisibility(View.INVISIBLE);
			image_up2.setVisibility(View.INVISIBLE);
			image_down2.setVisibility(View.INVISIBLE);
			image_up3.setVisibility(View.INVISIBLE);
			image_down3.setVisibility(View.INVISIBLE);
			image_up4.setVisibility(View.INVISIBLE);
			image_down4.setVisibility(View.INVISIBLE);
			image_up5.setVisibility(View.INVISIBLE);
			image_down5.setVisibility(View.INVISIBLE);
			break;
		case 1:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			image_up2.setVisibility(View.INVISIBLE);
			image_down2.setVisibility(View.INVISIBLE);
			image_up3.setVisibility(View.INVISIBLE);
			image_down3.setVisibility(View.INVISIBLE);
			image_up4.setVisibility(View.INVISIBLE);
			image_down4.setVisibility(View.INVISIBLE);
			image_up5.setVisibility(View.INVISIBLE);
			image_down5.setVisibility(View.INVISIBLE);
			break;
		case 2:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			image_up1.setVisibility(View.INVISIBLE);
			image_down1.setVisibility(View.INVISIBLE);
			image_up3.setVisibility(View.INVISIBLE);
			image_down3.setVisibility(View.INVISIBLE);
			image_up4.setVisibility(View.INVISIBLE);
			image_down4.setVisibility(View.INVISIBLE);
			image_up5.setVisibility(View.INVISIBLE);
			image_down5.setVisibility(View.INVISIBLE);
			break;
		case 3:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			image_up1.setVisibility(View.INVISIBLE);
			image_down1.setVisibility(View.INVISIBLE);
			image_up2.setVisibility(View.INVISIBLE);
			image_down2.setVisibility(View.INVISIBLE);
			image_up4.setVisibility(View.INVISIBLE);
			image_down4.setVisibility(View.INVISIBLE);
			image_up5.setVisibility(View.INVISIBLE);
			image_down5.setVisibility(View.INVISIBLE);
			break;
		case 4:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			image_up1.setVisibility(View.INVISIBLE);
			image_down1.setVisibility(View.INVISIBLE);
			image_up3.setVisibility(View.INVISIBLE);
			image_down3.setVisibility(View.INVISIBLE);
			image_up2.setVisibility(View.INVISIBLE);
			image_down2.setVisibility(View.INVISIBLE);
			image_up5.setVisibility(View.INVISIBLE);
			image_down5.setVisibility(View.INVISIBLE);
			break;
		case 5:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			image_up1.setVisibility(View.INVISIBLE);
			image_down1.setVisibility(View.INVISIBLE);
			image_up3.setVisibility(View.INVISIBLE);
			image_down3.setVisibility(View.INVISIBLE);
			image_up2.setVisibility(View.INVISIBLE);
			image_down2.setVisibility(View.INVISIBLE);
			image_up4.setVisibility(View.INVISIBLE);
			image_down4.setVisibility(View.INVISIBLE);
			break;
		}
	}
}
