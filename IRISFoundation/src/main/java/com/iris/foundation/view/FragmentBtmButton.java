package com.iris.foundation.view;

import com.iris.foundation.utils.DensityUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class FragmentBtmButton extends HorizontalScrollView {
	Context context;
	int[] selectedDrawble;
	int[] notSelectedDrawble;
	String[] tipsTexts;
	String[] tipsBgColors;
	String[] tipsTextColors;
	int btWidth, btHeight;
	ScrollView x;
	TextView[] btArray;
	CKNameView[] ckNameViews;
	RelativeLayout[] relativeLayouts;
	int[] btID;
	LinearLayout ll_container;
	int defSelect;

	public FragmentBtmButton(Context context) {
		super(context);
	}

	public FragmentBtmButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setHorizontalScrollBarEnabled(false);
	}

	public void setData(Context context, int[] selectedDrawble, int[] notSelectedDrawble, int defSelect) {
		this.context = context;
		this.selectedDrawble = selectedDrawble;
		this.notSelectedDrawble = notSelectedDrawble;
		this.defSelect = defSelect;
		btWidth = (int) getBtnWidth();
		btHeight = DensityUtil.dip2px(context, 50);
		btID = new int[selectedDrawble.length];
		tipsTexts = new String[selectedDrawble.length];
		tipsBgColors = new String[selectedDrawble.length];
		tipsTextColors = new String[selectedDrawble.length];
		for (int i = 0; i < selectedDrawble.length; i++) {
			btID[i] = i + 100;
			tipsTexts[i] = "";
			tipsBgColors[i] = "#00ffffff";
			tipsTextColors[i] = "#00ffffff";
		}
		addViewSub();
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if(null==isIndicateShow){
			return;
		}
		if (l == 0) {
			isIndicateShow.isShow(View.INVISIBLE, View.VISIBLE);
		} else {
			if (oldl == 0 || oldl + getWidth() == computeHorizontalScrollRange()) {
				isIndicateShow.isShow(View.VISIBLE, View.VISIBLE);
			}
			if (l + getWidth() == computeHorizontalScrollRange()) {
				isIndicateShow.isShow(View.VISIBLE, View.INVISIBLE);
			}
		}
	}

	/**
	 * 为scrollview添加子view
	 */
	private void addViewSub() {
		FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				btHeight);
		ll_container = new LinearLayout(context);
		ll_container.setOrientation(LinearLayout.HORIZONTAL);
		addView(ll_container, frameLayoutParams);
		LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(btWidth, btHeight);
		
		RelativeLayout.LayoutParams btAddlayoutParams = new RelativeLayout.LayoutParams(
				btWidth, btHeight);
		RelativeLayout.LayoutParams tipsAddlayoutParams = new RelativeLayout.LayoutParams(
				DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 10));
		tipsAddlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		tipsAddlayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		tipsAddlayoutParams.rightMargin = btWidth/5;
		
		btArray = new TextView[selectedDrawble.length];
		ckNameViews = new CKNameView[selectedDrawble.length];
		relativeLayouts = new RelativeLayout[selectedDrawble.length];
		for (int i = 0; i < selectedDrawble.length; i++) {
			btArray[i] = new TextView(context);
			btArray[i].setId(btID[i]);
			ckNameViews[i] = new CKNameView(context);
			ckNameViews[i].setText(tipsTexts[i]);
			ckNameViews[i].setPaintColor(tipsTextColors[i], tipsBgColors[i]);
			relativeLayouts[i] = new RelativeLayout(context);
			if (i == defSelect) {
				btArray[i].setBackgroundResource(selectedDrawble[i]);
			} else {
				btArray[i].setBackgroundResource(notSelectedDrawble[i]);
			}
			btArray[i].setOnClickListener(new OnButtonClick());
			ll_container.addView(relativeLayouts[i], linearLayoutParams);
			relativeLayouts[i].addView(btArray[i], btAddlayoutParams);
			relativeLayouts[i].addView(ckNameViews[i], tipsAddlayoutParams);
		}
	};

	public void setTips(String[] tipsTexts,String[] tipsBgColors,String[] tipsTextColors) {
		this.tipsTexts = tipsTexts;
		this.tipsBgColors = tipsBgColors;
		this.tipsTextColors = tipsTextColors;
		for (int i = 0; i < ckNameViews.length; i++) {
			ckNameViews[i].setText(tipsTexts[i]);
			ckNameViews[i].setPaintColor(tipsTextColors[i], tipsBgColors[i]);
			ckNameViews[i].invalidate();
		}
	}

	/**
	 * 设置指定位置的tips 文字,颜色,背景色
	 * 
	 * @param tipsText
	 * @param position
	 */
	public void setTips(String tipsText, String tipsBgColor,String tipsTextColor,int position) {
		tipsTexts[position] = tipsText;
		tipsBgColors[position] = tipsBgColor;
		tipsTextColors[position] = tipsTextColor;
		ckNameViews[position].setText(tipsTexts[position]);
		ckNameViews[position].setPaintColor(tipsTextColors[position], tipsBgColors[position]);
		ckNameViews[position].invalidate();
	}


	class OnButtonClick implements OnClickListener {
		public void onClick(View v) {
			int tempId = v.getId();
			for (int i = 0; i < selectedDrawble.length; i++) {
				if (tempId == btID[i]) {
					btArray[i].setBackgroundResource(selectedDrawble[i]);
					if (onBtmButtonSelect != null) {
						onBtmButtonSelect.onBtmButtonSelect(i);
					}
				} else {
					btArray[i].setBackgroundResource(notSelectedDrawble[i]);
				}
			}
		}

	}

	/**
	 * 获取底部单个button的宽度
	 * 
	 * @return
	 */
	private float getBtnWidth() {
		float tempWidth = context.getResources().getDisplayMetrics().widthPixels;
		float result = 0;
		float size = selectedDrawble.length >= 5 ? 5 : selectedDrawble.length;
		result = tempWidth / (size);
		return result;
	}

	public LinearLayout getLayoutContainer() {
		return ll_container;
	}

	public interface OnBtmButtonSelect {
		void onBtmButtonSelect(int position);
	}

	public void setOnBtmButtonSelect(OnBtmButtonSelect onBtmButtonSelect) {
		this.onBtmButtonSelect = onBtmButtonSelect;
	}

	OnBtmButtonSelect onBtmButtonSelect;

	public interface IsIndicateShow {
		void isShow(int leftState, int rightState);
	}

	public void setIsIndicateShow(IsIndicateShow isIndicateShow) {
		this.isIndicateShow = isIndicateShow;
	}

	IsIndicateShow isIndicateShow;
}
