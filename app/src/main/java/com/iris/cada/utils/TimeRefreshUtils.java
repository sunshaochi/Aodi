package com.iris.cada.utils;

import java.util.Date;

import com.iris.cada.ProfitApplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class TimeRefreshUtils {

	// 保存“当前时间”到“公共时间”
	public static void savePublicDate(Context context , Date currentDate) {
		// 保存时间
		Date mStartDate = DateAndTimeUtils.getDateTimeForStr("yyyy-MM-dd",
				DateAndTimeUtils.getFisrtDayOfMonth(currentDate));
		Date mEndDate = DateAndTimeUtils.getDateTimeForStr("yyyy-MM-dd",
				DateAndTimeUtils.getLastDateOfMonth(currentDate));

		sendTimeRefreshMsg(context , mStartDate, mEndDate);
	}

	public static void sendTimeRefreshMsg(Context context, Date startDate, Date endDate) {
		ProfitApplication.mStartDate = startDate;
		ProfitApplication.mEndDate = endDate;
		Intent intent = new Intent(ProfitApplication.TIME_REFRESH_MESSAGE);
		LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
	}

}
