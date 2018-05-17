package com.iris.cada.activity;

import com.iris.cada.ProfitApplication;
import com.iris.cada.view.CKTitleView;
import com.iris.cada.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingActivity extends BaseActivity implements OnCheckedChangeListener {
	private CKTitleView titleView;
	private ToggleButton model, customer;
	private Button btnCustomer; // 我的信息
	private Button btnMyInfo; // 我的信息
	private Button btnUpdatePwd; // 修改密码
	private Button btnHelpDocument; // 帮助文档
	private Button btnSafeExit; // 安全退出
	public Boolean flag = false; // 记录当前有没有本页选择和设置任何事件(如果为true则返回后去刷新，否则不进行刷新)

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		setContentView(R.layout.activity_setting);
		initView();
	}

	public void initView() {
		titleView = (CKTitleView) findViewById(R.id.title);
		titleView.getLeftButoon().setVisibility(View.INVISIBLE);
		titleView.getRightButoon().setVisibility(View.VISIBLE);
		titleView.getRightButoon().setBackgroundResource(R.drawable.back);
		titleView.getRightButoon().setOnClickListener(new BackOnClickListener());
		TextView title = new TextView(this);
		title.setText(R.string.title_setting);
		title.setTextSize(ProfitApplication.getTitleTextSize(this));
		title.setTextColor(getResources().getColor(R.color.black));
		titleView.getLinearLayout().addView(title);

		model = (ToggleButton) findViewById(R.id.togg_model);
		model.setOnCheckedChangeListener(this);
		customer = (ToggleButton) findViewById(R.id.togg_customer);
		customer.setOnCheckedChangeListener(new ToggBtnOnCheck());
		btnMyInfo = (Button) findViewById(R.id.btn_myInfo);
		btnMyInfo.setOnClickListener(this);
		btnUpdatePwd = (Button) findViewById(R.id.btn_update_pwd);
		btnUpdatePwd.setOnClickListener(this);
		btnHelpDocument = (Button) findViewById(R.id.btn_help_document);
		btnHelpDocument.setOnClickListener(this);
		btnSafeExit = (Button) findViewById(R.id.btn_safe_exit);
		btnSafeExit.setOnClickListener(this);
		btnCustomer = (Button) findViewById(R.id.btn_customer);
		
		if (ProfitApplication.isGroup()) {
			btnCustomer.setText("经销商模式");
		}
		if (ProfitApplication.orCarModel()) {
			model.setChecked(true);
			customer.setChecked(false);
		} else {
			customer.setChecked(true);
			model.setChecked(false);
		}
		flag = !flag;
		System.out.println(flag);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.putExtra("flag", flag);
			setResult(MainActivity.RESULT_FROM_SELECT_ACTIVITY, intent);
		}
		// 继续执行父类的其他点击事件
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 返回监听
	 * 
	 * @author jaihaoGuo
	 * 
	 */
	private class BackOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.putExtra("flag", flag);
			setResult(MainActivity.RESULT_FROM_SELECT_ACTIVITY, intent);
			finish();
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			ProfitApplication.userSelectEntity.setCarModel("是");
			model.setChecked(true);
			customer.setChecked(false);
			flag = true;
		} else {
			customer.setChecked(true);
			model.setChecked(false);
		}
		service.setSelectInfo(ProfitApplication.userSelectEntity);
	}

	class ToggBtnOnCheck implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				ProfitApplication.userSelectEntity.setCarModel("否");
				customer.setChecked(true);
				model.setChecked(false);
				flag = true;
			} else {
				model.setChecked(true);
				customer.setChecked(false);
			}
			service.setSelectInfo(ProfitApplication.userSelectEntity);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_myInfo:
			Intent myInfo = new Intent(this, MyInfoActivity.class);
			startActivity(myInfo);
			break;
		case R.id.btn_update_pwd:
			Intent updatePwd = new Intent(this, UpdatePwdActivity.class);
			startActivity(updatePwd);
			break;
		case R.id.btn_help_document:
			Intent help = new Intent(this, HelpDocumentActivity.class);
			startActivity(help);
			break;
		case R.id.btn_safe_exit:
			service.clearData();
			this.finish();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			break;
	
		}
	}
    
}
