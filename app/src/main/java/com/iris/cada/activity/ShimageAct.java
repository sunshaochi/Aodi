package com.iris.cada.activity;

import com.iris.foundation.view.CKTitleView;
import com.iris.foundation.view.CKTitleView.TitleClick;
import com.iris.cada.R;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ShimageAct extends NewBaseAct{
	@ViewInject(R.id.title)
	private CKTitleView titleview;
	@ViewInject(R.id.iv_help_content)
	private ImageView iv;
	private String key;
	

	@Override
	public void setLayout() {
		setContentView(R.layout.activity_help_image);
		
	}

	@Override
	public void init(Bundle savedInstanceState) {
		titleview.getLeftButoon().setVisibility(View.VISIBLE);
		titleview.getRightButoon().setVisibility(View.INVISIBLE);
		titleview.getLeftButoon().setBackgroundResource(R.drawable.icon_back);
		TextView title = new TextView(this);		
		title.setTextSize(20);
		title.setTextColor(getResources().getColor(R.color.white));
		titleview.getLinearLayout().addView(title);
		titleview.setTitleClick(new TitleClick() {

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
		
		Bundle bundle=getIntent().getExtras();
		key=bundle.getString("key");
		if(key.equals("5")){//设置界面
			title.setText("设置");
			iv.setImageResource(R.drawable.shset);
		}else if(key.equals("0")){//首页
			title.setText("首页");
			iv.setImageResource(R.drawable.shsy);
		}else if(key.equals("1")){//概览
			title.setText("概览");
			iv.setImageResource(R.drawable.shgl);
		}else if(key.equals("2")){//报表
			title.setText("报表");
			iv.setImageResource(R.drawable.shbb);
		}else if(key.equals("3")){//监控
			title.setText("监控");
			iv.setImageResource(R.drawable.shjk);
		}else if(key.equals("4")){//争端
			title.setText("诊断");
			iv.setImageResource(R.drawable.shzd);
		}
		
	}

}
