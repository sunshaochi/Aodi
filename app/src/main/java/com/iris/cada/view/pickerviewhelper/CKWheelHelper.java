package com.iris.cada.view.pickerviewhelper;

import java.io.InputStream;
import java.util.ArrayList;
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

public class CKWheelHelper {
	private Context context;
	private View viewGroup;
	/**
	 * 所有省
	 */
	public ArrayList<String> mProvinceDatas;
	/**
	 * key - 省 value - 市
	 */
	public ArrayList<ArrayList<String>> mCitisDatasMap = new ArrayList<ArrayList<String>>();
	/**
	 * key - 市 values - 区
	 */
	public ArrayList<ArrayList<ArrayList<String>>> mDistrictDatasMap = new ArrayList<ArrayList<ArrayList<String>>>();

	/**
	 * key - 区 values - 邮编
	 */
	public Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

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

	public CKWheelHelper(Context context, View viewGroup) {
		this.context = context;
		this.viewGroup = viewGroup;
	}

	public CKWheelHelper(Context context) {
		this.context = context;
	}

	public void dismiss() {
		viewGroup.setVisibility(View.GONE);
	}

	public void visible() {
		viewGroup.setVisibility(View.VISIBLE);
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
	 * 解析省市区的XML数据
	 */

	public void initProvinceDatas(String dataFileName) {
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
			mProvinceDatas = new ArrayList<String>();
			for (int i = 0; i < provinceList.size(); i++) {
				// 遍历所有省的数据
				mProvinceDatas.add(provinceList.get(i).getName());
				List<CityModel> cityList = provinceList.get(i).getCityList();
				ArrayList<String> cityNames = new ArrayList<String>();
				ArrayList<ArrayList<String>> cityTemp = new ArrayList<ArrayList<String>>();
				for (int j = 0; j < cityList.size(); j++) {
					// 遍历省下面的所有市的数据
					cityNames.add(cityList.get(j).getName());
					List<DistrictModel> districtList = cityList.get(j).getDistrictList();
					ArrayList<String> distrinctNameArray = new ArrayList<String>();
					List<DistrictModel> distrinctArray = new ArrayList<DistrictModel>();
					for (int k = 0; k < districtList.size(); k++) {
						// 遍历市下面所有区/县的数据
						DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(),
								districtList.get(k).getZipcode());
						// 区/县对于的邮编，保存到mZipcodeDatasMap
						mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
						distrinctArray.add(districtModel);
						distrinctNameArray.add(districtModel.getName());
					}
					// 市-区/县的数据，保存到mDistrictDatasMap
					cityTemp.add(distrinctNameArray);
				}
				mDistrictDatasMap.add(cityTemp);
				// 省-市的数据，保存到mCitisDatasMap
				mCitisDatasMap.add(cityNames);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {

		}
	}
}
