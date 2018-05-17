package com.iris.cada.activity;

import com.iris.foundation.view.CKTitleView;
import com.iris.foundation.view.CKTitleView.TitleClick;
import com.iris.cada.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShHelpAct extends NewBaseAct {
	@ViewInject(R.id.title)
	private CKTitleView titleview;

	@Override
	public void setLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_help_document);
	}

	@Override
	public void init(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		titleview.getLeftButoon().setVisibility(View.VISIBLE);
		titleview.getRightButoon().setVisibility(View.INVISIBLE);
		titleview.getLeftButoon().setBackgroundResource(R.drawable.icon_back);
		TextView title = new TextView(this);
		title.setText("帮助文档-售后");
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
	}

	@OnClick({ R.id.bt_helptext_data, R.id.bt_helptext_report, R.id.bt_helptext_funnel, R.id.bt_help_porfit,
			R.id.bt_helptext_model, R.id.bt_help_setting, R.id.bt_help_helpDocument })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.bt_helptext_data:
			Bundle bundle=new Bundle();
			bundle.putString("key", "0");
            openActivity(ShimageAct.class,bundle);
			break;

		case R.id.bt_helptext_report:
			Bundle bundle1=new Bundle();
			bundle1.putString("key", "1");
            openActivity(ShimageAct.class,bundle1);
			break;
			
		case R.id.bt_helptext_funnel:
			Bundle bundle2=new Bundle();
			bundle2.putString("key", "2");
            openActivity(ShimageAct.class,bundle2);
			break;
			
		case R.id.bt_help_porfit:
			Bundle bundle3=new Bundle();
			bundle3.putString("key", "3");
            openActivity(ShimageAct.class,bundle3);
			break;
			
		case R.id.bt_helptext_model:
			Bundle bundle4=new Bundle();
			bundle4.putString("key", "4");
            openActivity(ShimageAct.class,bundle4);
			break;
		case R.id.bt_help_setting:
			Bundle bundle5=new Bundle();
			bundle5.putString("key", "5");
            openActivity(ShimageAct.class,bundle5);
			break;
			
		case R.id.bt_help_helpDocument:
//			Toast.makeText(ShHelpAct.this, "点击了",Toast.LENGTH_SHORT).show();
            openActivity(ShKpiAct.class);
			break;
		}
	}

}
