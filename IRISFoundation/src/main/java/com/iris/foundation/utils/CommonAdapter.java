package com.iris.foundation.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**抽象类,将getview方法定义为抽象方法,这样继承commonAdapter只需要重写getView方法就可以了.
 * @author CK
 *
 * @param <T> 数据类型
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

	public Context context;
	public List<T> datas = new ArrayList<>();
	
	public CommonAdapter(Context context, List<T> datas) {
		this.context = context;
		if (null == datas) {//设置的数据为空,则清理当前datas集合，避免空指针异常
			this.datas.clear();
		}else {
			this.datas = datas;
		}
	}
	public void setdata(List<T> datas){
		if (null == datas) {//设置的数据为空,则清理当前datas集合，避免空指针异常
			this.datas.clear();
		}else {
			this.datas = datas;
		}
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		if (null == datas) {
			return 0;
		}
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		if (null == datas) {
			return null;
		}
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* 将getView定义为抽象方法,当我们继承commonAdapter类时会必须复写getView方法
	 * 
	 */
	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);

}
