package com.iris.cada.activity;

import java.util.ArrayList;
import java.util.List;

import com.iris.cada.adapter.WeatherPhenomenAdapter;
import com.iris.cada.entity.SortModel;
import com.iris.cada.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class WeatherPhenomenonAcivity extends Activity implements OnClickListener {
	private ListView lv;
	private WeatherPhenomenAdapter weatherPhenomenAdapter;
	private List<SortModel> SourceDateList;
	private ImageView title_back;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_weather_phenomen);
		initView();
		bindata();
		initData();
	}

	private void initData() {
		SourceDateList = filledData(getResources().getStringArray(R.array.date),
				getResources().getStringArray(R.array.letter));
		weatherPhenomenAdapter = new WeatherPhenomenAdapter(this, SourceDateList);
		lv.setAdapter(weatherPhenomenAdapter);
	}

	private void bindata() {

	}

	private void initView() {
		lv = (ListView) findViewById(R.id.activity_weather_lv);
		title_back = (ImageView) findViewById(R.id.title_back);
		title_back.setOnClickListener(this);

	}

	private List<SortModel> filledData(String[] date, String[] dates) {
		List<SortModel> mSortList = new ArrayList<SortModel>();
		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			sortModel.setLetters(dates[i]);
			mSortList.add(sortModel);
		}
		return mSortList;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;

		default:
			break;
		}

	}
}
