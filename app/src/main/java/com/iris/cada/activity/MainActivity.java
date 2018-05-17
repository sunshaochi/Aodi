package com.iris.cada.activity;

import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.fragment.DeptFragment;
import com.iris.cada.fragment.DerivedFragment;
import com.iris.cada.fragment.ExhibitionFragment;
import com.iris.cada.fragment.NewCarSalesFragment;
import com.iris.cada.fragment.ProfitFragment;
import com.iris.cada.newfragment.AnySyFra;
import com.iris.cada.newfragment.DiagDetailsFragment;
import com.iris.cada.newfragment.DiagnosisFragment;
import com.iris.cada.newfragment.HomePageFragment;
import com.iris.cada.newfragment.MenuFragment;
import com.iris.cada.newfragment.MonitorFragment;
import com.iris.cada.newfragment.OverviewFragment;
import com.iris.cada.newfragment.ReportFragment;
import com.iris.cada.newfragment.ReportOperateFragment;
import com.iris.cada.newfragment.ReportProfitFragment;
import com.iris.cada.newfragment.SxglFrag;
import com.iris.cada.R;

import com.lidroid.xutils.view.annotation.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import slidingmenu.lib.SlidingMenu;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 主页面
 * 
 * @author jiahaoGuo
 * @version 2015-11-5 11:25:03 1.0
 */
public class MainActivity extends BaseActivity {

	// view
	private RadioGroup rgTab; // 底部选项卡
	private Fragment from; // 记录当前选中的fragment
	private RadioButton rbtn; // 记录切换后的RadioButton
	private FragmentManager fm; // fragment管理者
	private FragmentTransaction ft; // fragment事务
	private Button btnSetting;
	private SlidingMenu slideMenu;	
	// 常量
	public final static int RESULT_FROM_SELECT_ACTIVITY = 222, START_SELECT_ACTIVTY = 221;
	private long mExitTime; // 记录当前准确的时间
	
	private LinearLayout ll_bg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		setContentView(R.layout.activity_main);
		initView();
//		initSlidingMenu();
	}

//	private void initSlidingMenu() {
//		setBehindContentView(R.layout.menu_frame);
//		getSupportFragmentManager().beginTransaction().replace(R.id.fl_menu, new MenuFragment()).commit();
//		this.slideMenu = getSlidingMenu();
//		this.slideMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//		this.slideMenu.setShadowDrawable(R.drawable.shadow);
//		this.slideMenu.setShadowWidthRes(R.dimen.shadow_width);
//		this.slideMenu.setMode(0);
//		this.slideMenu.setTouchModeAbove(0);
//	}

	/**
	 * 初始化各子页面
	 */
	public void initView() {
		btnSetting = (Button) findViewById(R.id.btn_setting);
		btnSetting.setOnClickListener(new OnSettingListener());
		rgTab = (RadioGroup) findViewById(R.id.rg_tab);
		// 获取当前登录用户的信息
		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();
		rgTab.setOnCheckedChangeListener(new OnRadioGroupListener());
		from = new ExhibitionFragment();
//		OverviewFragment overviewFragment = new OverviewFragment();
//		reFragment(overviewFragment);
		AnySyFra anySyFra=new AnySyFra();
		reFragment(anySyFra);

		ll_bg=(LinearLayout) findViewById(R.id.ll_bg);
		ll_bg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll_bg.setVisibility(View.GONE);
			}
		});
		
	}

	/**
	 * 设置按钮监听
	 *
	 */
	private class OnSettingListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainActivity.this, SettingActivity.class);
			startActivityForResult(intent, MainActivity.START_SELECT_ACTIVTY);
		}
	}

	/**
	 * 底部菜单监听
	 * 
	 * @author jiahaoGuo
	 * 
	 */
	private class OnRadioGroupListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {

			switch (checkedId) {
			case R.id.rb_data:
				// 数据
				AnySyFra anySyFra=new AnySyFra();
			    Bundle bundle=new Bundle();
			    bundle.putString("key", "1");
			    anySyFra.setArguments(bundle);
				reFragment(anySyFra);

				break;
			case R.id.rb_report:
				// 报表
				OverviewFragment overviewFragment = new OverviewFragment();
				reFragment(overviewFragment);

				break;
			case R.id.rb_kpi:
				// kpi

				ReportFragment reportFragment = new ReportFragment();
				// DerivedFragment kpi = new DerivedFragment();
				reFragment(reportFragment);

				break;
			case R.id.rb_funnel:
				// 漏斗

				// DeptFragment funnel = new DeptFragment();
				// reFragment(funnel);
				MonitorFragment monitorFragment = new MonitorFragment();
				reFragment(monitorFragment);

				break;
			case R.id.rb_setting:
				// 新车销售

				DiagnosisFragment diagnosisFragment = new DiagnosisFragment();
				// NewCarSalesFragment setting = new NewCarSalesFragment();
				reFragment(diagnosisFragment);
			
				break;
			}
		}

		
	}

	
	/**
	 * 页面的切换
	 * 
	 * @param fragment
	 *            当前要显示的页面
	 */
	public void reFragment(Fragment fragment) {
		ft = fm.beginTransaction();
		// 判断是否被add过
		if (!fragment.isAdded()) {
			// 隐藏当前的fragment，add下一个到Activity中
			ft.hide(from).add(R.id.fl_fragment, fragment).commit();
		} else {
			// 隐藏当前的fragment，显示下一个
			ft.hide(from).show(fragment).commit();
		}
		from = fragment;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_FROM_SELECT_ACTIVITY && requestCode == START_SELECT_ACTIVTY) {
			if (data.getExtras().getBoolean("flag")) {
				if (from instanceof DeptFragment) {
					DeptFragment.flag = true;
				}
//				from.refresh();
			}
		}
	}

	// 两秒之内按两次back键推出程序
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		// 继续执行父类的其他点击事件
		return super.onKeyDown(keyCode, event);
	}
	
	
	
	
}
