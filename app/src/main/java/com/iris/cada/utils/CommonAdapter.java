package com.iris.cada.utils;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 抽象类,将getview方法定义为抽象方法,这样继承commonAdapter只需要重写getView方法就可以了.
 * 
 * @author CK
 *
 * @param <T>
 *            数据类型
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

	public Context context;
	public List<T> datas;

	protected CommonAdapter(Context context, List<T> datas) {
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * 将getView定义为抽象方法,当我们继承commonAdapter类时会必须复写getView方法
	 */
	@Override
	public abstract View getView(int position, View convertView,
			ViewGroup parent);

}
