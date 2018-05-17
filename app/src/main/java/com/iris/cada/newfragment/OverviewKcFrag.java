package com.iris.cada.newfragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.ProfitApplication;
import com.iris.cada.Statcode;
import com.iris.cada.adapter.BottemAdap;
import com.iris.cada.entity.BbkcBean;
import com.iris.cada.entity.GlkcIfBean;
import com.iris.cada.entity.GlkcTime;
import com.iris.cada.entity.ShzdBean;
import com.iris.cada.fragment.NewBaseFrag;
import com.iris.cada.utils.DateAndTimeUtils;
import com.iris.cada.utils.TimeRefreshUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.utils.WebUtils;
import com.iris.cada.view.EditDialogUtils;
import com.iris.cada.view.CustomSheetDialog.OnListViewItemClick;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.cada.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class OverviewKcFrag extends NewBaseFrag {
	@ViewInject(R.id.overview_top_start_time)
	private TextView tv_start;// 开始时间
	@ViewInject(R.id.ll_webkc)
	private LinearLayout ll_web;
	@ViewInject(R.id.overview_top_end_time)
	private TextView tv_end;// 结束时间
	@ViewInject(R.id.overview_top_car_mode)
	private TextView tv_car;// 选择的车型或者销售顾问在输入框里面
	@ViewInject(R.id.text_car_mode)
	private TextView tv_type;// 车型或者是销售顾问在输入框左边
	@ViewInject(R.id.overview_top_car_mode)
	private TextView txtCarMode;
	@ViewInject(R.id.tv_kcsj)//库存数据
	private TextView tv_kcsj;
	@ViewInject(R.id.tv_qksj)//潜客数据
	private TextView tv_qksj;
	@ViewInject(R.id.tv_lrsj)//利润数据
	private TextView tv_lrsj;
	@ViewInject(R.id.tv_dqkc)//当前库存
	private TextView tv_dqkc;
	@ViewInject(R.id.tv_pjkc)//平均库存
	private TextView tv_pjkc;
	@ViewInject(R.id.lv_bottem)
	private ListView lv_bottem;//单车gp3下面list
	
    private BottemAdap adpter;

	private List<String> mCarModeList;
	private List<String> mcarcodeList;
	

	private String surl;
	private WebView webView;

	private AddTodoPickerViewUtils addPickerViewUtils;
	private String mTimeFormat;
	private String stime;// 选择的开始时间
	private Date startdate;// 选择的开始时间锉求结束时间
	private String etime;// 计算出的结束时间
	private Date enddate;// 结束时间锉和当前时间比较
	private String iscarmoudle;
	private String cartype;
	private Gson gson;
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {			
			super.handleMessage(msg);
			switch (msg.what) {
			case Statcode.SHSUC://获取更新时间和平均库存数量成功
				setData(msg);
				break;		
			case Statcode.SHFAILED://获取更新时间和平均库存数量成功
				tv_kcsj.setText("*库存数据更新于:");
				tv_qksj.setText("*潜客/成交数据更新于:");
				tv_lrsj.setText("*利润数据更新于:");
				tv_dqkc.setText(""+"台");
				tv_pjkc.setText(""+"天");
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				break;	
				
			case Statcode.XSSUC://获取GP3等数据成功
				setListDate(msg);
				break;		
			case Statcode.XS_FAILED://获取GP3等数据失败
				List<GlkcIfBean>nolist=new ArrayList<GlkcIfBean>();
				adpter=new BottemAdap(getActivity(), nolist);
				lv_bottem.setAdapter(adpter);
				adpter.notifyDataSetChanged();
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				break;	

			case ProfitApplication.SERVER_FAILED://连接服务器失败
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_net));
				break;
			
			}
		}

		

		

		
		
	};
	
	private void setListDate(Message msg) {
		// TODO Auto-generated method stub		
		try {
			String lmess=(String) msg.obj;
			List<GlkcIfBean>list=gson.fromJson(new JSONObject(lmess).getJSONArray("data").toString(), new TypeToken<List<GlkcIfBean>>(){}.getType());
			if(list!=null&&list.size()>0){
				adpter=new BottemAdap(getActivity(), list);
				lv_bottem.setAdapter(adpter);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setData(Message msg) {
		// TODO Auto-generated method stub
		try {
			String mess=(String) msg.obj;
//			Log.e("平均时间", mess);
			GlkcTime glkcbean=gson.fromJson(new JSONObject(mess).getJSONObject("data").toString(), GlkcTime.class);
			tv_kcsj.setText("*库存数据更新于:"+glkcbean.getRepertoryUpdateTime());
			tv_qksj.setText("*潜客/成交数据更新于:"+glkcbean.getReceptionUpdateTime());
			tv_lrsj.setText("*利润数据更新于:"+glkcbean.getProfitUpdateTime());
			tv_dqkc.setText(glkcbean.getStorageDaysNum()+"台");
			tv_pjkc.setText(glkcbean.getAvgStorageDays()+"天");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.frg_overkc, null);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mTimeFormat = "yyyy-MM-dd";
		addPickerViewUtils = new AddTodoPickerViewUtils(getActivity());// 工具内时间弹出框
		tv_start.setText(WebUtils.getStart());
		tv_end.setText(WebUtils.getend());
		startdate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, tv_start.getText().toString());// 赋值后的开始时间
		gson=new Gson();
		// 车型列表
		mCarModeList = new ArrayList<String>();
		mcarcodeList=new ArrayList<>();
		mCarModeList.add("All");
		mcarcodeList.add("All");
		if(ProfitApplication.loginBackInfo.getModels()!=null&&ProfitApplication.loginBackInfo.getModels().size()>0) {
			mCarModeList.addAll(ProfitApplication.loginBackInfo.getModels());
			mcarcodeList.addAll(ProfitApplication.loginBackInfo.getCarCodeList());
		}
		txtCarMode.setText(mCarModeList.get(0));
		iscarmoudle="N";
		cartype="";
		reFresh();//获取数据
		
//		surl = ProfitApplication.H5_XSGLKC+"?dealerCode="+ProfitApplication.loginBackInfo.getLicense()
//		+"&endDate="+tv_end.getText()
//		+"&isCarModel="+iscarmoudle+"&carType="+cartype;
//		webSet(surl);//调取链接
//		getTime(tv_end.getText().toString(),iscarmoudle,cartype);//获取界面上的几个时间和平均库存等
//	    getList(tv_end.getText().toString(),iscarmoudle,cartype);
	}

	private void getList(String endDate,String isCarModel,String carType) {
		// TODO Auto-generated method stub
		ProfitApplication.profitNetService.getViewRepertoryAndProfit(endDate,isCarModel,carType,handler);
	}

	private void getTime(String endDate,String isCarModel,String carType) {
		ProfitApplication.profitNetService.getViewUpdateTime(endDate,isCarModel,carType,handler);
		
	}

	private void webSet(String hurl) {
		// TODO Auto-generated method stub

		ll_web.removeAllViews();
		webView = null;
		webView = new WebView(getActivity());
		webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		ll_web.addView(webView);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setAllowFileAccess(true);

		webView.loadUrl(hurl);

		webView.setWebViewClient(new WebViewClient() {


			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});


	}

	@OnClick({ R.id.overview_top_start_time_layout, R.id.overview_top_end_time_layout,
			R.id.overview_top_car_mode_layout })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.overview_top_start_time_layout:// 开始时间
			showDateDialog(true);// 选择开始时间
			break;
		case R.id.overview_top_end_time_layout:// 结束时间
			showDateDialog(false);// 选择开始时间
			break;
		case R.id.overview_top_car_mode_layout:// 选择车型
			showCarModeDialog();
			break;

		}
	}

	private void showDateDialog(final boolean b) {
		// TODO Auto-generated method stub
		TimePickerViewDialog pvTimeDialog = addPickerViewUtils.initDatePickerView(b ? "请选择开始时间" : "请选择结束时间")
				.getPvTime();
		pvTimeDialog.getDialog().show();
		pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				// TODO Auto-generated method stub
				if (b) {
					if (!DateAndTimeUtils.isBefore(date, DateAndTimeUtils.getCurrentDate(mTimeFormat))) {// 开始时间比当前时间大
						ToastUtils.showMyToast(getActivity(), "开始时间不可大于当前时间");
						return;
					} else {// 开始时间比当前时间小
						stime = addPickerViewUtils.getDate(date);// 开始时间
						tv_start.setText(stime);

						startdate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, stime);// 开始时间锉
						ProfitApplication.mStartDate = startdate;
						etime = DateAndTimeUtils.getLastDateOfMonth(startdate);
						enddate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, etime);

						if (!DateAndTimeUtils.isBefore(enddate, DateAndTimeUtils.getCurrentDate(mTimeFormat))) {
							tv_end.setText(DateAndTimeUtils.getCurrentTime(mTimeFormat));// 当前时间
							// ProfitApplication.mEndDate=DateAndTimeUtils.getCurrentDate(mTimeFormat);
							TimeRefreshUtils.sendTimeRefreshMsg(getContext(), startdate,
									DateAndTimeUtils.getCurrentDate(mTimeFormat));
//							getTime(tv_end.getText().toString(),iscarmoudle,cartype);//获取界面上的几个时间和平均库存等
//						    getList(tv_end.getText().toString(),iscarmoudle,cartype);
//						    surl = ProfitApplication.H5_XSGLKC+"?dealerCode="+ProfitApplication.loginBackInfo.getLicense()
//							+"&endDate="+tv_end.getText()
//							+"&isCarModel="+iscarmoudle+"&carType="+cartype;
//							webSet(surl);//调取链接
							reFresh();
						} else {
							tv_end.setText(etime);
							// ProfitApplication.mEndDate=enddate;
							TimeRefreshUtils.sendTimeRefreshMsg(getContext(), startdate, enddate);
//							getTime(tv_end.getText().toString(),iscarmoudle,cartype);//获取界面上的几个时间和平均库存等
//						    getList(tv_end.getText().toString(),iscarmoudle,cartype);
//						    surl = ProfitApplication.H5_XSGLKC+"?dealerCode="+ProfitApplication.loginBackInfo.getLicense()
//							+"&endDate="+tv_end.getText()
//							+"&isCarModel="+iscarmoudle+"&carType="+cartype;
//							webSet(surl);//调取链接
							reFresh();
						}
					}

				} else {
					if (!DateAndTimeUtils.isBefore(date, DateAndTimeUtils.getCurrentDate(mTimeFormat))) {// 结束时间比当前时间大
						return;
					} else {
						etime = addPickerViewUtils.getDate(date);// 结束时间
						enddate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, etime);// 结束时间锉
						if (DateAndTimeUtils.isEqual(startdate, enddate)) {// 结束时间等于开始时间
							tv_end.setText(etime);
							// ProfitApplication.mEndDate=enddate;
							TimeRefreshUtils.sendTimeRefreshMsg(getContext(), startdate, enddate);
//							getTime(tv_end.getText().toString(),iscarmoudle,cartype);//获取界面上的几个时间和平均库存等
//						    getList(tv_end.getText().toString(),iscarmoudle,cartype);
//						    surl = ProfitApplication.H5_XSGLKC+"?dealerCode="+ProfitApplication.loginBackInfo.getLicense()
//							+"&endDate="+tv_end.getText()
//							+"&isCarModel="+iscarmoudle+"&carType="+cartype;
//							webSet(surl);//调取链接
							reFresh();
						} else if (!DateAndTimeUtils.isBefore(startdate, enddate)) {// 结束时间小于开始时间
							ToastUtils.showMyToast(getActivity(), "“结束时间”不可小于“开始时间”");
							return;
						} else {// 结束时间大于开始时间小于当前时间
							tv_end.setText(etime);
							// ProfitApplication.mEndDate=enddate;
							TimeRefreshUtils.sendTimeRefreshMsg(getContext(), startdate, enddate);
//							getTime(tv_end.getText().toString(),iscarmoudle,cartype);//获取界面上的几个时间和平均库存等
//						    getList(tv_end.getText().toString(),iscarmoudle,cartype);
//						    surl = ProfitApplication.H5_XSGLKC+"?dealerCode="+ProfitApplication.loginBackInfo.getLicense()
//							+"&endDate="+tv_end.getText()
//							+"&isCarModel="+iscarmoudle+"&carType="+cartype;
//							webSet(surl);//调取链接
							reFresh();
						}
					}

				}

			}

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub

			}
		});

	}

	// 显示车型
	private void showCarModeDialog() {

		EditDialogUtils.showSelectDialog(getContext(), txtCarMode, "请选择车型", mCarModeList)
				.setItemClick(new OnListViewItemClick() {

					@Override
					public void onClick(String str, int position) {
						if(txtCarMode.getText().toString().equals(str)){
							//什么事情不做
						}else{
						txtCarMode.setText(str);

						if (str.equals("All")) {
							iscarmoudle="N";
							cartype="";
						} else {
							iscarmoudle="Y";
							cartype=mcarcodeList.get(position-1);
						}
						reFresh();
//						getTime(tv_end.getText().toString(),iscarmoudle,cartype);//获取界面上的几个时间和平均库存等
//						getList(tv_end.getText().toString(),iscarmoudle,cartype);
//						
//						surl = ProfitApplication.H5_XSGLKC+"?dealerCode="+ProfitApplication.loginBackInfo.getLicense()
//						+"&endDate="+tv_end.getText()
//						+"&isCarModel="+iscarmoudle+"&carType="+cartype;
//						webSet(surl);//调取链接
					}

					}
				});
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (hidden) {
			  
//			 WebUtils.clearViewCache(wb, getActivity());
//			 wb.loadDataWithBaseURL(null, "","text/html", "utf-8",null);
		} else {
			upData();
		}
	}

	public void upData() {
		// TODO Auto-generated method stub
		if (tv_start.getText().equals(WebUtils.getStart()) && tv_end.getText().equals(WebUtils.getend())) {
			
			    surl = ProfitApplication.H5_XSGLKC+"?dealerCode="+ProfitApplication.loginBackInfo.getDealerId()
				+"&endDate="+tv_end.getText()
				+"&isCarModel="+iscarmoudle+"&carType="+cartype;
			    Log.e("url", surl);
//			    WebUtils.clearViewCache(wb, getActivity());
//			    wb.loadDataWithBaseURL(null, "","text/html", "utf-8",null);
				webSet(surl);//调取链接
				
		} else {
			tv_start.setText(WebUtils.getStart());
			tv_end.setText(WebUtils.getend());
//			getTime(tv_end.getText().toString(),iscarmoudle,cartype);//获取界面上的几个时间和平均库存等
//		    getList(tv_end.getText().toString(),iscarmoudle,cartype);
//		    surl = ProfitApplication.H5_XSGLKC+"?dealerCode="+ProfitApplication.loginBackInfo.getLicense()
//			+"&endDate="+tv_end.getText()
//			+"&isCarModel="+iscarmoudle+"&carType="+cartype;
//			webSet(surl);//调取链接
			
			reFresh();
		}
	}

	private void reFresh() {
		// TODO Auto-generated method stub
		getTime(tv_end.getText().toString(),iscarmoudle,cartype);//获取界面上的几个时间和平均库存等
	    getList(tv_end.getText().toString(),iscarmoudle,cartype);
	    surl = ProfitApplication.H5_XSGLKC+"?dealerCode="+ProfitApplication.loginBackInfo.getDealerId()
		+"&endDate="+tv_end.getText()
		+"&isCarModel="+iscarmoudle+"&carType="+cartype;
	    Log.e("url", surl);
//	    WebUtils.clearViewCache(wb, getActivity());
//	    wb.loadDataWithBaseURL(null, "","text/html", "utf-8",null);
//		String url="http://baidu.com";
	    webSet(surl);//调取链接
	}

}
