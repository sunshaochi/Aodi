package com.iris.cada.activity;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iris.cada.AppManager;
import com.iris.cada.ProfitApplication;
import com.iris.cada.Statcode;
import com.iris.cada.activity.HelpDocumentActivity.onclick;
import com.iris.cada.entity.IViewFirst;
import com.iris.cada.entity.ShsyBean;
import com.iris.cada.newfragment.MenuFragment;
import com.iris.cada.utils.DateAndTimeUtils;
import com.iris.cada.utils.StringUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.utils.WebUtils;
import com.iris.cada.view.CProgressDialog;
import com.iris.cada.view.KpiDialog;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.cada.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import slidingmenu.lib.SlidingMenu;
import slidingmenu.lib.app.SlidingFragmentActivity;

public class SeparateAct extends NewBaseAct {
	
	@ViewInject(R.id.ll_xs)
	private LinearLayout ll_xs;
	@ViewInject(R.id.ll_sh)
	private LinearLayout ll_sh;
	@ViewInject(R.id.iv_setting)
	private ImageView iv_setting;
	@ViewInject(R.id.tv_name)
	private TextView tv_name;
	@ViewInject(R.id.tv_start)
	private TextView tv_start;
	@ViewInject(R.id.tv_end)
	private TextView tv_end;
	@ViewInject(R.id.ll_kpi)
	private LinearLayout ll_kpi;
	@ViewInject(R.id.ll_kpish)
	private LinearLayout ll_kpish;
	@ViewInject(R.id.tv_wxtc)
	private TextView tv_wxtc;
	@ViewInject(R.id.tv_dccz)
	private TextView tv_dccz;
	@ViewInject(R.id.tv_zhsr)
	private TextView tv_zhsr;
	@ViewInject(R.id.tv_zhml)
	private TextView tv_zhml;
	
	@ViewInject(R.id.tv_runtime)
	private TextView tv_runtime;
	@ViewInject(R.id.tv_runtime1)
	private TextView tv_runtime1;
	@ViewInject(R.id.tv_runtime2)
	private TextView tv_runtime2;
	@ViewInject(R.id.tv_runtime3)
	private TextView tv_runtime3;
	
	@ViewInject(R.id.tv_cjs)
	private TextView tv_cjs;
	@ViewInject(R.id.tv_zjcs)
	private TextView tv_zjcs;
	@ViewInject(R.id.tv_xszhsr)
	private TextView tv_xszhsr;
	@ViewInject(R.id.tv_xszhml)
	private TextView tv_xszhml;
	@ViewInject(R.id.tv_num)
	private TextView tv_num;
	
	@ViewInject(R.id.tv_time)
	private TextView tv_time;
	@ViewInject(R.id.tv_time1)
	private TextView tv_time1;
	@ViewInject(R.id.tv_time2)
	private TextView tv_time2;
	@ViewInject(R.id.tv_time3)
	private TextView tv_time3;
	
	
	
	private AddTodoPickerViewUtils addPickerViewUtils;
	private String mTimeFormat;
	private Date startdate,enddate;//开始时间Date格式，结束时间Date格式
	private String stime,etime;//开始时间，结束时间
	
	private long touchTime = 0;
	
	private String premission;//权限

	
	private Gson gson;
	private Dialog dialog;
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {			
			super.handleMessage(msg);
			
			switch (msg.what) {
			case Statcode.SHSUC://获取售后数据成功

//				initShdate(msg);
				break;
			case Statcode.XSSUC://获取销售数据成功

				initSxdate(msg);
				break;	
				
			case Statcode.SHFAILED://获取售后数据失败
				ToastUtils.showMyToast(SeparateAct.this, getString(R.string.error_data));
				break;	
				
			case Statcode.XS_FAILED://获取销售数据失败
				ToastUtils.showMyToast(SeparateAct.this, getString(R.string.error_data));
				break;		

			case ProfitApplication.SERVER_FAILED://连接服务器失败
				ToastUtils.showMyToast(SeparateAct.this, getString(R.string.error_net));
				break;
			
			}
		}

	
		
	};

	@Override
	public void setLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.act_separate);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AppManager.getAppManager().addActivity(this);
		Statcode.type=0;//用在设置界面点击车型或者模式时候做不同超做
		Statcode.inactivity=1;
		gson=new Gson();
		premission = ProfitApplication.loginBackInfo.getPermission();
			if(TextUtils.isEmpty(premission)){
					premission="总经理";
			}
		if(!TextUtils.isEmpty(ProfitApplication.loginBackInfo.getDealerName())){
			tv_name.setText(ProfitApplication.loginBackInfo.getDealerName());
		}

		mTimeFormat = "yyyy-MM-dd";
		addPickerViewUtils = new AddTodoPickerViewUtils(SeparateAct.this);//工具内时间弹出框
		tv_start.setText(WebUtils.getStart());//开始时间
		tv_end.setText(WebUtils.getend());//结束时间
		startdate=DateAndTimeUtils.getDateTimeForStr(mTimeFormat, tv_start.getText().toString());//赋值后的开始时间锉

		if(Statcode.Messnum==0){
			tv_num.setVisibility(View.GONE);
		}else {
			tv_num.setVisibility(View.VISIBLE);
			tv_num.setText(Statcode.Messnum+"");
		}


//		getShinfo(tv_start.getText().toString(),tv_end.getText().toString());//售后数据请求
		getProfitInfo(tv_start.getText().toString(),tv_end.getText().toString());//销售数据请求
		
	}
	
	/*
	 * 获取售后数据
	 * **/
	private void getShinfo(String startDate, String endDate) {
		// TODO Auto-generated method stub
		        ProfitApplication.profitNetService.getShsydate(startDate,
				endDate,handler);
	}
	
	/*
	 * 获取销售数据
	 * **/
	private void getProfitInfo(String startDate, String endDate) {	
		ProfitApplication.profitNetService.getiviewFirstHomeServlet(startDate,
				endDate, handler);
	}
	
	
	private void initSxdate(Message msg) {
		// TODO Auto-generated method stub
		String mes=(String) msg.obj;
		try {
			String strMsg = (String) msg.obj;
			JSONObject object = new JSONObject(mes);
			IViewFirst iViewFirstdata = new Gson().fromJson(object.toString(), IViewFirst.class);
			
            if("售后半权限".equals(premission)){
            	tv_cjs.setText("-");
    			tv_zjcs.setText("-");
    			tv_xszhsr.setText("-");
				tv_xszhml.setText("-");
            }else {
				if ("仅运营权限".equals(premission) || "销售半权限".equals(premission)) {
				tv_xszhsr.setText("-");
				tv_xszhml.setText("-");
			} else {
				tv_xszhsr.setText(StringUtils.intDivTo0(iViewFirstdata.getGross()));// 销售综合收入
				tv_xszhml.setText(StringUtils.intDivTo0(iViewFirstdata.getRevenue()));// 销售综合毛利
			}

				tv_cjs.setText(iViewFirstdata.getTurnover());
				tv_zjcs.setText(iViewFirstdata.getRetail());
            }
			setShowview(tv_time,iViewFirstdata.getTimeTurnover());
			setShowview(tv_time1,iViewFirstdata.getTimeRetail());
			setShowview(tv_time2,iViewFirstdata.getTimeGross());
			setShowview(tv_time3,iViewFirstdata.getTimeRevenue());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
    
	//设置字体显示内容
	private void setShowview(TextView tv, String timeTurnover) {
		// TODO Auto-generated method stub
		if(!TextUtils.isEmpty(timeTurnover)){
			tv.setText("更新于" + timeTurnover);// 成交数更新时间
		}else {
			tv.setText("更新于" + "--");// 成交数更新时间
		}
	}

	
//	private void initShdate(Message msg) {
//		// TODO Auto-generated method stub
//		try {
//			String mes=(String) msg.obj;
//			JSONObject object=new JSONObject(mes).getJSONObject("data");
////			String data=object.getString("data");
//			ShsyBean bean=gson.fromJson(object.toString(), ShsyBean.class);
//			Log.e("hah", bean.toString());
//			if(bean!=null){
//				if("销售半权限".equals(premission)||"仅运营权限".equals(premission)){
//					tv_wxtc.setText("-");
//					tv_dccz.setText("-");
//					tv_zhsr.setText("-");
//					tv_zhml.setText("-");
//	            }else {
//	    			if ("售后半权限".equals(premission)) {
//	    				tv_dccz.setText("-");
//						tv_zhsr.setText("-");
//						tv_zhml.setText("-");
//	    			} else {
//	    				tv_dccz.setText(bean.getOutputValue()+"");
//	    				tv_zhsr.setText(bean.getIncome()+"");
//	    				tv_zhml.setText(bean.getProfit()+"");
//	    			}
//	    			tv_wxtc.setText(bean.getMaintenanceTimes()+"");
//	                }
//				setShowview(tv_runtime, bean.getReturntime());
//				setShowview(tv_runtime1, bean.getReturntime());
//				setShowview(tv_runtime2, bean.getReturntime());
//				setShowview(tv_runtime3, bean.getReturntime());
//
//			}else {
//				ToastUtils.showMyToast(SeparateAct.this, "暂无售后数据");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//
//		}
//	}


	@OnClick({R.id.tv_start,R.id.tv_end,R.id.ll_xs,R.id.ll_sh,R.id.iv_setting,R.id.ll_kpi,R.id.ll_kpish,R.id.iv_xx})
	public void OnClick(View v){
		switch (v.getId()) {
		case R.id.tv_start:
			showDateDialog(true);//选择开始时间
			break;
        case R.id.tv_end:
        	showDateDialog(false);//选择开始时间
			break;
			
         case R.id.ll_xs:
        	 if("售后全权限".equals(premission)||"售后半权限".equals(premission)){
        		 ToastUtils.showMyToast(SeparateAct.this, "暂无权限");
	            }else {
				openActivity(NewMainAct.class);
//        	    openActivity(MainActivity.class);
				finish();
	            }
			break;
			
//         case R.id.ll_sh:
//        	 if("销售全权限".equals(premission)||"销售半权限".equals(premission)){
//        		 ToastUtils.showMyToast(SeparateAct.this, "暂无权限");
//	            }else{
//        	    openActivity(ShmainAct.class);
//				finish();
//	            }
// 			break;
 			
         case R.id.iv_setting:
//        		startActivity(new Intent(SeparateAct.this, NewSettingAct.class));
        	  openActivity(NewSettingAct.class);
  			break;
  		
  		 case R.id.ll_kpi:
  		 showKpiDialog(0);
  		 break;
  		 
  		 case R.id.ll_kpish:
  			 showKpiDialog(1);
  	  		 break;
  	  		 
  	  	 case R.id.iv_xx:
  	  		 tv_num.setVisibility(View.GONE);
  	  		 openActivity(MymessAct.class);
  	  		 break ;
		
		}
	}
	

    private void showKpiDialog(int type) {
		// TODO Auto-generated method stub
    	List<String> list = new ArrayList<String>();
		if (type == 0) {
			list.add("成交数&当期新增订单量");
			list.add("总交车数&当期交车总数");
			list.add("销售综合收入&当期开票价格总额+衍生业务收入总计");
			list.add("销售综合毛利&进销差（GP1）+销售返利总计+衍生业务毛利总计");
		} else if (type == 1) {
			list.add("维修台次&按日去重的进厂车辆数");
			list.add("单车产值&维修收入除以维修台次");
			list.add("售后综合收入&售后主营+精品附件+续保延保的收入合计");
			list.add("售后综合毛利&售后主营+精品附件+续保延保的毛利合计");
		} 
		KpiDialog kpidialog = new KpiDialog(SeparateAct.this, list).builder();
		kpidialog.show();
		kpidialog.setCanceledOnTouchOutside(true);
	}

	/****
     * 
     * @param b
     */
	private void showDateDialog(final boolean b) {
		// TODO Auto-generated method stub
		TimePickerViewDialog pvTimeDialog = addPickerViewUtils.initDatePickerView(b ? "请选择开始时间" : "请选择结束时间")
				.getPvTime();
		pvTimeDialog.getDialog().show();
		pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {
			
			@Override
			public void onTimeSelect(Date date) {
				// TODO Auto-generated method stub
				if(b){			
					if(!DateAndTimeUtils.isBefore(date, DateAndTimeUtils.getCurrentDate(mTimeFormat))){//开始时间比当前时间大
						ToastUtils.showMyToast(SeparateAct.this, "开始时间不可大于当前时间");
						return;
					}else {//开始时间比当前时间小
						stime=addPickerViewUtils.getDate(date);//开始时间
						tv_start.setText(stime);
						
						startdate=DateAndTimeUtils.getDateTimeForStr(mTimeFormat, stime);//开始时间锉
						ProfitApplication.mStartDate=startdate;
						etime=DateAndTimeUtils.getLastDateOfMonth(startdate);
						enddate=DateAndTimeUtils.getDateTimeForStr(mTimeFormat, etime);
						
						if(!DateAndTimeUtils.isBefore(enddate, DateAndTimeUtils.getCurrentDate(mTimeFormat))){
							tv_end.setText(DateAndTimeUtils.getCurrentTime(mTimeFormat));//当前时间
							ProfitApplication.mEndDate=DateAndTimeUtils.getCurrentDate(mTimeFormat);
						}else{
							tv_end.setText(etime);
							ProfitApplication.mEndDate=enddate;
						}
//						getShinfo(tv_start.getText().toString(),tv_end.getText().toString());//售后数据请求
						getProfitInfo(tv_start.getText().toString(),tv_end.getText().toString());//销售数据请求
					}
				
				}else{
					if(!DateAndTimeUtils.isBefore(date, DateAndTimeUtils.getCurrentDate(mTimeFormat))){//结束时间比当前时间大	
						ToastUtils.showMyToast(SeparateAct.this, "结束时间不可大于当前时间");
						return;
					}else{
						etime=addPickerViewUtils.getDate(date);//结束时间
						enddate=DateAndTimeUtils.getDateTimeForStr(mTimeFormat, etime);//结束时间锉
						if (DateAndTimeUtils.isEqual(startdate, enddate)) {//结束时间等于开始时间
							tv_end.setText(etime);
							ProfitApplication.mEndDate=enddate;
//							getShinfo(tv_start.getText().toString(),tv_end.getText().toString());//售后数据请求
							getProfitInfo(tv_start.getText().toString(),tv_end.getText().toString());//销售数据请求
						}else if (!DateAndTimeUtils.isBefore(startdate, enddate)) {//结束时间小于开始时间
							ToastUtils.showMyToast(SeparateAct.this, "“结束时间”不可小于“开始时间”");
							return;
						}else {//结束时间大于开始时间小于当前时间
							tv_end.setText(etime);
							ProfitApplication.mEndDate=enddate;
//							getShinfo(tv_start.getText().toString(),tv_end.getText().toString());//售后数据请求
							getProfitInfo(tv_start.getText().toString(),tv_end.getText().toString());//销售数据请求
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


	@Override
	    public void onBackPressed() {
	        long currentTime = System.currentTimeMillis();
	        if ((currentTime - touchTime) >= 2000) {
	            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
	            touchTime = currentTime;
	        } else {	           
	            moveTaskToBack(false);

	        }
	    }

	
   public static final String UPDATENUM = "com.jpush.mess";
   private MyBroadCastReceiverTalk receiverTalk;
   
   @Override
    protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	if(receiverTalk==null){
		receiverTalk=new MyBroadCastReceiverTalk();
		registerReceiver(receiverTalk, new IntentFilter(UPDATENUM));
	}
    }
   
   private class MyBroadCastReceiverTalk extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int num=Statcode.Messnum;
		if(num==0){
			tv_num.setVisibility(View.GONE);
		}else {
			tv_num.setVisibility(View.VISIBLE);
			if(99>num){
			tv_num.setText(num+"");
			}else {
				tv_num.setText(99+"");
			}
			
		}
	}
	   
   }
   
   @Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	Statcode.inactivity=1;
}
   
   @Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	if(receiverTalk!=null){
		unregisterReceiver(receiverTalk);
		receiverTalk=null;
	}
}
	
	

}
