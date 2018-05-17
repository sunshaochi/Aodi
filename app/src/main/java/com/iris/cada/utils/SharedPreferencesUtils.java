package com.iris.cada.utils;

import java.util.HashMap;
import java.util.Map;

import com.iris.cada.entity.UserSelectEntity;
import com.iris.cada.fragment.ExhibitionFragment;
import com.iris.cada.fragment.ProfitFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class SharedPreferencesUtils {

	public Context context;
	private SharedPreferences sp;

	public SharedPreferencesUtils(Context context) {
		this.context = context;
	}

	/**
	 * 保存登录参数值
	 */
	public void save(String username, String password) {
		sp = context.getSharedPreferences("share", 0);
		Editor editor = sp.edit();
		editor.putString("username", username);
		editor.putString("password", password);
		editor.commit();
	}

	/**
	 * 获取登录的数据
	 * 
	 * @return
	 */
	public Map<String, String> getPreferences() {
		Map<String, String> params = new HashMap<String, String>();
		sp = context.getSharedPreferences("share", 0);
		params.put("username", sp.getString("username", ""));
		params.put("password", sp.getString("password", ""));
		return params;
	}

	/**
	 * 保存所选模式: true：顾问模式 false：车型模式
	 * 
	 * @param isConsulantMode
	 */
	public void setMode(boolean isConsulantMode) {
		sp = context.getSharedPreferences("share", 0);
		Editor editor = sp.edit();
		editor.putBoolean("mode", isConsulantMode);
		editor.commit();

	}

	/**
	 * 保存所选模式 0：顾问模式 1：车型模式
	 * 
	 * @param isConsulantMode
	 */
	public boolean getMode() {
		sp = context.getSharedPreferences("share", 0);
		return sp.getBoolean("mode", false);
	}

	public void setSelectInfo(UserSelectEntity userSelectEntity) {
		sp = context.getSharedPreferences("saveSelect", 0);
		Editor editor = sp.edit();
		// userSelectEntity.getCarModel();
		editor.putString("carModel", "");
		editor.commit();
	}

	/**
	 * 获取用户选择的选区及对比
	 * 
	 */
	public UserSelectEntity getSelectInfo() {
		sp = context.getSharedPreferences("saveSelect", 0);
		String carModel = sp.getString("carModel", "");
		if (TextUtils.isEmpty(carModel)) {
			return null;
		}
		return new UserSelectEntity(carModel);
	}

	// 登出
	@SuppressLint("SdCardPath")
	public void clearData() {
		SharedPreferences sp = context.getSharedPreferences("share", 0);
		SharedPreferences sp1 = context.getSharedPreferences("saveSelect", 0);
		sp.edit().clear().commit();
		sp1.edit().clear().commit();
		ExhibitionFragment.IS_DAY = true;
		ProfitFragment.IS_DAY = true;
	}
}
