package com.iris.cada.newfragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.JavaScriptinterface;
import com.iris.cada.ProfitApplication;
import com.iris.cada.Statcode;
import com.iris.cada.activity.FilterConsultantActivity;
import com.iris.cada.activity.NewSettingAct;
import com.iris.cada.activity.ShmainAct;
import com.iris.cada.activity.ShsxAct;
import com.iris.cada.entity.IViewSCBean;
import com.iris.cada.entity.ShzdBean;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.utils.SharedPreferencesUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.utils.WebUtils;
import com.iris.cada.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AnyZdFra extends Fragment implements OnClickListener{
	private static final int REQUSET = 2;
	private ImageView iv_setting;
	private TextView tv_sx;
	private WebView wb_view;
	private boolean bool=false;
	private ProgressBar pb;
	
	private String url;
	private String stime;
	private String etime;
	private String mode;//0是顾问模式 1是车型模式
	private SharedPreferencesUtils sh;
	private String username;
	private String customerName;//顾问模式要传递的值
	private String carType;//车型模式要传递的值
	private Gson gson;
	private List<ShzdBean>list;
	public ArrayList<String> checkedList = new ArrayList<String>();//被选中的的集合
	
	public static final String KEY_DATA_USER = "KEY_DATA_USER";//用户
    public static final int KEY_DATA_OK = 100;
    
    private String strmsg;
	
	//车型筛选
	public static final int KEY_CAR_OK = 101;
	public static final String KEY_DATA_CAR = "KEY_DATA_CAR";
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {			
			super.handleMessage(msg);		
			switch (msg.what) {
			case ProfitApplication.DATA_SUC://获取数据成功				
				loadH5(msg);//加载h5界面
				break;		
			case ProfitApplication.DATA_FAILED://获取数据失败
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				break;	
							
			case ProfitApplication.SERVER_FAILED://连接服务器失败
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_net));
				break;
			
			}
		}

		
	
	};
	
	/**加载h5界面**/
	@SuppressLint("NewApi")
	private void loadH5(Message msg) {
		// TODO Auto-generated method stub
		strmsg=(String) msg.obj;		
		if(!TextUtils.isEmpty(strmsg)){//数据不为空的时候把数据坪街道h5
			url=ProfitApplication.H5_ZD+"?startDate="+stime+"&endDate="
		        +etime+"&checkType="+mode+"&dealerCode="
				+ProfitApplication.loginBackInfo.getLicense();
//			    +"&listObj="+strmsg;
//			webset(url,wb_view,pb);
			WebUtils.WebSet(url, wb_view, pb);
			wb_view.loadUrl("javascript:getMyData('" + strmsg + "')");//调用js代码把请求的东西传递给h5
					
			/**让h5调用jave方法把选择的时间暴露给原生**/
			wb_view.addJavascriptInterface(new JavaScriptinterface(getActivity()), "wst");
		 	
		}
		/**以上是关于H5的界面部分，下面的是筛选时候的部分**/
		
		try {
			list=gson.fromJson(new JSONObject(strmsg).getJSONArray("data").toString(), new TypeToken<List<ShzdBean>>(){}.getType());
		    
			if(list!=null&&list.size()>0){
				list.remove(0);//把第一条抽出来拿到和筛选部分比较
			}
		
		  Log.e("tag", list.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		return super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_zd, container, false);	   
		iv_setting=(ImageView) view.findViewById(R.id.iv_setting);	
		tv_sx=(TextView) view.findViewById(R.id.tv_sx);
		tv_sx.setOnClickListener(this);
		iv_setting.setOnClickListener(this);
	    wb_view=(WebView) view.findViewById(R.id.wb_view);
	    pb = (ProgressBar)view.findViewById(R.id.pb);  
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		sh=new SharedPreferencesUtils(getActivity());
		stime=WebUtils.getStart();
		etime=WebUtils.getend();
		if(sh.getMode()==true){
			mode="0";
		}else {
			mode="1";
		}
		customerName="";//默认什么都不传
		carType="";
		gson=new Gson();
		
		if(ProfitApplication.isConsultantMode==true){//顾问模式车型模式给空字符串
		 getGwInfo(stime,etime,"gw",customerName);//先获取数据拼接到h5界面
       }else if (ProfitApplication.isConsultantMode==false){//车型模式
    	 getCarInfo(stime,etime,"cx",carType);   
       }
	}


    /**顾问模式**/
	private void getGwInfo(String startDate,String endDate,String type,String content) {
		// TODO Auto-generated method stub
		ProfitApplication.profitNetService.getShzdinfo(startDate,endDate,type,content,handler);
	}
	
	/**车型模式**/
	private void getCarInfo(String startDate,String endDate,String type,String content) {
		// TODO Auto-generated method stub
		ProfitApplication.profitNetService.getShzdinfo(startDate,endDate,type,content,handler);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_setting:
//			 ((ShmainAct) getActivity()).getSlidingMenu().toggle();
			Intent intent=new Intent(getActivity(),NewSettingAct.class);
			startActivity(intent);
			break;

		case R.id.tv_sx:
			Intent intent1 = new Intent(getActivity(), ShsxAct.class);
			intent1.putExtra("startTime", stime);
			intent1.putExtra("endTime", etime);			
			intent1.putExtra("date", (Serializable)list);	
			startActivityForResult(intent1, REQUSET);
			
			break ;
		}
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if(hidden){
			
		}else {
			reflush();
			
		}
	}
	
	public void reflush() {
		// TODO Auto-generated method stub
		
			stime=WebUtils.getStart();
			etime=WebUtils.getend();
			if(sh.getMode()==true){
				mode="0";
			}else {
				mode="1";
			}
//			url=ProfitApplication.H5_ZD+"?startDate="+stime+"&endDate="+etime+"&checkType="+mode+"&dealerCode="+ProfitApplication.loginBackInfo.getLicense();
//			WebUtils.WebSet(url, wb_view, pb);
//			wb_view.addJavascriptInterface(new JavaScriptinterface(getActivity()), "wst");
			
			if(ProfitApplication.isConsultantMode==true){//顾问模式车型模式给空字符串
				 getGwInfo(stime,etime,"gw",customerName);//先获取数据拼接到h5界面
		       }else if (ProfitApplication.isConsultantMode==false){//车型模式
		    	 getCarInfo(stime,etime,"cx",carType);   
		       }
	}
	

	public void ClearCache() {
		wb_view.loadDataWithBaseURL(null, "","text/html", "utf-8",null);//清除缓存	
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		checkedList.clear();//-----------------------------------------
		if (resultCode == KEY_DATA_OK) {
			String datem=data.getStringExtra("KEY_DATA_MODUE");			
			if(TextUtils.isEmpty(datem)){
				customerName="";					
			}else{
				customerName=datem;				
			}
			 getGwInfo(stime,etime,"gw",customerName);//先获取数据拼接到h5界面	
		
		}else if(resultCode == KEY_CAR_OK){//车型筛选
			String datec = data.getStringExtra(KEY_DATA_CAR);
			if(TextUtils.isEmpty(datec)){
				carType="";
			
			}else{
				carType=datec;
			}
			getCarInfo(stime,etime,"cx",carType);//先获取数据拼接到h5界面
		}
	}
	
	
	

}

