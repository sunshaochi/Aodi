package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.entity.ConsultantInfo;
import com.iris.cada.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SortAdapter extends BaseAdapter implements SectionIndexer {
	private List<ConsultantInfo> mConsultantlist = null;
	private Context mContext;

	public SortAdapter(Context mContext, List<ConsultantInfo> list) {
		this.mContext = mContext;
		this.mConsultantlist = list;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<ConsultantInfo> list) {
		this.mConsultantlist = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return mConsultantlist.size();
	}

	public Object getItem(int position) {
		return mConsultantlist.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		ViewHolder viewHolder = null;
//		final ConsultantInfo consultantInfo = mConsultantlist.get(position);
		
		if (null == view) {
			viewHolder = new ViewHolder();
			view = View.inflate(mContext, R.layout.item_filter_consultant, null);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.item_filter_consultant_catalog);
			viewHolder.chkChoose = (CheckBox) view.findViewById(R.id.item_filter_consultant_check);
			viewHolder.tvName = (TextView) view.findViewById(R.id.item_filter_consultant_name);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);

		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mConsultantlist.get(position).getLetter());
		} else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}

		
		viewHolder.chkChoose.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				mConsultantlist.get(position).setCheck(isChecked);
			}
		});
		// 根据isSelected来设置checkbox的选中状况
			viewHolder.chkChoose.setChecked(mConsultantlist.get(position).isCheck());
		    viewHolder.tvName.setText(mConsultantlist.get(position).getName());
		
		return view;

	}

	final static class ViewHolder {
		TextView tvLetter;
		CheckBox chkChoose;
		TextView tvName;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return mConsultantlist.get(position).getLetter().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = mConsultantlist.get(i).getLetter();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

}