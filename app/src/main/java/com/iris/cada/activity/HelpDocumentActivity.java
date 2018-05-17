package com.iris.cada.activity;

import com.iris.cada.ProfitApplication;
import com.iris.cada.entity.LoginBackInfo;
import com.iris.cada.entity.SalesInfo;
import com.iris.foundation.view.CKTitleView;
import com.iris.foundation.view.CKTitleView.TitleClick;
import com.iris.cada.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class HelpDocumentActivity extends Activity {
	private Button bt_helptext_data, bt_helptext_report, bt_helptext_funnel, bt_help_porfit, bt_helptext_model,
			bt_help_setting, bt_weather;
	@SuppressWarnings("unused")
	private CKTitleView titleView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		setContentView(R.layout.activity_help_document);
		titleView = (CKTitleView) findViewById(R.id.title);
		titleView.getLeftButoon().setVisibility(View.VISIBLE);
		titleView.getRightButoon().setVisibility(View.INVISIBLE);
		titleView.getLeftButoon().setBackgroundResource(R.drawable.icon_back);
		TextView title = new TextView(this);
		title.setText("帮助文档-销售");
		title.setTextSize(20);
		title.setTextColor(getResources().getColor(R.color.white));
		titleView.getLinearLayout().addView(title);

		bt_helptext_data = (Button) findViewById(R.id.bt_helptext_data);
		bt_helptext_report = (Button) findViewById(R.id.bt_helptext_report);
		bt_helptext_funnel = (Button) findViewById(R.id.bt_helptext_funnel);
		bt_help_porfit = (Button) findViewById(R.id.bt_help_porfit);
		bt_helptext_model = (Button) findViewById(R.id.bt_helptext_model);
		bt_help_setting = (Button) findViewById(R.id.bt_help_setting);
		bt_weather = (Button) findViewById(R.id.bt_help_helpDocument);

		bt_helptext_data.setOnClickListener(new onclick());
		bt_helptext_report.setOnClickListener(new onclick());
		bt_helptext_funnel.setOnClickListener(new onclick());
		bt_help_porfit.setOnClickListener(new onclick());
		bt_helptext_model.setOnClickListener(new onclick());
		bt_help_setting.setOnClickListener(new onclick());
		bt_weather.setOnClickListener(new onclick());
		titleView.setTitleClick(new TitleClick() {

			@Override
			public void btLeftClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}

			@Override
			public void btRightClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	class onclick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_helptext_data:
				// 监控图片分为经销商和其他两种
				Intent intent = new Intent(HelpDocumentActivity.this, HelpImageActivity.class);
				intent.putExtra("image", 0);
				HelpDocumentActivity.this.startActivity(intent);
				break;
			case R.id.bt_helptext_report:
				// 报表图片分为经销商,集团,区域三种
				Intent intent1 = new Intent(HelpDocumentActivity.this, HelpImageActivity.class);
				intent1.putExtra("image", 1);
				HelpDocumentActivity.this.startActivity(intent1);
				break;
			case R.id.bt_helptext_funnel:
				// week图片分为经销商,集团,区域三种
				Intent intent2 = new Intent(HelpDocumentActivity.this, HelpImageActivity.class);
				intent2.putExtra("image", 2);
				HelpDocumentActivity.this.startActivity(intent2);
				break;
			case R.id.bt_help_porfit:
				// funnel图片分为经销商和其他两种
				Intent intent3 = new Intent(HelpDocumentActivity.this, HelpImageActivity.class);
				intent3.putExtra("image", 3);
				HelpDocumentActivity.this.startActivity(intent3);
				break;
			case R.id.bt_helptext_model:
				// setting图片分为经销商和其他两种
				Intent intent4 = new Intent(HelpDocumentActivity.this, HelpImageActivity.class);
				intent4.putExtra("image", 4);
				HelpDocumentActivity.this.startActivity(intent4);
				break;
			case R.id.bt_help_setting:
				// setting图片分为经销商和其他两种
				Intent intent5 = new Intent(HelpDocumentActivity.this, HelpImageActivity.class);
				intent5.putExtra("image", 5);
				HelpDocumentActivity.this.startActivity(intent5);
				break;
			case R.id.bt_help_helpDocument:
				startActivity(new Intent(HelpDocumentActivity.this, WeatherPhenomenonAcivity.class));
				break;
			}
		}
	}
}
