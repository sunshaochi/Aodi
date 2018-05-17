package com.iris.cada.activity;

import com.iris.foundation.view.CKTitleView;
import com.iris.cada.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpImageActivity extends Activity {
	private ImageView iv;
	private CKTitleView titleView;

	// 1开头的数字为集团标识 2开头为区域标识,个位数为经销商标识
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		setContentView(R.layout.activity_help_image);
		iv = (ImageView) findViewById(R.id.iv_help_content);
		titleView = (CKTitleView) findViewById(R.id.title);
		titleView.getLeftButoon().setVisibility(View.VISIBLE);
		titleView.getRightButoon().setVisibility(View.INVISIBLE);
		titleView.getLeftButoon().setBackgroundResource(R.drawable.icon_back);
		titleView.getLeftButoon().setOnClickListener(new BackOnClickListener());
		TextView title = new TextView(this);
		title.setTextSize(20);
		title.setTextColor(getResources().getColor(R.color.white));
		titleView.getLinearLayout().addView(title);
		Intent intent = getIntent();

		int x = (Integer) intent.getExtras().get("image");
		switch (x) {
		case 0:
			iv.setImageResource(R.drawable.shsy);
			title.setText("首页");
			break;
		case 1:
			iv.setImageResource(R.drawable.help2);
			title.setText("概览");
			break;
		case 2:
			iv.setImageResource(R.drawable.help3);
			title.setText("报表");
			break;
		case 3:
			iv.setImageResource(R.drawable.help4);
			title.setText("监控");
			break;
		case 4:
			iv.setImageResource(R.drawable.help5);
			title.setText("诊断");
			break;
		case 5:
			iv.setImageResource(R.drawable.shset);
			title.setText("设置");
			break;
		}
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
			finish();
		}
	}
}
