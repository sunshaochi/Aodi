package com.iris.cada.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.adapter.ImportRecordAdapter;
import com.iris.cada.entity.Check;
import com.iris.cada.entity.IViewInputHistory;
import com.iris.cada.entity.IviewDiagnosisProductBean;
import com.iris.cada.net.ProfitNetService;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.view.CProgressDialog;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.cada.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/*
 * 导入记录 activity
 */
public class ImportRecordActivity extends Activity implements OnClickListener {
	private ImageView title_back;
	private ListView record_lv;
	private List<IViewInputHistory> list=new ArrayList<IViewInputHistory>();
	protected Dialog progressDialog;
	private AddTodoPickerViewUtils addTodoPickerViewUtils;
	private TextView tv_title;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_import_record);
		initView();
		bindata();
	}

	private void bindata() {
		title_back.setOnClickListener(this);
	}

	private void initView() {
		title_back = (ImageView) findViewById(R.id.title_back);
		record_lv = (ListView) findViewById(R.id.fragment_import_record_lv);
		tv_title=(TextView) findViewById(R.id.fragment_tv_title);
		//record_lv.setAdapter(new ImportRecordAdapter(this, list));
		getInfor();
		if (!TextUtils.isEmpty(ProfitApplication.loginBackInfo.getJXSName())) {
			tv_title.setText(ProfitApplication.loginBackInfo.getJXSName());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;
		}
	}

	public void getInfor() {
		progressDialog = CProgressDialog.createLoadingDialog(this);
		progressDialog.show();
		ProfitApplication.profitNetService.getIViewInputHistory(handler);
	}

	Handler handler = new MyHandler() {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
			case ProfitApplication.DATA_FAILED:
				ToastUtils.showMyToast(ImportRecordActivity.this, getString(R.string.error_data));
			case ProfitApplication.DATA_SUC:
				dealChannelInfo(msg);
				break;
			case ProfitApplication.SERVER_FAILED:
				ToastUtils.showMyToast(ImportRecordActivity.this, getString(R.string.error_net));
				break;
			}
		}
	};
	private void dealChannelInfo(Message msg) {
		if (null!=list) {
			list.clear();
		}
		try {
			String strMsg = (String) msg.obj;
			list = new Gson().fromJson(strMsg,
					new TypeToken<ArrayList<IViewInputHistory>>() {
			}.getType());
			showOperativeInfo(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showOperativeInfo(List<IViewInputHistory> list2) {
		if (null!=list2) {
			record_lv.setAdapter(new ImportRecordAdapter(this, list2));
		}
	}
}
