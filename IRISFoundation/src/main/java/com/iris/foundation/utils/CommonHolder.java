package com.iris.foundation.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 万能holder
 * 
 * @author CK
 *
 */
public class CommonHolder {
	private SparseArray<View> mViews;
	@SuppressWarnings("unused")
	private int mPositon;
	private View mConvertView;

	// 将构造方法私有化,只有当传入的convertView为空时,才会在内部调用.
	private CommonHolder(Context context, ViewGroup parent, int layoutId, int position) {
		this.mPositon = position;
		// 新建一个sparsArray用于存储view
		this.mViews = new SparseArray<View>();
		// inflate convertView
		this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		// 将当前holder的实例setTag
		mConvertView.setTag(this);

	}

	public static CommonHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			// 如果converView为空则调用私有化的构造方法
			return new CommonHolder(context, parent, layoutId, position);
		} else {
			// 如果传入的convertView不为空,则取出holder,并将position赋值给holder的position
			CommonHolder holder = (CommonHolder) convertView.getTag();
			holder.mPositon = position;
			return holder;
		}

	}

	// 获取convertView的方法
	public View getConvertView() {
		return mConvertView;
	}

	// 获取item中view的方法,传入viewId.
	@SuppressWarnings("unchecked")
	public <T extends View> T getViewItem(int viewId) {
		// 在sparseArray中利用viewId获取view
		View view = mViews.get(viewId);
		if (view == null) {
			// 如果view为空,说明该view需要find,find完成后保存到sparseArray
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public CommonHolder tvSetText(int viewId, String text) {
		TextView tv = getViewItem(viewId);
		tv.setText(text);
		return this;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public CommonHolder setText(int viewId, String text) {
		TextView view = (TextView) getViewItem(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public CommonHolder setImageResource(int viewId, int drawableId) {
		ImageView view = (ImageView) getViewItem(viewId);
		view.setImageResource(drawableId);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public CommonHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = (ImageView) getViewItem(viewId);
		view.setImageBitmap(bm);
		return this;
	}


	public int getPosition() {
		return mPositon;
	}
}
