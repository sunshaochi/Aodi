package com.iris.cada.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetHttpConnection {

	public static InputStream getHttpConnection(String path) throws IOException {
		// 创建网络链接
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		InputStream inStream = null;
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setConnectTimeout(20000);
		conn.setReadTimeout(20000);
		conn.setRequestMethod("POST");
		inStream = conn.getInputStream();
		return inStream;
	}
}
