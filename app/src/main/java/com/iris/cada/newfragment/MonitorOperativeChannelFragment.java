package com.iris.cada.newfragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.ProfitApplication;
import com.iris.cada.adapter.MonitorOperativeOrProfitChannelAdapter;
import com.iris.cada.adapter.MyGridViewAdapter;
import com.iris.cada.entity.ChannelInfo;
import com.iris.cada.entity.OverviewCustomerInfo;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.utils.DateAndTimeUtils;
import com.iris.cada.utils.MyLogUtils;
import com.iris.cada.utils.MyToast;
import com.iris.cada.utils.MyToastUtils;
import com.iris.cada.utils.TimeRefreshUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.view.EditDialogUtils;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 监控-运营-潜客渠道
 * 
 * @author LFJ
 *
 */
public class MonitorOperativeChannelFragment extends BaseFragment implements OnClickListener {

	private static final int UPDATE_UI =120;
	private ImageView mTopLeft;
	private TextView mTopTime ;
	private ImageView mTopRight ;
	private TextView mModeDes ;
	private LinearLayout mCarModeLayout;
	private TextView mModeTxt;
	private ListView mChannelListView;
	private GridView mPeriodGridView ;

	private List<ChannelInfo> mChannelList = new ArrayList<ChannelInfo>();
	private List<String> mCarModeList;
	private List<String> mConsulantModeList;
	private List<String> mCusCodelist;//顾问模式的code
	private List<String> mCarCodelist;//车型模式code
	private AddTodoPickerViewUtils addPickerViewUtils;
	private Date mCurrentDate;
	private MyGridViewAdapter myGridViewAdapter;
	private String timeFormat;
	private Gson gson;

	private boolean isMonth = true;
	private Map<String, Map<String, String>> mWeeksMap = new HashMap<String, Map<String, String>>();

	private String weekIndex;
	private String mModeSelect = "All";
	private String title="All";

	private MenuFragment menuFragment;

	private LocalBroadcastManager mLocalBroadcastManager;
	private BroadcastReceiver mBroadcastReceiver;

	private String startDate;
	private String endDate;

	public static final int MONITOR_DATA_SUC = 300;//请求销售顾问成功
	public static final int MONITOR_DATA_FIN = 401;

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
				dealChannelInfo(msg);//处理数据
				break;
			case ProfitApplication.SERVER_FAILED:
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_net));
				break;
			case MONITOR_DATA_SUC:
				handleCustomerMsg(msg);//获取销售数据
				break;
			case MONITOR_DATA_FIN:
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
					mConsulantModeList.add(mOverviewCustomerInfos.get(i).getScName());
					mCusCodelist.add(mOverviewCustomerInfos.get(i).getScCode());
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
					mModeDes.setText(ProfitApplication.isConsultantMode ? "顾问" : "车型");
					mModeTxt.setText("All");
//					if(ProfitApplication.isConsultantMode){//顾问模式就获取顾问
//						initCustomerData();
//					}
//
//					getMonitorOperativeChannelInfo();
					mModeSelect="All";
					title="All";
					initData();
				}

				if (action.equals(ProfitApplication.TIME_REFRESH_MESSAGE)) {
					mCurrentDate = ProfitApplication.mStartDate;
					mTopTime.setText(DateAndTimeUtils.getFirstAndLastDayOfMonth(mCurrentDate));
					refreshTimePeriod();
					initData();
				}

			}
		};
		mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_monitor_operative_channel, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		mTopLeft = (ImageView) view.findViewById(R.id.monitor_top_left);
		mTopLeft.setOnClickListener(this);

		mTopRight = (ImageView) view.findViewById(R.id.monitor_top_right);
		mTopRight.setOnClickListener(this);

		mTopTime = (TextView) view.findViewById(R.id.monitor_top_time);
		mTopTime.setOnClickListener(this);

		mCarModeLayout = (LinearLayout) view.findViewById(R.id.monitor_top_car_mode_layout);
		mCarModeLayout.setOnClickListener(this);

		mModeDes = (TextView) view.findViewById(R.id.monitor_top_mode_des);
		mModeDes.setText(ProfitApplication.isConsultantMode ? "销售顾问" : "车型");

		mModeTxt = (TextView) view.findViewById(R.id.monitor_top_car_mode);

		mChannelListView = (ListView) view.findViewById(R.id.monitor_channel_listview);

		mPeriodGridView = (GridView) view.findViewById(R.id.monitor_channel_gridview);
		mPeriodGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				myGridViewAdapter.setSelection(position);
				myGridViewAdapter.notifyDataSetInvalidated();

				if (position == 0) {
					isMonth = true;

					mTopTime.setText(DateAndTimeUtils.getFirstAndLastDayOfMonth(mCurrentDate));
					getMonitorOperativeChannelInfo();
				} else {
					isMonth = false;

					weekIndex = String.valueOf(position);
					getMonitorOperativeChannelInfo();
				}
			}
		});

		initData();
	}

	private void initData() {
		timeFormat = "yyyy/MM/dd";
		addPickerViewUtils = new AddTodoPickerViewUtils(getActivity());
		gson = new Gson();

		if (null == ProfitApplication.mStartDate) {
			// 默认显示当前月
			mCurrentDate = DateAndTimeUtils.getCurrentDate(timeFormat);
		} else {
			mCurrentDate = ProfitApplication.mStartDate;
		}

		mTopTime.setText(DateAndTimeUtils.getFirstAndLastDayOfMonth(mCurrentDate));
		// 计算周数对应日期
		DateAndTimeUtils.getWeekPeriod(mCurrentDate, mWeeksMap);

		// 时间段
		myGridViewAdapter = new MyGridViewAdapter(getActivity(), DateAndTimeUtils.getTimePeriod(mCurrentDate));
		mPeriodGridView.setNumColumns(DateAndTimeUtils.getPeriodNum(mCurrentDate));
		mPeriodGridView.setAdapter(myGridViewAdapter);

		splitDate();//切割时间

		if (!ProfitApplication.isConsultantMode) {// 车型
			// 车型列表
			mCarModeList = new ArrayList<String>();
			mCarCodelist=new ArrayList<>();
			mCarModeList.add("All");
			mCarCodelist.add("All");
			if(ProfitApplication.loginBackInfo.getModels()!=null&&ProfitApplication.loginBackInfo.getModels().size()>0) {
				mCarModeList.addAll(ProfitApplication.loginBackInfo.getModels());
				mCarCodelist.addAll(ProfitApplication.loginBackInfo.getCarCodeList());
			}
			mModeTxt.setText(title);

		}else{
			//请求销售顾问数据,原来是从登录数据中取得的
			initCustomerData();
		}

		// 顾问列表
		// mConsulantModeList = new ArrayList<String>();
		// mConsulantModeList.add("All");
		// mConsulantModeList.addAll(ProfitApplication.loginBackInfo.getManagers());

//		mModeSelect = ProfitApplication.isConsultantMode ? mConsulantModeList.get(0) : mCarModeList.get(0);
//		mModeTxt.setText(mModeSelect);

		getMonitorOperativeChannelInfo();
	}


	public void splitDate(){
		String currentText = mTopTime.getText().toString().trim();//2017/02/01-28
		String[] splitDates = currentText.split("/");
		String[] splitDays = splitDates[2].split("-");
		startDate = splitDates[0]+"-"+splitDates[1]+"-"+splitDays[0];
		endDate = splitDates[0]+"-"+splitDates[1]+"-"+splitDays[1];
	}


	public void initCustomerData(){
		mConsulantModeList = new ArrayList<String>();
		mConsulantModeList.add("All");
		mCusCodelist=new ArrayList<>();
		mCusCodelist.add("All");
		mModeTxt.setText(title);
		ProfitApplication.profitNetService.getMonitorOperativeChannelServlet2(startDate,endDate, "", handler);
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.monitor_top_left:
			refreshDate(true);
			break;
		case R.id.monitor_top_right:
			refreshDate(false);
			break;
		case R.id.monitor_top_time:
			showDateDialog();
			break;
		case R.id.monitor_top_car_mode_layout:
			showModeDialog();
			break;
		default:
			break;
		}
	}

	/**
	 * 点击之后选择车型或者模式
	 */
	private void showModeDialog() {
		EditDialogUtils
				.showSelectDialog(getContext(), mModeTxt, ProfitApplication.isConsultantMode ? "请选择顾问" : "请选择车型",
						ProfitApplication.isConsultantMode ? mConsulantModeList : mCarModeList)
				.setItemClick(new OnListViewItemClick() {

					@Override
					public void onClick(String str, int position) {
						mModeTxt.setText(str);
						mModeSelect = ProfitApplication.isConsultantMode ?mCusCodelist.get(position-1) :mCarCodelist.get(position-1);
                        title=str;
						getMonitorOperativeChannelInfo();
					}
				});
	}

	/** 显示日期选择器 */
	private void showDateDialog() {
		TimePickerViewDialog pvTimeDialog = addPickerViewUtils.initDateNoDayPickerView("请选择时间").getPvTime();
		pvTimeDialog.getDialog().show();

		pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				mCurrentDate = date;
				String strDate = DateAndTimeUtils.getFirstAndLastDayOfMonth(date);
				mTopTime.setText(strDate);

				TimeRefreshUtils.savePublicDate(getContext(), mCurrentDate);
				refreshTimePeriod();
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
		} else {
			currentLongTime = DateAndTimeUtils
					.nextMonth(DateAndTimeUtils.getLongTimeForStr(timeFormat, strCurrentDate));
		}

		mCurrentDate = DateAndTimeUtils.getDateTimeForStr(timeFormat,
				DateAndTimeUtils.getTimeForLongTime(timeFormat, currentLongTime));

		String strDate = DateAndTimeUtils.getFirstAndLastDayOfMonth(mCurrentDate);
		mTopTime.setText(strDate);

		TimeRefreshUtils.savePublicDate(getContext(), mCurrentDate);
		refreshTimePeriod();
	}

	private void getMonitorOperativeChannelInfo() {
		progressDialog.show();

		String startTime = null;
		String endTime = null;
		if (isMonth) {
			startTime = DateAndTimeUtils.getFisrtDayOfMonth(mCurrentDate);
			endTime = DateAndTimeUtils.getLastDateOfMonth(mCurrentDate);
		} else {
			if (mWeeksMap.containsKey(weekIndex)) {
				startTime = mWeeksMap.get(weekIndex).get("start");
				endTime = mWeeksMap.get(weekIndex).get("end");

				mTopTime.setText(DateAndTimeUtils.getWeekPeriodString(startTime, endTime));
			}
		}

		ProfitApplication.profitNetService.getMonitorOperativeChannelServlet(mModeSelect, startTime, endTime,
				addPickerViewUtils.getDateNoDay(mCurrentDate), isMonth ? "false" : "true", handler);
	}

	/**
	 * 处理数据
	 * @param msg
	 */
	private void dealChannelInfo(final Message msg) {

		if (null != mChannelList) {
			mChannelList.clear();
		}


		String strMsg = (String) msg.obj;

		try {
			JSONArray jsonArray = new JSONArray(strMsg);

				for (int i = 0; i < jsonArray.length(); i++) {
					ChannelInfo channelInfo = gson.fromJson(jsonArray.get(i).toString(), ChannelInfo.class);
					mChannelList.add(channelInfo);
				}

//			MyLogUtils.info("结果"+mCarModeList.toString());
//

//            new Thread(new Runnable() {
//				@Override
//				public void run() {
//					if (null != mChannelList) {
//						mChannelList.clear();
//					}
//
//
//					String strMsg = (String) msg.obj;
//
//					try {
//						JSONArray jsonArray = new JSONArray(strMsg);
//
//						for (int i = 0; i < jsonArray.length(); i++) {
//							ChannelInfo channelInfo = gson.fromJson(jsonArray.get(i).toString(), ChannelInfo.class);
//							mChannelList.add(channelInfo);
//						}
//
//
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//                  getActivity().runOnUiThread(new Runnable() {
//					  @Override
//					  public void run() {
////						  MonitorOperativeOrProfitChannelAdapter monitorOpeOrProChannelAdapter = new MonitorOperativeOrProfitChannelAdapter(
////								  getActivity(), mChannelList);
////						  mChannelListView.setAdapter(monitorOpeOrProChannelAdapter);
//						  MyToastUtils.showShortToast(getActivity(),"跟新ui");
//					  }
//				  });
//				}
//			}).start();
			MonitorOperativeOrProfitChannelAdapter monitorOpeOrProChannelAdapter = new MonitorOperativeOrProfitChannelAdapter(
					getActivity(), mChannelList);
			mChannelListView.setAdapter(monitorOpeOrProChannelAdapter);


		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}



	private void refreshTimePeriod() {
		// 计算周数对应日期
		DateAndTimeUtils.getWeekPeriod(mCurrentDate, mWeeksMap);

		// 周期 GridView refresh
		isMonth = true;
		myGridViewAdapter.setData(DateAndTimeUtils.getTimePeriod(mCurrentDate));
		mPeriodGridView.setNumColumns(DateAndTimeUtils.getPeriodNum(mCurrentDate));
		myGridViewAdapter.notifyDataSetChanged();
		myGridViewAdapter.setSelection(0);
		myGridViewAdapter.notifyDataSetInvalidated();

		getMonitorOperativeChannelInfo();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}






}
