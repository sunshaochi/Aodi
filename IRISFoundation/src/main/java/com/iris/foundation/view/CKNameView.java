package com.iris.foundation.view;

import com.iris.foundation.utils.DensityUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class CKNameView extends View {
	Paint textPaint;
	Paint bgPaint;
	float padding;
	String text = "";
	Bitmap bitmap;
	Context context;

	public CKNameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		padding = DensityUtil.dip2px(context, 5);
		textPaint = initPaint(DensityUtil.dip2px(context, 16), "#ffffff", 0, Paint.Style.STROKE);
		bgPaint = initPaint(0, "#F5A953", 1, Paint.Style.FILL);

	}

	public CKNameView(Context context) {
		this(context, null);
	}

	public void setPaintColor(String textColor, String bgColor) {
		textPaint.setColor(Color.parseColor(textColor));
		bgPaint.setColor(Color.parseColor(bgColor));
	}

	@SuppressWarnings("deprecation")
	public void setBitmap(int drawableID) {
		BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(drawableID);
		bitmap = bd.getBitmap();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		float height = getMeasuredHeight();
		float width = getMeasuredWidth();
		float min =  height>width?width:height;
		padding = min/10;
		textPaint.setTextSize(min/3);
		RectF rect = new RectF();
		if (height > width) {
			rect.set(padding, (height - width) / 2 + padding, width - padding, height - (height - width) / 2 - padding);
		} else {
			rect.set((width - height) / 2 + padding, padding, width - (width - height) / 2 - padding, height - padding);
		}
		canvas.drawArc(rect, 0, 360, true, bgPaint);
		float textSize = textPaint.getTextSize();
		float textYStart = (rect.bottom - rect.top) / 2 + textSize / 3 + rect.top;
		float textXStart = (rect.right - rect.left) / 2 - textPaint.measureText(text) / 2 + rect.left;
		canvas.drawText(text, textXStart, textYStart, textPaint);
		if (bitmap != null) {
			canvas.drawBitmap(bitmap, null, rect, textPaint);
		}
	}

	public void setText(String text) {
		this.text = text;
	}

	Paint initPaint(float textSize, String color, float lineWidth, Paint.Style style) {
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		paint.setAntiAlias(true);
		paint.setColor(Color.parseColor(color));
		paint.setStrokeWidth(lineWidth);
		paint.setStyle(style);
		paint.setAntiAlias(true);
		return paint;
	}
}
