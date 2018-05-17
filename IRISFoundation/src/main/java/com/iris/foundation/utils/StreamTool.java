package com.iris.foundation.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {

	/**
	 * 从输入流中获取数据
	 * @param inStream 输入流
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len=inStream.read(buffer)) != -1 ){
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		byte[] back = outStream.toByteArray();
		outStream.close();
		return back;
	}

	public static byte[] getBytes(InputStream is) throws Exception{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = is.read(buffer))!=-1){
			bos.write(buffer, 0, len);
		}
		is.close();
		bos.flush();
		byte[] result = bos.toByteArray();
		
		return  result;
	}
	
/*	
	public static StringBuffer getJsonString(InputStream instream) {
		StringBuffer json = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(instream, "utf-8"));
			char[] tmp = new char[1024];
			int l;
			while ((l = br.read(tmp)) != -1) {
				json.append(tmp, 0, l);
			}
		} catch (Throwable e) {
//			Logger.printStackTrace(e);
		}
		finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				br = null;
			}
		}
		return json;
	}*/

}
