package com.iris.cada.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.ReportSecondActivity;
import com.iris.cada.adapter.MyViewPagerAdapter;
import com.iris.cada.adapter.ReportListAdapter;
import com.iris.cada.entity.ILogData;
import com.iris.cada.service.IRISService;
import com.iris.cada.utils.CKDateCalculate;
import com.iris.cada.utils.NowTimeGetter;
import com.iris.cada.view.CKTitleView;
import com.iris.cada.view.CustomViewPager;
import com.iris.cada.view.CKTitleView.TitleClick;
import com.iris.cada.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 展厅表现
 * 
 * @author jiahaoGuo
 * @version 2015-12-16 16:09:18
 */
public class ExhibitionFragment extends BaseFragment {

	private CKTitleView titleView;
	private TextView tvDate;
	private TextView tvIsCar;
	private CustomViewPager pager;
	private ListView listReportNumber1;
	private ListView listReportNumber2;
	private ListView listReportNumber3;
	private ListView listReportNumber4;
	private View listReportNumber1Container;
	private View listReportNumber2Container;
	private View listReportNumber3Container;
	private View listReportNumber4Container;
	private View numberHeaderView1;
	private View numberHeaderView2;
	private View numberHeaderView3;
	private View numberHeaderView4;
	private Button btnLeft, btnRight;
	private LinearLayout llReportTitleParent;
	// 数据
	private ArrayList<ILogData> list;
	private ArrayList<ILogData> listCompare;
	private ArrayList<View> viewList = new ArrayList<View>();
	private static String date;
	// adapter
	private LayoutInflater inflate;
	private MyViewPagerAdapter pagerAdapter;
	private ReportListAdapter reportListAdapter1, reportListAdapter2, reportListAdapter3, reportListAdapter4;
	private Map<String, String> params;
	private Context context;
	private LinearLayout llDot;
	// 标识符
	public static Boolean IS_DAY = true;
	public static int IS_VP_FIRST = 0;
	private String model;

	Handler handler = new MyHandler(this) {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDialog.dismiss();
			String json = (String) msg.obj;
			switch (msg.what) {
			case ProfitApplication.DATA_SUC:
				ArrayList<ILogData> tempList = null;
				if (json != null) {
					Gson gson = new Gson();
					tempList = gson.fromJson(json, new TypeToken<ArrayList<ILogData>>() {
					}.getType());
				}
				resetView(tempList);
				break;
			case ProfitApplication.DATA_FAILED:
				reportListAdapter1.setData(new ArrayList<ILogData>(), new ArrayList<ILogData>(), IS_DAY);
				reportListAdapter2.setData(new ArrayList<ILogData>(), new ArrayList<ILogData>(), IS_DAY);
				reportListAdapter3.setData(new ArrayList<ILogData>(), new ArrayList<ILogData>(), IS_DAY);
				reportListAdapter4.setData(new ArrayList<ILogData>(), new ArrayList<ILogData>(), IS_DAY);
				Toast.makeText(getActivity(), "暂无数据!", Toast.LENGTH_SHORT).show();
				break;
			case ProfitApplication.SERVER_FAILED:
				Toast.makeText(getActivity(), "服务器连接失败!", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_exhibition, container, false);
		initView(view, inflater);
		return view;
	}

	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	public void initView(View v, LayoutInflater inflater) {
		titleView = (CKTitleView) v.findViewById(R.id.title);
		titleView.getLeftButoon().setVisibility(View.VISIBLE);
		titleView.getLeftButoon().setBackgroundResource(R.drawable.last);
		TextView title = new TextView(getActivity());
		//title.setText(IRISApplication.salesInfo.getName());
		title.setTextSize(ProfitApplication.getTitleTextSize(activity));
		title.setTextColor(getResources().getColor(R.color.black));
		titleView.getLinearLayout().addView(title);
		tvDate = (TextView) v.findViewById(R.id.tv_date);
		pager = (CustomViewPager) v.findViewById(R.id.pager);
		llReportTitleParent = (LinearLayout) v.findViewById(R.id.ll_exhibition_title_parent);
		llDot = (LinearLayout) v.findViewById(R.id.ll_dot);

		date = NowTimeGetter.getday();
		context = getActivity();
		inflate = LayoutInflater.from(context);
		list = new ArrayList<ILogData>();
		listCompare = new ArrayList<ILogData>();
		// 初始化底部button
		btnLeft = (Button) v.findViewById(R.id.btn_left);
		btnLeft.setOnClickListener(new BtnClickListener());
		btnRight = (Button) v.findViewById(R.id.btn_right);
		btnRight.setOnClickListener(new BtnClickListener());
		// 初始化listview
		listReportNumber1Container = inflater.inflate(R.layout.listview_exhibition_number1, null);
		listReportNumber2Container = inflater.inflate(R.layout.listview_exhibition_number2, null);
		listReportNumber3Container = inflater.inflate(R.layout.listview_exhibition_number3, null);
		listReportNumber4Container = inflater.inflate(R.layout.listview_exhibition_number4, null);
		listReportNumber1 = (ListView) listReportNumber1Container.findViewById(R.id.lv_report_number1);
		listReportNumber2 = (ListView) listReportNumber2Container.findViewById(R.id.lv_report_number2);
		listReportNumber3 = (ListView) listReportNumber3Container.findViewById(R.id.lv_report_number3);
		listReportNumber4 = (ListView) listReportNumber4Container.findViewById(R.id.lv_report_number4);

		numberHeaderView1 = inflate.inflate(R.layout.exhibition_title_number1, null);
		numberHeaderView2 = inflate.inflate(R.layout.exhibition_title_number2, null);
		numberHeaderView3 = inflate.inflate(R.layout.exhibition_title_number3, null);
		numberHeaderView4 = inflate.inflate(R.layout.exhibition_title_number4, null);
		// 初始化各listView的adapter
		reportListAdapter1 = new ReportListAdapter(context, list, listCompare, 1, IS_DAY);
		reportListAdapter2 = new ReportListAdapter(context, list, listCompare, 2, IS_DAY);
		reportListAdapter3 = new ReportListAdapter(context, list, listCompare, 3, IS_DAY);
		reportListAdapter4 = new ReportListAdapter(context, list, listCompare, 4, IS_DAY);
		// 为listview添加adapter
		listReportNumber1.setAdapter(reportListAdapter1);
		listReportNumber2.setAdapter(reportListAdapter2);
		listReportNumber3.setAdapter(reportListAdapter3);
		listReportNumber4.setAdapter(reportListAdapter4);
		// 为viewpager添加事件侦听
		pager.setOnPageChangeListener(new PagerListener());
		// spinner
		titleView.setTitleClick(new TitleClick() {

			@Override
			public void btRightClick() {
				if (IS_DAY) {
					if (!NowTimeGetter.getday().equals(date)) {
						date = CKDateCalculate.nextDay(date);
					}
				} else {
					if (!NowTimeGetter.getMon(NowTimeGetter.getday()).equals(NowTimeGetter.getMon(date))) {
						date = CKDateCalculate.nextMonth(date);
					}
				}
				exetuceRequest();
				progressDialog.show();
				pager.setCurrentItem(IS_VP_FIRST);
			}

			@Override
			public void btLeftClick() {
				if (IS_DAY) {
					date = CKDateCalculate.lastDay(date);
				} else {
					date = CKDateCalculate.lastMonth(date);
				}
				exetuceRequest();
				progressDialog.show();
				pager.setCurrentItem(IS_VP_FIRST);
			}
		});
		if (IS_DAY) {
			btnLeft.setBackgroundResource(R.drawable.current_left);
			btnRight.setBackgroundResource(R.drawable.default_right);
			btnLeft.setTextColor(getResources().getColor(R.color.white));
			btnRight.setTextColor(getResources().getColor(R.color.btn_text));
		} else {
			btnLeft.setBackgroundResource(R.drawable.default_left);
			btnRight.setBackgroundResource(R.drawable.current_right);
			btnLeft.setTextColor(getResources().getColor(R.color.btn_text));
			btnRight.setTextColor(getResources().getColor(R.color.white));
		}
		params = new HashMap<String, String>();
		//TODD
		//refresh();
	}

	/**
	 * 初始化Dot
	 */
	@SuppressLint("NewApi")
	private void initDot() {
		llDot.removeAllViews();
		for (int i = 0; i < viewList.size(); i++) {
			ImageView iv = new ImageView(activity);
			LayoutParams params = new LayoutParams(20, 20);
			if (i != 0) {
				params.leftMargin = 5;
			}
			iv.setLayoutParams(params);
			iv.setScaleType(ScaleType.CENTER_CROP);
			iv.setBackground(getResources().getDrawable(R.drawable.dot_selector));
			llDot.addView(iv);
		}
	}

	/**
	 * 更新图片对应的文字
	 */
	private void updateDotInfo() {
		int currentPosition = pager.getCurrentItem() % viewList.size();
		for (int i = 0; i < llDot.getChildCount(); i++) {
			llDot.getChildAt(i).setEnabled(i == currentPosition);
		}
	}

	List<String> jsons = null;

	/**
	 * 访问后台获取数据
	 */
	public void exetuceRequest() {
		model = ProfitApplication.userSelectEntity.getCarModel();
		params.put("date", date);
		params.put("license", ProfitApplication.salesInfo.getLicense());
		params.put("role", ProfitApplication.salesInfo.getRole());
		params.put("brand", ProfitApplication.salesInfo.getBrand());
		params.put("mode", model);
		if (IS_DAY) {
			// 日
			// http://120.26.66.145:7522/cadaService/GetILogReportServlet?date=2015-12-23&license=zsj&role=经销商&brand=奥迪&mode=是
			// http://120.26.66.145:7522/cadaService/GetILogJXSLicense?user=zsj&name=2%E5%8F%B7%E5%BA%97
			IRISService.exetuce(ProfitApplication.REPORT_DAY, params, handler);
			addDayView();
		} else {
			// 月
			new Thread(new Runnable() {

				@Override
				public void run() {
					jsons = IRISService.doReport(date, ProfitApplication.salesInfo.getLicense(),
							ProfitApplication.salesInfo.getRole(), ProfitApplication.salesInfo.getBrand(), model);
					if (jsons != null) {
						list = new Gson().fromJson(jsons.get(0), new TypeToken<ArrayList<ILogData>>() {
						}.getType());
						listCompare = new Gson().fromJson(jsons.get(1), new TypeToken<ArrayList<ILogData>>() {
						}.getType());
						handler.sendMessage(handler.obtainMessage(ProfitApplication.DATA_SUC));
					} else {
						handler.sendMessage(handler.obtainMessage(ProfitApplication.DATA_FAILED));
					}
				}
			}).start();
			addMonthView();
		}
		initDot();
		updateDotInfo();
	}

	/**
	 * 日数据列表
	 */
	public void addDayView() {
		llReportTitleParent.removeAllViews();
		llReportTitleParent.addView(numberHeaderView1);
		llReportTitleParent.addView(numberHeaderView2);
		llReportTitleParent.getChildAt(0).setVisibility(View.VISIBLE);
		llReportTitleParent.getChildAt(1).setVisibility(View.GONE);
		viewList.clear();
		viewList.add(listReportNumber1Container);
		viewList.add(listReportNumber2Container);
		pagerAdapter = new MyViewPagerAdapter(viewList, null);
		pager.setAdapter(pagerAdapter);
	}

	/**
	 * 月数据列表
	 */
	public void addMonthView() {
		llReportTitleParent.removeAllViews();
		llReportTitleParent.addView(numberHeaderView1);
		llReportTitleParent.addView(numberHeaderView2);
		llReportTitleParent.addView(numberHeaderView3);
		llReportTitleParent.addView(numberHeaderView4);
		llReportTitleParent.getChildAt(0).setVisibility(View.VISIBLE);
		llReportTitleParent.getChildAt(1).setVisibility(View.GONE);
		llReportTitleParent.getChildAt(2).setVisibility(View.GONE);
		llReportTitleParent.getChildAt(3).setVisibility(View.GONE);
		viewList.clear();
		viewList.add(listReportNumber1Container);
		viewList.add(listReportNumber2Container);
		viewList.add(listReportNumber3Container);
		viewList.add(listReportNumber4Container);
		pagerAdapter = new MyViewPagerAdapter(viewList, null);
		pager.setAdapter(pagerAdapter);
	}

	/**
	 * 
	 * @author jiahaoGuo
	 * 
	 */
	private class PagerListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int state) {
			// 这个方法在手指操作屏幕的时候发生变化。有三个值：0（END）,1(PRESS) , 2(UP) 。
			// 当用手指滑动翻页时，手指按下去的时候会触发这个方法，state值为1，手指抬起时，如果发生了滑动（即使很小），这个值会变为2，然后最后变为0。
			// 总共执行这个方法三次。一种特殊情况是手指按下去以后一点滑动也没有发生，这个时候只会调用这个方法两次，state值分别是1,0 。
			// 当setCurrentItem翻页时，会执行这个方法两次，state值分别为2 , 0 。
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			// 这个方法会在屏幕滚动过程中不断被调用。
		}

		@Override
		public void onPageSelected(int position) {
			updateDotInfo();
			// 在onPageScrolled执行方法前就会立即执行。
			switch (position) {
			case 0:
				IS_VP_FIRST = 0;
				tvIsCar = (TextView) numberHeaderView1.findViewById(R.id.tv_iscar);
				ProfitApplication.setListViewTitle(tvIsCar);
				numberHeaderView1.setVisibility(View.VISIBLE);
				numberHeaderView2.setVisibility(View.GONE);
				numberHeaderView3.setVisibility(View.GONE);
				numberHeaderView4.setVisibility(View.GONE);
				break;
			case 1:
				IS_VP_FIRST = 1;
				tvIsCar = (TextView) numberHeaderView2.findViewById(R.id.tv_iscar);
				ProfitApplication.setListViewTitle(tvIsCar);
				numberHeaderView1.setVisibility(View.GONE);
				numberHeaderView2.setVisibility(View.VISIBLE);
				numberHeaderView3.setVisibility(View.GONE);
				numberHeaderView4.setVisibility(View.GONE);
				break;
			case 2:
				IS_VP_FIRST = 2;
				tvIsCar = (TextView) numberHeaderView3.findViewById(R.id.tv_iscar);
				ProfitApplication.setListViewTitle(tvIsCar);
				numberHeaderView1.setVisibility(View.GONE);
				numberHeaderView2.setVisibility(View.GONE);
				numberHeaderView3.setVisibility(View.VISIBLE);
				numberHeaderView4.setVisibility(View.GONE);
				break;
			case 3:
				IS_VP_FIRST = 3;
				tvIsCar = (TextView) numberHeaderView4.findViewById(R.id.tv_iscar);
				ProfitApplication.setListViewTitle(tvIsCar);
				numberHeaderView1.setVisibility(View.GONE);
				numberHeaderView2.setVisibility(View.GONE);
				numberHeaderView3.setVisibility(View.GONE);
				numberHeaderView4.setVisibility(View.VISIBLE);
				break;
			}
		}
	}

	public void resetView(ArrayList<ILogData> tempList) {
		titleView.getRightButoon().setVisibility(View.VISIBLE);
		if (IS_DAY) {// 如果为日模式,则将左按钮文字设为上一日
			tvDate.setText(date);
			titleView.getLeftTextView().setText("上一日");
			if (NowTimeGetter.getday().equals(date)) {
				titleView.getRightButoon().setBackgroundResource(R.drawable.refresh);
				titleView.getRightTextView().setText("");
			} else {
				titleView.getRightButoon().setBackgroundResource(R.drawable.next);
				titleView.getRightTextView().setText("下一日");
			}
			list = dataHandle(tempList);
			reportListAdapter1.setData(list, listCompare, IS_DAY);
			reportListAdapter2.setData(list, listCompare, IS_DAY);
		} else {// 如果为月模式,则将左按钮文字设为上一月
			tvDate.setText(NowTimeGetter.getMon(date));
			titleView.getLeftTextView().setText("上一月");
			if (NowTimeGetter.getMon(NowTimeGetter.getday()).equals(NowTimeGetter.getMon(date))) {
				titleView.getRightButoon().setBackgroundResource(R.drawable.refresh);
				titleView.getRightTextView().setText("");
			} else {
				titleView.getRightButoon().setBackgroundResource(R.drawable.next);
				titleView.getRightTextView().setText("下一月");
			}
			list = dataHandle(list);
			// listCompare = dataHandle(listCompare);
			reportListAdapter1.setData(list, listCompare, IS_DAY);
			reportListAdapter2.setData(list, listCompare, IS_DAY);
			reportListAdapter3.setData(list, listCompare, IS_DAY);
			reportListAdapter4.setData(list, listCompare, IS_DAY);
		}
	}

	/**
	 * 遍历他的iLogDatas属性,得到ILogData中的总计
	 * 
	 * @param iLogData
	 * @return
	 */
	public ArrayList<ILogData> dataHandle(ArrayList<ILogData> iLogDatas) {
		int inRoom = 0;
		int quote = 0;
		int order = 0;
		int retail = 0;
		int collectible = 0;
		int finance = 0;
		int insurance = 0;
		int exInsurance = 0;
		int replacement = 0;
		int inRoomFir = 0;
		int inRoomSec = 0;
		String quoteRate = "", transRate = "", dealRate = "";
		ArrayList<ILogData> tempListILogDatas = iLogDatas;
		for (int i = 0; i < tempListILogDatas.size(); i++) {
			ILogData temp = tempListILogDatas.get(i);
			if (!IS_DAY) {
				if (i == 0) {
					quoteRate = temp.getQuoteRate();
					transRate = temp.getTransRate();
					dealRate = temp.getDealRate();
				}
			}
			if (!ProfitApplication.stringIsEmpty(temp.getInRoom()).equals("Na")) {
				inRoom += ProfitApplication.stringIsNull(temp.getInRoom());
			}
			if (!ProfitApplication.stringIsEmpty(temp.getQuote()).equals("Na")) {
				quote += ProfitApplication.stringIsNull(temp.getQuote());
			}
			if (!ProfitApplication.stringIsEmpty(temp.getOrder()).equals("Na")) {
				order += ProfitApplication.stringIsNull(temp.getOrder());
			}
			if (!ProfitApplication.stringIsEmpty(temp.getRetail()).equals("Na")) {
				retail += ProfitApplication.stringIsNull(temp.getRetail());
			}
			if (!ProfitApplication.stringIsEmpty(temp.getCollectible()).equals("Na")) {
				collectible += ProfitApplication.stringIsNull(temp.getCollectible());
			}
			if (!ProfitApplication.stringIsEmpty(temp.getFinance()).equals("Na")) {
				finance += ProfitApplication.stringIsNull(temp.getFinance());
			}
			if (!ProfitApplication.stringIsEmpty(temp.getInsurance()).equals("Na")) {
				insurance += ProfitApplication.stringIsNull(temp.getInsurance());
			}
			if (!ProfitApplication.stringIsEmpty(temp.getExInsurance()).equals("Na")) {
				exInsurance += ProfitApplication.stringIsNull(temp.getExInsurance());
			}
			if (!ProfitApplication.stringIsEmpty(temp.getReplacement()).equals("Na")) {
				replacement += ProfitApplication.stringIsNull(temp.getReplacement());
			}
			if (!ProfitApplication.stringIsEmpty(temp.getInRoomFir()).equals("Na")) {
				inRoomFir += ProfitApplication.stringIsNull(temp.getInRoomFir());
			}
			if (!ProfitApplication.stringIsEmpty(temp.getInRoomSec()).equals("Na")) {
				inRoomSec += ProfitApplication.stringIsNull(temp.getInRoomSec());
			}
		}
		String tempStr = "总计";
		ILogData tempILogData = new ILogData(tempStr, inRoom + "", quote + "", order + "", retail + "",
				collectible + "", finance + "", insurance + "", exInsurance + "", replacement + "", quoteRate,
				transRate, dealRate, inRoomFir + "", inRoomSec + "");
		tempListILogDatas.add(0, tempILogData);
		return tempListILogDatas;
	}

	public static int divider(int first, int second) {
		if (second == 0) {
			return 0;
		}
		DecimalFormat decimalFormat = new DecimalFormat("0");
		double temp = Math.ceil(first * 1.0f / second);
		String result = decimalFormat.format(temp);
		return Integer.parseInt(result);
	}

	class BtnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_left:
				// 日
				IS_DAY = true;
				pager.setCurrentItem(0);
				btnLeft.setBackgroundResource(R.drawable.current_left);
				btnRight.setBackgroundResource(R.drawable.default_right);
				btnLeft.setTextColor(getResources().getColor(R.color.white));
				btnRight.setTextColor(getResources().getColor(R.color.btn_text));
				exetuceRequest();
				progressDialog.show();
				break;
			case R.id.btn_right:
				// 月
				IS_DAY = false;
				pager.setCurrentItem(0);
				btnLeft.setBackgroundResource(R.drawable.default_left);
				btnRight.setBackgroundResource(R.drawable.current_right);
				btnLeft.setTextColor(getResources().getColor(R.color.btn_text));
				btnRight.setTextColor(getResources().getColor(R.color.white));
				exetuceRequest();
				progressDialog.show();
				break;
			default:
				break;
			}
		}
	}

	// ListView的item点击事件
	public class ReportItemOnClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (position != 0) {
				ListView lView = (ListView) parent;
				// 自定义适配器
				String models = ((ILogData) lView.getItemAtPosition(position)).getModels();
				if (!TextUtils.isEmpty(models)) {
					Intent intent = new Intent(activity, ReportSecondActivity.class);
					intent.putExtra("models", models);
					intent.putExtra("date", date);
					startActivity(intent);
				}
			}
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void refresh() {
		if (ProfitApplication.isGroup() && !ProfitApplication.orCarModel()) {
			listReportNumber1.setOnItemClickListener(new ReportItemOnClick());
			listReportNumber2.setOnItemClickListener(new ReportItemOnClick());
			listReportNumber3.setOnItemClickListener(new ReportItemOnClick());
			listReportNumber4.setOnItemClickListener(new ReportItemOnClick());
		} else {
			listReportNumber1.setOnItemClickListener(null);
			listReportNumber2.setOnItemClickListener(null);
			listReportNumber3.setOnItemClickListener(null);
			listReportNumber4.setOnItemClickListener(null);
		}
		exetuceRequest();
		progressDialog.show();
		pager.setCurrentItem(IS_VP_FIRST);
		tvIsCar = (TextView) numberHeaderView1.findViewById(R.id.tv_iscar);
		ProfitApplication.setListViewTitle(tvIsCar);
	}
}
