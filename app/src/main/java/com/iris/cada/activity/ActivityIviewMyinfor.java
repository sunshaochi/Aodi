package com.iris.cada.activity;

import java.util.Map;

import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.service.IRISService;
import com.iris.cada.utils.CodeUtils;
import com.iris.cada.utils.SharedPreferencesUtils;
import com.iris.cada.view.CProgressDialog;
import com.iris.cada.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("CutPasteId")
public class ActivityIviewMyinfor extends Activity {

	private Dialog progressDialog;
	private SharedPreferencesUtils service;
	private TextView userName;
	private TextView name;
	private TextView position;
	private EditText phone;
	private ImageView save;
	private String user, tel;
	private ImageView back;

	Handler handler = new MyHandler(this) {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDialog.dismiss();
			switch (msg.what) {
//
				case ProfitApplication.SERVER_FAILED:
					Toast.makeText(getApplicationContext(), "服务器连接失败!", Toast.LENGTH_SHORT).show();
					break;


				case ProfitApplication.DATA_FAILED://获取数据失败
					String tsMes = (String) msg.obj;
					Toast.makeText(getApplicationContext(), tsMes, Toast.LENGTH_SHORT).show();
					break;

				case ProfitApplication.DATA_SUC:
					Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
					ProfitApplication.loginBackInfo.setMobileNo(tel);
					break;
				case ProfitApplication.NO_DATE://
					Toast.makeText(getApplicationContext(), "暂无数据", Toast.LENGTH_SHORT).show();
					break;


			}
		}
	};

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_iview_my_infor);
		initView();
		bindata();
	}

	private void bindata() {
	}

	private void initView() {
		progressDialog = CProgressDialog.createLoadingDialog(this);
		service = new SharedPreferencesUtils(this);

		userName = (TextView) findViewById(R.id.activity_iview_user_name_et);
		name = (TextView) findViewById(R.id.activity_iview_user_my_name_et);
		position = (TextView) findViewById(R.id.activity_iview_user_posation_et);
		phone = (EditText) findViewById(R.id.activity_iview_user_mobile_et);
		save = (ImageView) findViewById(R.id.my_info_iv_save);
		back = (ImageView) findViewById(R.id.overview_title_setting);

		// 为文本框设置用户信息数据
		userName.setText(CodeUtils.decode(service.getPreferences().get("username")));
		name.setText(ProfitApplication.loginBackInfo.getPersonName());
		position.setText(ProfitApplication.loginBackInfo.getRoleName());
		tel = ProfitApplication.loginBackInfo.getMobileNo();

		phone.setText(tel);

		// 保存
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tel = phone.getText().toString();
//				loadData();
//				IRISService.doMyInfo(user, tel, handler);
				upDateInfo(tel,handler);

			}
		});

		// 返回
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * 保存
	 * @param
	 * @param tel
	 * @param handler
	 */
	private void upDateInfo(String tel, Handler handler) {
		ProfitApplication.profitNetService.upDatePhone(tel,handler);
		progressDialog.show();

	}

	/**
//	 * 加载用户保存的本地数据，若为空则返回false
//	 *
//	 * @return
//	 */
//	public boolean loadData() {
//		Map<String, String> params1 = service.getPreferences();
//		user = params1.get("username");
//		if (user.equals("")) {
//			return false;
//		} else {
//			user = CodeUtils.decode(user);
//			return true;
//		}
//	}
}
