package com.iris.cada.activity;

import com.iris.cada.AppManager;
import com.iris.cada.ProfitApplication;
import com.iris.cada.Statcode;
import com.iris.cada.fragment.FinZdFra;
import com.iris.cada.newfragment.AnyBbFra;
import com.iris.cada.newfragment.AnyGlFra;
import com.iris.cada.newfragment.AnyJkFrg;
import com.iris.cada.newfragment.AnySyFra;
import com.iris.cada.newfragment.AnyZdFra;
import com.iris.cada.newfragment.DiagnosisFragment;
import com.iris.cada.newfragment.HomePageFragment;
import com.iris.cada.newfragment.MenuFragment;
import com.iris.cada.newfragment.MonitorFragment;
import com.iris.cada.newfragment.NewZdFra;
import com.iris.cada.newfragment.OverviewFragment;
import com.iris.cada.newfragment.ReportFragment;
import com.iris.cada.utils.MyToast;
import com.iris.cada.utils.WebUtils;
import com.iris.cada.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import slidingmenu.lib.SlidingMenu;

public class ShmainAct extends FragmentActivity {

	private RadioGroup rgTab; // 底部选项卡
	private FragmentManager fm; // fragment管理者

	private AnySyFra syfra;
	private AnyGlFra glfra;
	private AnyBbFra bbfra;
	private AnyJkFrg jkfra;
//	private AnyZdFra zdfra;
	private NewZdFra newzdfra;
//	private FinZdFra finzdfra;
	private SlidingMenu slideMenu;
	private boolean modle;

	private long touchTime = 0;
	private int type;
	private String stime, etime;// 开始时间结束时间

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (savedInstanceState != null) {
			savedInstanceState.putParcelable("android:support:fragments", null);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_shmain);
		AppManager.getAppManager().addActivity(this);
		Statcode.type = 2;// 告诉设置页面这个是在售后模块
		Statcode.inactivity=4;
		stime = WebUtils.getStart();
		etime = WebUtils.getend();
		modle = ProfitApplication.isConsultantMode;
		// 获取当前登录用户的信息
		rgTab = (RadioGroup) findViewById(R.id.rg_tab);
		rgTab.setOnCheckedChangeListener(new OnRadioGroupListener());
		fm = getSupportFragmentManager();
		// initSlidingMenu();
		rgTab.setVisibility(View.VISIBLE);
		showfrg(1);
	}

	// private void initSlidingMenu() {
	// setBehindContentView(R.layout.menu_frame);
	// getSupportFragmentManager().beginTransaction().replace(R.id.fl_menu, new
	// MenuFragment()).commit();
	// this.slideMenu = getSlidingMenu();
	// this.slideMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
	// this.slideMenu.setShadowDrawable(R.drawable.shadow);
	// this.slideMenu.setShadowWidthRes(R.dimen.shadow_width);
	// this.slideMenu.setMode(0);
	// this.slideMenu.setTouchModeAbove(0);
	// }

	/**
	 * 底部菜单监听
	 * 
	 * @author jiahaoGuo
	 * 
	 */
	private class OnRadioGroupListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.rb_data:
				// 数据
				showfrg(0);
				break;
			case R.id.rb_report:
				// 盖卵
				showfrg(1);
				break;
			case R.id.rb_kpi:
				// 报表
				showfrg(2);
				break;
			case R.id.rb_funnel:
				// 监控
				showfrg(3);
				break;
			case R.id.rb_setting:
				// 诊断
				showfrg(4);
				break;
			}
		}

	}

	/**
	 * 显示frg
	 * 
	 * @param i
	 */
	private void showfrg(int i) {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);// 影长fragment
		switch (i) {
		case 0:
			type=0;
			if (syfra == null) {
				syfra = new AnySyFra();
				transaction.add(R.id.any_fragment, syfra);
			} else {
				transaction.show(syfra);
			}
			rgTab.setVisibility(View.GONE);
			
			break;

		case 1:
			type = 1;
			if (glfra == null) {
				glfra = new AnyGlFra();
				transaction.add(R.id.any_fragment, glfra);
			} else {
				transaction.show(glfra);
			}
			rgTab.setVisibility(View.VISIBLE);
			break;

		case 2:
			type = 2;
			if (bbfra == null) {
				bbfra = new AnyBbFra();
				transaction.add(R.id.any_fragment, bbfra);
			} else {
				transaction.show(bbfra);
			}
			rgTab.setVisibility(View.VISIBLE);
			break;

		case 3:
			type = 3;
			if (jkfra == null) {
				jkfra = new AnyJkFrg();
				transaction.add(R.id.any_fragment, jkfra);
			} else {
				transaction.show(jkfra);
			}
			rgTab.setVisibility(View.VISIBLE);
			break;

		case 4:
			type = 4;
			if (newzdfra == null) {
				newzdfra = new NewZdFra();
				transaction.add(R.id.any_fragment, newzdfra);
			} else {
				transaction.show(newzdfra);
			}
			rgTab.setVisibility(View.VISIBLE);
			break;

		}
		transaction.commit();
	}

	/*
	 * 影长
	 */
	private void hideFragment(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (syfra != null) {
			transaction.hide(syfra);
		}
		if (glfra != null) {
			transaction.hide(glfra);
		}
		if (jkfra != null) {
			transaction.hide(jkfra);
		}
		if (bbfra != null) {
			transaction.hide(bbfra);
		}
		if (newzdfra != null) {
			transaction.hide(newzdfra);
		}
	}

	@Override
	public void onBackPressed() {

		long currentTime = System.currentTimeMillis();
		if ((currentTime - touchTime) >= 2000) {
			Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
			Log.e("haha", "haha");
			touchTime = currentTime;
		} else {

			moveTaskToBack(false);
		
		}
	}

	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (myrecive == null) {
			myrecive = new Myrecive();
			registerReceiver(myrecive, new IntentFilter(UPDATEUI));
			registerReceiver(myrecive, new IntentFilter(UPDATENUM));
		}
	}

	/** 与广播相关的东西,设置里面改变模式后收到广播通知四个fragmenet重新加载页面 **/
	public static final String UPDATEUI = "com.newsetting.bro";
//	public static final String WEBTIME = "com.javascripter.bro";
	public static final String UPDATENUM = "com.jupsh.upnum";//收到通知后更新首页信封
	private Myrecive myrecive;

	public class Myrecive extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(UPDATEUI)) {// 从设置里面发过来的广播

				if(type==1){
					glfra.close();
					glfra.reflush();
				}else if (type==2){
					bbfra.close();
					bbfra.reflush();
				}else if (type==3){
					jkfra.close();
					jkfra.reflush();
				}
                else if(type==4){
					newzdfra.reflush();
				}

			}
				else if (intent.getAction().equals(UPDATENUM)){
				if(syfra!=null){
					syfra.updatenum();
				}
			}

		}

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Statcode.inactivity=4;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (myrecive != null) {
			unregisterReceiver(myrecive);
			myrecive = null;
		}
		
	 
		
	}
	

}
