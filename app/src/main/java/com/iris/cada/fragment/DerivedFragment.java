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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 衍生业务
 * 
 * @author jiahaoGuo
 *
 */
public class DerivedFragment extends BaseFragment {

	private CKTitleView titleView;
	private TextView tvDate;
	private String date;
	private Map<String, String> params;
	private ArrayList<ILogProfit> tempList;
	private Button btnProducts;
	private Button btnFinance;
	private Button btnInsurance;
	private Button btnYanbao;
	private Button btnDisp;
	private Button fromBtn;
	private LinearLayout llBottomBtn;
	RelativeLayout ll_container;
	private static int kpiType = 0;

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
		View view = inflater.inflate(R.layout.fragment_derived, container, false);
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
		 * http://120.26.66.145:7522/cadaService/GetILogBusinessProfitServlet
		 * ?date=2015-12-23&license=zsj&role=经销商&brand=奥迪&mode=是&kpi=精品
		 */
		params.put("date", date);
		params.put("license", ProfitApplication.salesInfo.getLicense());
		params.put("role", ProfitApplication.salesInfo.getRole());
		params.put("brand", ProfitApplication.salesInfo.getBrand());
		params.put("mode", carModel);
		switch (kpiType) {
		case 0:
			params.put("kpi", "精品");
			break;
		case 1:
			params.put("kpi", "金融");
			break;
		case 2:
			params.put("kpi", "保险");
			break;
		case 3:
			params.put("kpi", "延保");
			break;
		case 4:
			params.put("kpi", "置换");
			break;
		}
		IRISService.exetuce(ProfitApplication.DERIVED, params, handler);
	}

	class BtnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_products:
				// 精品
				setrButtonImage(0);
				kpiType = 0;
				exetuceRequest();
				progressDialog.show();
				break;
			case R.id.btn_finance:
				// 金融
				setrButtonImage(2);
				kpiType = 1;
				exetuceRequest();
				progressDialog.show();
				break;
			case R.id.btn_insurance:
				// 保险
				setrButtonImage(4);
				kpiType = 2;
				exetuceRequest();
				progressDialog.show();
				break;
			case R.id.btn_yanbao:
				// 延保
				setrButtonImage(6);
				kpiType = 3;
				exetuceRequest();
				progressDialog.show();
				break;
			case R.id.btn_displacement:
				// 置换
				setrButtonImage(8);
				kpiType = 4;
				exetuceRequest();
				progressDialog.show();
				break;
			default:
				break;
			}
		}
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
			lineDatas.add(profit.getRate());
			barColors.add("#CC0033");
			lineColors.add("#FFD671");
		}
		leftAxisDatas = ProfitApplication.getAxisData(barDatas, false);
		rightAxisDatas = ProfitApplication.getAxisData(lineDatas, true);

		CKChartViewStyle style = new CKChartViewStyle(barColors, lineColors);
		style.setTopWidth(10);
		ArrayList<String> touchText = new ArrayList<String>();
		touchText.add(ProfitApplication.getModelString());
		touchText.add("单车盈利");
		touchText.add("渗透率");
		CKChartData.setTouchText(touchText);
		indicates.add("■单车置换盈利");
		indicates.add("✜置换渗透率");
		indicateColors.add("#CC0033");
		indicateColors.add("#FFD671");
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
		exetuceRequest();
		progressDialog.show();
	}
}
