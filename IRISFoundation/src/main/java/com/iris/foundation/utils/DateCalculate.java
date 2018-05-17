package com.iris.foundation.utils;

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
public class DateCalculate {
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	/**
	 * 设置日期格式,默认为"yyyy-MM-dd"格式
	 * 
	 * @param sdf
	 */
	public static void setSdf(SimpleDateFormat sdf) {
			DateCalculate.sdf = sdf;
	}

	/**
	 * 计算前一天日期,返回前一天的日期字符
	 * 
	 * @param day
	 *            日期
	 * @return 前一天日期
	 * @throws ParseException
	 */
	public static String lastDay(String day) {

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
	 * 计算前一月日期,返回前一月日期字符
	 * 
	 * @param day
	 *            日期
	 * @return 前一月日期
	 * @throws ParseException
	 */
	public static String lastMonth(String day) {
		Calendar calendar = Calendar.getInstance();
		Date date;
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
	public static String lastYear(String day) {

		Calendar calendar = Calendar.getInstance();
		Date date;
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
	public static String lastQur(String day) {

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
	public static String nextDay(String day) {

		Date date;
		Calendar calendar = Calendar.getInstance();
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}

	/**
	 * 下一月
	 * 
	 * @param day
	 * @return 下一月日期
	 * @throws ParseException
	 */
	public static String nextMonth(String day) {

		Date date;
		Calendar calendar = Calendar.getInstance();
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
	public static String nextYear(String day) {
		Calendar calendar = Calendar.getInstance();
		Date date;
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
	public static String nextQur(String day) {

		Date date;
		Calendar calendar = Calendar.getInstance();
		try {
			date = sdf.parse(day);
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 3);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());
	}

	/**
	 * 判断某日是否为当月
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static Boolean orCurrentMonth(String date) {
		Boolean or = false;
		if (NowTimeGetter.getMon(date) == NowTimeGetter.getMon(NowTimeGetter.getday())) {
			or = true;
		} else {
			or = false;
		}
		return or;
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
	public static Date String2Date(String s) {
		try {
			Date date = sdf.parse(s);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
	public static String Date2String(Date date) {
		String s = sdf.format(date);
		return s;
	}
}
