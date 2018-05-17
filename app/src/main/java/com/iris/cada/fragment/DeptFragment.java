package com.iris.cada.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.adapter.GroupAdapter;
import com.iris.cada.entity.ILogProfit;
import com.iris.cada.service.IRISService;
import com.iris.cada.utils.CKDateCalculate;
import com.iris.cada.utils.NowTimeGetter;
import com.iris.cada.view.CKTitleView;
import com.iris.cada.view.CKTitleView.TitleClick;
import com.iris.cada.view.ckbar.CKChartData;
import com.iris.cada.view.ckbar.CKChartView;
import com.iris.cada.view.ckbar.CKChartViewStyle;
import com.iris.cada.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 部门
 * 
 * @author jiahaoGuo
 * @version 2015-11-5 13:54:42
 */
public class DeptFragment extends BaseFragment {

	private CKTitleView titleView;
	private PopupWindow popupWindow;
	private TextView tvDate;
	private static String date;
	private ListView lvGroup;
	private View view;
	private List<String> groups;
	private Button newCar;
	private Button btnProducts;
	private Button btnFinance;
	private Button btnInsurance;
	private Button btnYanbao;
	private Button btnDisp;
	private Button fromBtn;
	private Button carModel;
	private LinearLayout llBottomBtn;
	private ArrayList<ILogProfit> tempList;
	private Map<String, String> params;
	private static int kpiType = 0;
	private String car = "All";
	private RelativeLayout ll_container;
	public static Boolean flag = false; // 标记下拉是否要刷新

	Handler handler = new MyHandler(this) {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDialog.dismiss();
			String json = (String) msg.obj;
			switch (msg.what) {
			case ProfitApplication.DATA_SUC:
				if (json != null) {
					Gson gson = new Gson();
					tempList = gson.fromJson(json, new TypeToken<ArrayList<ILogProfit>>() {
					}.getType());
				}
				if (tempList != null) {
					resetView();
				}
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
		View view = inflater.inflate(R.layout.fragment_dept, container, false);
		initView(view);
		return view;
	}

	public void initView(View v) {
		titleView = (CKTitleView) v.findViewById(R.id.title);
		titleView.getLeftButoon().setVisibility(View.VISIBLE);
		titleView.getLeftButoon().setBackgroundResource(R.drawable.last);
		TextView title = new TextView(getActivity());
		title.setText(ProfitApplication.salesInfo.getName());
		title.setTextSize(ProfitApplication.getTitleTextSize(activity));
		title.setTextColor(getResources().getColor(R.color.black));
		titleView.getLinearLayout().addView(title);

		carModel = (Button) v.findViewById(R.id.btn_car_model);
		carModel.setOnClickListener(new BtnClickListener());

		newCar = (Button) v.findViewById(R.id.btn_new_car);
		newCar.setOnClickListener(new BtnClickListener());
		btnProducts = (Button) v.findViewById(R.id.btn_products);
		btnProducts.setOnClickListener(new BtnClickListener());
		btnFinance = (Button) v.findViewById(R.id.btn_finance);
		btnFinance.setOnClickListener(new BtnClickListener());
		btnInsurance = (Button) v.findViewById(R.id.btn_insurance);
		btnInsurance.setOnClickListener(new BtnClickListener());
		btnYanbao = (Button) v.findViewById(R.id.btn_yanbao);
		btnYanbao.setOnClickListener(new BtnClickListener());
		btnDisp = (Button) v.findViewById(R.id.btn_displacement);
		btnDisp.setOnClickListener(new BtnClickListener());
		llBottomBtn = (LinearLayout) v.findViewById(R.id.ll_bottom_btn);
		fromBtn = (Button) llBottomBtn.getChildAt(0);
		tvDate = (TextView) v.findViewById(R.id.tv_date);
		date = NowTimeGetter.getday();

		ll_container = (RelativeLayout) v.findViewById(R.id.ll_container);

		titleView.setTitleClick(new TitleClick() {

			@Override
			public void btRightClick() {
				if (!NowTimeGetter.getMon(NowTimeGetter.getday()).equals(NowTimeGetter.getMon(date))) {
					date = CKDateCalculate.nextMonth(date);
				}
				exetuceRequest();
				progressDialog.show();
			}

			@Override
			public void btLeftClick() {
				date = CKDateCalculate.lastMonth(date);
				exetuceRequest();
				progressDialog.show();
			}
		});
		params = new HashMap<String, String>();
		refresh();
	}

	public void exetuceRequest() {
		String carModel = ProfitApplication.userSelectEntity.getCarModel();
		/*
		 * http://120.26.66.145:7522/cadaService/
		 * GetILogDepartmentProfitServlet?date=2015-12-23&license=zsj&role=经销商&
		 * brand=奥迪&mode=是&kpi=精品&car=Q5
		 */
		params.put("date", date);
		params.put("license", ProfitApplication.salesInfo.getLicense());
		params.put("role", ProfitApplication.salesInfo.getRole());
		params.put("brand", ProfitApplication.salesInfo.getBrand());
		params.put("mode", carModel);
		params.put("car", car);
		switch (kpiType) {
		case 0:
			params.put("kpi", "新车");
			break;
		case 1:
			params.put("kpi", "精品");
			break;
		case 2:
			params.put("kpi", "金融");
			break;
		case 3:
			params.put("kpi", "保险");
			break;
		case 4:
			params.put("kpi", "延保");
			break;
		case 5:
			params.put("kpi", "置换");
			break;
		}
		IRISService.exetuce(ProfitApplication.DEPT, params, handler);
	}

	class BtnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_new_car:
				// 精品
				setrButtonImage(0);
				kpiType = 0;
				exetuceRequest();
				progressDialog.show();
				break;
			case R.id.btn_products:
				// 精品
				setrButtonImage(2);
				kpiType = 1;
				exetuceRequest();
				progressDialog.show();
				break;
			case R.id.btn_finance:
				// 金融
				setrButtonImage(4);
				kpiType = 2;
				exetuceRequest();
				progressDialog.show();
				break;
			case R.id.btn_insurance:
				// 保险
				setrButtonImage(6);
				kpiType = 3;
				exetuceRequest();
				progressDialog.show();
				break;
			case R.id.btn_yanbao:
				// 延保
				setrButtonImage(8);
				kpiType = 4;
				exetuceRequest();
				progressDialog.show();
				break;
			case R.id.btn_displacement:
				// 置换
				setrButtonImage(10);
				kpiType = 5;
				exetuceRequest();
				progressDialog.show();
				break;
			case R.id.btn_car_model:
				showWindow(v);
				((Button) v).setBackgroundResource(R.drawable.default_up);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * yearAndMonth
	 * 
	 * @param parent
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	private void showWindow(final View parent) {
		popupWindow = null;
		int pwHeightSize = 7; // popupWindow要显示item的数目--相对于item的高度,这里为30dp(最大高度的限制)
		LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.group_list, null);
		lvGroup = (ListView) view.findViewById(R.id.lvGroup);
		// 加载数据
		groups = new ArrayList<String>();
		groups.add(0, "All");

		if (ProfitApplication.orCarModel()) {
			groups.addAll(ProfitApplication.salesInfo.getModels());
		} else {
			groups.addAll(ProfitApplication.salesInfo.getManagers());
		}
		GroupAdapter groupAdapter = new GroupAdapter(activity, groups);
		lvGroup.setAdapter(groupAdapter);
		int groupSize = (groups.size() > pwHeightSize ? pwHeightSize : groups.size());
		// 创建一个PopuWidow对象
		popupWindow = new PopupWindow(view, parent.getWidth(),
				(parent.getHeight() * groupSize - groupSize * (parent.getHeight() / 8)));
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				((Button) parent).setBackgroundResource(R.drawable.current);
			}
		});
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// WindowManager windowManager = (WindowManager)
		// activity.getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
		// int xPos = windowManager.getDefaultDisplay().getWidth() / 2 -
		// popupWindow.getWidth() / 2;
		popupWindow.showAsDropDown(parent, 0, 0);
		lvGroup.setOnItemClickListener(new OnItemClickListener() {
			@SuppressLint("ShowToast")
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				// Toast.makeText(getActivity(), "groups.get(position)" +
				// groups.get(position), 1000).show();
				// tvtitle.setText(groups.get(position));
				if (popupWindow != null) {
					popupWindow.dismiss();
					carModel.setText(groups.get(position));
				}
				car = carModel.getText().toString();
				exetuceRequest();
				progressDialog.show();
			}
		});
	}

	public void resetView() {
		titleView.getRightButoon().setVisibility(View.VISIBLE);
		tvDate.setText(NowTimeGetter.getMon(date));
		titleView.getLeftTextView().setText("上一月");
		if (NowTimeGetter.getMon(NowTimeGetter.getday()).equals(NowTimeGetter.getMon(date))) {
			titleView.getRightButoon().setBackgroundResource(R.drawable.refresh);
			titleView.getRightTextView().setText("");
		} else {
			titleView.getRightButoon().setBackgroundResource(R.drawable.next);
			titleView.getRightTextView().setText("下一月");
		}
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
		style.setRightAxisWidth(0);
		indicates.add("■新车销售利润");
		indicateColors.add("#CC0033");
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

	/**
	 * 底部选项背景的切换
	 */
	public void setrButtonImage(int checkedId) {
		// 获取当前选择的RadioButton
		Button btn = (Button) llBottomBtn.getChildAt(checkedId);
		// 设置上一次选项卡的资源
		if (btn != null) {
			fromBtn.setBackgroundResource(
					getResources().getIdentifier("default_" + fromBtn.getTag(), "drawable", activity.getPackageName()));
			fromBtn.setTextColor(getResources().getColor(R.color.btn_text));
			// 保存当前选择的RadioButton，用来下一次切换时关闭
			fromBtn = btn;
			// 设置当前选项卡的资源
			btn.setBackgroundResource(
					getResources().getIdentifier("current_" + btn.getTag(), "drawable", activity.getPackageName()));
			btn.setTextColor(getResources().getColor(R.color.white));
		}
	}

	@Override
	public void refresh() {
		if (flag) {
			carModel.setText("All");
		}
		car = carModel.getText().toString();
		exetuceRequest();
		progressDialog.show();
	}
}
