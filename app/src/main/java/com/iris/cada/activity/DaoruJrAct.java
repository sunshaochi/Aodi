package com.iris.cada.activity;

import com.iris.cada.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DaoruJrAct extends FragmentActivity implements OnClickListener {
	private RelativeLayout rl_sx, rl_sh;// 销售售后
	private ImageView iv_finsh;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		if (arg0 != null) {
			arg0.putParcelable("android:support:fragments", null);
		}
		super.onCreate(arg0);
		setContentView(R.layout.act_daorujr);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		rl_sx = (RelativeLayout) findViewById(R.id.rl_xs);
		rl_sh = (RelativeLayout) findViewById(R.id.rl_sh);
		iv_finsh=(ImageView) findViewById(R.id.iv_finsh);
		iv_finsh.setOnClickListener(this);
		rl_sx.setOnClickListener(this);
		rl_sh.setOnClickListener(this);
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_title.setText("导入记录");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_xs:
			startActivity(new Intent(DaoruJrAct.this, ImportRecordActivity.class));
			break;

		case R.id.rl_sh:
			startActivity(new Intent(DaoruJrAct.this, DaoruFwAct.class));
			break;
			
		case R.id.iv_finsh:
            finish();
			break;

		}
	}

}
