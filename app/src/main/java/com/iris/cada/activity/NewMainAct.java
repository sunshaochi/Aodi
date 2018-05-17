package com.iris.cada.activity;

import com.iris.cada.AppManager;
import com.iris.cada.Statcode;
import com.iris.cada.newfragment.AnySyFra;
import com.iris.cada.newfragment.DiagnosisFragment;
import com.iris.cada.newfragment.MonitorFragment;
import com.iris.cada.newfragment.NewSyFra;
import com.iris.cada.newfragment.ReportFragment;
import com.iris.cada.newfragment.SxbbFragment;
import com.iris.cada.newfragment.SxglFrag;
import com.iris.cada.R;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NewMainAct extends NewBaseAct {
	
	@ViewInject(R.id.rg_tab)
	private RadioGroup rg_tab;
	@ViewInject(R.id.ll_bg)
	private LinearLayout ll_bg;
	private FragmentManager fm;
//	private AnySyFra syfra;// 首页
	private NewSyFra syfra;//首页
	private SxglFrag glfra;// 概览
//	private ReportFragment reportfra;// 报表（老报表）
	private SxbbFragment bbfra;//新的报表
	private MonitorFragment monitorfra;// 监控
	private DiagnosisFragment diagfra;// 诊断
	@ViewInject(R.id.rb_report)
	private RadioButton rb_report;//概览
	
	@ViewInject(R.id.rb_funnel)
	private RadioButton rb_funnel;//监控
	
	private String room;//判断是从哪个界面进来主要是判断消息列表界面进来的不同情况

	@Override
	public void setLayout() {
		// TODO Auto-generated method stub
		
		setContentView(R.layout.act_newmian);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		AppManager.getAppManager().addActivity(NewMainAct.this);
		Statcode.type=1;
		Statcode.inactivity=3;
		fm = getSupportFragmentManager();
		rg_tab.setOnCheckedChangeListener(new OnRadioGroupListener());
		rg_tab.setVisibility(View.VISIBLE);
		room=getIntent().getStringExtra("room");
		if(!TextUtils.isEmpty(room)){//从消息列表里面进来
			if("库存概览".equals(room)){
				slectfra(1);
				
			}else if("运营监控".equals(room)){
				slectfra(3);
				rb_funnel.setChecked(true);
			}else {
				  slectfra(1);
			}
		}else {//从其他地方进来
		   slectfra(1);
		}
	}

	

	/**
	 * 底部菜单监听
	 * 
	 * @author jiahaoGuo
	 * 
	 */
	private class OnRadioGroupListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {

			switch (group.getCheckedRadioButtonId()) {
			case R.id.rb_data:
				
				slectfra(0);// 选择显示首页

				break;
			case R.id.rb_report:
				
				slectfra(1);// 显示概览
				break;
			case R.id.rb_kpi:
				
				slectfra(2);// 显示报表
				break;
			case R.id.rb_funnel:
				
				slectfra(3);// 显示监控
				break;
			case R.id.rb_setting:			
				slectfra(4);// 诊断

				break;
			}
		}

	}

	private void slectfra(int i) {
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		// TODO Auto-generated method stub
		switch (i) {
		case 0:
			if (syfra == null) {
				syfra = new NewSyFra();				
				transaction.add(R.id.fl_fragment, syfra);
			} else {
				transaction.show(syfra);
			}
			rg_tab.setVisibility(View.GONE);
			
			break;

		case 1:
			if (glfra == null) {
				glfra = new SxglFrag();
				transaction.add(R.id.fl_fragment, glfra);
				Bundle bundle=new Bundle();			
				if(!TextUtils.isEmpty(room)){
					bundle.putString("room", room);
				}else {
					bundle.putString("room", "");
				}
				glfra.setArguments(bundle);
			} else {
				transaction.show(glfra);
			}			
			rg_tab.setVisibility(View.VISIBLE);
			break;
		case 2:
			if (bbfra == null) {
				bbfra = new SxbbFragment();
				transaction.add(R.id.fl_fragment, bbfra);
			} else {
				transaction.show(bbfra);
			}
			rg_tab.setVisibility(View.VISIBLE);
			break;
		case 3:
			if (monitorfra == null) {
				monitorfra = new MonitorFragment();
				transaction.add(R.id.fl_fragment, monitorfra);
			} else {
				transaction.show(monitorfra);
			}
			rg_tab.setVisibility(View.VISIBLE);
			break;
		case 4:
			if (diagfra == null) {
				diagfra = new DiagnosisFragment();
				transaction.add(R.id.fl_fragment, diagfra);
			} else {
				transaction.show(diagfra);
			}
			rg_tab.setVisibility(View.VISIBLE);
			break;
		}
		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (syfra != null) {
			transaction.hide(syfra);
		}
		if (glfra != null) {
			transaction.hide(glfra);
		}
		if (bbfra != null) {
			transaction.hide(bbfra);
		}
		if (monitorfra != null) {
			transaction.hide(monitorfra);
		}
		if (diagfra != null) {
			transaction.hide(diagfra);
		}
	}
	
	/**与广播相关的东西**/
	
	public static final String UPDATENUM = "com,jpush.num";//收到通知后刷新主页信封
	

	private Myrecivetype recive;
	
	public class Myrecivetype extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.e("走了", intent.getAction().toString());
			// TODO Auto-generated method stub
			if(intent.getAction().equals(UPDATENUM)){//刷新信封
				if(syfra!=null){
					syfra.upNum();//更新信封
				}
			}
			
		}
		
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(recive==null){
			recive=new Myrecivetype();			
			registerReceiver(recive, new IntentFilter(UPDATENUM));
			
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Statcode.inactivity=3;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(recive!=null){
			unregisterReceiver(recive);
			recive=null;
		}
	}
	

}
