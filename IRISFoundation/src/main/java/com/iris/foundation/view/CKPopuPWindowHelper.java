package com.iris.foundation.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 显示popupWindow依据anchorView
 * 
 * @author CK
 *
 */
public class CKPopuPWindowHelper {
	View anchorView;
	View contentView;
	private PopupWindow mPopupWindow;
	
	public CKPopuPWindowHelper(View anchorView, View contentView) {
		super();
		this.anchorView = anchorView;
		this.contentView = contentView;
	}

	/**
	 * @param xOffset
	 * @param yOffset
	 * @param animationSet -1 使用默认的动画
	 * @param width
	 * @param height
	 * @return
	 */
	public PopupWindow showPopupWindow(int xOffset, int yOffset, int animationSet, int width, int height) {

		final PopupWindow popupWindow = new PopupWindow(contentView, width, height, true);
		popupWindow.setTouchable(true);
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popupWindow.setAnimationStyle(animationSet);
		// 设置好参数之后再show
		popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, xOffset, yOffset);
		this.mPopupWindow = popupWindow;
		return popupWindow;
	}

	public View getAnchorView() {
		return anchorView;
	}

	public PopupWindow getmPopupWindow() {
		return mPopupWindow;
	}
}
