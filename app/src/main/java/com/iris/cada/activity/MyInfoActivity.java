package com.iris.cada.activity;

import java.util.Map;

import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.entity.SalesInfo;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyInfoActivity extends Activity {
	private SalesInfo salesInfo;
	private String user, tel;
	private EditText et_myInfo_phone;
	private Button bt_myInfo_submit;
	private TextView myinfo_username, myinfo_name, myinfo_position;
	private Dialog progressDialog;
	private SharedPreferencesUtils service;
	@SuppressLint("HandlerLeak")
	Handler handler = new MyHandler(this) {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDialog.dismiss();
			String json = (String) msg.obj;
			switch (msg.what) {
			case ProfitApplication.DATA_SUC:
				if (json.equals("true")) {
					Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "修改失败!", Toast.LENGTH_SHORT).show();
				}
				break;
			case ProfitApplication.DATA_FAILED:
				Toast.makeText(getApplicationContext(), "暂无数据!", Toast.LENGTH_SHORT).show();
				break;
			case ProfitApplication.SERVER_FAILED:
				Toast.makeText(getApplicationContext(), "服务器连接失败!", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		setContentView(R.layout.activity_my_info);
		// 获取进度条
		et_myInfo_phone = (EditText) findViewById(R.id.et_myInfo_phone);
		bt_myInfo_submit = (Button) findViewById(R.id.bt_myInfo_submit);
		myinfo_username = (TextView) findViewById(R.id.myinfo_username);
		myinfo_name = (TextView) findViewById(R.id.myinfo_name);
		myinfo_position = (TextView) findViewById(R.id.myinfo_position);

		salesInfo = ProfitApplication.salesInfo;
		progressDialog = CProgressDialog.createLoadingDialog(this);
		service = new SharedPreferencesUtils(this);
		// 为文本框设置用户信息数据
		myinfo_username.setText(CodeUtils.decode(service.getPreferences().get("username")));
		myinfo_name.setText(salesInfo.getName());
		myinfo_position.setText(salesInfo.getRole());

		tel = salesInfo.getTel();
		et_myInfo_phone.setText(salesInfo.getTel());

		bt_myInfo_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tel = et_myInfo_phone.getText().toString();
				loadData();
				IRISService.doMyInfo(user, tel, handler);
				progressDialog.show();
			}
		});
	}

	public void back(View v) {
		MyInfoActivity.this.finish();
	}

	/**
	 * 加载用户保存的本地数据，若为空则返回false
	 * 
	 * @return
	 */
	public boolean loadData() {
		Map<String, String> params1 = service.getPreferences();
		user = params1.get("username");
		if (user.equals("")) {
			return false;
		} else {
			user = CodeUtils.decode(user);
			return true;
		}
	}
}
