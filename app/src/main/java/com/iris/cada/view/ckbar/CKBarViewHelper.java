package com.iris.cada.view.ckbar;

import android.content.Context;
import android.util.TypedValue;

public class CKBarViewHelper {
	public static int dp2px(int dp, Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}

	public static int sp2px(int sp, Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
				context.getResources().getDisplayMetrics());
	}

	public static int percent2int(String percent) {
		if (percent.contains("%"))
			percent = percent.replace("%", "");
		return Integer.parseInt(percent);
	}

	public static float percent2float(String percent) {
		if (percent.contains("%"))
			percent = percent.replace("%", "");
		return Float.parseFloat(percent);
	}

}