package com.iris.cada.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.ProfitApplication;
import com.iris.cada.Statcode;
import com.iris.cada.entity.RepBean;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.view.NoscrollListView;
import com.iris.cada.view.SyncHorizontalScrollView;
import com.iris.cada.R;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DaoruFwAct extends FragmentActivity implements OnClickListener {
	private NoscrollListView mLeft;
	private NoscrollListView mData;
	private List<String> mListData;
	private LeftAdapter mLeftAdapter;
	private DataAdapter mDataAdapter;
	private ImageView iv_back;
	private TextView tv_jxsname;

	private SyncHorizontalScrollView mHeaderHorizontal;
	private SyncHorizontalScrollView mDataHorizontal;

	private List<RepBean> list;
	private Gson gson;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ProfitApplication.DATA_SUC:// 获取售后数据成功
				// initShdate(msg);
				initUi(msg);
				break;
			case ProfitApplication.DATA_FAILED:// 获取销售数据失败
				ToastUtils.showMyToast(DaoruFwAct.this, getString(R.string.error_data));
				break;

			case ProfitApplication.SERVER_FAILED:// 连接服务器失败
				ToastUtils.showMyToast(DaoruFwAct.this, getString(R.string.error_net));
				break;

			}
		}

	};

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		if (arg0 != null) {
			arg0.putParcelable("android:support:fragments", null);
		}
		super.onCreate(arg0);
		setContentView(R.layout.act_daorufw);
		initView();
		initDate();
	}

	/**
	 * 初始化控件
	 **/
	private void initView() {
		// TODO Auto-generated method stub
		mLeft = (NoscrollListView) findViewById(R.id.lv_left);
		mData = (NoscrollListView) findViewById(R.id.lv_data);
		mDataHorizontal = (SyncHorizontalScrollView) findViewById(R.id.data_horizontal);
		mHeaderHorizontal = (SyncHorizontalScrollView) findViewById(R.id.header_horizontal);

		mDataHorizontal.setScrollView(mHeaderHorizontal);
		mHeaderHorizontal.setScrollView(mDataHorizontal);

		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		tv_jxsname = (TextView) findViewById(R.id.tv_jsxname);

	}

	/**
	 * 初识化时间
	 **/
	private void initDate() {
		tv_jxsname.setText(ProfitApplication.loginBackInfo.getJXSName());
		gson = new Gson();
		getInfo();// 获取详情

		
	}

	private void getInfo() {
		ProfitApplication.profitNetService.getReporthistory(handler);
	}

	private void initUi(Message msg) {
		// TODO Auto-generated method stub
		try {
			String mes = (String) msg.obj;
			JSONObject object=new JSONObject(mes);
			JSONArray array=object.getJSONArray("data");
			list = gson.fromJson(array.toString(), new TypeToken<List<RepBean>>() {
			}.getType());
			
			Log.e("list", list.toString());
			if (list != null && list.size() > 0) {
				mLeftAdapter = new LeftAdapter();
				mLeft.setAdapter(mLeftAdapter);
				mDataAdapter = new DataAdapter();
				mData.setAdapter(mDataAdapter);				
			} else {
				ToastUtils.showMyToast(DaoruFwAct.this, getString(R.string.error_data));			
			}
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class LeftAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(DaoruFwAct.this).inflate(R.layout.item_left, null);
				holder.tvLeft = (TextView) convertView.findViewById(R.id.tv_left);
				holder.ll_left = (LinearLayout) convertView.findViewById(R.id.ll_left);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tvLeft.setText(list.get(position).getImport_date() + "");
			if (position % 2 == 0) {
				holder.ll_left.setBackgroundColor(Color.parseColor("#ffffffff"));
			} else {
				holder.ll_left.setBackgroundColor(Color.parseColor("#ebebeb"));
			}
			return convertView;
		}

		class ViewHolder {
			TextView tvLeft;
			LinearLayout ll_left;
		}
	}

	class DataAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(DaoruFwAct.this).inflate(R.layout.item_data, null);
				holder.tvData = (TextView) convertView.findViewById(R.id.tv_data);
				holder.tv_sb = (TextView) convertView.findViewById(R.id.tv_sb);
				holder.tv_sp = (TextView) convertView.findViewById(R.id.tv_sp);
				holder.tv_sj = (TextView) convertView.findViewById(R.id.tv_sj);
				holder.tv_cb = (TextView) convertView.findViewById(R.id.tv_cb);
				holder.tv_fj = (TextView) convertView.findViewById(R.id.tv_fj);
				holder.tv_yb = (TextView) convertView.findViewById(R.id.tv_yb);
				holder.linContent = (LinearLayout) convertView.findViewById(R.id.lin_content);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (list.get(position).getCxjz().equals("1")) {
				holder.tvData.setText("√");
				holder.tvData.setTextColor(Color.parseColor("#99cc99"));
			} else {
				holder.tvData.setText("×");
				holder.tvData.setTextColor(Color.parseColor("#ffcc0000"));
			}
			if (list.get(position).getShoubao().equals("1")) {
				holder.tv_sb.setText("√");
				holder.tv_sb.setTextColor(Color.parseColor("#99cc99"));
			} else {
				holder.tv_sb.setText("×");
				holder.tv_sb.setTextColor(Color.parseColor("#ffcc0000"));
			}

			if (list.get(position).getSuopei().equals("1")) {
				holder.tv_sp.setText("√");
				holder.tv_sp.setTextColor(Color.parseColor("#99cc99"));
			} else {
				holder.tv_sp.setText("×");
				holder.tv_sp.setTextColor(Color.parseColor("#ffcc0000"));
			}

			if (list.get(position).getPortal().equals("1")) {
				holder.tv_sj.setText("√");
				holder.tv_sj.setTextColor(Color.parseColor("#99cc99"));
			} else {
				holder.tv_sj.setText("×");
				holder.tv_sj.setTextColor(Color.parseColor("#ffcc0000"));
			}

			if (list.get(position).getR3().equals("1")) {
				holder.tv_cb.setText("√");
				holder.tv_cb.setTextColor(Color.parseColor("#99cc99"));
			} else {
				holder.tv_cb.setText("×");
				holder.tv_cb.setTextColor(Color.parseColor("#ffcc0000"));
			}

			if (list.get(position).getJpfj().equals("1")) {
				holder.tv_fj.setText("√");
				holder.tv_fj.setTextColor(Color.parseColor("#99cc99"));
			} else {
				holder.tv_fj.setText("×");
				holder.tv_fj.setTextColor(Color.parseColor("#ffcc0000"));
			}

			if (list.get(position).getXbyb().equals("1")) {
				holder.tv_yb.setText("√");
				holder.tv_yb.setTextColor(Color.parseColor("#99cc99"));
			} else {
				holder.tv_yb.setText("×");
				holder.tv_yb.setTextColor(Color.parseColor("#ffcc0000"));
			}

			if (position % 2 == 0) {
				holder.linContent.setBackgroundColor(Color.parseColor("#ffffffff"));
			} else {
				holder.linContent.setBackgroundColor(Color.parseColor("#ebebeb"));
			}

			return convertView;
		}

		class ViewHolder {
			TextView tvData, tv_sb, tv_sp, tv_sj, tv_cb, tv_fj, tv_yb;
			LinearLayout linContent;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;

		}
	}

}
