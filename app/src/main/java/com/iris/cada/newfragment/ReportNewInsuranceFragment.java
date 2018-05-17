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
import com.iris.cada.adapter.JpAdapter;
import com.iris.cada.adapter.NewinSuranceAdapter;
import com.iris.cada.adapter.XbAdapter;
import com.iris.cada.comparator.ReportEnglishAndNumComparatorAsc;
import com.iris.cada.comparator.ReportEnglishAndNumComparatorDesc;
import com.iris.cada.comparator.TurnoverComparatorAsc;
import com.iris.cada.comparator.TurnoverComparatorDesc;
import com.iris.cada.comparator.XinInsuranceGrossrateComparatorAsc;
import com.iris.cada.comparator.XinInsuranceGrossrateComparatorDesc;
import com.iris.cada.comparator.XinInsuranceIncomeComparatorAsc;
import com.iris.cada.comparator.XinInsuranceIncomeComparatorDesc;
import com.iris.cada.comparator.XinInsuranceSaleNumComparatorAsc;
import com.iris.cada.comparator.XinInsuranceSaleNumComparatorDesc;
import com.iris.cada.comparator.XinInsurancepermeaterateComparatorAsc;
import com.iris.cada.comparator.XinInsurancepermeaterateComparatorDesc;
import com.iris.cada.comparator.XinPotentialCustomerComparatorAsc;
import com.iris.cada.comparator.XinPotentialCustomerComparatorDesc;
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

/**
 * 新保险fragment
 * @ClassName: NewInsuranceFragment
 * @Description: TODO()
 * @author iris-gjh
 * @date 2016年10月11日 下午5:58:23 盈利報表新保頁面
 */
public class ReportNewInsuranceFragment extends BaseFragment implements OnClickListener {
	private ListView lv;
	private ListView plv_xb;//总计和平均那列
	private XbAdapter adapter;//平均总计适配器 
	private List<IViewProfitReport> zjlist=new ArrayList<IViewProfitReport>();//总计集合
	private List<IViewProfitReport> pjlist=new ArrayList<IViewProfitReport>();//平均集合
	private boolean bS = true;
	private List<String> datas;
	private LinearLayout star_data_linner, end_data_linner;
	private TextView star_time, end_time, fine_reposhow;
	private List<IViewProfitReport> FineProfitdata;
	private NewinSuranceAdapter newinSuranceAdapter;

	private AddTodoPickerViewUtils addTodoPickerViewUtils;
	private Date mStartDate;
	private Date mEndDate;
	private ImageView image_up1, image_down1, image_up2, image_down2, image_up3, image_down3, image_up4, image_down4,
			image_up5, image_down5;
	private LinearLayout awy_linner, extended_linner, extended_infiltration_linner, extended_income_linner,
			extended_carlon_linner;
	private LocalBroadcastManager mLocalBroadcastManager = null;
	private BroadcastReceiver mBroadcastReceiver = null;
	
	private LinearLayout maoli_rate_linner;
	private ImageView image_up6;
	private ImageView image_down6;

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
		View view = inflater.inflate(R.layout.fragment_new_insurance, container, false);
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
		star_data_linner.setOnClickListener(this);
		end_data_linner.setOnClickListener(this);
		awy_linner.setOnClickListener(this);
		extended_linner.setOnClickListener(this);
		extended_infiltration_linner.setOnClickListener(this);
		extended_income_linner.setOnClickListener(this);
		extended_carlon_linner.setOnClickListener(this);
	}

	private void initData() {
		addTodoPickerViewUtils = new AddTodoPickerViewUtils(getActivity());

		// 日期默认设置为本月日期
		if (null == ProfitApplication.mStartDate) {
			mStartDate = DateAndTimeUtils.getCurrentMonthOfFirstDay();
		} else {
			mStartDate = ProfitApplication.mStartDate;
		}
		star_time.setText(DateAndTimeUtils.getTimeForDate("yyyy-MM-dd", mStartDate));

		if (null == ProfitApplication.mEndDate) {
			mEndDate = DateAndTimeUtils.getCurrentDate("yyyy-MM-dd");
			end_time.setText(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"));
		} else {
			mEndDate = ProfitApplication.mEndDate;
			end_time.setText(DateAndTimeUtils.getTimeForDate("yyyy-MM-dd", mEndDate));
		}

		getProfitInfo();
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		fine_reposhow = (TextView) view.findViewById(R.id.fragment_report_fine_reposhow);
		lv = (ListView) view.findViewById(R.id.fragment_reort_carlon_lv);
		plv_xb=(ListView) view.findViewById(R.id.plv_xb);
		star_data_linner = (LinearLayout) view.findViewById(R.id.fragment_new_in_star_data);
		end_data_linner = (LinearLayout) view.findViewById(R.id.fragment_new_in_end_data);
		awy_linner = (LinearLayout) view.findViewById(R.id.fragment_report_awy_linner);
		extended_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_linner);
		extended_infiltration_linner = (LinearLayout) view
				.findViewById(R.id.fragment_report_fine_extended_infiltration_linner);
		extended_income_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_income_linner);
		extended_carlon_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_carlon_linner);
		star_time = (TextView) view.findViewById(R.id.fragment_report_fine_star_time);
		end_time = (TextView) view.findViewById(R.id.fragment_report_fine_end_time);
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

		// 新增
		maoli_rate_linner = (LinearLayout) view.findViewById(R.id.xin_bao_maoli_rate_linner);
		maoli_rate_linner.setOnClickListener(this);
		image_up6 = (ImageView) view.findViewById(R.id.xin_bao_maoli_rate_image_up);
		image_down6 = (ImageView) view.findViewById(R.id.xin_bao_maoli_rate_image_down);
		
		//新需求
		first_column_linner = (LinearLayout) view.findViewById(R.id.fragment_report_first_column_linner);
		first_column_linner.setOnClickListener(this);
		image_up0 = (ImageView) view.findViewById(R.id.fragment_report_first_column_image_up);
		image_down0 = (ImageView) view.findViewById(R.id.fragment_report_first_column_image_down);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_new_in_star_data:
			showDateDialog(star_time, true);
			break;
		case R.id.fragment_new_in_end_data:
			showDateDialog(end_time, false);
			break;
		case R.id.fragment_report_first_column_linner:
			firstColumnCompactor(bS,ProfitApplication.isConsultantMode);
			break;
		case R.id.fragment_report_awy_linner:
			TurnoverCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_linner:
			newInsuranceSaleNumCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_infiltration_linner:
			newInsurancepermeaterateCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_income_linner:
			newInsuranceIncomeCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_carlon_linner:
			newPotentialCustCompara(bS);
			break;
		case R.id.xin_bao_maoli_rate_linner://新保毛利率
			xinBaoGrossrateCompara(bS);
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
					end_time.setText(strEndDate);
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

	private void getProfitInfo() {
		progressDialog.show();
		// Toast.makeText(getActivity(),
		// start_time.getText().toString().trim()+end_time.getText().toString().trim(),
		// Toast.LENGTH_SHORT).show();
		ProfitApplication.profitNetService.getiviewProfitServlet(star_time.getText().toString().trim(),
				end_time.getText().toString().trim(), "新保", "IViewProfitReportNewInsuranceServlet", handler);
	}

	Handler handler = new MyHandler() {

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
				XbAdapter exadpter=new XbAdapter(getActivity(), zjlist, pjlist);
                plv_xb.setAdapter(exadpter);
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
        	    adapter=new XbAdapter(getActivity(), zjlist, pjlist);	       
	        	plv_xb.setAdapter(adapter);
			showOperativeInfo(FineProfitdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showOperativeInfo(List<IViewProfitReport> fineProfitdata2) {
		newinSuranceAdapter = new NewinSuranceAdapter(getActivity(), fineProfitdata2);
//		TurnoverCompara(false);
		firstColumnCompactor(false,ProfitApplication.isConsultantMode);
		lv.setAdapter(newinSuranceAdapter);
	}
	
	
	//第一列比较器
		public void firstColumnCompactor(boolean b,boolean isConsultantMode) {
			if (FineProfitdata != null && FineProfitdata.size() != 0) {
				ShutImage(0);
				if (b) {
					Collections.sort(FineProfitdata, new ReportEnglishAndNumComparatorAsc(isConsultantMode));
					newinSuranceAdapter.notifyDataSetChanged();
					bS = false;
					showimge(b, image_up0, image_down0);
				} else {
					Collections.sort(FineProfitdata, new ReportEnglishAndNumComparatorDesc(isConsultantMode));
					newinSuranceAdapter.notifyDataSetChanged();
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
				newinSuranceAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up1, image_down1);
			} else {
				Collections.sort(FineProfitdata, new TurnoverComparatorDesc());
				newinSuranceAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up1, image_down1);
			}
		}
	}

	public void newInsurancepermeaterateCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(3);
			if (b) {
				Collections.sort(FineProfitdata, new XinInsurancepermeaterateComparatorAsc());
				newinSuranceAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up3, image_down3);
			} else {
				Collections.sort(FineProfitdata, new XinInsurancepermeaterateComparatorDesc());
				newinSuranceAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up3, image_down3);
			}
		}
	}

	public void newPotentialCustCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(5);
			if (b) {
				Collections.sort(FineProfitdata, new XinPotentialCustomerComparatorAsc());
				newinSuranceAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up5, image_down5);
			} else {
				Collections.sort(FineProfitdata, new XinPotentialCustomerComparatorDesc());
				newinSuranceAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up5, image_down5);
			}
		}
	}

	// InsuranceSaleNum
	public void newInsuranceSaleNumCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(2);
			if (b) {
				Collections.sort(FineProfitdata, new XinInsuranceSaleNumComparatorAsc());
				newinSuranceAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up2, image_down2);
			} else {
				Collections.sort(FineProfitdata, new XinInsuranceSaleNumComparatorDesc());
				newinSuranceAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up2, image_down2);
			}
		}
	}

	public void newInsuranceIncomeCompara(boolean b) {
		if (FineProfitdata != null && FineProfitdata.size() != 0) {
			ShutImage(4);
			if (b) {
				Collections.sort(FineProfitdata, new XinInsuranceIncomeComparatorAsc());
				newinSuranceAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up4, image_down4);
			} else {
				Collections.sort(FineProfitdata, new XinInsuranceIncomeComparatorDesc());
				newinSuranceAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up4, image_down4);
			}
		}
	}
	
	
		//毛利率
		public void  xinBaoGrossrateCompara(boolean b){
			if (FineProfitdata != null && FineProfitdata.size() != 0) {
				ShutImage(6);
				if (b) {
					Collections.sort(FineProfitdata, new XinInsuranceGrossrateComparatorAsc());
					newinSuranceAdapter.notifyDataSetChanged();
					bS = false;
					showimge(b, image_up6, image_down6);
				} else {
					Collections.sort(FineProfitdata, new XinInsuranceGrossrateComparatorDesc());
					newinSuranceAdapter.notifyDataSetChanged();
					bS = true;
					showimge(b, image_up6, image_down6);
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
			image_up6.setVisibility(View.INVISIBLE);
			image_down6.setVisibility(View.INVISIBLE);
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
			image_up6.setVisibility(View.INVISIBLE);
			image_down6.setVisibility(View.INVISIBLE);
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
			image_up6.setVisibility(View.INVISIBLE);
			image_down6.setVisibility(View.INVISIBLE);
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
			image_up6.setVisibility(View.INVISIBLE);
			image_down6.setVisibility(View.INVISIBLE);
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
			image_up6.setVisibility(View.INVISIBLE);
			image_down6.setVisibility(View.INVISIBLE);
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
			image_up6.setVisibility(View.INVISIBLE);
			image_down6.setVisibility(View.INVISIBLE);
			break;
		case 6:
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
			image_up5.setVisibility(View.INVISIBLE);
			image_down5.setVisibility(View.INVISIBLE);
			break;
		}
	}
}
