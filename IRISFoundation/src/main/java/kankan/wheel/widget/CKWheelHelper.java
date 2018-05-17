package kankan.wheel.widget;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.ListWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

public class CKWheelHelper {
	Context context;
	View viewGroup;
	
	public CKWheelHelper(Context context, View viewGroup) {
		this.context = context;
		this.viewGroup = viewGroup;
	}

	public void setDateWheel(final WheelView year, final WheelView month, final WheelView day) {
		Calendar calendar = Calendar.getInstance();

		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(year, month, day);
			}
		};
		int curMonth = calendar.get(Calendar.MONTH);
		NumericWheelAdapter monAdapter = new NumericWheelAdapter(context, 1, 12, "%s月");
		month.setViewAdapter(monAdapter);
		month.setCyclic(true);
		month.setCurrentItem(curMonth);
		month.addChangingListener(listener);

		int curYear = calendar.get(Calendar.YEAR);
		NumericWheelAdapter yearAdapter = new NumericWheelAdapter(context, 1900, curYear, "%s年");
		year.setViewAdapter(yearAdapter);
		year.setCurrentItem(10);
		year.addChangingListener(listener);
		updateDays(year, month, day);
		day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
	}

	public void setTimeWheel(WheelView hour, WheelView minute) {
		NumericWheelAdapter hourAdapter = new NumericWheelAdapter(context, 1, 24, "%s时");
		hour.setViewAdapter(hourAdapter);
		hour.setCurrentItem(12);
		NumericWheelAdapter minuteAdapter = new NumericWheelAdapter(context, 1, 60, "%s分");
		minute.setViewAdapter(minuteAdapter);
		minute.setCurrentItem(30);
	}

	public void setStringWheel(WheelView wheelView, List<String> list) {
		ListWheelAdapter<String> adapter = new ListWheelAdapter<String>(context, list);
		wheelView.setViewAdapter(adapter);
	}
	public void setStringWheel(WheelView wheelView,String[] arrays ) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(context, arrays);
		wheelView.setViewAdapter(adapter);
	}

	public String getWheelText(WheelView wheel) {
		String result = "";
		try {
			NumericWheelAdapter adapter = (NumericWheelAdapter) wheel.getViewAdapter();
			result = adapter.getItemText(wheel.getCurrentItem()).toString();
		} catch (Exception e) {
			try {
				ListWheelAdapter<String> adapter = (ListWheelAdapter<String>) wheel.getViewAdapter();
				result = adapter.getItemText(wheel.getCurrentItem()).toString();
			} catch (Exception e2) {
				try {
					ArrayWheelAdapter<String> adapter = (ArrayWheelAdapter<String>) wheel.getViewAdapter();
					result = adapter.getItemText(wheel.getCurrentItem()).toString();
				} catch (Exception e3) {
				}
			}

		}

		return result;
	}

	public void dismiss() {
		viewGroup.setVisibility(View.GONE);
	}

	public void visible() {
		viewGroup.setVisibility(View.VISIBLE);
	}

	/**
	 * Updates day wheel. Sets max days according to selected month and year
	 */
	private void updateDays(WheelView year, WheelView month, WheelView day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());
		
		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		NumericWheelAdapter dayAdapter = new NumericWheelAdapter(context, 1, maxDays, "%s日");
		day.setViewAdapter(dayAdapter);
		day.setCyclic(true);
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
	}

	// -----------------------------addressWheelView---------------------

	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	
	public void setCarView(WheelView provinceView, WheelView cityView, WheelView districView) {
		mViewProvince = provinceView;
		mViewCity = cityView;
		mViewDistrict = districView;
		initProvinceDatas("car.xml");
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context, mProvinceDatas));
		updateCities();
		updateAreas();

		// 添加change事件
		mViewProvince.addChangingListener(new locationChangeListener());
		// 添加change事件
		mViewCity.addChangingListener(new locationChangeListener());
		// 添加change事件
		mViewDistrict.addChangingListener(new locationChangeListener());
	}
	public void setLocationView(WheelView provinceView, WheelView cityView, WheelView districView) {
		mViewProvince = provinceView;
		mViewCity = cityView;
		mViewDistrict = districView;
		initProvinceDatas("province_data.xml");
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context, mProvinceDatas));
		updateCities();
		updateAreas();

		// 添加change事件
		mViewProvince.addChangingListener(new locationChangeListener());
		// 添加change事件
		mViewCity.addChangingListener(new locationChangeListener());
		// 添加change事件
		mViewDistrict.addChangingListener(new locationChangeListener());
	}

	class locationChangeListener implements OnWheelChangedListener {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			if (wheel == mViewProvince) {
				updateCities();
			} else if (wheel == mViewCity) {
				updateAreas();
			} else if (wheel == mViewDistrict) {
				mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
				mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
			}
		}

	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context, areas));
		mViewDistrict.setCurrentItem(0);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	/**
	 * XML解析辅助类
	 * 
	 * @author CK
	 *
	 */
	public class XmlParserHandler extends DefaultHandler {

		/**
		 * 存储所有的解析对象
		 */
		private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();

		public XmlParserHandler() {

		}

		public List<ProvinceModel> getDataList() {
			return provinceList;
		}

		@Override
		public void startDocument() throws SAXException {
			// 当读到第一个开始标签的时候，会触发这个方法
		}

		ProvinceModel provinceModel = new ProvinceModel();
		CityModel cityModel = new CityModel();
		DistrictModel districtModel = new DistrictModel();

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			// 当遇到开始标记的时候，调用这个方法
			if (qName.equals("province")) {
				provinceModel = new ProvinceModel();
				provinceModel.setName(attributes.getValue(0));
				provinceModel.setCityList(new ArrayList<CityModel>());
			} else if (qName.equals("city")) {
				cityModel = new CityModel();
				cityModel.setName(attributes.getValue(0));
				cityModel.setDistrictList(new ArrayList<DistrictModel>());
			} else if (qName.equals("district")) {
				districtModel = new DistrictModel();
				districtModel.setName(attributes.getValue(0));
				districtModel.setZipcode(attributes.getValue(1));
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			// 遇到结束标记的时候，会调用这个方法
			if (qName.equals("district")) {
				cityModel.getDistrictList().add(districtModel);
			} else if (qName.equals("city")) {
				provinceModel.getCityList().add(cityModel);
			} else if (qName.equals("province")) {
				provinceList.add(provinceModel);
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
		}

	}

	/**
	 * 城市实体类
	 * 
	 * @author CK
	 *
	 */
	public class CityModel {
		private String name;
		private List<DistrictModel> districtList;

		public CityModel() {
			super();
		}

		public CityModel(String name, List<DistrictModel> districtList) {
			super();
			this.name = name;
			this.districtList = districtList;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<DistrictModel> getDistrictList() {
			return districtList;
		}

		public void setDistrictList(List<DistrictModel> districtList) {
			this.districtList = districtList;
		}

		@Override
		public String toString() {
			return "CityModel [name=" + name + ", districtList=" + districtList + "]";
		}

	}

	/**
	 * 省份实体类
	 * 
	 * @author CK
	 *
	 */
	public class ProvinceModel {
		private String name;
		private List<CityModel> cityList;

		public ProvinceModel() {
			super();
		}

		public ProvinceModel(String name, List<CityModel> cityList) {
			super();
			this.name = name;
			this.cityList = cityList;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<CityModel> getCityList() {
			return cityList;
		}

		public void setCityList(List<CityModel> cityList) {
			this.cityList = cityList;
		}

		@Override
		public String toString() {
			return "ProvinceModel [name=" + name + ", cityList=" + cityList + "]";
		}

	}

	/**
	 * 区域实体类
	 * 
	 * @author CK
	 *
	 */
	public class DistrictModel {
		private String name;
		private String zipcode;

		public DistrictModel() {
			super();
		}

		public DistrictModel(String name, String zipcode) {
			super();
			this.name = name;
			this.zipcode = zipcode;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getZipcode() {
			return zipcode;
		}

		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}

		@Override
		public String toString() {
			return "DistrictModel [name=" + name + ", zipcode=" + zipcode + "]";
		}

	}

	/**
	 * 所有省
	 */
	protected String[] mProvinceDatas;
	/**
	 * key - 省 value - 市
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

	/**
	 * key - 区 values - 邮编
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

	/**
	 * 当前省的名称
	 */
	protected String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	protected String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	protected String mCurrentDistrictName = "";

	/**
	 * 当前区的邮政编码
	 */
	protected String mCurrentZipCode = "";

	/**
	 * 解析省市区的XML数据
	 */

	protected void initProvinceDatas(String dataFileName) {
		List<ProvinceModel> provinceList = null;
		AssetManager asset = context.getAssets();
		try {
			InputStream input = asset.open(dataFileName);
			// 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// 解析xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// 获取解析出来的数据
			provinceList = handler.getDataList();
			// */ 初始化默认选中的省、市、区
			if (provinceList != null && !provinceList.isEmpty()) {
				mCurrentProviceName = provinceList.get(0).getName();
				List<CityModel> cityList = provinceList.get(0).getCityList();
				if (cityList != null && !cityList.isEmpty()) {
					mCurrentCityName = cityList.get(0).getName();
					List<DistrictModel> districtList = cityList.get(0).getDistrictList();
					mCurrentDistrictName = districtList.get(0).getName();
					mCurrentZipCode = districtList.get(0).getZipcode();
				}
			}
			// */
			mProvinceDatas = new String[provinceList.size()];
			for (int i = 0; i < provinceList.size(); i++) {
				// 遍历所有省的数据
				mProvinceDatas[i] = provinceList.get(i).getName();
				List<CityModel> cityList = provinceList.get(i).getCityList();
				String[] cityNames = new String[cityList.size()];
				for (int j = 0; j < cityList.size(); j++) {
					// 遍历省下面的所有市的数据
					cityNames[j] = cityList.get(j).getName();
					List<DistrictModel> districtList = cityList.get(j).getDistrictList();
					String[] distrinctNameArray = new String[districtList.size()];
					DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
					for (int k = 0; k < districtList.size(); k++) {
						// 遍历市下面所有区/县的数据
						DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(),
								districtList.get(k).getZipcode());
						// 区/县对于的邮编，保存到mZipcodeDatasMap
						mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
						distrinctArray[k] = districtModel;
						distrinctNameArray[k] = districtModel.getName();
					}
					// 市-区/县的数据，保存到mDistrictDatasMap
					mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
				}
				// 省-市的数据，保存到mCitisDatasMap
				mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {

		}
	}

}
