package com.iris.cada.utils;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class NowTimeGetter {
	static String time;
	static SimpleDateFormat formatter;
	static Date curDate;

	public static String getday() {
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		curDate = new Date(System.currentTimeMillis());// 获取当前时间
		time = formatter.format(curDate);
		return time;
	}

	public static String getMon(String date) {
		formatter = new SimpleDateFormat("yyyy-MM");
		try {
			curDate = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		time = formatter.format(curDate);
		return time;
	}

	public static String getYea(String date) {
		formatter = new SimpleDateFormat("yyyy");
		try {
			curDate = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		} // 获取当前时间
		time = formatter.format(curDate);
		return time;
	}

	@SuppressWarnings("deprecation")
	public static String getQur(String date) {
		// 创建一个包含所有月份的list根据月份返回第一季度
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int x = 0;
		Date year = new Date();
		try {
			curDate = formatter.parse(date);
			// 获取当前时间
			x = curDate.getMonth() + 1;
			year = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (x < 4) {
			time = sdf.format(year) + "第一季度";
		} else if (x < 7) {
			time = sdf.format(year) + "第二季度";
		} else if (x < 10) {
			time = sdf.format(year) + "第三季度";
		} else {
			time = sdf.format(year) + "第四季度";
		}
		return time;
	}
}
