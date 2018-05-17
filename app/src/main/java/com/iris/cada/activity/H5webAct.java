package com.iris.cada.activity;

import com.iris.cada.activity.HelpDocumentActivity.onclick;
import com.iris.cada.utils.WebUtils;
import com.iris.cada.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class H5webAct extends NewBaseAct{
	@ViewInject(R.id.tv_title)
	private TextView tv_title;
	@ViewInject(R.id.webpb)
	private ProgressBar pb;	
	@ViewInject(R.id.wb_view)
	private WebView wb_view;
	
	private String url;
	private String name;

	@Override
	public void setLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.act_h5web);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		url=getIntent().getStringExtra("url");
		name=getIntent().getStringExtra("name");
		tv_title.setText(name);
		WebUtils.WebSet(url, wb_view, pb);
	}
	
	@OnClick({R.id.iv_finsh})
	public void OnClick(View v){
		switch (v.getId()) {
		case R.id.iv_finsh:
			finish();
			break;
		
		}
	}

}
