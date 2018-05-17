package com.iris.cada.service;

import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.entity.TimeBean;
import com.iris.cada.utils.GetHttpConnection;
import com.iris.cada.utils.StreamTool;

public class UpdateService {

	static String path = "http://115.28.28.247:7522/AppDeadline/IAccountApp";
	// static String path = "http://192.168.1.99:8081/AppDeadline/ILogApp";
	static String s = "";

	public static Boolean update(String version) throws Exception {
		// ArrayList<TimeBean> list = new ArrayList<TimeBean>();

		byte[] data = StreamTool.readInputStream(GetHttpConnection.getHttpConnection(path));
		String json = new String(data);
		Gson gson = new Gson();
		List<TimeBean> list = gson.fromJson(json, new TypeToken<List<TimeBean>>() {
		}.getType());
        
		Log.e("版本",list.toString());
		
		for (TimeBean time : list) {
			s = time.getValue();
		}
		if (Float.parseFloat(s) > Float.parseFloat(version)) {
			// 如果服务器的版本号大于本地版本号,则返回true
			return true;
		} else {
			return false;
		}
	}
}
