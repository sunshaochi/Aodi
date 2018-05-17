package com.iris.cada.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.AppManager;
import com.iris.cada.ProfitApplication;
import com.iris.cada.Statcode;
import com.iris.cada.adapter.MessAdapter;
import com.iris.cada.entity.IviewDiagnosisProductBean;
import com.iris.cada.entity.MessBean;
import com.iris.cada.utils.CodeUtils;
import com.iris.cada.utils.SharedPreferencesUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.view.MyListview;
import com.iris.cada.R;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

public class MymessAct extends NewBaseAct{
	

	/*@ViewInject(R.id.sc_view)
	private ScrollView sc_view;*/
	@ViewInject(R.id.lv_mylv)
	private ListView lv_mylv;
	
	private MessAdapter adapter;
	private SharedPreferencesUtils service;
	private String username;
	private Gson gson;
	private List<MessBean>list;
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {			
			super.handleMessage(msg);
			switch (msg.what) {
			case ProfitApplication.DATA_SUC://获取售后数据成功
//				ToastUtils.showMyToast(SeparateAct.this, getString(R.string.error_data));
				showList(msg);
				break;
			case ProfitApplication.DATA_FAILED://获取销售数据成功
				ToastUtils.showMyToast(MymessAct.this, getString(R.string.error_data));
	
				break;	

			case ProfitApplication.SERVER_FAILED://连接服务器失败
				ToastUtils.showMyToast(MymessAct.this, getString(R.string.error_net));
				break;
			
			}
		}

		

	
		
	};
	
	private void showList(Message msg) {
		// TODO Auto-generated method stub
		
		try {
			String strmess=(String) msg.obj;
			list=gson.fromJson(new JSONObject(strmess).getJSONArray("data").toString(), new TypeToken<List<MessBean>>() {
			}.getType());
			
			if(list!=null&&list.size()>0){
				adapter=new MessAdapter(MymessAct.this, list,Statcode.Messnum);
				lv_mylv.setAdapter(adapter);
				lv_mylv.setSelection(list.size()-Statcode.Messnum);  //设置自动刷新到某个位置			   
				Statcode.Messnum=0;//把数据置为0
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("解析错误", e.toString());
		} 

			
		
	}

	@Override
	public void setLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.act_mymess);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AppManager.getAppManager().addActivity(this);
		service=new SharedPreferencesUtils(MymessAct.this);
		username=CodeUtils.decode(service.getPreferences().get("username"));
		gson=new Gson();
		getMesslist(username);
//		list=new ArrayList<MessBean>();
//		for(int i=0;i<15;i++){
//			list.add(new MessBean("测试内容", "2017年10月9号", "", "", "", "0", "data"));
//		}
	}

	private void getMesslist(String username) {
		   ProfitApplication.profitNetService.getMesslist(username,handler);		
	}
	
	@OnClick({R.id.iv_finsh})
	public void OnClick(View v){
		switch (v.getId()) {
		case R.id.iv_finsh:
			finish();
			break;

		
		}
	}
	
	public static final String UPDATELIST = "com.iris.brode";
	
	private MyBroadCastReceiverList receiverTalk;
	   
	   @Override
	    protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(receiverTalk==null){
			receiverTalk=new MyBroadCastReceiverList();
			registerReceiver(receiverTalk, new IntentFilter(UPDATELIST));
		}
	    }

	   
	   private class MyBroadCastReceiverList extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			getMesslist(username);
		}
		   
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
