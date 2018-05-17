package com.iris.foundation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ViewPagerIndex extends View {
	int[] positions;
	Paint paint, selectPaint;
	int factor = 80;

	public ViewPagerIndex(Context context, AttributeSet attrs) {
		super(context, attrs);
		positions = new int[1];
		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setDither(true);
		selectPaint = new Paint();
		selectPaint.setStyle(Paint.Style.FILL);
		selectPaint.setDither(true);
	}

	/**
	 * 根据positions的长度来判断有多少个点,根据positions的第一位数作为默认选择位
	 * 
	 * @param positions
	 * @param color
	 * @param selectColor
	 */
	public void setData(int[] positions, String color, String selectColor) {
		this.positions = positions;
		paint.setColor(Color.parseColor(color));
		selectPaint.setColor(Color.parseColor(selectColor));
		invalidate();
	}

	public void setSelect(int position) {
		if (positions != null && position < positions.length) {
			positions[0] = position;
			invalidate();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth();
		int height = getHeight();
		int indexRadius = width / factor;
		int x = width / 2 - ((3 * positions.length - 2) * indexRadius);
		int y = height / 2 - indexRadius;
		RectF[] rects = new RectF[positions.length];
		int step = 4 * indexRadius;
		for (int i = 0; i < positions.length; i++) {
			RectF rect = new RectF(x + (step * i), y, x + 2 * indexRadius + (i * step), y + 2 * indexRadius);
			rects[i] = rect;
			if (i == positions[0]) {
				canvas.drawOval(rect, selectPaint);
			} else {
				canvas.drawOval(rect, paint);
			}
		}

	}
}
