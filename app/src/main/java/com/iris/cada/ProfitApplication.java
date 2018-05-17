package com.iris.cada;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.iris.cada.entity.LoginBackInfo;
import com.iris.cada.entity.SalesInfo;
import com.iris.cada.entity.UserInfoBean;
import com.iris.cada.entity.UserSelectEntity;
import com.iris.cada.net.ProfitNetService;
import com.iris.cada.utils.CKNumFormat;
import com.iris.cada.utils.SharedPreferencesUtils;
import com.iris.cada.view.CProgressDialog;
import com.iris.cada.view.ckbar.CKBarViewHelper;
import com.iris.foundation.activity.IRISApplication;
//import com.zhy.http.okhttp.OkHttpUtils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
//import okhttp3.OkHttpClient;

/**
 * 
 * @author jiahaoGuo
 * 
 */
@SuppressLint("HandlerLeak")
public class ProfitApplication extends IRISApplication {

    public static Dialog progressDialog;
	public static SharedPreferencesUtils service;
	private static ProfitApplication application;
	public static SalesInfo salesInfo;
	public static UserSelectEntity userSelectEntity; // 存储用户选择的信息
	public static int carModelSize;
	public static int carDistributor;
	
	public static ProfitNetService profitNetService; 
	
	public static List<String> timePeriodList ; //时间段
	
	public static LoginBackInfo loginBackInfo = null;

	public static boolean isConsultantMode=true;//进来默认变成顾问模式
	
	public static final String BROADCAST_MESSAGE = "com.iris.cada.viewrefresh";
	public static final String TIME_REFRESH_MESSAGE = "com.iris.iproft.timerefresh";
	
	public static Date mStartDate = null;
	public static Date mEndDate = null;

	public void onCreate() {
		super.onCreate();
		progressDialog = CProgressDialog.createLoadingDialog(this);
		service = new SharedPreferencesUtils(this);
		application = this;
//		JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);

//		initHttpUtils();
		
		if (profitNetService == null) {
			profitNetService = new ProfitNetService();
		}
		
		timePeriodList = Arrays.asList(new String[]{"M", "W1", "W2", "W3", "W4" , "W5"});
	}

	public static synchronized ProfitApplication getApplication() {
		return application;
	}

	/**
	 * 初始化
	 */
//	private void initHttpUtils() {
//		try {
//			OkHttpClient okHttpClient = new OkHttpClient.Builder()
//					.connectTimeout(10000L, TimeUnit.MILLISECONDS)
//					.readTimeout(100000L, TimeUnit.MILLISECONDS)
//					.build();
//			OkHttpUtils.initClient(okHttpClient);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * 是否是车型模式
	 * 
	 * @return 是或否
	 */
	public static Boolean orCarModel() {
		if (userSelectEntity.getCarModel().equals("是"))
			return true;
		return false;
	}

	/**
	 * 是否是集团模式
	 * 
	 * @return
	 */
	public static Boolean isGroup() {
		if (salesInfo.getRole().equals("集团"))
			return true;
		return false;
	}

	public static void setListViewTitle(TextView tv) {
		if (isGroup()) {
			// 集团
			if (orCarModel()) {
				// 车型
				tv.setText("车型");
			} else {
				// 经销商
				tv.setText("经销商");
			}
		} else {
			// 经销商
			if (orCarModel()) {
				// 车型
				tv.setText("车型");
			} else {
				// 销售顾问
				tv.setText("顾问");
			}
		}
	}

	public static String getModelString() {
		String result = "车型";
		if (isGroup()) {
			// 集团
			if (orCarModel()) {
				// 车型
				result = "车型";
			} else {
				// 经销商
				result = "经销商";
			}
		} else {
			// 经销商
			if (orCarModel()) {
				// 车型
				result = "车型";
			} else {
				// 销售顾问
				result = "销售顾问";
			}
		}
		return result;
	}

	/**
	 * 计算除数和被除数的百分比
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static String int2percent(int first, int second) {
		if (second == 0) {
			return 0.00 + "%";
		}
		double temp = first * 100f / second;
		BigDecimal bd = new BigDecimal(temp);
		String result = "" + bd.setScale(1, BigDecimal.ROUND_HALF_UP);
		result = result + "%";
		return result;
	}

	/**
	 * 如果字符串为null或者空将值设为0
	 * 
	 * @param str
	 * @return
	 */
	public static int stringIsNull(String str) {
		int temp = 0;
		if (str != null && !str.isEmpty()) {
			if (!str.equals("N/A")) {
				temp = Integer.parseInt(str);
			}
		}
		return temp;
	}

	public static String stringIsEmpty(String str) {
		String temp = str;
		if (temp == null || temp.isEmpty() || temp.equals("N/A")) {
			temp = "N/A";
		}
		return temp;
	}

	/**
	 * 首页的第一、二页
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static String unitConversion(String num1, String num2) {
		int res = 0;
		if (num2 != null && !num2.isEmpty() && !num2.equals("N/A")) {
			res = (stringIsNull(num1) - stringIsNull(num2));
			return res + "";
		} else {
			return "N/A";
		}
	}

	/**
	 * 首页的第三、四页
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static String conversion(String num1, String num2) {
		if (!num2.equals("N/A")) {
			double temp = (stringIsFloat(num1) - stringIsFloat(num2)) * 1f / 1000;
			BigDecimal bd = new BigDecimal(temp);
			String result = "" + bd.setScale(1, BigDecimal.ROUND_HALF_UP);
			return stringIsFloat(result) + "";
		}
		return "N/A";
	}

	/**
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static String conversionScale(String num1, String num2) {
		if (!num2.equals("N/A")) {
			double temp = (stringIsFloat(num1) - stringIsFloat(num2)) * 1f;
			return temp + "";
		}
		return "N/A";
	}

	/**
	 * 讲float类型转换为String并只保留一位有效数
	 * 
	 * @param f
	 * @return
	 */
	public static String conStr(double f) {
		BigDecimal bd = new BigDecimal(f);
		String result = "" + bd.setScale(1, BigDecimal.ROUND_HALF_UP);
		return result;
	}

	public static String conversStr(String num1, String num2) {
		Float res = 0 * 1f;
		String res1 = "";
		String res2 = "";
		if (num1.contains("%") && num2.contains("%")) {
			res1 = num1.replaceAll("%", "");
			Float f1 = Float.valueOf(res1);
			res2 = num2.replaceAll("%", "");
			Float f2 = Float.valueOf(res2);
			res = f1 - f2;
			return conStr(res) + "%";
		}
		return "N/A";
	}

	public static int conversStr(String num) {
		int res = 0;
		String res1 = "";
		if (!TextUtils.isEmpty(num) && !num.equals("N/A")) {
			res1 = num.replaceAll("%", "");
			float f = Float.valueOf(res1);
			return (int) f;
		}
		return res;
	}

	public static float convers(String num) {
		float res = 0;
		if (!TextUtils.isEmpty(num) && !num.equals("N/A")) {
			float f = Float.valueOf(num);
			return f;
		}
		return res;
	}

	/**
	 * 如果字符串为null或者空将值设为0
	 * 
	 * @param str
	 * @return
	 */
	public static double stringIsFloat(String str) {
		double temp = 0.0;
		if (str != null && !str.isEmpty()) {
			if (!str.equals("-")) {
				temp = Double.parseDouble(str);
			}
		}
		return temp;
	}

	public static double unitConversion(String num) {
		double temp = stringIsFloat(num) * 1f / 1000;
		BigDecimal bd = new BigDecimal(temp);
		String result = "" + bd.setScale(1, BigDecimal.ROUND_HALF_UP);
		return stringIsFloat(result);
	}
	//http://192.168.1.220:8080/ILogService/  i_view 借口地址
	//http://120.26.66.145:7522/cadaService/
	//http://192.168.191.1:8080/ILogService/ ck
	//http://120.26.66.145:7522/IViewService/
	//http://192.168.91.254:8080/ IViewService/黄永平的
//	public static final String NO_BASE = "http://192.168.1.254:8080/IView/";
	//测试服务
//	public static final String NO_BASE = "http://118.89.138.26/IViewService/";//黄永平
//	public static final String NO_BASE = "http://192.168.1.123:8080/IViewService/";//刘伟品
//	public static final String NO_BASE = "http://192.168.1.254:8080/IViewService/"; 
	// 常量  IViewService_test
//	public static final String NO_BASE ="http://120.26.66.145:8666/IViewService/";//测试
//	public static final String NO_BASE ="192.168.1.254:8080/IViewService/";//黄永平
//	public static final String NO_BASE ="http://120.26.66.145:8666/iviewtest/";//开发测试
//	public static final String NO_BASE ="http://192.168.1.159:8080/hyp/";//中奇测试
//	public static final String NO_BASE ="http://192.168.1.67:8088/";//陈玉林测试
//	public static final String NO_BASE ="http://192.168.1.74:8080/hyp/";//顾测试
//	public static final String NO_BASE = "http://120.26.66.145:7522/IView/";//正式的
//	public static final String NO_BASE = "http://192.168.2.105:8080/cadaD20/";//修改后的登录


	/*正式的*/
//	public static final String H5ADDER ="http://192.168.2.105:8080/cadaD20/audi.html";//H5正式地址
//	public static final String LOGINBASE = "http://192.168.2.105:8080/cadaD20/";//修改后的登录
//	public static final String NO_BASE = "http://192.168.2.105:8080/cadaD20/cadaD20Sales/";//除掉登录的接口


	public static final String H5ADDER ="http://121.43.34.185:7522/cadaD20/audi.html";
	public static final String LOGINBASE = "http://121.43.34.185:7522/cadaD20/";
	public static final String NO_BASE = "http://121.43.34.185:7522/cadaD20/cadaD20Sales/";

	/*朱莉*/
//	public static final String LOGINBASE = "http://192.168.2.102:8080/cadaD20/";
//	public static final String NO_BASE = "http://192.168.2.102:8080/cadaD20/cadaD20Sales/";
//	public static final String H5ADDER ="http://192.168.2.102:8080/cadaD20/audi.html";

//	public static final String H5ADDER ="http://192.168.1.159:8080/iviewh5/#/afterSale/";//中奇
//	public static final String BAIDUH5 ="http://baidu.com";//H5正式地址
	 
	/** 登录 */
	public static final String DO_LOGIN = LOGINBASE + "LoginILog";
	//i_view  首页的接口
	public static final String IV_HOME = NO_BASE + "IViewFirstServlet";
	/** 首页报表-日 */
	public static final String REPORT_DAY = NO_BASE + "GetILogReportServlet";

	/** 首页报表-月 */
	public static final String REPORT_MON = NO_BASE + "GetILogReportMonServlet";

	/** 首页报表目标值-月 */
	public static final String REPORT_TARGET = NO_BASE + "GetILogReportMonTAServlet";
	// http://120.26.66.145:7522/cadaService/GetILogReportMonTAServlet?date=2015-12-28&role=集团&brand=奥迪&mode=否
	/** 利润报表-日 */
	public static final String PROFIT_DAY = NO_BASE + "GetILogProfitReportServlet";

	/** 利润报表-月 */
	public static final String PROFIT_MON = NO_BASE + "GetILogProfitReportMonServlet";

	/** 利润报表目标-月 */
	public static final String PROFIT_TARGET = NO_BASE + "GetILogProfitReportMonTAServlet";

	/** 衍生业务 */
	public static final String DERIVED = NO_BASE + "GetILogBusinessProfitServlet";

	/** 部门 */
	public static final String DEPT = NO_BASE + "GetILogDepartmentProfitServlet";

	/** 我的信息 */
	public static final String MY_INFO = NO_BASE + "ModifyILogTel";

	/** 修改密码 */
	public static final String UPDATE_PWD = NO_BASE + "ModifyILogPass";

	/** 首页(集团并为销售顾问模式) */
	public static final String REPORT_SECOND = NO_BASE + "GetILogJXSLicense";

	/** 获取单店的licence */
	public static final String GET_LICENCE = NO_BASE + "GetILogJXSLicense";

	/** 获取新车销售信息-日 */
	public static final String NEW_CAR_DAY = NO_BASE + "GetILogSingleCarReportServlet";

	/** 获取新车销售信息-月 */
	public static final String NEW_CAR_Mon = NO_BASE + "GetILogSingleCarReportMonServlet";

	/** 获取概览运营h5地址 */
	public static final String H5_GL_YY = H5ADDER + "overview/user";
	
	/** 获取概览盈利h5地址 */
	public static final String H5_GL_YL = H5ADDER + "overview/profit";
	
	/** 报表用户 */
	public static final String H5_BB_YH = H5ADDER +"reportForm/user";
	
	/** 报表主营业务 */
	public static final String H5_BB_ZY = H5ADDER + "reportForm/profit";
	
	/** 报表衍生业务 */
	public static final String H5_BB_YS = H5ADDER + "reportForm/profitBusiness";
	
	/** 监控运营 */
	public static final String H5_JK_YY = H5ADDER + "watchForm/operate";
	
	/** 监控盈利 */
	public static final String H5_JK_YL = H5ADDER + "watchForm/watchProfit";

	/** 诊断 */
	public static final String H5_ZD =H5ADDER+"diagnostics/diagnostics";
	
	/** 销售概览库存地址 */
	public static final String H5_XSGLKC = H5ADDER;
	
	
	/** 售后诊断详情 */
	public static final String H5_INFO = H5ADDER + "diagnostics/diagnosticsDetail";
	
	/** 首页数据 */
	public static final String SHSY_DATE =NO_BASE+"customerService/home";
	
	
	/** 售后模块导入记录 */
	public static final String REPORT_HISTORY =NO_BASE+"import/history";
	
	/** 报表数据更新时间 */
	public static final String UPDATETIME_URL =NO_BASE+"stock/reportFormUpdateTime";
	
	/** 报表数据左边内容 */
	public static final String REPORTFORMLEFT_URL =NO_BASE+"stock/reportFormLeft";
	
	/** 报表数据右边内容 */
	public static final String REPORTFORMRIGHT_URL =NO_BASE+"stock/reportFormRigth";
	
	/** 概览报表一些更新时间 */
	public static final String GETVIEWUPDATETIME_URL =NO_BASE+"getViewUpdateTime";
	
	/** 概览报表一些更新时间 */
	public static final String GETVIEWREPERTORYANDPROFIT_URL =NO_BASE+"getViewRepertoryAndProfit";
	
	/**消息列表**/
	public static final String MESSAGELIST_URL =NO_BASE+"/app/messageList";
	
	/**消息列表**/
//	public static final String MESSAGELIST_URL ="http://192.168.1.67:8088///app/messageList";
	
	/**售后诊断列表**/
	public static final String DIALIST_URL =NO_BASE+"/diagnosis/list";
	
	/**售后诊断顾问列表**/
	public static final String SALESUSER_URL =NO_BASE+"getSalesUserList";
	
	/**售后诊断车型列表**/
	public static final String CARTYPELIST_URL =NO_BASE+"getSalesCarTypeList";
	// ----------------------some helper---------------------
	/**
	 * 根据数据获取x轴的值
	 * 
	 * @param dataSource
	 * @param ISPERCENT
	 * @return
	 */
	public static ArrayList<String> getAxisData(ArrayList<String> dataSource, Boolean ISPERCENT) {
		ArrayList<String> result = new ArrayList<String>();
		float min = 0, max = -1000, count = 9;
		String add = "";// 在最后要返回值时需要添加的后缀
		if (ISPERCENT) {
			add = "%";
			max=100;
		}
		for (String str : dataSource) {
			float temp = CKBarViewHelper.percent2float(str);
			max = (temp > max) ? temp : max;
			min = (temp < min) ? temp : min;
		}
		if (min >= 0) {// 如果最小值大于0,那么最小值等于0
			min = 0;
		} else {// 找出最大值和最小值中绝对值最大的
			if (Math.abs(max) >= Math.abs(min)) {
				min = -max;
			} else {
				max = -min;
			}
		}
		float step = (max - min) / count;
		result.add(CKNumFormat.decimalRound(min) + add);
		if (max != min) {
			for (int i = 0; i < count; i++) {
				if ((i == count - 1) && (max > (i * step + step))) {
					result.add(CKNumFormat.decimalRound(max) + add);
				} else {
					result.add(CKNumFormat.decimalRound(i * step + step + min) + add);
				}
			}
		}
		return result;
	}

	public static float getTitleTextSize(Context context) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8, context.getResources().getDisplayMetrics());
	}
	
	
	/*TRIM_MEMORY_COMPLETE：内存不足，并且该进程在后台进程列表最后一个，马上就要被清理
	TRIM_MEMORY_MODERATE：内存不足，并且该进程在后台进程列表的中部。
	TRIM_MEMORY_BACKGROUND：内存不足，并且该进程是后台进程。
	TRIM_MEMORY_UI_HIDDEN：内存不足，并且该进程的UI已经不可见了。*/
	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
		super.onTrimMemory(level);
		switch(level)

		{
		case TRIM_MEMORY_MODERATE: 	
			//杀死程序防止内存被回收程序没被杀死出现空白卡死现象
			AppManager.getAppManager().AppExit(this);
			break;
		}

		
	}

	
}
