package com.iris.cada.utils;

import java.util.Date;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.SeparateAct;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * webview的共用方法
 **/
public class WebUtils {
	public static Date startdate;// 开始时间Date格式，结束时间Date格式
	public static String mTimeFormat = "yyyy-MM-dd";
	public static Date enddate;
	private static String stime;// 开始时间
	private static String etime;// 结束时间

	public static void WebSet(String luurl, WebView webview,final ProgressBar pb) {
		
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);		
//		webSettings.setDomStorageEnabled(true); // 有时候加载不出来或是一片空白是因为对某些标签不显示就要加上这句就行了
		 webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);// 默认不使用缓存
		// webSettings.setJavaScriptEnabled(true); // 让WebView能够执行javaScript

		// webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //
		// 让JavaScript可以自动打开windows

		// webSettings.setAppCacheEnabled(true); // 设置缓存

		// webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //
		// 设置缓存模式,一共有四种模式

		// webSettings.setAppCachePath(""); // 设置缓存路径

		// webSettings.setSupportZoom(true); // 支持缩放(适配到当前屏幕)

		// webSettings.setUseWideViewPort(true);// 将图片调整到合适的大小
		// 支持内容重新布局,一共有四种方式

		// webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		// // 默认的是NARROW_COLUMNS

		// webSettings.setDisplayZoomControls(true);// 设置可以被显示的屏幕控制

		// webSettings.setDefaultFontSize(12); // 设置默认字体大小

		
		pb.setMax(100);
		pb.setVisibility(View.VISIBLE);
		
		webview.loadUrl(luurl);
		
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				
				view.loadUrl(url);
				
				return true;
			}
			
			
			
		});

		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				pb.setProgress(newProgress);
				
				if (newProgress == 100) {
					pb.setVisibility(View.GONE);
				}else {
					pb.setVisibility(View.VISIBLE);
				}
				super.onProgressChanged(view, newProgress);
			}
		});

	}

	/**
	 * 获取开始时间
	 **/
	public static String getStart() {
		if (ProfitApplication.mStartDate == null) {
			startdate = DateAndTimeUtils.getCurrentMonthOfFirstDay();// 获取当前月的第一天
			ProfitApplication.mStartDate = startdate;
		} else {
			startdate = ProfitApplication.mStartDate;// 获取当前月的第一天
		}
		return DateAndTimeUtils.getTimeForDate(mTimeFormat, startdate);// 开始时间;
	}

	/**
	 * 获取结束时间
	 **/
	public static String getend() {
		if (ProfitApplication.mEndDate == null) {
			enddate = DateAndTimeUtils.getCurrentDate(mTimeFormat);
			ProfitApplication.mEndDate = enddate;
		} else {
			enddate = ProfitApplication.mEndDate;
		}
		return DateAndTimeUtils.getTimeForDate(mTimeFormat, enddate);// 开始时间;
	}

	public static void showDatePic(final AddTodoPickerViewUtils addPickerViewUtils, final boolean b,
			final Context context, final TextView tvstart, final TextView tvend) {
		   TimePickerViewDialog pvTimeDialog = addPickerViewUtils.initDatePickerView(b ? "请选择开始时间" : "请选择结束时间")
				.getPvTime();
		   pvTimeDialog.getDialog().show();
		   pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				// TODO Auto-generated method stub
				if (b) {
					if (!DateAndTimeUtils.isBefore(date, DateAndTimeUtils.getCurrentDate(mTimeFormat))) {// 开始时间比当前时间大
						ToastUtils.showMyToast(context, "开始时间不可大于当前时间");
						return;
					} else {// 开始时间比当前时间小
						stime = addPickerViewUtils.getDate(date);// 开始时间
						tvstart.setText(stime);

						startdate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, stime);// 开始时间锉
						ProfitApplication.mStartDate = startdate;
						etime = DateAndTimeUtils.getLastDateOfMonth(startdate);
						enddate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, etime);

						if (!DateAndTimeUtils.isBefore(enddate, DateAndTimeUtils.getCurrentDate(mTimeFormat))) {
							tvend.setText(DateAndTimeUtils.getCurrentTime(mTimeFormat));// 当前时间
							ProfitApplication.mEndDate = DateAndTimeUtils.getCurrentDate(mTimeFormat);
						} else {
							tvend.setText(etime);
							ProfitApplication.mEndDate = enddate;
						}
					}

				} else {
					if (!DateAndTimeUtils.isBefore(date, DateAndTimeUtils.getCurrentDate(mTimeFormat))) {// 结束时间比当前时间大
						return;
					} else {
						etime = addPickerViewUtils.getDate(date);// 结束时间
						enddate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, etime);// 结束时间锉
						if (DateAndTimeUtils.isEqual(startdate, enddate)) {// 结束时间等于开始时间
							tvend.setText(etime);
							ProfitApplication.mEndDate = enddate;
						} else if (!DateAndTimeUtils.isBefore(startdate, enddate)) {// 结束时间小于开始时间
							ToastUtils.showMyToast(context, "“结束时间”不可小于“开始时间”");
							return;
						} else {// 结束时间大于开始时间小于当前时间
							tvend.setText(etime);
							ProfitApplication.mEndDate = enddate;
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
	
	public static void clearViewCache(WebView view,Context context){
		//清空所有Cookie
	    CookieSyncManager.createInstance(context);  //Create a singleton CookieSyncManager within a context
	    CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
	    cookieManager.removeAllCookie();// Removes all cookies.
	    CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

	    view.setWebChromeClient(null);
	    view.setWebViewClient(null);
	    view.getSettings().setJavaScriptEnabled(false);
	    view.clearCache(true);
	   
	    
	}

}
