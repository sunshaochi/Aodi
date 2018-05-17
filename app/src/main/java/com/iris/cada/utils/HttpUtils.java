package com.iris.cada.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.iris.cada.ProfitApplication;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.callback.ResultCallback;
import com.zhy.http.okhttp.request.OkHttpRequest;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

public class HttpUtils {

	private Handler handler;
	private String url;
	private Map<String, String> params;
	OkHttpClient client = new OkHttpClient();

	public HttpUtils(Handler handler, String url, Map<String, String> params) {
		super();
		this.handler = handler;
		this.url = url;
		this.params = params;
	}

	public HttpUtils(Map<String, String> params) {
		super();
		this.params = params;
	}

	public HttpUtils() {
		super();
	}

	public HttpUtils httpUtils() {
		return this;
	}

	public void execute() {
		new OkHttpRequest.Builder().url(url).params(params).get(new ResultCallback<String>() {
			@Override
			public void onError(Request arg0, Exception e) {// 如果请求失败
				Message msg = new Message();
				msg.what = ProfitApplication.SERVER_FAILED;
				handler.handleMessage(msg);
			}

			@Override
			public void onResponse(String s) {
				Message msg = new Message();
				if (!TextUtils.isEmpty(s) && !"[]".equals(s)) {
					msg.what = ProfitApplication.DATA_SUC;
					msg.obj = s;
					handler.handleMessage(msg);
				} else {
					msg.what = ProfitApplication.DATA_FAILED;
					handler.handleMessage(msg);
				}
			}
		});
	}

	public String post(String url) {
		try {
			Request request = new Request.Builder().url(url).build();
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().string();
			} else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取实际值和目标值得方法,
	 * 
	 * @param actualUrl
	 *            实际的url
	 * @param compareUrl
	 *            对比的url
	 * @return 返回一个list,包含实际值和目标值的json.如果实际或者目标任意一个值为空则返回null
	 */
	public List<String> post(String actualUrl, String compareUrl) {
		ArrayList<String> result = null;
		try {
			String tempActualData = this.post(actualUrl);
			String tempCompareData = this.post(compareUrl);
			if (tempActualData != null && tempCompareData != null && tempActualData.length() > 0
					&& tempCompareData.length() > 0) {
				result = new ArrayList<String>();
				result.add(tempActualData);
				result.add(tempCompareData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
