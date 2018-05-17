package com.iris.cada.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iris.cada.ProfitApplication;
import com.iris.cada.Statcode;
import com.iris.cada.entity.IViewSCBean;
import com.iris.cada.newfragment.MonitorOperativeChannelFragment;
import com.iris.cada.newfragment.MonitorOperativeProcessFragment;
import com.iris.cada.newfragment.MonitorProfitChannelFragment;
import com.iris.cada.newfragment.OverviewOperativeFragment;
import com.iris.cada.newfragment.OverviewProfitFragment;
import com.iris.foundation.utils.NetRequestUtils;
import com.iris.foundation.utils.SpUtils;
import com.lidroid.xutils.http.RequestParams;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

public class ProfitNetService {

	NetRequestUtils netRequestUtils;

	public ProfitNetService() {
		if (netRequestUtils == null) {
			netRequestUtils = new NetRequestUtils();
		}
	}

	/**
	 * 概览-运营	车型
	 * 
	 * @param delearCode:经销商代码
	 * @param brand:品牌
	 * @param role:角色
	 * @param startTime:起始日期
	 * @param endTime:结束日期
	 * @param car:车型/All
	 * @param handler
	 */
	public void getOverviewOperativeServlet(String startTime, String endTime, String parameter, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");

		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());//SA00000
		params.addQueryStringParameter("brand", ProfitApplication.loginBackInfo.getBrand());
		params.addQueryStringParameter("role", ProfitApplication.loginBackInfo.getRole());
		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "N" : "Y");
		params.addQueryStringParameter("startTime", startTime);//2017-01-01
		params.addQueryStringParameter("endTime", endTime);//2017-01-12
		params.addQueryStringParameter("parameter", parameter);
 
		netRequestUtils.executePost(ProfitApplication.NO_BASE + "IViewOperatingServlet", params, handler);
	}
	
	
	/**
	 * 
	 * 首页和分界页面的售后数据
	 * 	
	 */
	public void getShsydate(String startDate, String endDate,Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");		
		params.addQueryStringParameter("startDate", startDate);//2017-01-01
		params.addQueryStringParameter("endDate", endDate);//2017-01-12
		params.addQueryStringParameter("dealerCode",ProfitApplication.loginBackInfo.getLicense());	
        netRequestUtils.executePost(ProfitApplication.SHSY_DATE , params, handler,Statcode.SHSUC,Statcode.SHFAILED);
	} 
	
	
	/**
	 * 
	 * 售后模块导入记录
	 * 	
	 */
	public void getReporthistory(Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");		
		params.addQueryStringParameter("dealerCode", ProfitApplication.loginBackInfo.getLicense());	
		netRequestUtils.executePost(ProfitApplication.REPORT_HISTORY, params, handler);
//		Log.e("hah", ProfitApplication.loginBackInfo.getLicense());
	}
	
	
	/**
	 * 概览-运营 销售顾问
	 * 
	 * @param delearCode:经销商代码
	 * @param brand:品牌
	 * @param role:角色
	 * @param startTime:起始日期
	 * @param endTime:结束日期
	 * @param car:车型/All
	 * @param handler
	 */
	public void getOverviewOperativeServlet2(String startTime, String endTime, String parameter, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");

		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
//		params.addQueryStringParameter("brand", ProfitApplication.loginBackInfo.getBrand());
//		params.addQueryStringParameter("role", ProfitApplication.loginBackInfo.getRole());
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);
//		params.addQueryStringParameter("parameter", parameter);

		netRequestUtils.executePost(ProfitApplication.NO_BASE + "GetIViewSCList", params, handler,OverviewOperativeFragment.OVERVIEW_DATA_SUC,OverviewOperativeFragment.OVERVIEW_DATA_FIN);
	}


	/**
	 * 概览-盈利
	 * 
	 * @param delearCode:经销商代码
	 * @param brand:品牌
	 * @param role:角色
	 * @param startTime:起始日期
	 * @param endTime:结束日期
	 * @param car:车型/All
	 * @param handler
	 */
	public void getOverviewProfitServlet(String startTime, String endTime, String parameter, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");

		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getDealerCode());//SA88888888
//		params.addQueryStringParameter("brand", ProfitApplication.loginBackInfo.getBrand());//奥迪
//		params.addQueryStringParameter("role", ProfitApplication.loginBackInfo.getRole());//经销商
		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "N" : "Y");//缓过来了试一下
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);
		params.addQueryStringParameter("parameter", parameter);//王一成

		netRequestUtils.executePost(ProfitApplication.NO_BASE + "IViewProfitServlet", params, handler);
	}
	
	
	/**
	 * 概览-盈利 销售顾问
	 * 
	 * @param delearCode:经销商代码
	 * @param brand:品牌
	 * @param role:角色
	 * @param startTime:起始日期
	 * @param endTime:结束日期
	 * @param car:车型/All
	 * @param handler
	 */
	public void getOverviewProfitServlet2(String startTime, String endTime, String car, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");

		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
//		params.addQueryStringParameter("brand", ProfitApplication.loginBackInfo.getBrand());
//		params.addQueryStringParameter("role", ProfitApplication.loginBackInfo.getRole());
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);
//		params.addQueryStringParameter("car", car);

		netRequestUtils.executePost(ProfitApplication.NO_BASE + "GetIViewSCList", params, handler,OverviewProfitFragment.OVERVIEW_DATA_SUC,OverviewProfitFragment.OVERVIEW_DATA_FIN);
	}

	/**
	 * 首页
	 * 
	 * @param delearCode:经销商代码
	 * @param brand:品牌
	 * @param role:角色
	 * @param startTime:起始日期
	 * @param endTime:结束日期
	 * @param car:车型/All
	 * @param handler
	 */
	public void getiviewFirstHomeServlet(String startTime, String endTime, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("brand", ProfitApplication.loginBackInfo.getBrand());
		params.addQueryStringParameter("role", ProfitApplication.loginBackInfo.getRole());
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);
		netRequestUtils.executePost(ProfitApplication.NO_BASE + "IViewFirstServlet", params, handler,Statcode.XSSUC,Statcode.XS_FAILED);
	}

	/**报表-盈利
	 * delearCode:经销商代码, brand:品牌, role:角色, startTime:起始日期, endTime:结束日期,
	 * type:精品/车贷/新保/延保/置换, isCarMode:是/否 Iview_serlet 请求链接后缀
	 */	
	public void getiviewProfitServlet(String startTime, String endTime, String type, String Iview_serlet,
			Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("brand", ProfitApplication.loginBackInfo.getBrand());
		params.addQueryStringParameter("role", ProfitApplication.loginBackInfo.getRole());
		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "N" : "Y");
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);
		params.addQueryStringParameter("type", type);
		netRequestUtils.executePost(ProfitApplication.NO_BASE + Iview_serlet, params, handler);
		
	}
	/**
	 * 登录
	 * @param user
	 * @param pass
	 * @param uid
	 * @param ver
	 * @param handler
	 */

	public void doLogin(String userName, String password, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json;charset=utf-8;");
//		params.addQueryStringParameter("user", user);
//		params.addQueryStringParameter("pass", pass);
//		params.addQueryStringParameter("uid", uid);
//		params.addQueryStringParameter("ver", ver);
//		netRequestUtils.executePost(ProfitApplication.NO_BASE+ "LoginILog", params, handler);

		params=getParam(params);


		Map<String,String>map=new HashMap<>();
		map.put("userName",userName);
		map.put("password",password);

		netRequestUtils.executePostForjson(ProfitApplication.LOGINBASE+ "login/appLogin", params,map, handler);
//		netRequestUtils.executePost(ProfitApplication.NO_BASE+ "LoginILog", params,handler);
	    
	}


	/**修改用户手机号
	 * @param
	 * @param newMobileNo
	 * @param
	 * @param
	 * @param
	 * @param handler
	 */
	public void upDatePhone(String newMobileNo,Handler handler) {

		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json;charset=utf-8;");
//		params.addQueryStringParameter("user", user);
//		params.addQueryStringParameter("pass", pass);
//		params.addQueryStringParameter("uid", uid);
//		params.addQueryStringParameter("ver", ver);
//		netRequestUtils.executePost(ProfitApplication.NO_BASE+ "LoginILog", params, handler);

		params=getParam(params);


		Map<String,String>map=new HashMap<>();
		map.put("userName",ProfitApplication.loginBackInfo.getUserName());
		map.put("newMobileNo",newMobileNo);
		map.put("mobileNo",ProfitApplication.loginBackInfo.getMobileNo());
		map.put("personName",ProfitApplication.loginBackInfo.getPersonName());
		map.put("newPersonName",ProfitApplication.loginBackInfo.getPersonName());

		netRequestUtils.executePostForjson(ProfitApplication.LOGINBASE+ "loginUser/updateMobile", params,map, handler);
//		netRequestUtils.executePost(ProfitApplication.NO_BASE+ "LoginILog", params,handler);

	}

	/**修改用户密码
	 * @param
	 * @param
	 * @param
	 * @param
	 * @param
	 * @param handler
	 */
	public void upDatePwd(String userOldPassWord,String userNewPassWord,Handler handler) {

		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json;charset=utf-8;");
//		params.addQueryStringParameter("user", user);
//		params.addQueryStringParameter("pass", pass);
//		params.addQueryStringParameter("uid", uid);
//		params.addQueryStringParameter("ver", ver);
//		netRequestUtils.executePost(ProfitApplication.NO_BASE+ "LoginILog", params, handler);

//		params=getParam(params);


		Map<String,String>map=new HashMap<>();
		map.put("userId",ProfitApplication.loginBackInfo.getId());
		map.put("userOldPassWord",userOldPassWord);
		map.put("userNewPassWord",userNewPassWord);

		netRequestUtils.executePostForjson(ProfitApplication.LOGINBASE+"loginUser/updatePassword",params,map, handler);
//		netRequestUtils.executePost(ProfitApplication.NO_BASE+ "LoginILog", params,handler);

	}
	
	
	/**监控---销售顾问*/
	public void getMonitorOperativeChannelServlet2(String startTime, String endTime, String car, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");

		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);

		netRequestUtils.executePost(ProfitApplication.NO_BASE + "GetIViewSCList", params, handler,MonitorOperativeChannelFragment.MONITOR_DATA_SUC,MonitorOperativeChannelFragment.MONITOR_DATA_FIN);
	}

	/**
	 * 监控-潜客量监控
	 * 
	 * @param delearCode:经销商代码
	 * @param brand:品牌
	 * @param role:角色
	 * @param isCarMode:是/否
	 * @param parameter:车型/销售顾问用户名/All
	 * @param startTime:起始日期
	 * @param endTime:结束日期
	 * @param date：2016-10
	 * @param week:true/false
	 * @param handler
	 */
	public void getMonitorOperativeChannelServlet(String parameter, String startTime, String endTime, String date,
			String week, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("brand", ProfitApplication.loginBackInfo.getBrand());
		params.addQueryStringParameter("role", ProfitApplication.loginBackInfo.getRole());
		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "N" : "Y");
		params.addQueryStringParameter("parameter", parameter);
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);
		params.addQueryStringParameter("date", date);
		params.addQueryStringParameter("week", week);
		netRequestUtils.executePost(ProfitApplication.NO_BASE + "OperationMonitorHServlet", params, handler);
	}

	/**
	 * 监控-KPI监控
	 * 
	 * @param delearCode:经销商代码
	 * @param brand:品牌
	 * @param role:角色
	 * @param isCarMode:是/否
	 * @param parameter:车型/销售顾问用户名/All
	 * @param startTime:起始日期
	 * @param endTime:结束日期
	 * @param date：2016-10
	 * @param week:true/false
	 * @param handler
	 */
	public void getMonitorOperativeProcessServlet(String parameter, String startTime, String endTime, String date,
			String week, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("brand", ProfitApplication.loginBackInfo.getBrand());
		params.addQueryStringParameter("role", ProfitApplication.loginBackInfo.getRole());
		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "N" : "Y");
		params.addQueryStringParameter("parameter", parameter);
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);
		params.addQueryStringParameter("date", date);
		params.addQueryStringParameter("week", week);
		netRequestUtils.executePost(ProfitApplication.NO_BASE + "IOperatorMonitorKpiServlet", params, handler);
	}
	
	

	/**监控---销售顾问*/
	public void getMonitorOperativeProcessServlet2(String startTime, String endTime, String car, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");

		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);

		netRequestUtils.executePost(ProfitApplication.NO_BASE + "GetIViewSCList", params, handler,MonitorOperativeProcessFragment.MONITOR_DATA_SUC,MonitorOperativeProcessFragment.MONITOR_DATA_FIN);
	}

	/**
	 * 监控-盈利监控
	 * 
	 * @param delearCode:经销商代码
	 * @param brand:品牌
	 * @param role:角色
	 * @param isCarMode:是/否
	 * @param parameter:车型/销售顾问用户名/All
	 * @param startTime:起始日期
	 * @param endTime:结束日期
	 * @param date：2016-10
	 * @param week:true/false
	 * @param handler
	 */
	public void getMonitorProfitChannelServlet(String parameter, String startTime, String endTime, String date,
			String week, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("brand", ProfitApplication.loginBackInfo.getBrand());
		params.addQueryStringParameter("role", ProfitApplication.loginBackInfo.getRole());
		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "N" : "Y");
		params.addQueryStringParameter("parameter", parameter);
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);
		params.addQueryStringParameter("date", date);
		params.addQueryStringParameter("week", week);
		netRequestUtils.executePost(ProfitApplication.NO_BASE + "MonitorProfitServlet", params, handler);
	}
	
	
	/**监控--盈利-销售顾问*/
	public void getMonitorProfitChannelServlet2(String startTime, String endTime, String car, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");

		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);

		netRequestUtils.executePost(ProfitApplication.NO_BASE + "GetIViewSCList", params, handler,MonitorProfitChannelFragment.MONITOR_DATA_SUC,MonitorProfitChannelFragment.MONITOR_DATA_FIN);
	}

	// date=2016-10&delearCode=ZSJ&isCarMode=否
	// ?
	// ?
	// ?
	/**
	 * 
	 * @param date
	 * @param handler
	 *            诊断页面的数据
	 */
	
	
	public void getDiagnosisData(String startime, String endtime, ArrayList<IViewSCBean> list, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());//经销商代码
		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "N" : "Y");//是否是车型模式
		params.addQueryStringParameter("startTime", startime);//开始时间
		params.addQueryStringParameter("endTime", endtime);//结束时间
		netRequestUtils.executePost(ProfitApplication.NO_BASE + "CheckPageServlet", params, list, handler);
		
	}
	
	/**
	 * 获取筛选车型数据
	 * @param startime
	 * @param endtime
	 * @param list
	 * @param handler
	 * 
	 * @param carType	新增参数，用于接收车型-------Q%_CKD,Q6_CKD
	 */
	public void getDiagnosisData2(String startime, String endtime, ArrayList<IViewSCBean> list, Handler handler,String carType) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "N" : "Y");
		params.addQueryStringParameter("startTime", startime);
		params.addQueryStringParameter("endTime", endtime);
		params.addQueryStringParameter("carType", carType);
		netRequestUtils.executePost(ProfitApplication.NO_BASE + "CheckPageServlet", params, list, handler);
	}

//	public void getDiagnosisData(String date, ArrayList<IViewSCBean> list, Handler handler) {
//		RequestParams params = new RequestParams("UTF-8");
//		params.addHeader("Content-Type", "application/json");
//		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
//		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "否" : "是");
//		params.addQueryStringParameter("date", date);
//		netRequestUtils.executePost(ProfitApplication.NO_BASE + "CheckPageServlet", params, list, handler);
//	}

	/**
	 * 
	 * @param parameter
	 * @param date
	 * @param handler
	 *            诊断详情页面的数据请求
	 */
	
	
	public void getDiagnosisProductData(String parameter, String startTime, String endTime, Handler handler,ArrayList<IViewSCBean> list) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "N" : "Y");
		params.addQueryStringParameter("brand", ProfitApplication.loginBackInfo.getBrand());
		params.addQueryStringParameter("role", ProfitApplication.loginBackInfo.getRole());
		params.addQueryStringParameter("parameter", parameter);
		netRequestUtils.executePost(ProfitApplication.NO_BASE + "CheckPageDetailServlet", params,list, handler);
	}
	
	
	public void getDiagnosisProductData2(String parameter, String startTime, String endTime, Handler handler,String carType,List<IViewSCBean> list) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "N" : "Y");
		params.addQueryStringParameter("brand", ProfitApplication.loginBackInfo.getBrand());
		params.addQueryStringParameter("role", ProfitApplication.loginBackInfo.getRole());
		params.addQueryStringParameter("parameter", parameter);
		params.addQueryStringParameter("carType", carType);
		netRequestUtils.executePost(ProfitApplication.NO_BASE + "CheckPageDetailServlet", params,list,handler);
	}
	
	
//	public void getDiagnosisProductData(String parameter, String startTime, String endTime, Handler handler) {
//		RequestParams params = new RequestParams("UTF-8");
//		params.addHeader("Content-Type", "application/json");
//		params.addQueryStringParameter("startTime", startTime);
//		params.addQueryStringParameter("endTime", endTime);
//		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
//		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "否" : "是");
//		params.addQueryStringParameter("brand", ProfitApplication.loginBackInfo.getBrand());
//		params.addQueryStringParameter("role", ProfitApplication.loginBackInfo.getRole());
//		params.addQueryStringParameter("parameter", parameter);
//		netRequestUtils.executePost(ProfitApplication.NO_BASE + "CheckPageDetailServlet", params, handler);
//	}


/**
 * 报表-运营
 * @param startime
 * @param endtime
 * @param type
 * @param handler
 */
	// delearCode:经销商代码,
	// brand:品牌,
	// role:角色,
	// startTime:起始日期,
	// endTime:结束日期,
	// type:总体/展厅/ADC,
	// isCarMode:是/否
	public void getIViewOperateReportServlet(String startime, String endtime, String type, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("startTime", startime);
		params.addQueryStringParameter("endTime", endtime);
		params.addQueryStringParameter("type", type);
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("brand", ProfitApplication.loginBackInfo.getBrand());
		params.addQueryStringParameter("role", ProfitApplication.loginBackInfo.getRole());
		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "N" : "Y");
		netRequestUtils.executePost(ProfitApplication.NO_BASE + "IViewOperateReportServlet", params, handler);
	    Log.e("时间", startime);
	}

	/**
	 * 
	 * @param liu
	 *            导入历史 delearCode 经销商代码 获取导入数据记录
	 */
	public void getIViewInputHistory(Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		netRequestUtils.executePost(ProfitApplication.NO_BASE+ "GetIViewInputHistory", params, handler);

	}

	/**
	 * "http://192.168.191.1:8080/ILogService/"
	 * @param liu
	 *            经销商筛选 delearCode 经销商代码
	 */
	public void getIViewFilterConsultan(Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		netRequestUtils.executePost(ProfitApplication.NO_BASE + "GetIViewSCList", params, handler);

	}
	
	
	public void getIViewFilterConsultan2(String startTime, String endTime, String parameter, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("startTime", startTime);
		params.addQueryStringParameter("endTime", endTime);
		netRequestUtils.executePost(ProfitApplication.NO_BASE + "GetIViewSCList", params, handler);

	}

	public void getUdtime(Handler handler) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("dealerCode", ProfitApplication.loginBackInfo.getLicense());
		netRequestUtils.executePost(ProfitApplication.UPDATETIME_URL , params, handler);
	}

	public void getreportFormLeft(String startDate,String endDate,Handler handler) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("dealerCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("startDate", startDate);
		params.addQueryStringParameter("endDate", endDate);
		netRequestUtils.executePost(ProfitApplication.REPORTFORMLEFT_URL , params, handler);
		
	}

	public void getreportFormRight(String startDate, String endDate, Handler handler) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("dealerCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("startDate", startDate);
		params.addQueryStringParameter("endDate", endDate);
		netRequestUtils.executePost(ProfitApplication.REPORTFORMRIGHT_URL , params, handler);
	}
	/**
	 * 获取概览页面更新时间和库存等数量
	 * 
	 * **/
	public void getViewUpdateTime(String endDate,String isCarModel,String carType, Handler handler) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("dealerCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("endDate", endDate);
		params.addQueryStringParameter("isCarModel", isCarModel);
		if(!TextUtils.isEmpty(carType)){
			params.addQueryStringParameter("carType", carType);
		}
		netRequestUtils.executePost(ProfitApplication.GETVIEWUPDATETIME_URL , params, handler,Statcode.SHSUC,Statcode.SHFAILED);
	    Log.e("路径", ProfitApplication.GETVIEWUPDATETIME_URL+"?dealerCode="+ProfitApplication.loginBackInfo.getLicense()+"&endDate="+endDate+"&isCarModel="+"isCarModel"+"&carType="+carType);
	}
	/**
	 * 获取概览页库存gp3
	 * 
	 * **/

	public void getViewRepertoryAndProfit(String endDate, String isCarModel, String carType, Handler handler) {
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("dealerCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("endDate", endDate);
		params.addQueryStringParameter("isCarModel", isCarModel);
		if(!TextUtils.isEmpty(carType)){
			params.addQueryStringParameter("carType", carType);
		}
		netRequestUtils.executePost(ProfitApplication.GETVIEWREPERTORYANDPROFIT_URL , params, handler,Statcode.XSSUC,Statcode.XS_FAILED);
		
	}

	public void getMesslist(String userName,Handler handler) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");
		params.addQueryStringParameter("dealerCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("userName", userName);
		netRequestUtils.executePost(ProfitApplication.MESSAGELIST_URL, params, handler);
	}
	
    /**获取售后诊断数据**/
	public void getShzdinfo(String startDate,String endDate,String type,String content,Handler handler) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");	
		params.addQueryStringParameter("isCarMode", ProfitApplication.isConsultantMode ? "N" : "Y");//车型传Y顾问穿否
		params.addQueryStringParameter("delearCode", ProfitApplication.loginBackInfo.getLicense());
		params.addQueryStringParameter("startTime",startDate);
		params.addQueryStringParameter("endTime", endDate);
		if(type.equals("gw")){
			if(!TextUtils.isEmpty(content)){
				params.addQueryStringParameter("customerName", content);
			}
			
		}else if (type.equals("cx")){
			if(!TextUtils.isEmpty(content)){
				
			params.addQueryStringParameter("carType", content);
			
			}
		}
		
		netRequestUtils.executePost(ProfitApplication.DIALIST_URL, params, handler);
		
		
	}

	
	/**售后筛选顾问**/
	public void getSalesUserList(Handler handler) {		
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");			
		params.addQueryStringParameter("dealerCode", ProfitApplication.loginBackInfo.getLicense());				
		netRequestUtils.executePost(ProfitApplication.SALESUSER_URL, params, handler);
	}

	/**售后筛选车型**/
	public void getSalesCarTypeList(Handler handler) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type", "application/json");			
		netRequestUtils.executePost(ProfitApplication.CARTYPELIST_URL, params, handler);
	}



	/**
	 * 获取请求头
	 *
	 * @param
	 * @return //
	 */
	public RequestParams getParam(RequestParams params) {

		if (!TextUtils.isEmpty(SpUtils.getCookie(ProfitApplication.getApplication()))) {
			params.addHeader("Cookie", SpUtils.getCookie(ProfitApplication.getApplication()));

		}
		return params;
	}



}
