package com.iris.cada.newfragment;

import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.ActivityChangePassWord;
import com.iris.cada.activity.ActivityIviewMyinfor;
import com.iris.cada.activity.HelpDocumentActivity;
import com.iris.cada.activity.ImportRecordActivity;
import com.iris.cada.activity.LoginActivity;
import com.iris.cada.activity.WeatherPhenomenonAcivity;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.utils.SharedPreferencesUtils;
import com.iris.cada.R;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuFragment extends BaseFragment implements View.OnClickListener {
	private LinearLayout change_password;
	private LinearLayout help_word;
	private LinearLayout my_infor;
	private LinearLayout fragment_setting_change_exit, fragment_setting_pople_text_password3;
	private LinearLayout import_record;
	private ImageView cars_model_switch, adviser_model_switch;
	private LinearLayout ll_kf;
	private LinearLayout ll_cjwt;
	private View view_line;
	private TextView tv_cjwt;
	private SharedPreferencesUtils mSharedPreferencesUtils = null;

	private void initData() {
		mSharedPreferencesUtils = new SharedPreferencesUtils(getContext());

		ProfitApplication.isConsultantMode = mSharedPreferencesUtils.getMode();
		if (ProfitApplication.isConsultantMode) {
			adviser_model_switch.setImageResource(R.drawable.butn_open);
			cars_model_switch.setImageResource(R.drawable.butn_close);
		} else {
			adviser_model_switch.setImageResource(R.drawable.butn_close);
			cars_model_switch.setImageResource(R.drawable.butn_open);
		}
	}

	private void initView(View paramView) {
		my_infor = ((LinearLayout) paramView.findViewById(R.id.fragment_setting_my_infor));
		change_password = ((LinearLayout) paramView.findViewById(R.id.fragment_setting_change_password));
		help_word = ((LinearLayout) paramView.findViewById(R.id.fragment_setting_change_password3));
		cars_model_switch = (ImageView) paramView.findViewById(R.id.fragment_setting_cars_model_switch);
		adviser_model_switch = (ImageView) paramView.findViewById(R.id.fragment_setting_adviser_model_switch);
		fragment_setting_change_exit = (LinearLayout) paramView.findViewById(R.id.fragment_setting_change_exit);
		import_record = (LinearLayout) paramView.findViewById(R.id.fragment_setting_import_record);
		fragment_setting_pople_text_password3 = (LinearLayout) paramView
				.findViewById(R.id.fragment_setting_pople_text_password3);
		my_infor.setOnClickListener(this);
		change_password.setOnClickListener(this);
		cars_model_switch.setOnClickListener(this);
		adviser_model_switch.setOnClickListener(this);
		help_word.setOnClickListener(this);
		fragment_setting_change_exit.setOnClickListener(this);
		import_record.setOnClickListener(this);
		fragment_setting_pople_text_password3.setOnClickListener(this);
		ll_kf=(LinearLayout) paramView.findViewById(R.id.ll_kf);
		ll_kf.setOnClickListener(this);
		ll_cjwt=(LinearLayout) paramView.findViewById(R.id.ll_cjwt);
		ll_cjwt.setOnClickListener(this);
		view_line=paramView.findViewById(R.id.view_line);
		tv_cjwt=(TextView) paramView.findViewById(R.id.tv_cjwt);
	    if(ProfitApplication.loginBackInfo.getIsShowQa().equals("0")){
	    	ll_cjwt.setVisibility(View.GONE);
	    	tv_cjwt.setText("");
	    	view_line.setVisibility(View.GONE);
	    }else {
	    	ll_cjwt.setVisibility(View.VISIBLE);
	    	tv_cjwt.setText("Q&A");
	    	view_line.setVisibility(View.VISIBLE);
	    }
	}
	

	boolean ischanck = true;

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.fragment_setting_cars_model_switch:
			if (ProfitApplication.isConsultantMode) {
				adviser_model_switch.setImageResource(R.drawable.butn_close);
				cars_model_switch.setImageResource(R.drawable.butn_open);

				ProfitApplication.isConsultantMode = false;
				mSharedPreferencesUtils.setMode(false);

				Intent intent = new Intent(ProfitApplication.BROADCAST_MESSAGE);
				LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
			} else {

			}
			break;
		case R.id.fragment_setting_adviser_model_switch:
			if (!ProfitApplication.isConsultantMode) {
				adviser_model_switch.setImageResource(R.drawable.butn_open);
				cars_model_switch.setImageResource(R.drawable.butn_close);

				ProfitApplication.isConsultantMode = true;
				mSharedPreferencesUtils.setMode(true);

				Intent intent = new Intent(ProfitApplication.BROADCAST_MESSAGE);
				LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
			} else {

			}
			break;
		case R.id.fragment_setting_my_infor:
			startActivity(new Intent(getActivity(), ActivityIviewMyinfor.class));
			break;
		case R.id.fragment_setting_change_password:
			startActivity(new Intent(getActivity(), ActivityChangePassWord.class));
			break;
		case R.id.fragment_setting_change_password3:
			Intent help = new Intent(getActivity(), HelpDocumentActivity.class);
			startActivity(help);
			break;
		case R.id.fragment_setting_change_exit:
			service.clearData();
			getActivity().finish();
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.fragment_setting_import_record:
			startActivity(new Intent(getActivity(), ImportRecordActivity.class));
			break;
		case R.id.fragment_setting_pople_text_password3:
			
			break;
		case R.id.ll_kf:
			phoneCall();//拨打电话
			break;
		case R.id.ll_cjwt:
			Toast.makeText(getActivity(), "点了常见", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	private void phoneCall() {
		// TODO Auto-generated method stub
		Intent phoneIntent = new Intent("android.intent.action.CALL",Uri.parse("tel:" +"4007280250"));
		startActivity(phoneIntent);
	}

	public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.layout_menu, paramViewGroup, false);
		initView(localView);
		initData();
		return localView;
	}

	public void refresh() {
	}

	// private ModeChangeListener modeChangeListener;
	// public interface ModeChangeListener {
	// public void updateUI();
	// }
	// public void setModeChangeListener(ModeChangeListener modeChangeListener){
	// this.modeChangeListener = modeChangeListener;
	// }

}

/*
 * Location:
 * C:\Users\Administrator\Desktop\反编译\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name: com.iris.cada.newfragment.MenuFragment JD-Core Version:
 * 0.6.2
 */