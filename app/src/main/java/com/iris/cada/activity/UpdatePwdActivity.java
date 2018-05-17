package com.iris.cada.activity;

import java.util.Map;

import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.service.IRISService;
import com.iris.cada.utils.CodeUtils;
import com.iris.cada.utils.SharedPreferencesUtils;
import com.iris.cada.view.CProgressDialog;
import com.iris.cada.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePwdActivity extends Activity {
	private String pswd, user;
	private EditText et_changePsw_old, et_changePsw_new;
	private String password;
	private String newPassword;
	private Dialog progressDialog;
	private SharedPreferencesUtils service;
	private Button btUpdatePwd;
	Handler handler = new MyHandler(this) {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDialog.dismiss();
			String json = (String) msg.obj;
			switch (msg.what) {
//			case ProfitApplication.DATA_SUC:
//				if (json.equals("true")) {
//					Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
//					service.save("", "");
//					finish();
//					Intent intent = new Intent(UpdatePwdActivity.this, LoginActivity.class);
//					startActivity(intent);
//				} else {
//					Toast.makeText(getApplicationContext(), "修改失败!", Toast.LENGTH_SHORT).show();
//				}
//				break;

			case ProfitApplication.SERVER_FAILED:
				Toast.makeText(getApplicationContext(), "服务器连接失败!", Toast.LENGTH_SHORT).show();
				break;


				case ProfitApplication.DATA_FAILED://获取数据失败
					String tsMes = (String) msg.obj;
					Toast.makeText(getApplicationContext(), tsMes, Toast.LENGTH_SHORT).show();
					break;

				case ProfitApplication.DATA_SUC:
					Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
					service.save("", "");
					finish();
					Intent intent = new Intent(UpdatePwdActivity.this, LoginActivity.class);
					startActivity(intent);
					break;
				case ProfitApplication.NO_DATE://
					Toast.makeText(getApplicationContext(), "暂无数据", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		setContentView(R.layout.activity_update_pwd);
		et_changePsw_new = (EditText) findViewById(R.id.et_changePsw_new);
		et_changePsw_old = (EditText) findViewById(R.id.et_changePsw_old);
		btUpdatePwd = (Button) findViewById(R.id.bt_yes);
		btUpdatePwd.setOnClickListener(new UpdatePwdListener());
		progressDialog = CProgressDialog.createLoadingDialog(this);
		service = new SharedPreferencesUtils(this);
		loadData();
	}

	class UpdatePwdListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			changePsw(v);
		}
	}

	public void changePsw(View v) {
		password = et_changePsw_old.getText().toString();
		newPassword = et_changePsw_new.getText().toString();
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(this, R.string.please_password, Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(newPassword)) {
			Toast.makeText(this, R.string.new_password_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		if (et_changePsw_old.getText().toString().endsWith(pswd)) {
			// 如果旧密码正确启动线程更新密码
//			IRISService.doUpdatePwd(user, newPassword, handler);
			upDatePwd(password, newPassword, handler);
			progressDialog.show();
		} else {
			Toast.makeText(UpdatePwdActivity.this, "请输入正确密码", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 跟新密码
	 * @param
	 * @param newPassword
	 * @param handler
	 */
	private void upDatePwd(String pass, String newPassword, Handler handler) {
		ProfitApplication.profitNetService.upDatePwd(pass,newPassword,handler);
	}

	public void back(View view) {
		UpdatePwdActivity.this.finish();
	}

	/**
	 * 加载用户保存的本地数据，若为空则返回false
	 * 
	 * @return
	 */
	public boolean loadData() {
		Map<String, String> params1 = service.getPreferences();
		user = params1.get("username");
		pswd = params1.get("password");
		if (user.equals("") || pswd.equals("")) {
			return false;
		} else {
			user = CodeUtils.decode(user);
			pswd = CodeUtils.decode(pswd);
			return true;
		}
	}
}
