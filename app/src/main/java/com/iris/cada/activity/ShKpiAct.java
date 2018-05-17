package com.iris.cada.activity;

import java.util.ArrayList;
import java.util.List;

import com.iris.cada.adapter.WeatherPhenomenAdapter;
import com.iris.cada.entity.SortModel;
import com.iris.cada.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ShKpiAct extends NewBaseAct{
	@ViewInject(R.id.activity_weather_lv)
	private ListView lv;
	

	private List<SortModel> SourceDateList;
	private WeatherPhenomenAdapter weatherPhenomenAdapter;

	@Override
	public void setLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_weather_phenomen);
		
	}

	@Override
	public void init(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		        SourceDateList = filledData(getResources().getStringArray(R.array.shdate),
				getResources().getStringArray(R.array.shletter));
		        
		        weatherPhenomenAdapter = new WeatherPhenomenAdapter(this, SourceDateList);
				lv.setAdapter(weatherPhenomenAdapter);
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
	
	@OnClick({R.id.title_back})
	public void OnClick(View v){
		switch (v.getId()) {
		case R.id.title_back:
			finish();
			break;

		default:
			break;
		}
	}


}
