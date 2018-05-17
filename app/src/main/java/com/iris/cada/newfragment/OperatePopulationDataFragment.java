package com.iris.cada.newfragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.adapter.AdclistDataAdapter;
import com.iris.cada.adapter.MyGridViewAdapter;
import com.iris.cada.adapter.OperatePopuAdapter;
import com.iris.cada.adapter.SubstitutionAdapter;
import com.iris.cada.adapter.ViewPagerAdapter;
import com.iris.cada.adapter.ZjandpjAdapter;
import com.iris.cada.comparator.EnglishAndNumCompactorAsc;
import com.iris.cada.comparator.EnglishAndNumCompactorDesc;
import com.iris.cada.comparator.OperateFirstindentAsc;
import com.iris.cada.comparator.OperateFirstindentDsc;
import com.iris.cada.comparator.OperateNewpotentialAsc;
import com.iris.cada.comparator.OperateNewpotentialDsc;
import com.iris.cada.comparator.OperateQuotationAsc;
import com.iris.cada.comparator.OperateQuotationDsc;
import com.iris.cada.comparator.OperateTurnoverAsc;
import com.iris.cada.comparator.OperateTurnoverDsc;
import com.iris.cada.comparator.OperatefirstDriveAsc;
import com.iris.cada.comparator.OperatefirstDriveDsc;
import com.iris.cada.comparator.OperatesIndentAsc;
import com.iris.cada.comparator.OperatesIndentDsc;
import com.iris.cada.comparator.TurnoverComparatorAsc;
import com.iris.cada.comparator.TurnoverComparatorDesc;
import com.iris.cada.entity.IViewOperateReport;
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
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author CK 报表页面的运营总体页面 数据展示
 * 新增加潛客那列
 */

public class OperatePopulationDataFragment extends BaseFragment implements OnClickListener {
	private ListView lv;
	private ListView plv_pjandzj;//平均和总计
	private ZjandpjAdapter adpter;//总计和平均的适配器
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
	private boolean bS = true;
	private OperatePopuAdapter operatePopuAdapter;
	private List<IViewOperateReport> IViewOperateReportlist = new ArrayList<IViewOperateReport>();
	private List<IViewOperateReport> zjlist=new ArrayList<IViewOperateReport>();//总计集合
	private List<IViewOperateReport> pjlist=new ArrayList<IViewOperateReport>();//平均集合
	private String type;
	private ImageView image_up1, image_down1, image_up2, image_down2, image_up3, image_down3, image_up4, image_down4,
			image_up5, image_down5, image_up6, image_down6;
	private LinearLayout awy_linner, extended_linner, extended_infiltration_linner, extended_income_linner,
			extended_carlon_linner, extended_carlons_linner;
	private LocalBroadcastManager mLocalBroadcastManager = null;
	private BroadcastReceiver mBroadcastReceiver = null;

	public OperatePopulationDataFragment(String type) {
		super();
		this.type = type;
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
				if( action.equals(ProfitApplication.BROADCAST_MESSAGE) ){
					fine_reposhow.setText(ProfitApplication.isConsultantMode ? "顾问" : "车型");
					getProfitInfo();
				}
				
				if( action.equals(ProfitApplication.TIME_REFRESH_MESSAGE)){
					initData();
				}
				
			}
		};
		mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
	}
    //新增加潜客
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_oper_population_data, container, false);
		initView(view);
		initData();
		bindata();
		return view;
	}
	
	private void bindata() {
		try {
			if (!ProfitApplication.isConsultantMode) {
				fine_reposhow.setText("车型");
			} else {
				fine_reposhow.setText("销售顾问");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		lv = (ListView) view.findViewById(R.id.fragment_oper_popula_lv);
		plv_pjandzj=(ListView) view.findViewById(R.id.plv_pjandzj);
		fine_reposhow = (TextView) view.findViewById(R.id.fragment_report_first_column_reposhow);
		star_data_rela = (RelativeLayout) view.findViewById(R.id.fragment_popula_star_rela);
		end_data_rela = (RelativeLayout) view.findViewById(R.id.fragment_popula_end_rela);
		start_time = (TextView) view.findViewById(R.id.fragment_popula_start_time);
		end_time = (TextView) view.findViewById(R.id.fragment_popula_end_time);
		awy_linner = (LinearLayout) view.findViewById(R.id.fragment_report_awy_linner);
		extended_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_linner);
		extended_infiltration_linner = (LinearLayout) view
				.findViewById(R.id.fragment_report_fine_extended_infiltration_linner);
		extended_income_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_income_linner);
		extended_carlon_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_carlon_linner);
		extended_carlons_linner = (LinearLayout) view.findViewById(R.id.fragment_report_fine_extended_carlons_linner);
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
		image_up6 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_carlons_image_up);
		image_down6 = (ImageView) view.findViewById(R.id.fragment_report_fine_extended_carlons_image_down);
		star_data_rela.setOnClickListener(this);
		end_data_rela.setOnClickListener(this);
		awy_linner.setOnClickListener(this);
		extended_linner.setOnClickListener(this);
		extended_infiltration_linner.setOnClickListener(this);
		extended_income_linner.setOnClickListener(this);
		extended_carlon_linner.setOnClickListener(this);
		extended_carlons_linner.setOnClickListener(this);
		
		//新需求----默认按第一列排序
		first_column_linner = (LinearLayout) view.findViewById(R.id.fragment_report_first_column_linner);
		first_column_linner.setOnClickListener(this);
		image_up0 = (ImageView) view.findViewById(R.id.fragment_report_first_column_image_up);
		image_down0 = (ImageView) view.findViewById(R.id.fragment_report_first_column_image_down);

	}

	/** 显示日期选择器 */
	private void showDateDialog(final TextView tvTime, final boolean isStart) {//initDatePickerView----到日
		TimePickerViewDialog pvTimeDialog = addTodoPickerViewUtils
				.initDatePickerView(isStart ? "请选择开始时间" : "请选择结束时间").getPvTime();//initDateNoDayPickerView---到月
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

		// // Car mode demo data
		// mCarModeList = new ArrayList<String>();
		// mCarModeList.add("All");
		// mCarModeList.add("A3 SportBack e-tron");
		//
		// txtCarMode.setText(mCarModeList.get(0));

		getProfitInfo();
	}
    //获取数据
	
	
	
	private void getProfitInfo() {
		progressDialog.show();
		// Toast.makeText(getActivity(),
		// start_time.getText().toString().trim() +
		// end_time.getText().toString().trim() + type,
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
				// showhomedata(msg);
				IViewOperateReportlist.clear();
				operatePopuAdapter = new OperatePopuAdapter(getActivity(), IViewOperateReportlist);
				operatePopuAdapter.notifyDataSetChanged();
				lv.setAdapter(operatePopuAdapter);
				zjlist.clear();
				pjlist.clear();
                ZjandpjAdapter zjadpter=new ZjandpjAdapter(getActivity(), zjlist, pjlist);
                plv_pjandzj.setAdapter(zjadpter);
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
    //新增加潜客那列
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
//			Log.e("hahah", strMsg);
			IViewOperateReportlist = new Gson().fromJson(strMsg, new TypeToken<ArrayList<IViewOperateReport>>() {
			}.getType());
			IViewOperateReport IV1=new IViewOperateReport("销售顾问","总计","111","222","333","444","555","666","777","888","999","1010","666","777","888","999","1111");
			IViewOperateReport IV2=new IViewOperateReport("销售顾问","平均","345","678","910","112","123","666","777","888","999","1010","666","777","888","999","1111");
			Log.e("车市", IViewOperateReportlist.size()+"");
			
//			IViewOperateReportlist.add(IV1);
//			IViewOperateReportlist.add(IV2);
			
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
	            
	        
	      	       
	        adpter=new ZjandpjAdapter(getActivity(), zjlist, pjlist);	       
        	plv_pjandzj.setAdapter(adpter);
	        	
	        
			operatePopuAdapter = new OperatePopuAdapter(getActivity(), IViewOperateReportlist);
//			Collections.sort(IViewOperateReportlist, new OperateNewpotentialDsc());
			firstColumnCompactor(false,ProfitApplication.isConsultantMode);//默认第一列排序
			operatePopuAdapter.notifyDataSetChanged();
			lv.setAdapter(operatePopuAdapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fragment_popula_star_rela:
			showDateDialog(start_time, true);
			break;
		case R.id.fragment_popula_end_rela:
			showDateDialog(end_time, false);
			break;
		case R.id.fragment_report_awy_linner:
			TurnoverCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_linner:
			FirstDriveCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_infiltration_linner:
			QuotationCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_income_linner:
			IndentCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_carlon_linner:
			FristIndentCompara(bS);
			break;
		case R.id.fragment_report_fine_extended_carlons_linner:
			TuroverCompara(bS);
			break;
		case R.id.fragment_report_first_column_linner://第一列
			//TODO
			firstColumnCompactor(bS,ProfitApplication.isConsultantMode);//倒叙还是顺序集合
			break;
		}
	}
	
	public void firstColumnCompactor(boolean b,boolean isConsultantMode) {
		if (IViewOperateReportlist != null && IViewOperateReportlist.size() != 0) {
			ShutImage(0);
			if (b) {
				
				Collections.sort(IViewOperateReportlist, new EnglishAndNumCompactorAsc(isConsultantMode));
				operatePopuAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up0, image_down0);
			} else {
				Collections.sort(IViewOperateReportlist, new EnglishAndNumCompactorDesc(isConsultantMode));
				operatePopuAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up0, image_down0);
			}
		}
	}
	

	public void TurnoverCompara(boolean b) {
		if (IViewOperateReportlist != null && IViewOperateReportlist.size() != 0) {
			ShutImage(1);
			if (b) {
				Collections.sort(IViewOperateReportlist, new OperateNewpotentialAsc());
				operatePopuAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up1, image_down1);
			} else {
				Collections.sort(IViewOperateReportlist, new OperateNewpotentialDsc());
				operatePopuAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up1, image_down1);
			}
		}
	}

	public void FirstDriveCompara(boolean b) {
		if (IViewOperateReportlist != null && IViewOperateReportlist.size() != 0) {
			ShutImage(2);
			if (b) {
				Collections.sort(IViewOperateReportlist, new OperatefirstDriveAsc());
				operatePopuAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up2, image_down2);
			} else {
				Collections.sort(IViewOperateReportlist, new OperatefirstDriveDsc());
				operatePopuAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up2, image_down2);
			}
		}
	}

	public void QuotationCompara(boolean b) {

		if (IViewOperateReportlist != null && IViewOperateReportlist.size() != 0) {
			ShutImage(3);
			if (b) {
				Collections.sort(IViewOperateReportlist, new OperateQuotationAsc());
				operatePopuAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up3, image_down3);
			} else {
				Collections.sort(IViewOperateReportlist, new OperateQuotationDsc());
				operatePopuAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up3, image_down3);
			}
		}
	}

	public void IndentCompara(boolean b) {
		if (IViewOperateReportlist != null && IViewOperateReportlist.size() != 0) {
			ShutImage(4);
			if (b) {
				Collections.sort(IViewOperateReportlist, new OperatesIndentAsc());
				operatePopuAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up4, image_down4);
				for (int i = 0; i < IViewOperateReportlist.size(); i++) {
					Log.e("iview_list", IViewOperateReportlist.get(i).toString());
				}
			} else {
				Collections.sort(IViewOperateReportlist, new OperatesIndentDsc());
				operatePopuAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up4, image_down4);
				for (int i = 0; i < IViewOperateReportlist.size(); i++) {
					// IViewOperateReportlist.get(i).toString();
					Log.e("iview_list", IViewOperateReportlist.get(i).toString());

				}
			}
		}
	}

	public void FristIndentCompara(boolean b) {
		if (IViewOperateReportlist != null && IViewOperateReportlist.size() != 0) {
			ShutImage(5);
			if (b) {
				Collections.sort(IViewOperateReportlist, new OperateFirstindentAsc());
				operatePopuAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up5, image_down5);
			} else {
				Collections.sort(IViewOperateReportlist, new OperateFirstindentDsc());
				operatePopuAdapter.notifyDataSetChanged();
				bS = true;
				showimge(b, image_up5, image_down5);
			}
		}
	}

	public void TuroverCompara(boolean b) {
		if (IViewOperateReportlist != null && IViewOperateReportlist.size() != 0) {
			ShutImage(6);
			if (b) {
				Collections.sort(IViewOperateReportlist, new OperateTurnoverAsc());
				operatePopuAdapter.notifyDataSetChanged();
				bS = false;
				showimge(b, image_up6, image_down6);
			} else {
				Collections.sort(IViewOperateReportlist, new OperateTurnoverDsc());
				operatePopuAdapter.notifyDataSetChanged();
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
