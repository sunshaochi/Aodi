package com.iris.cada;

import java.lang.ref.WeakReference;
import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;

public class MyHandler extends Handler {
	@SuppressWarnings("unused")
	private WeakReference<Activity> activity;
	@SuppressWarnings("unused")
	private WeakReference<Fragment> fragment;

	public MyHandler() {
		super();
	}

	public MyHandler(Activity activity) {
		this.activity = new WeakReference<Activity>(activity);
	}

	public MyHandler(Fragment fragment) {
		this.fragment = new WeakReference<Fragment>(fragment);
	}

}
