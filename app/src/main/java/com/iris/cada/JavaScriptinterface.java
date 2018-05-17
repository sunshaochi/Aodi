package com.iris.cada;

import java.util.Date;

import com.iris.cada.activity.ShmainAct;
import com.iris.cada.utils.DateAndTimeUtils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JavaScriptinterface {
	 Context context;
	 String mTimeFormat = "yyyy-MM-dd";
	
	    public JavaScriptinterface(Context c) {
	        context= c;
	      
	    }

	    /**
	     * 与js交互时用到的方法，在js里直接调用的
	     */
	    @JavascriptInterface
	    public void showToast(String starttime,String endtime) {
	    	Log.e("开始时间", starttime);
	    	Log.e("结束时间", endtime);
//	        Toast.makeText(context, starttime, Toast.LENGTH_LONG).show();
	        if(TextUtils.isEmpty(endtime)){//只是选择的时间点
	        	Date sdate=DateAndTimeUtils.getDateTimeForStr(mTimeFormat, starttime);
	        	ProfitApplication.mStartDate=sdate;//开始时间锉
	        	String etime=DateAndTimeUtils.getLastDateOfMonth(sdate);//结束时间
	        	Date edate=DateAndTimeUtils.getDateTimeForStr(mTimeFormat, etime);//结束时间戳
	        	
	        	if(DateAndTimeUtils.isBefore(DateAndTimeUtils.getCurrentDate(mTimeFormat), edate)){
	        		ProfitApplication.mEndDate=DateAndTimeUtils.getCurrentDate(mTimeFormat);
	        	}else {//结束时间小于或者等于当前时间
	        		ProfitApplication.mEndDate=edate;
	        	}
	        	
	        }else {//选择的时间段
	        	Date sdate=DateAndTimeUtils.getDateTimeForStr(mTimeFormat, starttime);
	        	ProfitApplication.mStartDate=sdate;
	        	Date edate=DateAndTimeUtils.getDateTimeForStr(mTimeFormat, endtime);
	        	ProfitApplication.mEndDate=edate;
	        }
//	        context.sendBroadcast(new Intent(ShmainAct.WEBTIME));
	    }
	    
	    
}
