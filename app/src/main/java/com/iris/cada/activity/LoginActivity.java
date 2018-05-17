package com.iris.cada.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.iris.cada.AppManager;
import com.iris.cada.ProfitApplication;

import com.iris.cada.entity.UserInfoBean;
import com.iris.cada.entity.UserSelectEntity;


import com.iris.cada.utils.CodeUtils;

import com.iris.cada.utils.SharedPreferencesUtils;

import com.iris.cada.view.CProgressDialog;
import com.iris.cada.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushMessage;

/**
 * 登录
 * 
 * @author jiahaoGuo
 * @version 2015-11-12 11:01:37
 */
/**
 * @author jiahaoGuo
 * 
 */
public class LoginActivity extends Activity implements OnClickListener {
	// view
	private EditText etUserName;
	private EditText etPassword;
	private Button btnLogin;
	private Dialog progressDialog;
	private SharedPreferencesUtils service;
	Context context = LoginActivity.this;
	private String username;
	private String password;
	private UserSelectEntity selectEntity;
	private TextView change_pwd;
	private TextView tv_phone;//服务热线
	private JPushMessage jpush;
	public final int LOGIN_SUC = 0;
	String FILE_TARGET = "http://120.26.66.145:7522/IAccountUpdate/i-view.apk";
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			super.handleMessage(msg);
			switch (msg.what) {
				case ProfitApplication.SERVER_FAILED://服务器链接失败
					Toast.makeText(context, R.string.server_failed, Toast.LENGTH_SHORT).show();
					break;

				case ProfitApplication.DATA_FAILED://获取数据失败
					String tsMes = (String) msg.obj;
					Toast.makeText(context, tsMes, Toast.LENGTH_SHORT).show();
					break;

				case ProfitApplication.DATA_SUC:
					dealLoginBack(msg);//保存数据
					break;
				case ProfitApplication.NO_DATE://
					Toast.makeText(context, "暂无数据", Toast.LENGTH_SHORT).show();
					break;

				case LOGIN_SUC:
					if (!loadSelecData()) {// 如果本地没有保存上次的选择,则初始化
						selectEntity = new UserSelectEntity();
					}
					ProfitApplication.userSelectEntity = selectEntity;
					service.setSelectInfo(ProfitApplication.userSelectEntity);
					service.save(CodeUtils.encode(username), CodeUtils.encode(password));

					//后来加的把原来的默认车型模式改为顾问模式，默认每次进来重新加载时间所以都设置为null
					service.setMode(true);//默认设置为顾问模式，刚开始是车型模式
					ProfitApplication.isConsultantMode=true;
					ProfitApplication.mStartDate=null;
					ProfitApplication.mEndDate=null;

					Intent intent1 = new Intent(context,SeparateAct.class);
					intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intent1);
					finish();
					break;




			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);
		requestWritePermission();
		AppManager.getAppManager().addActivity(LoginActivity.this);
		tv_phone=(TextView) findViewById(R.id.tv_phone);//服务热线
		tv_phone.setOnClickListener(this);
		etUserName = (EditText) findViewById(R.id.et_user_name);
		etPassword = (EditText) findViewById(R.id.et_user_pwd);
		change_pwd = (TextView) findViewById(R.id.activity_login_pwd);

		service = new SharedPreferencesUtils(context);

		btnLogin = (Button) findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(this);
		change_pwd.setOnClickListener(this);
		// 判断用户是否登录过
		if (loadData()) {
			etUserName.setText(username);
			etPassword.setText(password);

			exetuceRequest();

		}
	}




	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			// 登录
			username = etUserName.getText().toString();
			password = etPassword.getText().toString();
			if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
				Toast.makeText(context, R.string.username_empty, Toast.LENGTH_SHORT).show();
			} else {
				exetuceRequest();
			}
			break;
		case R.id.activity_login_pwd:
			startActivity(new Intent(this, UpdatePwdActivity.class));
			break;
			
		case R.id.tv_phone:
			phoneCall();//拨打电话
			break;
		}
	}
 
	private void phoneCall() {
		// TODO Auto-generated method stub
		
			Intent phoneIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" +"4007280250"));
			phoneIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(phoneIntent);
	}




	public void exetuceRequest() {
		progressDialog = CProgressDialog.createLoadingDialog(context);
		progressDialog.show();
		ProfitApplication.profitNetService.doLogin(username,password,handler);

	}





	/**
	 * 加载用户保存的本地数据，若为空则返回false
	 * 
	 * @return
	 */
	public boolean loadData() {
		Map<String, String> params1 = service.getPreferences();
		username = params1.get("username");
		password = params1.get("password");
		if (username.equals("") || password.equals("")) {
			return false;
		} else {
			username = CodeUtils.decode(username);
			password = CodeUtils.decode(password);
			return true;
		}
	}

	/**
	 * 加载用户保存的本地数据，若为空则返回false
	 * 
	 * @return
	 */
	public boolean loadSelecData() {
		selectEntity = service.getSelectInfo();
		if (selectEntity == null) {
			return false;
		} else {
			return true;
		}
	}
	
	
	private void requestWritePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
                return;
            }
        }
    }

	private void dealLoginBack(Message msg) {

		try {
			String strMsg = (String) msg.obj;

			Gson gson = new Gson();
			JSONObject json = new JSONObject(strMsg);
			JSONObject obj = json.getJSONObject("obj");
			UserInfoBean user = gson.fromJson(obj.toString(), UserInfoBean.class);
			ProfitApplication.loginBackInfo = user.getUserWeb();
			List<String>list=new ArrayList<>();
			List<String>carcodelist=new ArrayList<>();
			if(ProfitApplication.loginBackInfo.getCarModelList().size()>0&&ProfitApplication.loginBackInfo.getCarModelList()!=null){
				for (int i=0;i<ProfitApplication.loginBackInfo.getCarModelList().size();i++){
					list.add(ProfitApplication.loginBackInfo.getCarModelList().get(i).getName());
					carcodelist.add(ProfitApplication.loginBackInfo.getCarModelList().get(i).getCode());
				}
			}
			ProfitApplication.loginBackInfo.setModels(list);
			ProfitApplication.loginBackInfo.setCarCodeList(carcodelist);//设置车型编码

			Message msg5 = handler.obtainMessage(LOGIN_SUC);
			handler.sendMessage(msg5);


		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	 
}
