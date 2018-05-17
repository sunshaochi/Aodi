package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.entity.ChannelInfo;
import com.iris.cada.utils.StringUtils;
import com.iris.foundation.utils.CommonAdapter;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MonitorOperativeOrProfitChannelAdapter extends CommonAdapter<ChannelInfo> {

	public MonitorOperativeOrProfitChannelAdapter(Context context, List<ChannelInfo> datas) {
		super(context, datas);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ChannelInfo channelInfo = datas.get(position);
		viewHolder.setData(channelInfo, position, convertView);

		return viewHolder.getConvertView();
	}

	class ViewHolder extends BaseHolder<ChannelInfo> {

		private LinearLayout layoutChannel;
		private TextView tvChannelEffect;
		private TextView tvChannelTarget;
		private TextView tvChannelPractical;
		private TextView tvChannelAchieveProgress;

		@Override
		protected View initView() {
			View view = View.inflate(context, R.layout.item_monitor_channel_layout, null);

			layoutChannel = (LinearLayout) view.findViewById(R.id.item_channel_layout);
			tvChannelEffect = (TextView) view.findViewById(R.id.item_channel_effect);
			tvChannelTarget = (TextView) view.findViewById(R.id.item_channel_target);
			tvChannelPractical = (TextView) view.findViewById(R.id.item_channel_practical);
			tvChannelAchieveProgress = (TextView) view.findViewById(R.id.item_channel_achieve_progress);

			return view;
		}

		@Override
		protected void refreshView(ChannelInfo channelInfo, int position, View convertView) {
			// TODO Auto-generated method stub
			String target = StringUtils.FormatString(channelInfo.getGoal());
			String practical = StringUtils.FormatString(channelInfo.getReality());

			if (null != target && null != practical) {
				if (Float.compare(Float.valueOf(target).floatValue(), Float.valueOf(practical).floatValue()) > 0) {
					tvChannelPractical.setTextColor(Color.parseColor("#ff4400"));
					tvChannelAchieveProgress.setTextColor(Color.parseColor("#ff4400"));
				} else {
					tvChannelPractical.setTextColor(Color.parseColor("#a7d859"));
					tvChannelAchieveProgress.setTextColor(Color.parseColor("#a7d859"));
				}
			}

			String name = channelInfo.getName();
			tvChannelEffect.setText(name);
			if (name.substring(name.length() - 1).equals("率")) {
				tvChannelTarget.setText(target + "%");
				tvChannelPractical.setText(practical + "%");
			}else{
				tvChannelTarget.setText(target);
				tvChannelPractical.setText(StringUtils.intDivTo0(practical));
			}
			tvChannelAchieveProgress.setText(StringUtils.FormatString(channelInfo.getCompletePro()) + "%");

			if (position % 2 == 0) {
				layoutChannel.setBackgroundColor(Color.parseColor("#ffffff"));
			} else {
				layoutChannel.setBackgroundColor(Color.parseColor("#EDF4FA"));
			}
		}
	}

}
