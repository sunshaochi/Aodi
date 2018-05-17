package com.iris.cada.activity;

import com.iris.cada.AppManager;
import com.iris.cada.ProfitApplication;
import com.iris.cada.Statcode;
import com.iris.cada.utils.SharedPreferencesUtils;
import com.iris.cada.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

public class NewSettingAct extends Activity implements OnClickListener {
	private RelativeLayout rl_info, rl_update, rl_chexin, rl_gw, rl_dr,
			rl_help, rl_qa;
	private TextView tv_exit, tv_qa;
	private LinearLayout ll_phone;
	private ImageView iv_car, iv_gw, iv_finsh;
	private View line_view;
	private SharedPreferencesUtils mSharedPreferencesUtils;
	protected SharedPreferencesUtils service;
	private boolean modle;

	@Override
	public void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.act_newsetting);
		initView();
		initData();

	}

	private void initView() {
		// TODO Auto-generated method stub
		rl_info = (RelativeLayout) findViewById(R.id.rl_info);
		rl_info.setOnClickListener(this);

		rl_update = (RelativeLayout) findViewById(R.id.rl_update);
		rl_update.setOnClickListener(this);

		rl_chexin = (RelativeLayout) findViewById(R.id.rl_chexin);
		rl_chexin.setOnClickListener(this);

		rl_gw = (RelativeLayout) findViewById(R.id.rl_gw);
		rl_gw.setOnClickListener(this);

		rl_dr = (RelativeLayout) findViewById(R.id.rl_dr);
		rl_dr.setOnClickListener(this);

		rl_help = (RelativeLayout) findViewById(R.id.rl_help);
		rl_help.setOnClickListener(this);

		rl_qa = (RelativeLayout) findViewById(R.id.rl_qa);
		rl_qa.setOnClickListener(this);

		tv_exit = (TextView) findViewById(R.id.tv_exit);
		tv_exit.setOnClickListener(this);

		tv_qa = (TextView) findViewById(R.id.tv_qa);
		tv_qa.setText("Q&A");

		ll_phone = (LinearLayout) findViewById(R.id.ll_phone);
		ll_phone.setOnClickListener(this);

		iv_car = (ImageView) findViewById(R.id.iv_car);
		iv_gw = (ImageView) findViewById(R.id.iv_gw);

		line_view = findViewById(R.id.line_view);

		iv_finsh = (ImageView) findViewById(R.id.iv_finsh);
		iv_finsh.setOnClickListener(this);

	}

	private void initData() {
		// TODO Auto-generated method stub
		mSharedPreferencesUtils = new SharedPreferencesUtils(this);
		// ProfitApplication.isConsultantMode =
		// mSharedPreferencesUtils.getMode();
		modle = ProfitApplication.isConsultantMode;
		// service = new SharedPreferencesUtils(NewSettingAct.this);
		if (ProfitApplication.isConsultantMode) {// 顾问模式
			iv_gw.setImageResource(R.drawable.butn_open);
			iv_car.setImageResource(R.drawable.butn_close);
		} else {// 车型模式
			iv_gw.setImageResource(R.drawable.butn_close);
			iv_car.setImageResource(R.drawable.butn_open);
		}

		if (!TextUtils.isEmpty(ProfitApplication.loginBackInfo.getIsShowQa())) {
			if (ProfitApplication.loginBackInfo.getIsShowQa().equals("0")) {
				rl_qa.setVisibility(View.GONE);
				tv_qa.setText("");
				line_view.setVisibility(View.GONE);
			} else {
				rl_qa.setVisibility(View.VISIBLE);
				tv_qa.setText("Q&A");
				line_view.setVisibility(View.VISIBLE);
			}
		} else {
			rl_qa.setVisibility(View.GONE);
			tv_qa.setText("");
			line_view.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_info:
			startActivity(new Intent(NewSettingAct.this,
					ActivityIviewMyinfor.class));
			break;

		case R.id.rl_update:
			startActivity(new Intent(NewSettingAct.this,
					ActivityChangePassWord.class));
			break;
		case R.id.rl_chexin:
			// if (ProfitApplication.isConsultantMode) {//如果是顾问模式变化
			// iv_gw.setImageResource(R.drawable.butn_close);
			// iv_car.setImageResource(R.drawable.butn_open);
			//
			// ProfitApplication.isConsultantMode = false;
			// mSharedPreferencesUtils.setMode(false);
			// if(Statcode.type==1){//表示在销售部分
			// Intent intent = new Intent(ProfitApplication.BROADCAST_MESSAGE);
			// LocalBroadcastManager.getInstance(NewSettingAct.this).sendBroadcast(intent);
			// } else if(Statcode.type==2){//表示在售后模式
			// sendBroadcast(new Intent(ShmainAct.UPDATEUI));
			// } else if(Statcode.type==0){//表示在分割界面
			//
			// }
			// } else {
			//
			// }
			iv_gw.setImageResource(R.drawable.butn_close);
			iv_car.setImageResource(R.drawable.butn_open);
			ProfitApplication.isConsultantMode = false;
			mSharedPreferencesUtils.setMode(false);
			break;
		case R.id.rl_gw:
			// if (!ProfitApplication.isConsultantMode) {//如果本身是车型模式变化
			// iv_gw.setImageResource(R.drawable.butn_open);
			// iv_car.setImageResource(R.drawable.butn_close);
			//
			// ProfitApplication.isConsultantMode = true;
			// mSharedPreferencesUtils.setMode(true);
			// if(Statcode.type==1){
			// Intent intent = new Intent(ProfitApplication.BROADCAST_MESSAGE);
			// LocalBroadcastManager.getInstance(NewSettingAct.this).sendBroadcast(intent);
			// }else if(Statcode.type==2){
			// sendBroadcast(new Intent(ShmainAct.UPDATEUI));
			// }else if(Statcode.type==0){//表示在分割界面
			//
			// }
			// } else {
			//
			// }
			iv_gw.setImageResource(R.drawable.butn_open);
			iv_car.setImageResource(R.drawable.butn_close);
			ProfitApplication.isConsultantMode = true;
			mSharedPreferencesUtils.setMode(true);
			break;
		case R.id.rl_dr:
			startActivity(new Intent(NewSettingAct.this, DaoruJrAct.class));
			break;
		case R.id.rl_help:
			startActivity(new Intent(NewSettingAct.this, HelpAct.class));
			break;
		case R.id.tv_exit:
			mSharedPreferencesUtils.clearData();
			JPushInterface.setAlias(NewSettingAct.this, 0, "");
			finish();

			AppManager.getAppManager().finishAllActivity();
			Intent intent = new Intent(NewSettingAct.this, LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.rl_qa:
			Toast.makeText(NewSettingAct.this, "暂无数据", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.ll_phone:
			phoneCall();// 拨打电话
			break;

		case R.id.iv_finsh:
			if (modle == ProfitApplication.isConsultantMode) {// 表示顾问模式没切换

			} else {
				if (Statcode.type == 1) {// 表示在销售部分
					Intent intent1 = new Intent(
							ProfitApplication.BROADCAST_MESSAGE);
					LocalBroadcastManager.getInstance(NewSettingAct.this)
							.sendBroadcast(intent1);
				} else if (Statcode.type == 2) {// 表示在售后模式
					sendBroadcast(new Intent(ShmainAct.UPDATEUI));
				} else if (Statcode.type == 0) {// 表示在分割界面

				}
			}
			finish();
			break;
		}
	}

	private void phoneCall() {
		// TODO Auto-generated method stub
		Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
				+ "4007280250"));
		phoneIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(phoneIntent);
	}

}
