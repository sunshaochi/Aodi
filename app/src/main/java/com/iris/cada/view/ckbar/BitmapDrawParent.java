package com.iris.cada.view.ckbar;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PointF;

public abstract class BitmapDrawParent {
	protected Bitmap bitmap;
	protected Canvas canvas;
	protected int width, height, yStart;
	protected List<String> datas;
	protected List<PointF> points;
	protected List<String> colors;
	public BitmapDrawParent(int width, int height, List<String> datas, int yStart,List<PointF> points,List<String> colors) {
		bitmap = Bitmap.createBitmap(width, height, Config.ARGB_4444);
		canvas = new Canvas(bitmap);
		this.width = width;
		this.height = height;
		this.datas = datas;
		this.yStart = yStart;
		this.points = points;
		this.colors = colors;
	}

	public Bitmap setAnimationFactor(float animationFactor) {
		return draw(animationFactor);
	}
	protected abstract Bitmap draw(float animationFactor);
	public abstract void draw(Canvas canvas);
	public void recyle(){
		bitmap.recycle();
	}
}
