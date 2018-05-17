package com.iris.cada.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.adapter.ReportListAdapter;
import com.iris.cada.entity.ILogData;
import com.iris.cada.fragment.ProfitFragment;
import com.iris.cada.service.IRISService;
import com.iris.cada.utils.CKDateCalculate;
import com.iris.cada.utils.NowTimeGetter;
import com.iris.cada.view.CKTitleView;
import com.iris.cada.view.CKTitleView.TitleClick;
import com.iris.cada.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 展厅表现
 * 
 * @author jiahaoGuo
 * @version 2015-12-16 16:09:18
 */
public class ProfitSecondActivity extends BaseActivity {

	private CKTitleView titleView;
	private ListView listVeiw;
	private TextView tvDate;
	private TextView tvIsCar;
	private String date;
	private Button btnLeft, btnRight;
	private ReportListAdapter reportListAdapter;
	private Map<String, String> params;
	private ArrayList<ILogData> list;
	private ArrayList<ILogData> listCompare;
	public static Boolean IS_DAY = true;
	private String models;
	private String carModel = "否";
	private static String licence = "";
	private final int SUCCESS_LICENCE = 122;

	Handler handler = new MyHandler(this) {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDialog.dismiss();
			String json = (String) msg.obj;
			switch (msg.what) {
			case SUCCESS_LICENCE:
				exetuceRequest();
				progressDialog.show();
				break;
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
				Toast.makeText(getApplicationContext(), "暂无数据!", Toast.LENGTH_SHORT).show();
				break;
			case ProfitApplication.SERVER_FAILED:
				Toast.makeText(getApplicationContext(), "服务器连接失败!", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		setContentView(R.layout.activity_profit_second);
		initView();
	}

	public void initView() {
		models = getIntent().getStringExtra("models");
		date = getIntent().getStringExtra("date");
		// 获取报表信息
		IS_DAY = ProfitFragment.IS_DAY;
		tvIsCar = (TextView) findViewById(R.id.tv_iscar);
		setListViewTitle(tvIsCar, carModel);
		titleView = (CKTitleView) findViewById(R.id.title);
		titleView.getLeftButoon().setVisibility(View.GONE);
		titleView.getRightButoon().setVisibility(View.VISIBLE);
		titleView.getRightButoon().setBackgroundResource(R.drawable.back);
		TextView title = new TextView(this);
		title.setText(models);
		title.setTextSize(ProfitApplication.getTitleTextSize(this));
		title.setTextColor(getResources().getColor(R.color.black));
		titleView.getLinearLayout().addView(title);

		tvDate = (TextView) findViewById(R.id.tv_date);
		list = new ArrayList<ILogData>();
		listCompare = new ArrayList<ILogData>();
		// 初始化底部button
		btnLeft = (Button) findViewById(R.id.btn_left);
		btnLeft.setOnClickListener(new BtnClickListener());
		btnRight = (Button) findViewById(R.id.btn_right);
		btnRight.setOnClickListener(new BtnClickListener());
		btnLeft.setBackgroundResource(R.drawable.current_left);
		btnRight.setBackgroundResource(R.drawable.default_right);
		btnLeft.setTextColor(getResources().getColor(R.color.white));
		btnRight.setTextColor(getResources().getColor(R.color.btn_text));

		listVeiw = (ListView) findViewById(R.id.lv_profit);
		reportListAdapter = new ReportListAdapter(this, list, listCompare, 5, IS_DAY);
		listVeiw.setAdapter(reportListAdapter);
		titleView.setTitleClick(new TitleClick() {

			@Override
			public void btRightClick() {
				// if (IS_DAY) {
				// if (!NowTimeGetter.getday().equals(date)) {
				// date = CKDateCalculate.nextDay(date);
				// }
				// } else {
				// if
				// (!NowTimeGetter.getMon(NowTimeGetter.getday()).equals(NowTimeGetter.getMon(date)))
				// {
				// date = CKDateCalculate.nextMonth(date);
				// }
				// }
				// exetuceRequest();
				// progressDialog.show();
				finish();
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
			}
		});
		params = new HashMap<String, String>();
		getLicence();
	}

	/**
	 * 获取licence
	 * 
	 * @return
	 */
	public void getLicence() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				licence = IRISService.getLicence(ProfitApplication.salesInfo.getLicense(), models);
				handler.sendMessage(handler.obtainMessage(SUCCESS_LICENCE));
			}
		}).start();
	}

	class BtnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_left:
				// 销售顾问
				carModel = "否";
				setListViewTitle(tvIsCar, carModel);
				btnLeft.setBackgroundResource(R.drawable.current_left);
				btnRight.setBackgroundResource(R.drawable.default_right);
				btnLeft.setTextColor(getResources().getColor(R.color.white));
				btnRight.setTextColor(getResources().getColor(R.color.btn_text));
				exetuceRequest();
				progressDialog.show();
				break;
			case R.id.btn_right:
				// 车型
				carModel = "是";
				setListViewTitle(tvIsCar, carModel);
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

	List<String> jsons = null;

	public void exetuceRequest() {
		params.put("date", date);
		params.put("license", licence);
		params.put("role", "经销商");
		params.put("brand", ProfitApplication.salesInfo.getBrand());
		params.put("mode", carModel);
		if (IS_DAY) {
			IRISService.exetuce(ProfitApplication.PROFIT_DAY, params, handler);
		} else {
			new Thread(new Runnable() {

				@Override
				public void run() {
					jsons = IRISService.doIporfit(date, licence, "经销商", ProfitApplication.salesInfo.getBrand(), carModel);
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
		}
	}

	public void resetView(ArrayList<ILogData> datas) {
		titleView.getRightButoon().setVisibility(View.VISIBLE);
		if (IS_DAY) {// 如果为日模式,则将左按钮文字设为上一日
			tvDate.setText(date);
			// titleView.getLeftTextView().setText("上一日");
			// if (NowTimeGetter.getday().equals(date)) {
			// titleView.getRightButoon().setBackgroundResource(R.drawable.refresh);
			// titleView.getRightTextView().setText("");
			// } else {
			// titleView.getRightButoon().setBackgroundResource(R.drawable.next);
			// titleView.getRightTextView().setText("下一日");
			// }
			list = dataHandle(datas);
			reportListAdapter.setData(list, listCompare, IS_DAY);
		} else {// 如果为月模式,则将左按钮文字设为上一月
			tvDate.setText(NowTimeGetter.getMon(date));
			// titleView.getLeftTextView().setText("上一月");
			// if
			// (NowTimeGetter.getMon(NowTimeGetter.getday()).equals(NowTimeGetter.getMon(date)))
			// {
			// titleView.getRightButoon().setBackgroundResource(R.drawable.refresh);
			// titleView.getRightTextView().setText("");
			// } else {
			// titleView.getRightButoon().setBackgroundResource(R.drawable.next);
			// titleView.getRightTextView().setText("下一月");
			// }
			list = dataHandle(list);
			reportListAdapter.setData(list, listCompare, IS_DAY);
			Log.d("list", list.toString());
			Log.d("listCompare", listCompare.toString());
		}
	}

	/**
	 * 遍历他的iLogDatas属性,得到ILogData中的总计
	 * 
	 * @param iLogData
	 * @return
	 */
	public ArrayList<ILogData> dataHandle(ArrayList<ILogData> iLogDatas) {
		int singleCar = 0;
		int collectible = 0;
		int finance = 0;
		int insurance = 0;
		int exInsurance = 0;
		int replacement = 0;
		int profit = 0;
		ArrayList<ILogData> tempListILogDatas = iLogDatas;
		for (int i = 0; i < tempListILogDatas.size(); i++) {
			ILogData temp = tempListILogDatas.get(i);

			if (!ProfitApplication.stringIsEmpty(temp.getSingleCar()).equals("Na")) {
				singleCar += ProfitApplication.stringIsNull(temp.getSingleCar());
			}
			if (!ProfitApplication.stringIsEmpty(temp.getSingleCar()).equals("Na")) {
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
		}
		String tempStr = "总计";
		ILogData tempILogData = new ILogData(tempStr, singleCar + "", collectible + "", finance + "", insurance + "",
				exInsurance + "", replacement + "", profit + "");
		tempListILogDatas.add(0, tempILogData);
		return tempListILogDatas;
	}
}
