package com.iris.cada.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iris.cada.ProfitApplication;
import com.iris.cada.utils.HttpUtils;

import android.os.Handler;

public class IRISService {
	public final static int ISCOMPARE = 1000;

	/**
	 * 
	 * @param url
	 * @param params
	 * @param handler
	 */
	public static void exetuce(String url, Map<String, String> params, Handler handler) {
		new HttpUtils(handler, url, params).execute();
	}

//	/**
//	 * 登录
//	 *
//	 * @param url
//	 * @param user
//	 * @param pass
//	 * @param uid
//	 * @param ver
//	 * @param handler
//	 */
//	public static void doLogin(String user, String pass, String uid, String ver, Handler handler) {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("user", user);
//		params.put("pass", pass);
//		params.put("uid", uid);
//		params.put("ver", ver);
//		new HttpUtils(handler, ProfitApplication.DO_LOGIN, params).execute();
//	}

//	/**
//	 *
//	 * @param user
//	 * @param pass
//	 * @param uid
//	 * @param ver
//	 * @param handler
//	 */
//	public static void doHome(String delearCode, String brand, String role, String startTime, String endTime,
//			Handler handler) {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("user", delearCode);
//		params.put("brand", brand);
//		params.put("role", role);
//		params.put("startTime", startTime);
//		params.put(endTime, endTime);
//		new HttpUtils(handler, ProfitApplication.IV_HOME, params).execute();
//	}

	/**
	 * 我的信息
	 * 
	 * @param
	 * @param user
	 * @param
	 * @param
	 * @param
	 * @param handler
	 */
	public static void doMyInfo(String user, String tel, Handler handler) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("user", user);
		params.put("tel", tel);
		new HttpUtils(handler, ProfitApplication.MY_INFO, params).execute();
	}

	/**
	 * 获取单店licence
	 * 
	 * @param user
	 * @param name
	 * @param
	 */
	public static String getLicence(String user, String name) {
		String url = "";
		try {
			url = ProfitApplication.GET_LICENCE + "?user=" + URLEncoder.encode(user, "UTF-8") + "&name="
					+ URLEncoder.encode(name, "UTF-8") + "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new HttpUtils().post(url);
	}

	/**
	 * 修改密码
	 * 
	 * @param user
	 * @param pass
	 * @param handler
	 */
	public static void doUpdatePwd(String user, String pass, Handler handler) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("user", user);
		params.put("pass", pass);
		new HttpUtils(handler, ProfitApplication.UPDATE_PWD, params).execute();
	}

	/**
	 * 首页-报表
	 * 
	 * @param date
	 * @param license
	 * @param role
	 * @param brand
	 * @param mode
	 * @throws UnsupportedEncodingException
	 */
	public static List<String> doReport(String date, String license, String role, String brand, String mode) {
		String actualUrl = "";
		String compareUrl = "";
		try {
			actualUrl = ProfitApplication.REPORT_MON + "?date=" + URLEncoder.encode(date, "UTF-8") + "&license="
					+ URLEncoder.encode(license, "UTF-8") + "&role=" + URLEncoder.encode(role, "UTF-8") + "&brand="
					+ URLEncoder.encode(brand, "UTF-8") + "&mode=" + URLEncoder.encode(mode, "UTF-8") + "";
			compareUrl = ProfitApplication.REPORT_TARGET + "?date=" + URLEncoder.encode(date, "UTF-8") + "&license="
					+ URLEncoder.encode(license, "UTF-8") + "&role=" + URLEncoder.encode(role, "UTF-8") + "&brand="
					+ URLEncoder.encode(brand, "UTF-8") + "&mode=" + URLEncoder.encode(mode, "UTF-8") + "";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new HttpUtils().post(actualUrl, compareUrl);
	}

	/**
	 * 利润-报表
	 * 
	 * @param date
	 * @param license
	 * @param role
	 * @param brand
	 * @param mode
	 * @throws UnsupportedEncodingException
	 */
	public static List<String> doIporfit(String date, String license, String role, String brand, String mode) {
		String actualUrl = "";
		String compareUrl = "";
		try {
			actualUrl = ProfitApplication.PROFIT_MON + "?date=" + URLEncoder.encode(date, "UTF-8") + "&license="
					+ URLEncoder.encode(license, "UTF-8") + "&role=" + URLEncoder.encode(role, "UTF-8") + "&brand="
					+ URLEncoder.encode(brand, "UTF-8") + "&mode=" + URLEncoder.encode(mode, "UTF-8") + "";
			compareUrl = ProfitApplication.PROFIT_TARGET + "?date=" + URLEncoder.encode(date, "UTF-8") + "&license="
					+ URLEncoder.encode(license, "UTF-8") + "&role=" + URLEncoder.encode(role, "UTF-8") + "&brand="
					+ URLEncoder.encode(brand, "UTF-8") + "&mode=" + URLEncoder.encode(mode, "UTF-8") + "";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new HttpUtils().post(actualUrl, compareUrl);
	}

	/**
	 * 
	 * @param delearCode:经销商代码
	 * @param brand:品牌
	 * @param role:角色
	 * @param startTime:起始日期
	 * @param endTime:结束日期
	 * @param car:车型/All
	 * @param handler
	 */
	public static void getOverviewOperativeServlet(String delearCode, String brand, String role, String startTime,
			String endTime, String car, Handler handler) {
		String requestUrl = ProfitApplication.NO_BASE + "IViewOperatingServlet";
		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("delearCode", delearCode);
		params.put("brand", brand);
		params.put("role", role);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("car", car);
		
		new HttpUtils(handler, requestUrl , params).execute();
	}
}
