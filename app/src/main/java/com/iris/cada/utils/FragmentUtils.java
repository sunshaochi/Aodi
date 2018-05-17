package com.iris.cada.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentUtils {
	
	public static void FragmentShowHide(int layoutId, Fragment fromFragment, Fragment toFragment, FragmentManager fm) {

		FragmentTransaction ft = fm.beginTransaction();
		if (!toFragment.isAdded()) {
			ft.add(layoutId, toFragment).commit();
		} else {
			ft.hide(fromFragment).show(toFragment).commit();
		}
	}

}
