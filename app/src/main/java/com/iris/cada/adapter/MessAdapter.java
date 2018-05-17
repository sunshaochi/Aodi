package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.AppManager;
import com.iris.cada.Statcode;
import com.iris.cada.activity.DaoruJrAct;
import com.iris.cada.activity.MymessAct;
import com.iris.cada.activity.NewMainAct;
import com.iris.cada.entity.MessBean;
import com.iris.cada.R;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessAdapter extends BaseAdapter {

	private Context context;
	private List<MessBean> list;
	private int index;

	public MessAdapter(Context context, List<MessBean> list, int index) {
		super();
		this.context = context;
		this.list = list;
		this.index = index;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodle hodle;
		hodle = new ViewHodle();
		ViewTiemHodle timehodle = null;
		timehodle = new ViewTiemHodle();
		int type = getItemViewType(position);
		if (convertView == null) {
			if (type == 0) {// 显示时间那列布局
				convertView = View.inflate(context, R.layout.item_timeandzx, null);
				timehodle.ll_zx = (LinearLayout) convertView.findViewById(R.id.ll_zx);
				timehodle.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
				convertView.setTag(timehodle);
			} else if (type == 1) {
				convertView = View.inflate(context, R.layout.itemmess, null);
				hodle.ll_zx = (LinearLayout) convertView.findViewById(R.id.ll_zx_mess);
				hodle.tv_time = (TextView) convertView.findViewById(R.id.tv_time_mess);
				hodle.tv_bt = (TextView) convertView.findViewById(R.id.tv_bt);
				hodle.tv_lb = (TextView) convertView.findViewById(R.id.tv_lb);
				hodle.tv_nr = (TextView) convertView.findViewById(R.id.tv_nr);
				hodle.tv_ck = (TextView) convertView.findViewById(R.id.tv_ck);
				hodle.iv_xf = (ImageView) convertView.findViewById(R.id.iv_xf);

				convertView.setTag(hodle);
			}
		} else {
			if (type == 0) {
				timehodle = (ViewTiemHodle) convertView.getTag();
			} else if (type == 1) {
				hodle = (ViewHodle) convertView.getTag();
			}
		}

		// 设置资源
		if (type == 0) {
			timehodle.tv_time.setText(list.get(position).getSendDateTime());
			if (position == list.size() - index) {
				timehodle.ll_zx.setVisibility(View.VISIBLE);
			} else {
				timehodle.ll_zx.setVisibility(View.GONE);
			}
		} else if (type == 1) {

			if (position == list.size() - index) {
				hodle.ll_zx.setVisibility(View.VISIBLE);
			} else {
				hodle.ll_zx.setVisibility(View.GONE);
			}

			if (list.get(position).getType().equals("0")) {// 运营上传提醒
				hodle.tv_bt.setText("运营");
				hodle.tv_lb.setText("上传提醒");
				hodle.tv_ck.setText("查看导入记录");
				hodle.iv_xf.setImageResource(R.drawable.icon_kc_sc);
				hodle.tv_ck.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context, DaoruJrAct.class);
						context.startActivity(intent);
						
					}
				});

			} else if (list.get(position).getType().equals("1")) {// 运营消息提醒
				hodle.tv_bt.setText("运营");
				hodle.tv_lb.setText("消息提醒");
				hodle.iv_xf.setImageResource(R.drawable.icon_yy_xx);
				hodle.tv_ck.setText("查看运营概览页");
				
				hodle.tv_ck.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if(AppManager.getAppManager().findActivity(NewMainAct.class)){
							AppManager.getAppManager().finishActivity(NewMainAct.class);
						}//检查是否在任务 栈，在就先关掉防止anr
						Intent intent = new Intent(context, NewMainAct.class);
						intent.putExtra("room", "运营概览");
						context.startActivity(intent);
//						context.sendBroadcast(new Intent(NewMainAct.LOOKGL));
						AppManager.getAppManager().finishActivity(MymessAct.class);
						
						
					}
				});
                
				

			} else if (list.get(position).getType().equals("2")) {// 运营目标达成提醒
				hodle.tv_bt.setText("运营");
				hodle.tv_lb.setText("目标达成提醒");
				hodle.iv_xf.setImageResource(R.drawable.icon_yy_mb);
				hodle.tv_ck.setText("查看运营监控页");
				
				hodle.tv_ck.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if(AppManager.getAppManager().findActivity(NewMainAct.class)){
							AppManager.getAppManager().finishActivity(NewMainAct.class);
						}//检查是否在任务 栈，在就先关掉防止anr
						Intent intent = new Intent(context, NewMainAct.class);
						intent.putExtra("room", "运营监控");
						context.startActivity(intent);
//						context.sendBroadcast(new Intent(NewMainAct.LOOKJK));
						AppManager.getAppManager().finishActivity(MymessAct.class);
						
						
					}
				});

			} else if (list.get(position).getType().equals("3")) {// 库存消息提醒
				hodle.tv_bt.setText("库存");
				hodle.tv_lb.setText("消息提醒");
				hodle.iv_xf.setImageResource(R.drawable.icon_yy_xx);
				hodle.tv_ck.setText("查看库存概览页");
				
				hodle.tv_ck.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if(AppManager.getAppManager().findActivity(NewMainAct.class)){
							AppManager.getAppManager().finishActivity(NewMainAct.class);
						}//检查是否在任务 栈，在就先关掉防止anr
						
						Intent intent = new Intent(context, NewMainAct.class);
						intent.putExtra("room", "库存概览");
						context.startActivity(intent);
//						context.sendBroadcast(new Intent(NewMainAct.LOOKGLKC));
						AppManager.getAppManager().finishActivity(MymessAct.class);
						
//						
					}
				});
				
			} else if (list.get(position).getType().equals("4")) {// 库存上传提醒
				hodle.tv_bt.setText("库存");
				hodle.tv_lb.setText("上传提醒");
				hodle.tv_ck.setText("查看导入记录");
				hodle.iv_xf.setImageResource(R.drawable.icon_kc_sc);
				hodle.tv_ck.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context, DaoruJrAct.class);
						context.startActivity(intent);
						
                        
					}
				});

			}
			hodle.tv_nr.setText(list.get(position).getMessageText());
		}

		return convertView;
	}

	@Override
	public int getItemViewType(int i) {
		if (list.get(i).getListType().equals("date")) {
			return 0;// 时间
		} else {
			return 1;// 信息
		}

	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	class ViewHodle {
		private LinearLayout ll_zx;
		private TextView tv_time, tv_bt, tv_lb, tv_nr, tv_ck;
		private ImageView iv_xf;
	}

	class ViewTiemHodle {
		private LinearLayout ll_zx;
		private TextView tv_time;
	}

}
