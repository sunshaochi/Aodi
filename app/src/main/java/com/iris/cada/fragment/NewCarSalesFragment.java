package com.iris.cada.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.entity.ILogProfit;
import com.iris.cada.service.IRISService;
import com.iris.cada.utils.CKDateCalculate;
import com.iris.cada.utils.CKDateWheelHelper;
import com.iris.cada.utils.CKNumFormat;
import com.iris.cada.utils.NowTimeGetter;
import com.iris.cada.view.CKTitleView;
import com.iris.cada.view.CKTitleView.TitleClick;
import com.iris.cada.view.ckbar.CKChartData;
import com.iris.cada.view.ckbar.CKChartView;
import com.iris.cada.view.ckbar.CKChartViewStyle;
import com.iris.cada.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import kankan.wheel.widget.WheelView;

/**
 * 设置
 * 
 * @author jiahaoGuo
 * @version 2015-11-5 13:56:12
 */

public class NewCarSalesFragment extends BaseFragment {

	private CKTitleView titleView;
	private Button btnLeft, btnRight; // 日和月
	private Map<String, String> params;
	private TextView tvStartDate, tvEndDate, tv_start_date_value, tv_end_date_value;
	private String startDate, endDate;
	private LinearLayout ll_container;
	// ------wheelView------
	LinearLayout ll_wheel_container;
	Button bt_cancel, bt_confirm;
	WheelView wheel_year, wheel_month, wheel_day;
	CKDateWheelHelper ckDateWheelHelper;
	// 标识符
	public static Boolean IS_DAY = true;
	private Boolean IS_START_DAY = true;
	Handler handler = new MyHandler(this) {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDialog.dismiss();
			String json = (String) msg.obj;
			switch (msg.what) {
			case ProfitApplication.DATA_SUC:
				ArrayList<ILogProfit> tempList = null;
				if (json != null) {
					Gson gson = new Gson();
					tempList = gson.fromJson(json, new TypeToken<ArrayList<ILogProfit>>() {
					}.getType());
				}
				resetView(tempList);
				break;
			case ProfitApplication.DATA_FAILED:
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
		View view = inflater.inflate(R.layout.fragment_new_car, container, false);
		initView(view);
		refresh();
		return view;
	}

	public void initView(View v) {
		titleView = (CKTitleView) v.findViewById(R.id.title);
		titleView.getLeftButoon().setVisibility(View.INVISIBLE);
		titleView.getRightButoon().setVisibility(View.VISIBLE);
		titleView.getRightButoon().setBackgroundResource(R.drawable.refresh);
		TextView title = new TextView(getActivity());
		title.setText(ProfitApplication.salesInfo.getName());
		title.setTextSize(ProfitApplication.getTitleTextSize(activity));
		title.setTextColor(getResources().getColor(R.color.black));
		titleView.getLinearLayout().addView(title);

		// 初始化
		btnLeft = (Button) v.findViewById(R.id.btn_left);
		btnLeft.setOnClickListener(new BtnClickListener());
		btnRight = (Button) v.findViewById(R.id.btn_right);
		btnRight.setOnClickListener(new BtnClickListener());
		tvStartDate = (TextView) v.findViewById(R.id.tv_start_date);
		tvEndDate = (TextView) v.findViewById(R.id.tv_end_date);

		ll_container = (LinearLayout) v.findViewById(R.id.ll_container);

		startDate = CKDateCalculate.DayRange(NowTimeGetter.getday(), -30);
		tv_start_date_value = (TextView) v.findViewById(R.id.tv_start_date_value);
		tv_start_date_value.setText(startDate);
		tv_start_date_value.setOnClickListener(new BtnClickListener());

		endDate = NowTimeGetter.getday();
		tv_end_date_value = (TextView) v.findViewById(R.id.tv_end_date_value);
		tv_end_date_value.setText(endDate);
		tv_end_date_value.setOnClickListener(new BtnClickListener());
		// ---wheelView 初始化----
		ll_wheel_container = (LinearLayout) v.findViewById(R.id.ll_wheel_container);
		bt_confirm = (Button) v.findViewById(R.id.bt_confirm);
		bt_confirm.setOnClickListener(new BtnClickListener());
		bt_cancel = (Button) v.findViewById(R.id.bt_cancel);
		bt_cancel.setOnClickListener(new BtnClickListener());
		wheel_year = (WheelView) v.findViewById(R.id.wheel_year);
		wheel_month = (WheelView) v.findViewById(R.id.wheel_month);
		wheel_day = (WheelView) v.findViewById(R.id.wheel_day);
		ckDateWheelHelper = new CKDateWheelHelper(activity, ll_wheel_container);
		ckDateWheelHelper.initWheelView(wheel_year, wheel_month, wheel_day);

		titleView.setTitleClick(new TitleClick() {

			@Override
			public void btRightClick() {
				exetuceRequest();
				progressDialog.show();
			}

			@Override
			public void btLeftClick() {

			}
		});

		params = new HashMap<String, String>();
	}

	public void exetuceRequest() {
		/*
		 * http://120.26.66.145:7522/cadaService/
		 * GetILogSingleCarReportServlet?start=2015-10-23&end=2015-12-20&license
		 * =zsj&role=经销商&brand=奥迪&mode=是&car=All
		 */
		final String carModel = ProfitApplication.userSelectEntity.getCarModel();
		params.put("start", startDate);
		params.put("end", endDate);
		params.put("license", ProfitApplication.salesInfo.getLicense());
		params.put("role", ProfitApplication.salesInfo.getRole());
		params.put("brand", ProfitApplication.salesInfo.getBrand());
		params.put("mode", carModel);
		params.put("car", "All");
		if (IS_DAY) {
			IRISService.exetuce(ProfitApplication.NEW_CAR_DAY, params, handler);
		} else {
			IRISService.exetuce(ProfitApplication.NEW_CAR_Mon, params, handler);
		}
	}

	class BtnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_left:
				// 日
				IS_DAY = true;
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
				btnLeft.setBackgroundResource(R.drawable.default_left);
				btnRight.setBackgroundResource(R.drawable.current_right);
				btnLeft.setTextColor(getResources().getColor(R.color.btn_text));
				btnRight.setTextColor(getResources().getColor(R.color.white));
				tv_start_date_value.setText(NowTimeGetter.getMon(startDate));
				tv_end_date_value.setText(NowTimeGetter.getMon(endDate));
				exetuceRequest();
				progressDialog.show();
				break;
			case R.id.tv_start_date_value:
				// 起始日期
				IS_START_DAY = true;
				wheel_year.setCurrentItem(CKDateCalculate.String2Date(startDate).getYear() + 1900 - 2000);
				wheel_month.setCurrentItem(CKDateCalculate.String2Date(startDate).getMonth());
				wheel_day.setCurrentItem(CKDateCalculate.String2Date(startDate).getDate()-1);
				ckDateWheelHelper.visible();
				break;
			case R.id.tv_end_date_value:
				// 结束日期
				IS_START_DAY = false;
				wheel_year.setCurrentItem(CKDateCalculate.String2Date(endDate).getYear() + 1900 - 2000);
				wheel_month.setCurrentItem(CKDateCalculate.String2Date(endDate).getMonth());
				wheel_day.setCurrentItem(CKDateCalculate.String2Date(endDate).getDate()-1);
				ckDateWheelHelper.visible();
				break;
			case R.id.bt_cancel:
				ckDateWheelHelper.dismiss();
				break;
			case R.id.bt_confirm:
				String tempDate = CKDateCalculate.formatString(
						ckDateWheelHelper.getWheelText(wheel_year) + "-" + ckDateWheelHelper.getWheelText(wheel_month)
								+ "-" + ckDateWheelHelper.getWheelText(wheel_day));
				if (CKDateCalculate.compareTime(tempDate, NowTimeGetter.getday()) > 0) {
					Toast.makeText(activity, "所选日期不能超过今日", Toast.LENGTH_SHORT).show();
					return;
				}
				if (IS_START_DAY) {
					// 判断是否符合规则,如果合乎规则,则赋值并请求,如果不合规则,则提示
					if (getDateLegal(tempDate, endDate)) {
						startDate = tempDate;
						tv_start_date_value.setText(startDate);
						ckDateWheelHelper.dismiss();
						refresh();
					} else {
						Toast.makeText(activity, "日跨度不能大于60天,月跨度不能大于12月", Toast.LENGTH_SHORT).show();
					}
				} else {
					// 判断是否符合规则,如果合乎规则,则赋值并请求
					if (getDateLegal(startDate, tempDate)) {
						endDate = tempDate;
						tv_end_date_value.setText(endDate);
						ckDateWheelHelper.dismiss();
						refresh();
					} else {
						Toast.makeText(activity, "日跨度不能大于60天,月跨度不能大于12月", Toast.LENGTH_SHORT).show();
					}
				}

				break;
			default:
				break;
			}
		}
	}

	public void resetView(ArrayList<ILogProfit> tempList) {
		// 添加bar,line
		ll_container.removeAllViews();
		// 添加bar,line
		// handleData
		// 添加数据
		ArrayList<String> leftAxisDatas = new ArrayList<String>();
		ArrayList<String> rightAxisDatas = new ArrayList<String>();
		ArrayList<String> xAxisDatas = new ArrayList<String>();
		ArrayList<String> barDatas = new ArrayList<String>();
		ArrayList<String> lineDatas = new ArrayList<String>();
		ArrayList<String> barColors = new ArrayList<String>();
		ArrayList<String> lineColors = new ArrayList<String>();
		ArrayList<String> indicates = new ArrayList<String>();
		ArrayList<String> indicateColors = new ArrayList<String>();
		for (ILogProfit profit : tempList) {
			xAxisDatas.add(profit.getModels());
			barDatas.add(profit.getProfit());
			barColors.add("#CC0033");
			lineColors.add("#FFD671");
		}
		leftAxisDatas = ProfitApplication.getAxisData(barDatas, false);

		CKChartViewStyle style = new CKChartViewStyle(barColors, lineColors);
		style.setTopWidth(10);
		style.setBtmWidth(0);
		style.setRightAxisWidth(0);
		ArrayList<String> touchText = new ArrayList<String>();
		touchText.add("日期");
		touchText.add("利润");
		CKChartData.setTouchText(touchText);
		CKChartView chartView = new CKChartView(getActivity(), style,
				new CKChartData(leftAxisDatas, rightAxisDatas, xAxisDatas, barDatas, lineDatas), "", indicates,
				indicateColors, null);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ll_container.getWidth(),
				ll_container.getHeight());
		ll_container.addView(chartView, params);
	}

	@Override
	public void refresh() {
		exetuceRequest();
		progressDialog.show();
	}

	/**
	 * @param firstDate
	 *            起始日期
	 * @param secondDate
	 *            结束日期
	 * @return 如果合法
	 */
	Boolean getDateLegal(String firstDate, String secondDate) {
		String temp = secondDate;
		if (IS_DAY) {
			temp = CKDateCalculate.DayRange(firstDate, 60);
		} else {
			temp = CKDateCalculate.monthRange(firstDate, 12);
		}
		if (CKDateCalculate.compareTime(secondDate, temp) <= 0) {
			return true;
		}
		return false;
	}
}
