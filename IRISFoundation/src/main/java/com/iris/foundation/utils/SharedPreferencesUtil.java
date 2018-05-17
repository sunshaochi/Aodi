package com.iris.foundation.utils;

import java.util.Set;

import com.iris.foundation.activity.IRISApplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences工具，用于读写SharedPreferences
 */
public class SharedPreferencesUtil {
	private static final String DEFAULT = "DEFAULT";

	public static void putString(String key, String value) {
		putString(DEFAULT, key, value);
	}

	public static  void putString(String name, String key, String value) {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}

	public static   void putStringSet(String name, String key, Set<String> value) {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
		sp.edit().putStringSet(key, value).commit();
	}

	public  static  void putBoolean(String name, String key, boolean value) {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}

	public  static  void putLong(String name, String key, long value) {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
		sp.edit().putLong(key, value).commit();
	}

	public  static  void putInt(String name, String key, int value) {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}

	public  static  void putFloat(String name, String key, float value) {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
		sp.edit().putFloat(key, value).commit();
	}

	public  static  String getString(String name, String key, String defValue) {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}

	public static   String getString(String key, String defValue) {
		return getString(DEFAULT, key, defValue);
	}

	public  static  Set<String> getStringSet(String name, String key, Set<String> defValue) {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getStringSet(key, defValue);
	}

	public static   boolean getBoolean(String name, String key, boolean defValue) {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}

	public  static  int getInt(String name, String key, int defValue) {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getInt(key, defValue);
	}

	public  static  long getLong(String name, String key, long defValue) {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getLong(key, defValue);
	}

	public static   float getFloat(String name, String key, float defValue) {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getFloat(key, defValue);
	}

	public static   Boolean clear() {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(DEFAULT, Context.MODE_PRIVATE);
		return sp.edit().clear().commit();
	}

	public static   Boolean clear(String name) {
		SharedPreferences sp = IRISApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.edit().clear().commit();
	}
}
