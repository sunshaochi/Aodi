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
import com.iris.cada.adapter.AdclistDataAdapter;
import com.iris.cada.adapter.ExhiZpAdapter;
import com.iris.cada.adapter.MyGridViewAdapter;
import com.iris.cada.adapter.OperatePopuAdapter;
import com.iris.cada.adapter.SubstitutionAdapter;
import com.iris.cada.comparator.CheckXinPotentialCustomerComparatorAsc;
import com.iris.cada.comparator.CheckXinPotentialCustomerComparatorDesc;
import com.iris.cada.comparator.EnglishAndNumCompactorAsc;
import com.iris.cada.comparator.EnglishAndNumCompactorDesc;
import com.iris.cada.comparator.OperateagainrateAsc;
import com.iris.cada.comparator.OperateagainrateDesc;
import com.iris.cada.comparator.OperatecloserateAsc;
import com.iris.cada.comparator.OperatecloserateDesc;
import com.iris.cada.comparator.OperatefirstDriverateAsc;
import com.iris.cada.comparator.OperatefirstDriverateDesc;
import com.iris.cada.comparator.OperateofferrateAsc;
import com.iris.cada.comparator.OperateofferrateDesc;
import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.entity.IviewDiagnosisProductBean;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author CK adc 运营数据页面
 * 首次试乘率
 */

public class AdcDataFragment extends BaseFragment implements OnClickListener {
	private ListView lv;
	private ListView plv_xinz;//总计和平均那列
	private AdcZjAdpter adpter;//总计和平均的适配器
	private List<String> datas;
	private RelativeLayout star_data_rela, end_data_rela;
	private TextView start_time, end_time, fine_reposhow;
	private AddTodoPickerViewUtils addPickerViewUtils;
	private Date mCurrentDate = null;
	private MyGridViewAdapter myGridViewAdapter = null;
	private String timeFormat = null;
	private AddTodoPickerViewUtils addTodoPickerViewUtils;
	private Date mStartDate;
	private Date mEndDate;
	private List<IViewOperateReport> IViewOperateReportlist = new ArrayList<IViewOperateReport>();
	private List<IViewOperateReport> zjlist=new ArrayList<IViewOperateReport>();//总计集合
	private List<IViewOperateReport> pjlist=new ArrayList<IViewOperateReport>();//平均集合
	private String type;
	private ImageView up_image1, down_img1, up_image2, down_img2, up_image3, down_img3, up_image4, down_img4;
	private boolean bS = true;
	private LinearLayout report_awy_linner, report_fine_extended_linner, report_fine_extended_infiltration_linner,
			report_fine_extended_income_linner;
	private AdclistDataAdapter adclistDataAdapter;
	private LocalBroadcastManager mLocalBroadcastManager = null;
	private BroadcastReceiver mBroadcastReceiver = null;

	public AdcDataFragment(String type) {
		super();
		this.type = type;
	}

	@Override
	public void refresh() {

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
		View view = inflater.inflate(R.layout.fragment_adc, container, false);
		initView(view);
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
		addTodoPickerViewUtils = new AddTodoPickerViewUtils(getActivity());
		fine_reposhow = (TextView) view.findViewById(R.id.fragment_report_fine_reposhow);
		up_image1 = (ImageView) view.findViewById(R.id.fragment_report_fine_awy_image_up);
		down_img1 = (ImageView) view.findViewById(R.id.fragment_report_fine_awy_image_down);
		up_image2 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_image_up);
		down_img2 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_image_down);
		up_image3 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_infiltration_image_up);
		down_img3 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_infiltration_image_down);
		up_image4 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_income_image_up);
		down_img4 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_income_image_down);
		lv = (ListView) view.findViewById(R.id.fragment_oper_popula_lv);
		plv_xinz=(ListView) view.findViewById(R.id.plv_xinz);
		report_awy_linner = (LinearLayout) view.findViewById(R.id.fragment_report_awy_linner);
		report_fine_extended_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_linner);
		report_fine_extended_infiltration_linner = (LinearLayout) view
				.findViewById(R.id.fragment_report_fine_extended_infiltration_linner);
		report_fine_extended_income_linner = (LinearLayout) view
				.findViewById(R.id.fragment_report_fine_extended_income_linner);

		star_data_rela = (RelativeLayout) view.findViewById(R.id.fragment_star_adc_rela);
		end_data_rela = (RelativeLayout) view.findViewById(R.id.fragment_end_adc_rela);
		start_time = (TextView) view.findViewById(R.id.fragment_adc_start_time);
		end_time = (TextView) view.findViewById(R.id.fragment_adc_end_time);
		star_data_rela.setOnClickListener(this);
		end_data_rela.setOnClickListener(this);
		report_awy_linner.setOnClickListener(this);
		report_fine_extended_linner.setOnClickListener(this);
		report_fine_extended_infiltration_linner.setOnClickListener(this);
		report_fine_extended_income_linner.setOnClickListener(this);

		// 新需求：默认按第一列排序
		first_column_linner = (LinearLayout) view.findViewById(R.id.fragment_report_first_column_linner);
		first_column_linner.setOnClickListener(this);
		image_up0 = (ImageView) view.findViewById(R.id.fragment_report_first_column_image_up);
		image_down0 = (ImageView) view.findViewById(R.id.fragment_report_first_column_image_down);
		
	}

	/** 显示日期选择器 */
	private void showDateDialog(final TextView tvTime, final boolean isStart) {
		TimePickerViewDialog pvTimeDialog = addTodoPickerViewUtils.initDatePickerView(isStart ? "请选择开始时间" : "请选择结束时间")
				.getPvTime();
		pvTimeDialog.getDialog().show();

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_star_adc_rela:
			showDateDialog(start_time, true);
			break;
		case R.id.fragment_end_adc_rela:
			showDateDialog(end_time, false);
			break;
		case R.id.fragment_report_first_column_linner:
			firstColumnCompactor(bS,ProfitApplication.isConsultantMode);
			break;
		case R.id.fragment_report_awy_linner:
			Compara(bS);
			break;
		case R.id.fragment_report_fine_extended_linner:
			AgainrateCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_infiltration_linner:
			OfferrateCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_income_linner:
			closeCompara(bS);
			break;
		default:
			break;
		}
	}
	
	public void firstColumnCompactor(boolean b,boolean isConsultantMode) {
		if (IViewOperateReportlist != null && IViewOperateReportlist.size() != 0) {
			ShutImage(0);
			if (b) {
				Collections.sort(IViewOperateReportlist, new EnglishAndNumCompactorAsc(isConsultantMode));
				adclistDataAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up0, image_down0);
			} else {
				Collections.sort(IViewOperateReportlist, new EnglishAndNumCompactorDesc(isConsultantMode));
				adclistDataAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up0, image_down0);
			}
		}
	}
	

	public void closeCompara(boolean b) {
		if (IViewOperateReportlist != null) {
			ShutImage(4);
			if (b) {
				Collections.sort(IViewOperateReportlist, new OperatecloserateAsc());
				adclistDataAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, up_image4, down_img4);
			} else {
				Collections.sort(IViewOperateReportlist, new OperatecloserateDesc());
				adclistDataAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, up_image4, down_img4);
			}
		}

	}

	public void OfferrateCompara(boolean b) {
		if (IViewOperateReportlist != null && IViewOperateReportlist.size() != 0) {
			ShutImage(3);
			if (b) {
				Collections.sort(IViewOperateReportlist, new OperateofferrateAsc());
				adclistDataAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, up_image3, down_img3);
			} else {
				Collections.sort(IViewOperateReportlist, new OperateofferrateDesc());
				adclistDataAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, up_image3, down_img3);
			}
		}
	}

	public void AgainrateCompara(boolean b) {
		if (IViewOperateReportlist != null && IViewOperateReportlist.size() != 0) {
			ShutImage(2);
			if (b) {
				Collections.sort(IViewOperateReportlist, new OperateagainrateAsc());
				adclistDataAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, up_image2, down_img2);
			} else {
				Collections.sort(IViewOperateReportlist, new OperateagainrateDesc());
				adclistDataAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, up_image2, down_img2);
			}
		}
	}

	public void Compara(boolean b) {
		if (IViewOperateReportlist != null && IViewOperateReportlist.size() != 0) {
			ShutImage(1);
			if (b) {
				Collections.sort(IViewOperateReportlist, new OperatefirstDriverateAsc());
				adclistDataAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, up_image1, down_img1);
			} else {
				Collections.sort(IViewOperateReportlist, new OperatefirstDriverateDesc());
				adclistDataAdapter.notifyDataSetChanged();
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
			mEndDate = DateAndTimeUtils.getCurrentDate("yyyy-MM-dd");
			end_time.setText(DateAndTimeUtils.getCurrentTime("yyyy-MM-dd"));
		} else {
			mEndDate = ProfitApplication.mEndDate;
			end_time.setText(DateAndTimeUtils.getTimeForDate("yyyy-MM-dd", mEndDate));
		}

		getProfitInfo();
	}

	private void getProfitInfo() {
		progressDialog.show();
		// Toast.makeText(getActivity(), start_time.getText().toString().trim()
		// + end_time.getText().toString().trim(),
		// Toast.LENGTH_SHORT).show();
		ProfitApplication.profitNetService.getIViewOperateReportServlet(start_time.getText().toString().trim(),
				end_time.getText().toString().trim(), type, handler);
	}

	Handler handler = new MyHandler(this) {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
			case ProfitApplication.DATA_SUC:
				showhomedata(msg);
				// ToastUtils.showMyToast(getActivity(),
				// getString(R.string.channel_target));
				break;
			case ProfitApplication.DATA_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				IViewOperateReportlist.clear();
				adclistDataAdapter = new AdclistDataAdapter(getActivity(), IViewOperateReportlist);
				adclistDataAdapter.notifyDataSetChanged();
				lv.setAdapter(adclistDataAdapter);
				
				zjlist.clear();
				pjlist.clear();
				AdcZjAdpter exadpter=new AdcZjAdpter(getActivity(), zjlist, pjlist);
                plv_xinz.setAdapter(exadpter);
				break;
			case ProfitApplication.SERVER_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_net));
				break;
			}
		}

		private void showhomedata(Message msg) {
			if (IViewOperateReportlist != null) {
				IViewOperateReportlist.clear();
			}
			
			if(zjlist!=null){
				zjlist.clear();
			}
			if(pjlist!=null){
				pjlist.clear();
			}
			try {
				String strMsg = (String) msg.obj;
				IViewOperateReportlist = new Gson().fromJson(strMsg, new TypeToken<ArrayList<IViewOperateReport>>() {
				}.getType());
				
//				IViewOperateReport IV1=new IViewOperateReport("销售顾问","总计","111","222","333","444","555","666","777","888","999","1010","666","777","888","999","1111");
//				IViewOperateReport IV2=new IViewOperateReport("销售顾问","平均","345","678","910","112","123","666","777","888","999","1010","666","777","888","999","1111");
//				Log.e("车市", IViewOperateReportlist.size()+"");
				
//				IViewOperateReportlist.add(IV1);
//				IViewOperateReportlist.add(IV2);
				
				/**新加总计和平均(先把总计和平均的放到另外一个集合)**/		
		       	       
		        	for(int i=0;i<IViewOperateReportlist.size();i++){
		        		if(IViewOperateReportlist.get(i).getModels().equals("总计")){	        		  
		        			if(IViewOperateReportlist.get(i).getModels().equals("总计")){
		        			      zjlist.add(IViewOperateReportlist.get(i));
		        			      IViewOperateReportlist.remove(i);
		        			}
		        			if(IViewOperateReportlist.get(i).getModels().equals("平均")){
		        				  pjlist.add(IViewOperateReportlist.get(i));
		        				  IViewOperateReportlist.remove(i);
		        			}
		        			
		        		}
		        	
		        	}
		            
		        
		      	       
		        adpter=new AdcZjAdpter(getActivity(), zjlist, pjlist);	       
	        	plv_xinz.setAdapter(adpter);
			
				showOperativeInfo(IViewOperateReportlist);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void showOperativeInfo(List<IViewOperateReport> iViewOperateReportlist) {
			adclistDataAdapter = new AdclistDataAdapter(getActivity(), iViewOperateReportlist);
			adclistDataAdapter.notifyDataSetChanged();
//			Compara(false);
			firstColumnCompactor(false,ProfitApplication.isConsultantMode);//默认按第一列排序
			lv.setAdapter(adclistDataAdapter);
		}

	};
	private LinearLayout first_column_linner;
	private ImageView image_up0;
	private ImageView image_down0;

	public void ShutImage(int image_index) {
		switch (image_index) {
		case 0:
			up_image1.setVisibility(View.INVISIBLE);
			down_img1.setVisibility(View.INVISIBLE);
			up_image2.setVisibility(View.INVISIBLE);
			down_img2.setVisibility(View.INVISIBLE);
			up_image3.setVisibility(View.INVISIBLE);
			down_img3.setVisibility(View.INVISIBLE);
			up_image4.setVisibility(View.INVISIBLE);
			down_img4.setVisibility(View.INVISIBLE);
			break;
		case 1:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			up_image2.setVisibility(View.INVISIBLE);
			down_img2.setVisibility(View.INVISIBLE);
			up_image3.setVisibility(View.INVISIBLE);
			down_img3.setVisibility(View.INVISIBLE);
			up_image4.setVisibility(View.INVISIBLE);
			down_img4.setVisibility(View.INVISIBLE);
			break;
		case 2:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			up_image1.setVisibility(View.INVISIBLE);
			down_img1.setVisibility(View.INVISIBLE);
			up_image3.setVisibility(View.INVISIBLE);
			down_img3.setVisibility(View.INVISIBLE);
			up_image4.setVisibility(View.INVISIBLE);
			down_img4.setVisibility(View.INVISIBLE);
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
			break;
		case 4:
			image_up0.setVisibility(View.INVISIBLE);
			image_down0.setVisibility(View.INVISIBLE);
			up_image1.setVisibility(View.INVISIBLE);
			down_img1.setVisibility(View.INVISIBLE);
			up_image2.setVisibility(View.INVISIBLE);
			down_img2.setVisibility(View.INVISIBLE);
			up_image3.setVisibility(View.INVISIBLE);
			down_img3.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}

	}
}
