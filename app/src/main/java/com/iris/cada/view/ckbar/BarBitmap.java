package com.iris.cada.view.ckbar;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

public class BarBitmap extends BitmapDrawParent {
	int barRange = 0;
	Paint paint;

	public BarBitmap(int width, int height, List<String> datas, int yStart, List<PointF> points, List<String> colors,
			int barWidth) {
		super(width, height, datas, yStart, points, colors);
		barRange = barWidth / 2;
		paint = new Paint();
		paint.setAntiAlias(true);
	}

	@Override
	protected Bitmap draw(float animationFactor) {
		canvas = new Canvas(bitmap);
		canvas.clipRect(0, height - height * animationFactor, width, height);
		draw(canvas);
		return bitmap;
	}

	Rect getRect(PointF p) {
		Rect tempRect = new Rect();
		if (p.y > yStart) {
			tempRect = new Rect((int) (p.x - barRange), yStart, (int) (p.x) + barRange, (int) (p.y));
		} else {
			tempRect = new Rect((int) (p.x - barRange), (int) (p.y), (int) (p.x) + barRange, yStart);
		}
		return tempRect;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		for (int i = 0; i < points.size(); i++) {
			paint.setColor(Color.parseColor(colors.get(i)));
			canvas.drawRect(getRect(points.get(i)), paint);
		}
	}
}
