package com.iris.cada.utils;

import java.util.Calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

public class CKDateWheelHelper {
	Context context;
	ViewGroup viewGroup;
	public CKDateWheelHelper(Context context,ViewGroup viewGroup) {
		this.context = context;
		this.viewGroup = viewGroup;
	}
	
	public void initWheelView(final WheelView year, final WheelView month, final WheelView day) {
		Calendar calendar = Calendar.getInstance();

		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(year, month, day);
			}
		};
		int curMonth = calendar.get(Calendar.MONTH);
		DateNumericAdapter monAdapter = new DateNumericAdapter(context, 1, 12, curMonth);
		month.setViewAdapter(monAdapter);
		month.setCyclic(true);
		month.setCurrentItem(curMonth);
		month.addChangingListener(listener);

		int curYear = calendar.get(Calendar.YEAR);
		DateNumericAdapter yearAdapter = new DateNumericAdapter(context, 2000, curYear, curYear);
		year.setViewAdapter(yearAdapter);
		year.setCurrentItem(10);
		year.addChangingListener(listener);
		updateDays(year, month, day);
		day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
	}

	public String getWheelText(WheelView wheel) {
		DateNumericAdapter adapter = (DateNumericAdapter) wheel.getViewAdapter();
		return (String) adapter.getItemText(wheel.getCurrentItem());
	}
	
	public void dismiss(){
		viewGroup.setVisibility(View.GONE);
	}
	public void visible(){
		viewGroup.setVisibility(View.VISIBLE);
	}
	/**
	 * Updates day wheel. Sets max days according to selected month and year
	 */
	private void updateDays(WheelView year, WheelView month, WheelView day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		DateNumericAdapter dayAdapter = new DateNumericAdapter(context, 1, maxDays, maxDays - 1);
		day.setViewAdapter(dayAdapter);
		day.setCyclic(true);
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
	}

	/**
	 * Adapter for numeric wheels. Highlights the current value.
	 */
	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		@SuppressWarnings("unused")
		int currentItem;
		// Index of item to be highlighted
		@SuppressWarnings("unused")
		int currentValue;

		/**
		 * Constructor
		 */
		public DateNumericAdapter(Context context, int minValue, int maxValue, int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
		}

		// @Override
		// protected void configureTextView(TextView view) {//����ѡ�е���ʽ
		// super.configureTextView(view);
		// if (currentItem == currentValue) {
		// view.setTextColor(0xFF00FFF0);
		// }
		// view.setTypeface(Typeface.SANS_SERIF);
		// }

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}
}
