package com.iris.cada.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.adapter.SortAdapter;
import com.iris.cada.entity.Check;
import com.iris.cada.entity.ConsultantInfo;
import com.iris.cada.entity.IViewSCBean;
import com.iris.cada.entity.ShcarBean;
import com.iris.cada.entity.ShuserBean;
import com.iris.cada.entity.ShzdBean;
import com.iris.cada.utils.CharacterParser;
import com.iris.cada.utils.PinyinComparator;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.view.CProgressDialog;
import com.iris.cada.view.SideBar;
import com.iris.cada.view.SideBar.OnTouchingLetterChangedListener;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.cada.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShsxAct extends Activity implements OnClickListener {

	private ImageView mTopBackImg = null;
	private TextView mTopCheckAll = null;
	private ListView mSortListView;
	private SideBar mSideBar;
	private TextView mLetterDialog;
	private Button mConfirmBtn = null;
	
    private TextView tv_title;

	private CharacterParser mCharacterParser = null;
	private PinyinComparator mPinyinComparator = null;
	private SortAdapter mSortAdapter;
	private boolean ischeck = true;//false
	private List<ShuserBean> list = new ArrayList<ShuserBean>();
	private List<ShcarBean> carlist = new ArrayList<ShcarBean>();
	protected Dialog progressDialog;
	private AddTodoPickerViewUtils addTodoPickerViewUtils;
	public static final String KEY_DATA_USER = "KEY_DATA_USER";
	public static final int KEY_DATA_OK = 100;
	// 原始顾问列表
	private List<ConsultantInfo> mConsultantList;
	public static final int KEY_CAR_OK = 101;
	public static final String KEY_DATA_CAR = "KEY_DATA_CAR";

	private List<ConsultantInfo> mCarList;

	// 车型
	// private List<Check> filterListdatas = new ArrayList<Check>();
	private List<String> mCarModeList;
	private List<ShzdBean> datas;
	private List<String>moudes=new ArrayList<String>();
	
	
	Handler handler = new MyHandler() {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			Log.e("code", msg.what+"");
			switch (msg.what) {
			case ProfitApplication.DATA_FAILED:
				ToastUtils.showMyToast(ShsxAct.this, getString(R.string.error_data));
				break;
			case ProfitApplication.DATA_SUC:
				if(ProfitApplication.isConsultantMode==true){
				dealChannelInfo(msg);//处理顾问模式数据
				}else {
					chuliCarInfo(msg);//处理车型模式数据
				}
				break;
			case ProfitApplication.SERVER_FAILED:
				ToastUtils.showMyToast(ShsxAct.this, getString(R.string.error_net));
				break;
			}
		};
	};
	
	/**处理顾问模式的数据**/
	private void dealChannelInfo(Message msg) {
		if (null != list) {
			list.clear();
		}
		try {
			String strMsg = (String) msg.obj;
			list = new Gson().fromJson(new JSONObject(strMsg).getJSONArray("data").toString(), new TypeToken<ArrayList<ShuserBean>>() {
			}.getType());
			if(list.size()==datas.size()){
				mTopCheckAll.setText("取消");
			}else {
				mTopCheckAll.setText("全选");
			}
			initData(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**处理车型模式的数据**/
	protected void chuliCarInfo(Message msg) {
		// TODO Auto-generated method stub
		if (null != carlist) {
			carlist.clear();
		}
		try {
			String strMsg = (String) msg.obj;
			carlist = new Gson().fromJson(new JSONObject(strMsg).getJSONArray("data").toString(), new TypeToken<ArrayList<ShcarBean>>() {
			}.getType());
			if(carlist.size()==datas.size()){
				mTopCheckAll.setText("取消");
			}else {
				mTopCheckAll.setText("全选");
			}
			initCarData(carlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		setContentView(R.layout.activity_filter_consultant);
		initView();

		if (!ProfitApplication.isConsultantMode) {// 车型
			tv_title.setText("筛选车型");	
			
			moudes.clear();//汽车类型结合
            if(datas!=null&&datas.size()>0){
            	for(int i=0;i<datas.size();i++){
            		moudes.add(datas.get(i).get车型());
            	}
            }
            getCarinfo();//调取所有车型


		} else {
			tv_title.setText("筛选服务顾问");
			moudes.clear();//顾问类型结合
			// 请求销售顾问数据
			 if(datas!=null&&datas.size()>0){
	            	for(int i=0;i<datas.size();i++){
	            		moudes.add(datas.get(i).get服务顾问());
	            	}
	            }
			getinfor();//调取所有的顾问模式
		}

	}

	/**获取顾问模式列表**/
	private void getinfor() {
		progressDialog = CProgressDialog.createLoadingDialog(this);
		progressDialog.show();		
		ProfitApplication.profitNetService.getSalesUserList(handler);
	}
	
  
	/**获取车型模式列表**/
	private void getCarinfo() {
		// TODO Auto-generated method stub
		progressDialog = CProgressDialog.createLoadingDialog(this);
		progressDialog.show();		
		ProfitApplication.profitNetService.getSalesCarTypeList (handler);
	}
	
	/**
	 * 车的那列
	 */
	private void initCarData(List<ShcarBean>carlist) {
		mCharacterParser = CharacterParser.getInstance();
		mPinyinComparator = new PinyinComparator();
		mCarList = new ArrayList<ConsultantInfo>();

		for (int i = 0; i < carlist.size(); i++) {
			ConsultantInfo consultantInfo = new ConsultantInfo();
			consultantInfo.setName(carlist.get(i).getCarType());

			// 汉字转换成拼音
			String pinyin = mCharacterParser.getSelling(carlist.get(i).getCarType());
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				consultantInfo.setLetter(sortString.toUpperCase());
			} else {
				consultantInfo.setLetter("#");
			}
			if (moudes == null || moudes.size()==0) {//如果穿戴过来的为空
				consultantInfo.setCheck(false);
			} else {
				if(moudes.contains(carlist.get(i).getCarType())){
					consultantInfo.setCheck(true);
				}else {
					consultantInfo.setCheck(false);
					}
			}
			mCarList.add(consultantInfo);
		}
		
		// 根据a-z进行排序源数据
		Collections.sort(mCarList, mPinyinComparator);
		mSortAdapter = new SortAdapter(this, mCarList);
		mSortListView.setAdapter(mSortAdapter);

	}

	private void initView() {

		Intent intent = getIntent();
		startTime = intent.getStringExtra("startTime");
		endTime = intent.getStringExtra("endTime");
		datas = (List<ShzdBean>) intent.getExtras().get("date");
		
		tv_title=(TextView) findViewById(R.id.tv_title);
		mTopBackImg = (ImageView) findViewById(R.id.filter_consultant_back);
		mTopBackImg.setOnClickListener(this);

		mTopCheckAll = (TextView) findViewById(R.id.filter_consultant_check_all);
		mTopCheckAll.setOnClickListener(this);      
		mSortListView = (ListView) findViewById(R.id.filter_consultant_listview);

		mSideBar = (SideBar) findViewById(R.id.filter_consultant_sidrbar);
		mLetterDialog = (TextView) findViewById(R.id.filter_consultant_letter_show);
		mSideBar.setTextView(mLetterDialog);
		// 设置右侧触摸监听
		mSideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				if (null != list && list.size() != 0) {
					// 该字母首次出现的位置
					int position = mSortAdapter.getPositionForSection(s.charAt(0));
					if (position != -1) {
						mSortListView.setSelection(position);
					}
				}
			}
		});

		mConfirmBtn = (Button) findViewById(R.id.filter_consultant_confirm);
		mConfirmBtn.setOnClickListener(this);

	}

	/**
	 * 经销商的集合
	 * 
	 * @param list
	 * 昨天注释掉的
	 */
	private void initData(List<ShuserBean> list) {
		mCharacterParser = CharacterParser.getInstance();
		mPinyinComparator = new PinyinComparator();
		mConsultantList = new ArrayList<ConsultantInfo>();

		// List<String> mConsulant =
		// ProfitApplication.loginBackInfo.getManagers();
		for (int i = 0; i < list.size(); i++) {
			ConsultantInfo consultantInfo = new ConsultantInfo();
			consultantInfo.setName(list.get(i).getPersonnelName());
			consultantInfo.setCode("0");

			// 汉字转换成拼音
			String pinyin = mCharacterParser.getSelling(list.get(i).getPersonnelName());
			String sortString = pinyin.substring(0, 1).toUpperCase();
           
			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				consultantInfo.setLetter(sortString.toUpperCase());
			} else {
				consultantInfo.setLetter("#");
			}
			if (moudes == null || moudes.size()==0) {
				consultantInfo.setCheck(false);
			} else {
				if(moudes.contains(list.get(i).getPersonnelName())){
					consultantInfo.setCheck(true);
				}else{
					consultantInfo.setCheck(false);
				}
			}
			// consultantInfo.setCheck(false);

			mConsultantList.add(consultantInfo);
		}

		// 根据a-z进行排序源数据
		Collections.sort(mConsultantList, mPinyinComparator);
		mSortAdapter = new SortAdapter(this, mConsultantList);
		mSortListView.setAdapter(mSortAdapter);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

		switch (view.getId()) {
		case R.id.filter_consultant_back:
			this.finish();
			break;
		case R.id.filter_consultant_check_all://全选还是取消

			if (!ProfitApplication.isConsultantMode) {// 车型
//				if (ischeck && null != mCarList && mCarList.size() != 0) {//!ischeck
//					setAllCheckStatus(false);//true
//					ischeck = false;//true
//					mTopCheckAll.setText("取消");//全选
//				
//				} else {
//					setAllCheckStatus(true);//false
//					ischeck = true;//false
//					mTopCheckAll.setText("全选");//取消
//				
//				}
				if(mTopCheckAll.getText().equals("全选")){
					mTopCheckAll.setText("取消");
					setAllCheckStatus(true);//true
				}else if(mTopCheckAll.getText().equals("取消")){
					mTopCheckAll.setText("全选");
					setAllCheckStatus(false);//true
				}
			} else {//经销商
//				if (!ischeck && null != list && list.size() != 0) {
//					setAllCheckStatus(true);
//					ischeck = true;
//					mTopCheckAll.setText("取消");
//				} else {
//					setAllCheckStatus(false);
//					ischeck = false;
//					mTopCheckAll.setText("全选");
//				}
				if(mTopCheckAll.getText().equals("全选")){
					mTopCheckAll.setText("取消");
					setAllCheckStatus(true);//true
				}else if(mTopCheckAll.getText().equals("取消")){
					mTopCheckAll.setText("全选");
					setAllCheckStatus(false);//true
				}
			}

			break;
		case R.id.filter_consultant_confirm:
			if (!ProfitApplication.isConsultantMode) {// 车型
				getCheckedCarList();
			} else {// 销售顾问
				getCheckedList();
			}

			break;
		default:
			break;
		}
	}

	// 全选/全不选
	private void setAllCheckStatus(boolean bStatus) {

		if (!ProfitApplication.isConsultantMode) {// 车型

			if (null != mCarList && mCarList.size() != 0) {
				for (int i = 0; i < mCarList.size(); i++) {
					mCarList.get(i).setCheck(bStatus);
				}
			}
		} else {// 销售顾问
			if (null != list && list.size() != 0) {
				// 遍历list的长度，将MyAdapter中的map值全部设为true
				for (int i = 0; i < mConsultantList.size(); i++) {
					mConsultantList.get(i).setCheck(bStatus);
				}
			}
		}

		mSortAdapter.notifyDataSetChanged();
	}

	// 获取选中的车型数据
	private void getCheckedCarList() {
		ArrayList<String> checkedList = new ArrayList<String>();
		String value = "";// 用于拼接字符串
		Log.e("", "" + mCarList.toString());
		if (null != mCarList && mCarList.size() != 0) {
			for (int i = 0; i < mCarList.size(); i++) {
				if (mCarList.get(i).isCheck()) {
					checkedList.add(mCarList.get(i).getName());
				}
			}
			Log.e("", "" + checkedList.toString());
			if (checkedList.size() == 0) {
				// 说明没有选择
				value = "";
			} else {
				// 因为后台需要的参数是："a,b,c"这样的字符串，所以这边需要拼接
				for (int i = 0; i < checkedList.size(); i++) {
					if (i == checkedList.size() - 1) {
						// 如果是最后一个元素
						value += checkedList.get(i);
					} else {
						value += checkedList.get(i) + ",";
					}
				}
			}

			Intent intent = new Intent();
			intent.putExtra(KEY_DATA_CAR, value);
			// intent.putStringArrayListExtra(KEY_DATA_CAR, checkedList);
			setResult(KEY_CAR_OK, intent);
			Log.e("checkcar", value+"");
			finish();
		} else {
			Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
		}
	}

	// 获取选中数据
	private void getCheckedList() {
		ArrayList<String> checkedList = new ArrayList<String>();
		String mvalue = "";// 用于拼接字符串
		if (null != mConsultantList && mConsultantList.size() != 0) {
			for (int i = 0; i < mConsultantList.size(); i++) {
				if (mConsultantList.get(i).isCheck()) {
					checkedList.add(mConsultantList.get(i).getName());
					Log.e("checkdata1", mConsultantList.get(i).getName());
				}
			}
			
			if (checkedList.size() == 0) {
				// 说明没有选择
				mvalue = "";
			} else {
				// 因为后台需要的参数是："a,b,c"这样的字符串，所以这边需要拼接
				for (int i = 0; i < checkedList.size(); i++) {
					if (i == checkedList.size() - 1) {
						// 如果是最后一个元素
						mvalue += checkedList.get(i);
					} else {
						mvalue += checkedList.get(i) + ",";
					}
				}
			}

			
			Intent intent = new Intent();
			intent.putExtra("KEY_DATA_MODUE", mvalue);
//			intent.putStringArrayListExtra(KEY_DATA_USER, checkedList);
			setResult(KEY_DATA_OK, intent);
			finish();
		} else {
			Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show();
		}
	}
    


	
	private String startTime;
	private String endTime;

	
}
