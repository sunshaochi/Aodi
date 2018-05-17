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
import com.iris.cada.adapter.ReportFineAdapter;
import com.iris.cada.adapter.ReportShangPaiAdapter;
import com.iris.cada.adapter.SpAdapter;
import com.iris.cada.adapter.ZhAdapter;
import com.iris.cada.comparator.AvgShangPaiGrossComparatorAsc;
import com.iris.cada.comparator.AvgShangPaiGrossComparatorDesc;
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
import com.iris.cada.comparator.LastInsuranceSaleNumComparatorAsc;
import com.iris.cada.comparator.LastInsuranceSaleNumComparatorDesc;
import com.iris.cada.comparator.ReportEnglishAndNumComparatorAsc;
import com.iris.cada.comparator.ReportEnglishAndNumComparatorDesc;
import com.iris.cada.comparator.ShangPaiCountCompactorAsc;
import com.iris.cada.comparator.ShangPaiCountCompactorDesc;
import com.iris.cada.comparator.ShangPaiGrossComparatorAsc;
import com.iris.cada.comparator.ShangPaiGrossComparatorDesc;
import com.iris.cada.comparator.ShangPaiGrossrateComparatorAsc;
import com.iris.cada.comparator.ShangPaiGrossrateComparatorDesc;
import com.iris.cada.comparator.ShangPaiShenTouRateCompactorAsc;
import com.iris.cada.comparator.ShangPaiShenTouRateCompactorDesc;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 上牌
 * 报表--盈利中的上牌
 * 
 * @author geng
 *
 */
public class ReportShangPaiFragment extends BaseFragment implements OnClickListener{

	private LinearLayout star_time_linner;//开始日期
	private TextView stardata;//开始日期文本
	private LinearLayout end_time_linner;//结束日期
	private TextView enddata;//结束日期文本
	
	private TextView shang_pai_reposhow;//盈利表现
	//总交车数
	private LinearLayout awy_linner;
	private ImageView awy_image_up;
	private ImageView awy_image_down;
	//上牌数
	private LinearLayout shang_pai_count_linner;
	private ImageView shang_pai_count_image_up;
	private TextView shang_pai_count_text_down;
	private ImageView shang_pai_count_image_down;
	//渗透率
	private LinearLayout shen_tou_rate_linner;
	private ImageView shen_tou_rate_image_up;
	private TextView shen_tou_rate_text_down;
	private ImageView shen_tou_rate_image_down;
	//上牌总毛利
	private LinearLayout total_maoli_linner;
	private ImageView total_maoli_image_up;
	private TextView total_maoli_text_up;
	private TextView total_maoli_text_down;
	private ImageView total_maoli_image_down;
	//毛利率
	private LinearLayout maoli_rate_linner;
	private ImageView maoli_rate_image_up;
	private TextView maoli_rate_text_up;
	private ImageView maoli_rate_image_down;
	//单车  上牌毛利
	private LinearLayout one_shangpai_linner;
	private ImageView one_shangpai_image_up;
	private TextView one_shangpai_text_up;
	private TextView one_shangpai_text_down;
	private ImageView one_shangpai_image_down;
	
	private ListView shang_pai_listview;
	private ListView plv_sp;//总计和平均
	private List<IViewProfitReport> zjlist=new ArrayList<IViewProfitReport>();//总计集合
	private List<IViewProfitReport> pjlist=new ArrayList<IViewProfitReport>();//平均集合
	private SpAdapter adapter;//平均总计适配器 
	
	
	
	private LocalBroadcastManager mLocalBroadcastManager = null;
	private BroadcastReceiver mBroadcastReceiver = null;
	
	private AddTodoPickerViewUtils addTodoPickerViewUtils;
	private Date mStartDate;
	private boolean bS = true;
	private Date mEndDate;
	
	private List<IViewProfitReport> shangPacadadata;
	private ReportShangPaiAdapter reportShangPaiAdapter;
	
	
	
	Handler handler = new MyHandler(this) {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
			case ProfitApplication.DATA_SUC:
				showdata(msg);
				break;
			case ProfitApplication.DATA_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				if (shangPacadadata != null) {
					shangPacadadata.clear();
					showOperativeInfo(shangPacadadata);
				}
				zjlist.clear();
				pjlist.clear();
				SpAdapter exadpter=new SpAdapter(getActivity(), zjlist, pjlist);
                plv_sp.setAdapter(exadpter);
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
		shangPacadadata = new ArrayList<IViewProfitReport>();
		if (shangPacadadata != null && shangPacadadata.size() != 0) {
			shangPacadadata.clear();
		}
		if(zjlist!=null){
			zjlist.clear();
		}
		if(pjlist!=null){
			pjlist.clear();
		}
		try {
			String strMsg = (String) msg.obj;
			shangPacadadata = new Gson().fromJson(strMsg, new TypeToken<ArrayList<IViewProfitReport>>() {
			}.getType());
			/**新加总计和平均(先把总计和平均的放到另外一个集合)**/		
	 	    Log.e("上牌车市", shangPacadadata.toString());
        	for(int i=0;i<shangPacadadata.size();i++){
        		if(shangPacadadata.get(i).getModels().equals("总计")){	        		  
        			if(shangPacadadata.get(i).getModels().equals("总计")){
        			      zjlist.add(shangPacadadata.get(i));
        			      shangPacadadata.remove(i);
        			}
        			if(shangPacadadata.get(i).getModels().equals("平均")){
        				  pjlist.add(shangPacadadata.get(i));
        				  shangPacadadata.remove(i);
        			}
        			
        		}
        	
        	}
        	adapter=new SpAdapter(getActivity(), zjlist, pjlist);	       
        	plv_sp.setAdapter(adapter);
			showOperativeInfo(shangPacadadata);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 数据展示
	 * 
	 * @param fineProfitdata2
	 */
	private void showOperativeInfo(List<IViewProfitReport> shangPacadadatas) {
		reportShangPaiAdapter = new ReportShangPaiAdapter(getActivity(), shangPacadadatas);
//		TurnoverCompara(false);
		firstColumnCompactor(false,ProfitApplication.isConsultantMode);
		shang_pai_listview.setAdapter(reportShangPaiAdapter);
	}
	


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
					shang_pai_reposhow.setText(ProfitApplication.isConsultantMode ? "销售顾问" : "车型");
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
		View view = inflater.inflate(R.layout.fragment_report_shangpai, container, false);
		initView(view);
		initData();
		bindata();
		return view;
	}
	
	
	private void initData() {
		addTodoPickerViewUtils = new AddTodoPickerViewUtils(getActivity());

		// 日期默认设置为本月日期
		if (null == ProfitApplication.mStartDate) {
			mStartDate = DateAndTimeUtils.getCurrentMonthOfFirstDay();
		} else {
			mStartDate = ProfitApplication.mStartDate;
		}
		stardata.setText(DateAndTimeUtils.getTimeForDate("yyyy-MM-dd", mStartDate));

		if (null == ProfitApplication.mEndDate) {
			mEndDate = DateAndTimeUtils.getCurrentDate("yyyy-MM-dd");
			enddata.setText(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"));
		} else {
			mEndDate = ProfitApplication.mEndDate;
			enddata.setText(DateAndTimeUtils.getTimeForDate("yyyy-MM-dd", mEndDate));
		}

		getProfitInfo();
	}
	
	/**请求数据*/
	private void getProfitInfo() {
		progressDialog.show();
		ProfitApplication.profitNetService.getiviewProfitServlet(stardata.getText().toString().trim(),
				enddata.getText().toString().trim(), "上牌", "IViewProfitReportOnCardServlet", handler);
	}
	
	
	private void bindata() {
		if (!ProfitApplication.isConsultantMode) {
			shang_pai_reposhow.setText("车型");
		} else {
			shang_pai_reposhow.setText("销售顾问");
		}
	}
	
	
	//第一列比较器
			public void firstColumnCompactor(boolean b,boolean isConsultantMode) {
					if (shangPacadadata != null && shangPacadadata.size() != 0) {
						ShutImage(0);
						if (b) {
							Collections.sort(shangPacadadata, new ReportEnglishAndNumComparatorAsc(isConsultantMode));
							reportShangPaiAdapter.notifyDataSetChanged();
							bS = false;
							showimge(b, image_up0, image_down0);
						} else {
							Collections.sort(shangPacadadata, new ReportEnglishAndNumComparatorDesc(isConsultantMode));
							reportShangPaiAdapter.notifyDataSetChanged();
							bS = true;
							showimge(b, image_up0, image_down0);
						}
					}
			}
	
	
	public void TurnoverCompara(boolean b) {
		if (shangPacadadata != null && shangPacadadata.size() != 0) {
			ShutImage(1);
			if (b) {
				Collections.sort(shangPacadadata, new TurnoverComparatorAsc());
				reportShangPaiAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, awy_image_up, awy_image_down);
			} else {
				Collections.sort(shangPacadadata, new TurnoverComparatorDesc());
				reportShangPaiAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, awy_image_up, awy_image_down);
			}
		}
	}
	
	public void shangPaiSaleNumCompara(boolean b) {//上牌数
		if (shangPacadadata != null && shangPacadadata.size() != 0) {
			ShutImage(2);
			if (b) {
				Collections.sort(shangPacadadata, new ShangPaiCountCompactorAsc());
				reportShangPaiAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, shang_pai_count_image_up, shang_pai_count_image_down);
			} else {
				Collections.sort(shangPacadadata, new ShangPaiCountCompactorDesc());
				reportShangPaiAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, shang_pai_count_image_up, shang_pai_count_image_down);
			}
		}
	}
	
	
	public void shangPaiShenTouRateCompara(boolean b) {//渗透率
		if (shangPacadadata != null && shangPacadadata.size() != 0) {
			ShutImage(3);
			if (b) {
				Collections.sort(shangPacadadata, new ShangPaiShenTouRateCompactorAsc());
				reportShangPaiAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, shen_tou_rate_image_up, shen_tou_rate_image_down);
			} else {
				Collections.sort(shangPacadadata, new ShangPaiShenTouRateCompactorDesc());
				reportShangPaiAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, shen_tou_rate_image_up, shen_tou_rate_image_down);
			}
		}
	}
	
	
	public void shangPaiTotalGrossCompara(boolean b) {//总毛利
		if (shangPacadadata != null && shangPacadadata.size() != 0) {
			ShutImage(4);
			if (b) {
				Collections.sort(shangPacadadata, new ShangPaiGrossComparatorAsc());
				reportShangPaiAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, total_maoli_image_up, total_maoli_image_down);
			} else {
				Collections.sort(shangPacadadata, new ShangPaiGrossComparatorDesc());
				reportShangPaiAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, total_maoli_image_up, total_maoli_image_down);
			}
		}
	}
	
	
	public void shangPaiGrossrateCompara(boolean b) {//毛利率
		if (shangPacadadata != null && shangPacadadata.size() != 0) {
			ShutImage(5);
			if (b) {
				Collections.sort(shangPacadadata, new ShangPaiGrossrateComparatorAsc());
				reportShangPaiAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, maoli_rate_image_up, maoli_rate_image_down);
			} else {
				Collections.sort(shangPacadadata, new ShangPaiGrossrateComparatorDesc());
				reportShangPaiAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, maoli_rate_image_up, maoli_rate_image_down);
			}
		}
	}
	
	
	public void avgShangPaiCompara(boolean b) {//单车上牌毛利
		if (shangPacadadata != null && shangPacadadata.size() != 0) {
			ShutImage(6);
			if (b) {
				Collections.sort(shangPacadadata, new AvgShangPaiGrossComparatorAsc());
				reportShangPaiAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, one_shangpai_image_up, one_shangpai_image_down);
			} else {
				Collections.sort(shangPacadadata, new AvgShangPaiGrossComparatorDesc());
				reportShangPaiAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, one_shangpai_image_up, one_shangpai_image_down);
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
					enddata.setText(strEndDate);
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



	private void initView(View view) {
		stardata = (TextView) view.findViewById(R.id.shang_pai_stardata);
		star_time_linner = (LinearLayout) view.findViewById(R.id.shang_pai_star_time_linner);
		end_time_linner = (LinearLayout) view.findViewById(R.id.shang_pai_end_time_linner);
		enddata = (TextView) view.findViewById(R.id.shang_pai_enddata);
		shang_pai_reposhow = (TextView) view.findViewById(R.id.shang_pai_reposhow);
		//总交车数
		awy_linner = (LinearLayout) view.findViewById(R.id.shang_pai_awy_linner);
		awy_image_up = (ImageView) view.findViewById(R.id.shang_pai_awy_image_up);
		awy_image_down = (ImageView) view.findViewById(R.id.shang_pai_awy_image_down);
		
		//上牌数
		shang_pai_count_linner = (LinearLayout) view.findViewById(R.id.shang_pai_count_linner);
		shang_pai_count_image_up = (ImageView) view.findViewById(R.id.shang_pai_count_image_up);
		shang_pai_count_text_down = (TextView) view.findViewById(R.id.shang_pai_count_text_down);
		shang_pai_count_image_down = (ImageView) view.findViewById(R.id.shang_pai_count_image_down);
		//渗透率
		shen_tou_rate_linner = (LinearLayout) view.findViewById(R.id.shang_pai_shen_tou_rate_linner);
		shen_tou_rate_image_up = (ImageView) view.findViewById(R.id.shang_pai_shen_tou_rate_image_up);
		shen_tou_rate_text_down = (TextView) view.findViewById(R.id.shang_pai_shen_tou_rate_text_down);
		shen_tou_rate_image_down = (ImageView) view.findViewById(R.id.shang_pai_shen_tou_rate_image_down);
		//上牌总毛利
		total_maoli_linner = (LinearLayout) view.findViewById(R.id.shang_pai_total_maoli_linner);
		total_maoli_image_up = (ImageView) view.findViewById(R.id.shang_pai_total_maoli_image_up);
		total_maoli_text_up = (TextView) view.findViewById(R.id.shang_pai_total_maoli_text_up);
		total_maoli_text_down = (TextView) view.findViewById(R.id.shang_pai_total_maoli_text_down);
		total_maoli_image_down = (ImageView) view.findViewById(R.id.shang_pai_total_maoli_image_down);
		
		//毛利率
		maoli_rate_linner = (LinearLayout) view.findViewById(R.id.shang_pai_maoli_rate_linner);
		maoli_rate_image_up = (ImageView) view.findViewById(R.id.shang_pai_maoli_rate_image_up);
		maoli_rate_text_up = (TextView) view.findViewById(R.id.shang_pai_maoli_rate_text_up);
		maoli_rate_image_down = (ImageView) view.findViewById(R.id.shang_pai_maoli_rate_image_down);
		
		//单车  上牌毛利
		one_shangpai_linner = (LinearLayout) view.findViewById(R.id.shang_pai_one_shangpai_linner);
		one_shangpai_image_up = (ImageView) view.findViewById(R.id.shang_pai_one_shangpai_image_up);
		one_shangpai_text_up = (TextView) view.findViewById(R.id.shang_pai_one_shangpai_text_up);
		one_shangpai_text_down = (TextView) view.findViewById(R.id.shang_pai_one_shangpai_text_down);
		one_shangpai_image_down = (ImageView) view.findViewById(R.id.shang_pai_one_shangpai_image_down);
		
		
		star_time_linner.setOnClickListener(this);
		end_time_linner.setOnClickListener(this);
		awy_linner.setOnClickListener(this);
		shang_pai_count_linner.setOnClickListener(this);
		shen_tou_rate_linner.setOnClickListener(this);
		total_maoli_linner.setOnClickListener(this);
		maoli_rate_linner.setOnClickListener(this);
		one_shangpai_linner.setOnClickListener(this);
		//ListView
		shang_pai_listview = (ListView) view.findViewById(R.id.shang_pai_listview);
		plv_sp=(ListView) view.findViewById(R.id.plv_sp);
		
		//新需求
		first_column_linner = (LinearLayout) view.findViewById(R.id.fragment_report_first_column_linner);
		first_column_linner.setOnClickListener(this);
		image_up0 = (ImageView) view.findViewById(R.id.fragment_report_first_column_image_up);
		image_down0 = (ImageView) view.findViewById(R.id.fragment_report_first_column_image_down);
		
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_report_first_column_linner:
			firstColumnCompactor(bS,ProfitApplication.isConsultantMode);
			break;
		case R.id.shang_pai_awy_linner://总交车数
			TurnoverCompara(bS);
			break;
		case R.id.shang_pai_count_linner://上牌数
			shangPaiSaleNumCompara(bS);
			break;
		case R.id.shang_pai_shen_tou_rate_linner://渗透率
			shangPaiShenTouRateCompara(bS);
			break;
		case R.id.shang_pai_total_maoli_linner://总毛利
			shangPaiTotalGrossCompara(bS);
			break;
		case R.id.shang_pai_maoli_rate_linner://毛利率
			shangPaiGrossrateCompara(bS);
			break;
		case R.id.shang_pai_one_shangpai_linner://单车--上牌毛利
			avgShangPaiCompara(bS);
			break;
		case R.id.shang_pai_star_time_linner:
			showDateDialog(stardata, true);
			break;
		case R.id.shang_pai_end_time_linner:
			showDateDialog(enddata, false);
			break;
		}
	}


	public void ShutImage(int image_index) {
		switch (image_index) {
		case 0:
			awy_image_up.setVisibility(View.INVISIBLE);
			awy_image_down.setVisibility(View.INVISIBLE);
			shang_pai_count_image_up.setVisibility(View.INVISIBLE);
			shang_pai_count_image_down.setVisibility(View.INVISIBLE);
			shen_tou_rate_image_up.setVisibility(View.INVISIBLE);
			shen_tou_rate_image_down.setVisibility(View.INVISIBLE);
			total_maoli_image_up.setVisibility(View.INVISIBLE);
			total_maoli_image_down.setVisibility(View.INVISIBLE);
			maoli_rate_image_up.setVisibility(View.INVISIBLE);
			maoli_rate_image_down.setVisibility(View.INVISIBLE);
			one_shangpai_image_up.setVisibility(View.INVISIBLE);
			one_shangpai_image_down.setVisibility(View.INVISIBLE);
			break;
		case 1:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			shang_pai_count_image_up.setVisibility(View.INVISIBLE);
			shang_pai_count_image_down.setVisibility(View.INVISIBLE);
			shen_tou_rate_image_up.setVisibility(View.INVISIBLE);
			shen_tou_rate_image_down.setVisibility(View.INVISIBLE);
			total_maoli_image_up.setVisibility(View.INVISIBLE);
			total_maoli_image_down.setVisibility(View.INVISIBLE);
			maoli_rate_image_up.setVisibility(View.INVISIBLE);
			maoli_rate_image_down.setVisibility(View.INVISIBLE);
			one_shangpai_image_up.setVisibility(View.INVISIBLE);
			one_shangpai_image_down.setVisibility(View.INVISIBLE);
			break;
		case 2:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			awy_image_up.setVisibility(View.INVISIBLE);
			awy_image_down.setVisibility(View.INVISIBLE);
			shen_tou_rate_image_up.setVisibility(View.INVISIBLE);
			shen_tou_rate_image_down.setVisibility(View.INVISIBLE);
			total_maoli_image_up.setVisibility(View.INVISIBLE);
			total_maoli_image_down.setVisibility(View.INVISIBLE);
			maoli_rate_image_up.setVisibility(View.INVISIBLE);
			maoli_rate_image_down.setVisibility(View.INVISIBLE);
			one_shangpai_image_up.setVisibility(View.INVISIBLE);
			one_shangpai_image_down.setVisibility(View.INVISIBLE);
			break;
		case 3:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			awy_image_up.setVisibility(View.INVISIBLE);
			awy_image_down.setVisibility(View.INVISIBLE);
			shang_pai_count_image_up.setVisibility(View.INVISIBLE);
			shang_pai_count_image_down.setVisibility(View.INVISIBLE);
			total_maoli_image_up.setVisibility(View.INVISIBLE);
			total_maoli_image_down.setVisibility(View.INVISIBLE);
			maoli_rate_image_up.setVisibility(View.INVISIBLE);
			maoli_rate_image_down.setVisibility(View.INVISIBLE);
			one_shangpai_image_up.setVisibility(View.INVISIBLE);
			one_shangpai_image_down.setVisibility(View.INVISIBLE);
			break;
		case 4://总毛利
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			awy_image_up.setVisibility(View.INVISIBLE);
			awy_image_down.setVisibility(View.INVISIBLE);
			shang_pai_count_image_up.setVisibility(View.INVISIBLE);
			shang_pai_count_image_down.setVisibility(View.INVISIBLE);
			shen_tou_rate_image_up.setVisibility(View.INVISIBLE);
			shen_tou_rate_image_down.setVisibility(View.INVISIBLE);
			maoli_rate_image_up.setVisibility(View.INVISIBLE);
			maoli_rate_image_down.setVisibility(View.INVISIBLE);
			one_shangpai_image_up.setVisibility(View.INVISIBLE);
			one_shangpai_image_down.setVisibility(View.INVISIBLE);
			break;
		case 5://毛利率
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			awy_image_up.setVisibility(View.INVISIBLE);
			awy_image_down.setVisibility(View.INVISIBLE);
			shang_pai_count_image_up.setVisibility(View.INVISIBLE);
			shang_pai_count_image_down.setVisibility(View.INVISIBLE);
			shen_tou_rate_image_up.setVisibility(View.INVISIBLE);
			shen_tou_rate_image_down.setVisibility(View.INVISIBLE);
			total_maoli_image_up.setVisibility(View.INVISIBLE);
			total_maoli_image_down.setVisibility(View.INVISIBLE);
			one_shangpai_image_up.setVisibility(View.INVISIBLE);
			one_shangpai_image_down.setVisibility(View.INVISIBLE);
			break;
		case 6://单车上牌毛利
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			awy_image_up.setVisibility(View.INVISIBLE);
			awy_image_down.setVisibility(View.INVISIBLE);
			shang_pai_count_image_up.setVisibility(View.INVISIBLE);
			shang_pai_count_image_down.setVisibility(View.INVISIBLE);
			shen_tou_rate_image_up.setVisibility(View.INVISIBLE);
			shen_tou_rate_image_down.setVisibility(View.INVISIBLE);
			total_maoli_image_up.setVisibility(View.INVISIBLE);
			total_maoli_image_down.setVisibility(View.INVISIBLE);
			maoli_rate_image_up.setVisibility(View.INVISIBLE);
			maoli_rate_image_down.setVisibility(View.INVISIBLE);
			break;
		}
	}

}
