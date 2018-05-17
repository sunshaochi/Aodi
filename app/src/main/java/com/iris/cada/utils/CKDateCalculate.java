package com.iris.cada.utils;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期计算helper类
 * 
 * @author CK
 * 
 */
public class CKDateCalculate {
	@SuppressLint("SimpleDateFormat")
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	/**
	 * 设置日期格式,默认为"yyyy-MM-dd"格式
	 * 
	 * @param sdf
	 */
	@SuppressLint("SimpleDateFormat")
	public static void setSdf(SimpleDateFormat sdf) {
		if (sdf == null) {
			setSdf(new SimpleDateFormat("yyyy-MM-dd"));
		} else {
			CKDateCalculate.sdf = sdf;
		}
	}

	/**
	 * 查询某个日期之前或之后多少天
	 * 
	 * @param day
	 * @param range
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String DayRange(String day, int range) {

		setSdf(new SimpleDateFormat("yyyy-MM-dd"));
		Calendar calendar = Calendar.getInstance();
		Date date;
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
			calendar.add(Calendar.DATE, range);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}

	/**
	 * 计算前一天日期,返回前一天的日期字符
	 * 
	 * @param day
	 *            日期
	 * @return 前一天日期
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static String lastDay(String day) {

		setSdf(new SimpleDateFormat("yyyy-MM-dd"));
		Calendar calendar = Calendar.getInstance();
		Date date;
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
			calendar.add(Calendar.DATE, -1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}

	/**
	 * 计算前一周日期,返回前一周的日期字符
	 * 
	 * @param day
	 *            日期
	 * @return 前一周日期
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static String lastWeek(String day) {

		setSdf(new SimpleDateFormat("yyyy-MM-dd"));
		Calendar calendar = Calendar.getInstance();
		Date date;
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
			calendar.add(Calendar.DATE, -7);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}

	/**
	 * 计算前一月日期,返回前一月日期字符
	 * 
	 * @param day
	 *            日期
	 * @return 前一月日期
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static String lastMonth(String day) {
		setSdf(new SimpleDateFormat("yyyy-MM-dd"));
		Calendar calendar = Calendar.getInstance();
		Date date;
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.MONTH, -1);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 计算前一年日期,返回前一年日期字符
	 * 
	 * @param day
	 *            日期
	 * @return 前一年日期
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static String lastYear(String day) {
		setSdf(new SimpleDateFormat("yyyy-MM-dd"));
		Calendar calendar = Calendar.getInstance();
		Date date;
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.YEAR, -1);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 计算前一季度日期,返回前一月季度字符
	 * 
	 * @param day
	 *            日期
	 * @return 前一季度字符
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static String lastQur(String day) {
		setSdf(new SimpleDateFormat("yyyy-MM-dd"));
		Calendar calendar = Calendar.getInstance();
		Date date;
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, -3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}

	/**
	 * 下一天
	 * 
	 * @param day
	 *            日期
	 * @return 下一天日期
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static String nextDay(String day) {
		setSdf(new SimpleDateFormat("yyyy-MM-dd"));
		Date date;
		Calendar calendar = Calendar.getInstance();
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}

	/**
	 * 下一周
	 * 
	 * @param day
	 *            日期
	 * @return 下一周日期
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static String nextWeek(String day) {
		setSdf(new SimpleDateFormat("yyyy-MM-dd"));
		Date date;
		Calendar calendar = Calendar.getInstance();
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 7);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}

	/**
	 * 计算前一月日期,返回前一月日期字符
	 * 
	 * @param day
	 *            日期
	 * @return 前一月日期
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static String monthRange(String day, int range) {
		setSdf(new SimpleDateFormat("yyyy-MM-dd"));
		Calendar calendar = Calendar.getInstance();
		Date date;
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.MONTH, range);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 下一月
	 * 
	 * @param day
	 * @return 下一月日期
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static String nextMonth(String day) {
		setSdf(new SimpleDateFormat("yyyy-MM-dd"));
		Date date;
		Calendar calendar = Calendar.getInstance();
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.MONTH, 1);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 下一年
	 * 
	 * @param day
	 * @return 下一年日期
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static String nextYear(String day) {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date date;
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.YEAR, 1);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 下一季
	 * 
	 * @param day
	 * @return 下一季日期
	 * @throws ParseException
	 */
	@SuppressLint("SimpleDateFormat")
	public static String nextQur(String day) {
		setSdf(new SimpleDateFormat("yyyy-MM-dd"));
		Date date;
		Calendar calendar = Calendar.getInstance();
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}

	/**
	 * 判断某月有多少天
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String getNumDay(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(String2Date(date));
		int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
		return dateOfMonth + "";
	}

	/**
	 * 日期字符串转换为date类型
	 * 
	 * @param s
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date String2Date(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = sdf.parse(s);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期字符串转换为date类型
	 * 
	 * @param s
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String Date2String(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s = sdf.format(date);
		return s;
	}

	/**
	 * 格式化字符串为日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatString(String date) {
		return Date2String(String2Date(date));
	}

	/**
	 * 两个时间比较
	 * 
	 * @param s1
	 *            第一个时间
	 * @param s2
	 *            第二个时间
	 * @return 0 :s1=s2 1 :s1>s2 -1:s1<s2
	 * @throws ParseException
	 */
	public static int compareTime(String s1, String s2) {
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Calendar c1 = java.util.Calendar.getInstance();
		java.util.Calendar c2 = java.util.Calendar.getInstance();
		try {
			c1.setTime(df.parse(s1));
			c2.setTime(df.parse(s2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c1.compareTo(c2);
	}
}
