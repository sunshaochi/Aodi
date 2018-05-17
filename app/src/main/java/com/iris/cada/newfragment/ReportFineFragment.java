package com.iris.cada.newfragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.adapter.AdcZjAdpter;
import com.iris.cada.adapter.DiagDetailsAdapter;
import com.iris.cada.adapter.JpAdapter;
import com.iris.cada.adapter.ReportFineAdapter;
import com.iris.cada.comparator.AvgbountiqueGrossComparatorAsc;
import com.iris.cada.comparator.AvgbountiqueGrossComparatorDesc;
import com.iris.cada.comparator.BountiqueAddrateComparatorAsc;
import com.iris.cada.comparator.BountiqueAddrateComparatorDesc;
import com.iris.cada.comparator.BountiqueGrossComparatorAsc;
import com.iris.cada.comparator.BountiqueGrossComparatorDesc;
import com.iris.cada.comparator.BountiqueGrossrateComparatorAsc;
import com.iris.cada.comparator.BountiqueGrossrateComparatorDesc;
import com.iris.cada.comparator.BountiqueSaleNumComparatorAsc;
import com.iris.cada.comparator.BountiqueSaleNumComparatorDesc;
import com.iris.cada.comparator.EnglishAndNumCompactorAsc;
import com.iris.cada.comparator.EnglishAndNumCompactorDesc;
import com.iris.cada.comparator.OperateagainrateAsc;
import com.iris.cada.comparator.OperateagainrateDesc;
import com.iris.cada.comparator.ReportEnglishAndNumComparatorAsc;
import com.iris.cada.comparator.ReportEnglishAndNumComparatorDesc;
import com.iris.cada.comparator.TurnoverComparatorAsc;
import com.iris.cada.comparator.TurnoverComparatorDesc;
import com.iris.cada.entity.ILogData;
import com.iris.cada.entity.IViewFirst;
import com.iris.cada.entity.IViewOperateReport;
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
import android.widget.Toast;

/**
 * 精品那列
 * @ClassName: ReportFineFragment
 * @Description: TODO()
 * @author iris-gjh
 * @date 2016年10月11日 上午10:07:54 報表盈利精品頁面
 * 
 */
public class ReportFineFragment extends BaseFragment implements OnClickListener {
	private boolean bS = true;
	private ListView report_fine_lv;
	private ListView plv_jp;//总计平局那列
	private JpAdapter adapter;//平均总计适配器
	private List<String> datas;
	private TextView star_data, end_data, fine_reposhow;// 开始日期,结束日期
	private Date mStartDate;
	private Date mEndDate;
	private List<IViewProfitReport> zjlist=new ArrayList<IViewProfitReport>();//总计集合
	private List<IViewProfitReport> pjlist=new ArrayList<IViewProfitReport>();//平均集合
	private LinearLayout star_time_linner, end_time_linner;
	private LinearLayout awy_linner, extended_linner, infiltration_linner, extended_income_linner,
			extended_gross_linner, carlon_linner;
	private ImageView awy_image_up1, awy_image_down1, extended_image_up2, extended_image_down2, infiltration_image_up3,
			infiltration_image_down3, carlon_image_up6, carlon_image_down6;
	private ImageView income_image_up4, income_image_down4, gross_image_up5, gross_image_down5;
	private AddTodoPickerViewUtils addTodoPickerViewUtils;
	private List<IViewProfitReport> FineProfitdata;
	private ReportFineAdapter reportFineAdapter;
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
				}

				if (action.equals(ProfitApplication.TIME_REFRESH_MESSAGE)) {
					initData();
				}

				getProfitInfo();
			}
		};
		mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_report_fine, container, false);
		initView(view);

		// fragment_diag_lv.setAdapter(new
		// DiagDetailsAdapter(getActivity(),datas));
		initData();
		bindata();
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
		// TODO Auto-generated method stub
		fine_reposhow = (TextView) view.findViewById(R.id.fragment_report_fine_reposhow);
		end_time_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_end_time_linner);
		star_time_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_star_time_linner);
		awy_linner = (LinearLayout) view.findViewById(R.id.fragment_report_awy_linner);
		extended_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_linner);
		infiltration_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_infiltration_linner);
		extended_income_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_income_linner);
		extended_gross_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_gross_linner);
		carlon_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_carlon_linner);
		awy_image_up1 = (ImageView) view.findViewById(R.id.fragment_report_fine_awy_image_up);
		awy_image_down1 = (ImageView) view.findViewById(R.id.fragment_report_fine_awy_image_down);
		extended_image_up2 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_image_up);
		extended_image_down2 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_image_down);
		infiltration_image_up3 = (ImageView) view
				.findViewById(R.id.fragment_report_fine_extended_infiltration_image_up);
		infiltration_image_down3 = (ImageView) view
				.findViewById(R.id.fragment_report_fine_extended_infiltration_image_down);
		income_image_up4 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_income_image_up);
		income_image_down4 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_income_image_down);
		gross_image_up5 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_gross_image_up);
		gross_image_down5 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_gross_image_down);
		carlon_image_up6 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_carlon_image_up);
		carlon_image_down6 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_carlon_image_down);

		awy_linner.setOnClickListener(this);
		extended_linner.setOnClickListener(this);
		infiltration_linner.setOnClickListener(this);
		extended_income_linner.setOnClickListener(this);
		extended_gross_linner.setOnClickListener(this);
		carlon_linner.setOnClickListener(this);
		star_time_linner.setOnClickListener(this);
		end_time_linner.setOnClickListener(this);
		awy_linner = (LinearLayout) view.findViewById(R.id.fragment_report_awy_linner);
		star_data = (TextView) view.findViewById(R.id.fragment_report_fine_stardata);
		end_data = (TextView) view.findViewById(R.id.fragment_report_fine_enddata);
		report_fine_lv = (ListView) view.findViewById(R.id.fragment_report_fine_listview);
		plv_jp=(ListView) view.findViewById(R.id.plv_jp);
		
		//新需求
		first_column_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_first_column_linner);
		first_column_linner.setOnClickListener(this);
		first_image_up0 = (ImageView) view.findViewById(R.id.fragment_report_fine_first_column_image_up);
		first_image_down0 = (ImageView) view.findViewById(R.id.fragment_report_fine_first_column_image_down);
		
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.fragment_report_first_column_linner:
			firstColumnCompactor(bS,ProfitApplication.isConsultantMode);
			break;
		case R.id.fragment_report_awy_linner:
			TurnoverCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_linner:
			BountiqueSaleNumCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_infiltration_linner:
			BountiqueAddCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_income_linner:
			BountiqueGrossCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_gross_linner:
			BountiqueGrossrateCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_carlon_linner:
			AvgbountiqueCompara(bS);
			break;
		case R.id.fragment_report_fine_star_time_linner:
			showDateDialog(star_data, true);
			break;
		case R.id.fragment_report_fine_end_time_linner:
			showDateDialog(end_data, false);
			break;
		}
	}
	
	
	/**
	 * 获取竞品数据
	 */
	private void getProfitInfo() {
		progressDialog.show();
		// Toast.makeText(getActivity(),
		// start_time.getText().toString().trim()+end_time.getText().toString().trim(),
		// Toast.LENGTH_SHORT).show();
		ProfitApplication.profitNetService.getiviewProfitServlet(star_data.getText().toString().trim(),
				end_data.getText().toString().trim(), "精品", "IViewProfitReportBoutiqueServlet", handler);
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
				JpAdapter exadpter=new JpAdapter(getActivity(), zjlist, pjlist);
                plv_jp.setAdapter(exadpter);
				break;
			case ProfitApplication.SERVER_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_net));
				break;
			}
		}
	};
	private LinearLayout first_column_linner;
	private ImageView first_image_up0;
	private ImageView first_image_down0;

	/**
	 * 显示竞品数据
	 * @param msg
	 */
	private void showdata(Message msg) {
		
		first_image_up0.setVisibility(View.INVISIBLE);
		first_image_down0.setVisibility(View.VISIBLE);
		awy_image_up1.setVisibility(View.INVISIBLE);
		awy_image_down1.setVisibility(View.INVISIBLE);
		
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
        	    adapter=new JpAdapter(getActivity(), zjlist, pjlist);	       
	        	plv_jp.setAdapter(adapter);
			showOperativeInfo(FineProfitdata);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
				getProfitInfo();

			}

			@Override
			public void onCancel() {

			}
		});
	}
	
	
	//第一列比较器
	public void firstColumnCompactor(boolean b,boolean isConsultantMode) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(0);
			if (b) {
				Collections.sort(FineProfitdata, new ReportEnglishAndNumComparatorAsc(isConsultantMode));
				reportFineAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, first_image_up0, first_image_down0);
			} else {
				Collections.sort(FineProfitdata, new ReportEnglishAndNumComparatorDesc(isConsultantMode));
				reportFineAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, first_image_up0, first_image_down0);
			}
		}
	}
	

	public void TurnoverCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(1);
			if (b) {
				Collections.sort(FineProfitdata, new TurnoverComparatorAsc());
				reportFineAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, awy_image_up1, awy_image_down1);
			} else {
				Collections.sort(FineProfitdata, new TurnoverComparatorDesc());
				reportFineAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, awy_image_up1, awy_image_down1);
			}
		}
	}

	public void AvgbountiqueCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(6);
			if (b) {
				Collections.sort(FineProfitdata, new AvgbountiqueGrossComparatorAsc());
				reportFineAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, carlon_image_up6, carlon_image_down6);
			} else {
				Collections.sort(FineProfitdata, new AvgbountiqueGrossComparatorDesc());
				reportFineAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, carlon_image_up6, carlon_image_down6);
			}
		}
	}

	public void BountiqueSaleNumCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(2);
			if (b) {
				Collections.sort(FineProfitdata, new BountiqueSaleNumComparatorAsc());
				reportFineAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, extended_image_up2, extended_image_down2);
			} else {
				Collections.sort(FineProfitdata, new BountiqueSaleNumComparatorDesc());
				reportFineAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, extended_image_up2, extended_image_down2);
			}
		}
	}

	public void BountiqueGrossrateCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(5);
			if (b) {
				Collections.sort(FineProfitdata, new BountiqueGrossrateComparatorAsc());
				reportFineAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, gross_image_up5, gross_image_down5);
			} else {
				Collections.sort(FineProfitdata, new BountiqueGrossrateComparatorDesc());
				reportFineAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, gross_image_up5, gross_image_down5);
			}
		}
	}

	public void BountiqueAddCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(3);
			if (b) {
				Collections.sort(FineProfitdata, new BountiqueAddrateComparatorAsc());
				reportFineAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, infiltration_image_up3, infiltration_image_down3);
			} else {
				Collections.sort(FineProfitdata, new BountiqueAddrateComparatorDesc());
				reportFineAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, infiltration_image_up3, infiltration_image_down3);
			}
		}
	}

	public void BountiqueGrossCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(4);
			if (b) {
				Collections.sort(FineProfitdata, new BountiqueGrossComparatorAsc());
				reportFineAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, income_image_up4, income_image_down4);
			} else {
				Collections.sort(FineProfitdata, new BountiqueGrossComparatorDesc());
				reportFineAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, income_image_up4, income_image_down4);
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

	/**
	 * 数据展示
	 * 
	 * @param fineProfitdata2
	 */
	private void showOperativeInfo(List<IViewProfitReport> fineProfitdatas) {
		reportFineAdapter = new ReportFineAdapter(getActivity(), fineProfitdatas);
//		TurnoverCompara(false);
		firstColumnCompactor(false,ProfitApplication.isConsultantMode);
		report_fine_lv.setAdapter(reportFineAdapter);

		// Toast.makeText(getActivity(), fineProfitdatas.get(0).toString(),
		// Toast.LENGTH_SHORT).show();

	}

	public void ShutImage(int image_index) {
		switch (image_index) {
		case 0:
			awy_image_up1.setVisibility(View.INVISIBLE);
			awy_image_down1.setVisibility(View.INVISIBLE);
			extended_image_up2.setVisibility(View.INVISIBLE);
			extended_image_down2.setVisibility(View.INVISIBLE);
			infiltration_image_up3.setVisibility(View.INVISIBLE);
			infiltration_image_down3.setVisibility(View.INVISIBLE);
			income_image_up4.setVisibility(View.INVISIBLE);
			income_image_down4.setVisibility(View.INVISIBLE);
			gross_image_up5.setVisibility(View.INVISIBLE);
			gross_image_down5.setVisibility(View.INVISIBLE);
			carlon_image_up6.setVisibility(View.INVISIBLE);
			carlon_image_down6.setVisibility(View.INVISIBLE);
			break;
		case 1:
			first_image_up0.setVisibility(View.INVISIBLE);
			first_image_down0.setVisibility(View.INVISIBLE);
			extended_image_up2.setVisibility(View.INVISIBLE);
			extended_image_down2.setVisibility(View.INVISIBLE);
			infiltration_image_up3.setVisibility(View.INVISIBLE);
			infiltration_image_down3.setVisibility(View.INVISIBLE);
			income_image_up4.setVisibility(View.INVISIBLE);
			income_image_down4.setVisibility(View.INVISIBLE);
			gross_image_up5.setVisibility(View.INVISIBLE);
			gross_image_down5.setVisibility(View.INVISIBLE);
			carlon_image_up6.setVisibility(View.INVISIBLE);
			carlon_image_down6.setVisibility(View.INVISIBLE);
			break;
		case 2:
			first_image_up0.setVisibility(View.INVISIBLE);
			first_image_down0.setVisibility(View.INVISIBLE);
			awy_image_up1.setVisibility(View.INVISIBLE);
			awy_image_down1.setVisibility(View.INVISIBLE);
			infiltration_image_up3.setVisibility(View.INVISIBLE);
			infiltration_image_down3.setVisibility(View.INVISIBLE);
			income_image_up4.setVisibility(View.INVISIBLE);
			income_image_down4.setVisibility(View.INVISIBLE);
			gross_image_up5.setVisibility(View.INVISIBLE);
			gross_image_down5.setVisibility(View.INVISIBLE);
			carlon_image_up6.setVisibility(View.INVISIBLE);
			carlon_image_down6.setVisibility(View.INVISIBLE);
			break;
		case 3:
			first_image_up0.setVisibility(View.INVISIBLE);
			first_image_down0.setVisibility(View.INVISIBLE);
			awy_image_up1.setVisibility(View.INVISIBLE);
			awy_image_down1.setVisibility(View.INVISIBLE);
			extended_image_up2.setVisibility(View.INVISIBLE);
			extended_image_down2.setVisibility(View.INVISIBLE);
			income_image_up4.setVisibility(View.INVISIBLE);
			income_image_down4.setVisibility(View.INVISIBLE);
			gross_image_up5.setVisibility(View.INVISIBLE);
			gross_image_down5.setVisibility(View.INVISIBLE);
			carlon_image_up6.setVisibility(View.INVISIBLE);
			carlon_image_down6.setVisibility(View.INVISIBLE);
			break;
		case 4:
			first_image_up0.setVisibility(View.INVISIBLE);
			first_image_down0.setVisibility(View.INVISIBLE);
			awy_image_up1.setVisibility(View.INVISIBLE);
			awy_image_down1.setVisibility(View.INVISIBLE);
			infiltration_image_up3.setVisibility(View.INVISIBLE);
			infiltration_image_down3.setVisibility(View.INVISIBLE);
			extended_image_up2.setVisibility(View.INVISIBLE);
			extended_image_down2.setVisibility(View.INVISIBLE);
			gross_image_up5.setVisibility(View.INVISIBLE);
			gross_image_down5.setVisibility(View.INVISIBLE);
			carlon_image_up6.setVisibility(View.INVISIBLE);
			carlon_image_down6.setVisibility(View.INVISIBLE);
			break;
		case 5:
			first_image_up0.setVisibility(View.INVISIBLE);
			first_image_down0.setVisibility(View.INVISIBLE);
			awy_image_up1.setVisibility(View.INVISIBLE);
			awy_image_down1.setVisibility(View.INVISIBLE);
			infiltration_image_up3.setVisibility(View.INVISIBLE);
			infiltration_image_down3.setVisibility(View.INVISIBLE);
			extended_image_up2.setVisibility(View.INVISIBLE);
			extended_image_down2.setVisibility(View.INVISIBLE);
			income_image_up4.setVisibility(View.INVISIBLE);
			income_image_down4.setVisibility(View.INVISIBLE);
			carlon_image_up6.setVisibility(View.INVISIBLE);
			carlon_image_down6.setVisibility(View.INVISIBLE);
			break;
		case 6:
			first_image_up0.setVisibility(View.INVISIBLE);
			first_image_down0.setVisibility(View.INVISIBLE);
			awy_image_up1.setVisibility(View.INVISIBLE);
			awy_image_down1.setVisibility(View.INVISIBLE);
			infiltration_image_up3.setVisibility(View.INVISIBLE);
			infiltration_image_down3.setVisibility(View.INVISIBLE);
			extended_image_up2.setVisibility(View.INVISIBLE);
			extended_image_down2.setVisibility(View.INVISIBLE);
			income_image_up4.setVisibility(View.INVISIBLE);
			income_image_down4.setVisibility(View.INVISIBLE);
			gross_image_up5.setVisibility(View.INVISIBLE);
			gross_image_down5.setVisibility(View.INVISIBLE);
			break;
		}
	}
}
