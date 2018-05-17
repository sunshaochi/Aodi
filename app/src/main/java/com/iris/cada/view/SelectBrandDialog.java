package com.iris.cada.view;

import java.util.ArrayList;
import java.util.List;

import com.iris.cada.adapter.ListViewAdapter;
import com.iris.cada.view.customsearchview.Bean;
import com.iris.cada.view.customsearchview.SearchView;
import com.iris.cada.view.customsearchview.SearchView.SearchViewListener;
import com.iris.cada.view.swipeXListView.SwipeMenuListView;
import com.iris.foundation.utils.DensityUtil;
import com.iris.foundation.view.CKTitleView;
import com.iris.cada.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * @ClassName: SelectBrandDialog
 * @Description: TODO(联动搜索)
 * @author iris-gjh
 * @date 2016年8月6日 上午11:54:31
 *
 */
public class SelectBrandDialog implements SearchViewListener {
	private Context context;
	private Dialog dialog;
	private CKTitleView titleView;
	private SwipeMenuListView listView;
	/**
	 * 所有数据
	 */
	private List<String> datas;
	private OnListViewItemClick itemClick;

	/**
	 * 搜索view
	 */
	private SearchView searchView;

	/**
	 * 热搜框列表adapter
	 */
	private ListViewAdapter hintAdapter;

	/**
	 * 自动补全列表adapter
	 */
	private ListViewAdapter autoCompleteAdapter;

	private List<Bean> dbData;

	/**
	 * 热搜版数据
	 */
	private List<String> hintData;

	/**
	 * 搜索过程中自动补全数据
	 */
	private List<String> autoCompleteData;

	/**
	 * 提示框显示项的个数
	 */
	private static int hintSize = 10;

	/**
	 * 设置提示框显示项的个数
	 *
	 * @param hintSize
	 *            提示框显示个数
	 */
	public static void setHintSize(int hSize) {
		hintSize = hSize;
	}

	public void setItemClick(OnListViewItemClick itemClick) {
		this.itemClick = itemClick;
	}

	public SelectBrandDialog(Context context) {
		this.context = context;
		builder();
	}

	public SelectBrandDialog builder() {
		initData();
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_select_brand, null);
		view.setMinimumWidth(DensityUtil.getScreenSize(context).x);

		listView = (SwipeMenuListView) view.findViewById(R.id.listview);
		searchView = (SearchView) view.findViewById(R.id.main_search_layout);

		titleView = (CKTitleView) view.findViewById(R.id.title);
		titleView.getLeftButoon().setBackgroundResource(R.drawable.dialog_cancel);
		TextView title = new TextView(context);
		title.setText("");
		title.setTextColor(context.getResources().getColor(R.color.white));
		title.setTextSize(18);
		titleView.getLinearLayout().addView(title);
		titleView.getLeftButoon().setOnClickListener(new OnTitleLeftBtnClick());

		// 设置searchView监听
		searchView.setSearchViewListener(this);
		// 设置ListView、adapter
		searchView.setListView(listView);
		searchView.setTipsHintAdapter(hintAdapter);
		searchView.setAutoCompleteAdapter(autoCompleteAdapter);

		listView.setOnItemClickListener(new OnListViewItemClickListener());
		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(false);

		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.setContentView(view);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = 0;
		dialogWindow.setAttributes(lp);
		return this;
	}

	class OnListViewItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			itemClick.onClick(datas.get(position - 1));
			dialog.dismiss();
		}
	}

	class OnTitleLeftBtnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			dialog.dismiss();
		}
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		datas = new ArrayList<String>();
		// String str[] = { "无", "NA", "阿尔法罗密欧", "阿斯顿·马丁", "安凯客车", "奥迪", "巴博斯",
		// "宝骏", "宝马", "保时捷", "北京汽车", "北汽幻速", "北汽威旺",
		// "北汽新能源", "北汽制造", "奔驰", "奔腾", "本田", "比亚迪", "标致", "别克", "宾利", "布加迪",
		// "昌河", "成功汽车", "长安", "长安商用", "长城",
		// "Dacia", "DS", "大发", "大众", "道奇", "东风", "东风风度", "东风风行", "东风风神",
		// "东风小康", "东南", "Fisker", "法拉利", "菲亚特",
		// "丰田", "福迪", "福汽启腾", "福特", "福田", "GMC", "Gumpert", "观致", "光冈", "广汽传祺",
		// "广汽吉奥", "Hennessey", "哈飞", "哈弗",
		// "海格", "海马", "悍马", "恒天", "红旗", "华普", "华骐", "华颂", "华泰", "黄海", "Jeep",
		// "吉利汽车", "江淮", "江铃", "江铃集团轻汽", "捷豹",
		// "金杯", "金旅", "九龙", "KTM", "卡尔森", "卡威", "开瑞", "凯佰赫", "凯迪拉克", "凯翼",
		// "科尼赛克", "克莱斯勒", "兰博基尼", "朗世", "劳斯莱斯",
		// "雷克萨斯", "雷诺", "理念", "力帆", "莲花汽车", "猎豹汽车", "林肯", "铃木", "陆风", "路虎",
		// "路特斯", "MG", "MINI", "马自达", "玛莎拉蒂",
		// "迈巴赫", "迈凯伦", "摩根", "Noble", "纳智捷", "讴歌", "欧宝", "欧朗", "帕加尼", "佩奇奥",
		// "奇瑞", "启辰", "起亚", "日产", "荣威", "如虎",
		// "瑞麒", "Scion", "smart", "SPIRRA", "SSC", "萨博", "赛麟", "三菱", "上海",
		// "上汽大通", "绅宝", "世爵", "双环", "双龙", "思铭",
		// "斯巴鲁", "斯柯达", "特斯拉", "腾势", "威麟", "威兹曼", "沃尔沃", "沃克斯豪尔", "五菱汽车",
		// "五十铃", "西雅特", "现代", "雪佛兰", "雪铁龙",
		// "亚琛施纳泽", "野马汽车", "一汽", "依维柯", "英菲尼迪", "英致", "永源", "之诺", "知豆", "中华",
		// "中兴", "众泰" };
		String str[] = { "宝马", "奥迪", "保时捷", "雷克萨斯", "凯迪拉克", "沃尔沃", };
		for (String string : str) {
			datas.add(string);
		}
		// 从数据库获取数据
		// getDbData();
		// 初始化热搜版数据
		getHintData();
		// 初始化自动补全数据
		getAutoCompleteData(null);
	}

	/**
	 * 获取db 数据
	 */
	private void getDbData() {
		int size = 100;
		dbData = new ArrayList<Bean>();
		for (String car : datas) {
			dbData.add(new Bean(R.drawable.icon, "android开发必备技能", car, ""));
		}

	}

	/**
	 * 获取所有data和对应的adapter
	 */
	private void getHintData() {
		hintData = new ArrayList<String>();
		for (int i = 0; i < datas.size(); i++) {
			hintData.add(datas.get(i));
		}
		hintAdapter = new ListViewAdapter(context, hintData, R.layout.item_customlevel);
	}

	/**
	 * 获取自动补全data 和adapter
	 */
	private void getAutoCompleteData(String text) {
		if (autoCompleteData == null) {
			// 初始化
			autoCompleteData = new ArrayList<String>(hintSize);
		} else {
			// 根据text 获取auto data
			autoCompleteData.clear();
			for (int i = 0, count = 0; i < datas.size() && count < hintSize; i++) {
				if (datas.get(i).contains(text.trim())) {
					autoCompleteData.add(datas.get(i));
					count++;
				}
			}
		}
		if (autoCompleteAdapter == null) {
			autoCompleteAdapter = new ListViewAdapter(context, autoCompleteData, R.layout.item_customlevel);
		} else {
			autoCompleteAdapter.setdata(autoCompleteData);
		}
	}

	/**
	 * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
	 * 
	 * @param text
	 */
	@Override
	public void onRefreshAutoComplete(String text) {
		if (TextUtils.isEmpty(text)) {
			hintAdapter.setdata(datas);
		} else {
			// 更新数据
			getAutoCompleteData(text);
		}
	}

	/**
	 * 点击搜索键时edit text触发的回调
	 *
	 * @param text
	 */
	@Override
	public void onSearch(String text) {
		// 更新result数据
		// getResultData(text);
		// listView.setVisibility(View.VISIBLE);
		// // 第一次获取结果 还未配置适配器
		// if (listView.getAdapter() == null) {
		// // 获取搜索数据 设置适配器
		// listView.setAdapter(resultAdapter);
		// } else {
		// // 更新搜索数据
		// resultAdapter.notifyDataSetChanged();
		// }
		// Toast.makeText(context, "完成搜索", Toast.LENGTH_SHORT).show();
	}

	public SelectBrandDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public SelectBrandDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	public void show() {
		dialog.show();
	}

	public interface DialogDismissListener {
		void onDismiss(DialogInterface dialog);
	}

	public interface OnListViewItemClick {
		void onClick(String str);
	}
}
