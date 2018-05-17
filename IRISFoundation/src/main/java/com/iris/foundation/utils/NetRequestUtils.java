package com.iris.foundation.utils;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.iris.foundation.activity.IRISApplication;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

/**
 * HttpUtils 请求封装类 当数据为空时返回值为DATA_FAILED,当服务器请求失败返回值为SERVER_FAILED
 * 
 * @author CK
 *
 */
@SuppressWarnings("deprecation")
public class NetRequestUtils {

	private Handler mDelivery;
	private Gson mgson;

	/**
	 * Post 请求数据
	 *
	 * @param url
	 * @param params
	 * @param handler
	 */
	public void executePost(String url, RequestParams params, final Handler handler) {
		execute(HttpMethod.POST, url, params, null, handler, IRISApplication.DATA_SUC, IRISApplication.DATA_FAILED);
	}
	/**
	 * GET 请求数据
	 *
	 * @param url
	 * @param params
	 * @param handler
	 */
	public void executeGet(String url, RequestParams params, final Handler handler) {
		execute(HttpMethod.GET, url, params, null, handler, IRISApplication.DATA_SUC, IRISApplication.DATA_FAILED);
	}

	/**
	 * Post请求数据,
	 *
	 * @param url
	 * @param params
	 * @param handler
	 * @param sucCode
	 *            成功标识符
	 * @param failedCode
	 *            失败标识符
	 */
	public void executePost(String url, RequestParams params, final Handler handler, int sucCode, int failedCode) {
		execute(HttpMethod.POST, url, params, null, handler, sucCode, failedCode);
	}
	/**
	 * Get请求数据,
	 *
	 * @param url
	 * @param params
	 * @param handler
	 * @param sucCode
	 *            成功标识符
	 * @param failedCode
	 *            失败标识符
	 */
	public void executeGet(String url, RequestParams params, final Handler handler, int sucCode, int failedCode) {
		execute(HttpMethod.GET, url, params, null, handler, sucCode, failedCode);
	}


	/**
	 * Post 上传数据,包含body
	 *
	 * @param url
	 * @param params
	 * @param bodys
	 * @param handler
	 */
	public  void executePost(String url, RequestParams params, Object bodys, final Handler handler) {
		execute(HttpMethod.POST, url, params, bodys, handler, IRISApplication.DATA_SUC, IRISApplication.DATA_FAILED);
	}

	/**
	 * Post 上传数据,包含body
	 *
	 * @param url
	 * @param params
	 * @param bodys
	 * @param handler
	 * @param sucCode
	 * @param failedCode
	 */
	public  void executePost(String url, RequestParams params, Object bodys, final Handler handler, int sucCode,
							 int failedCode) {
		execute(HttpMethod.POST, url, params, bodys, handler, sucCode, failedCode);
	}

	/**将含body的Object转换为二进制
	 * @param method
	 * @param url
	 * @param params
	 * @param bodys
	 * @param handler
	 * @param sucCode
	 * @param failedCode
	 */
	public void execute(HttpMethod method, String url, RequestParams params, Object bodys, final Handler handler,
						final int sucCode, final int failedCode) {
		// -------------start上传body-----------------
		String test = "";
		Gson g = new Gson();
		test = g.toJson(bodys);
		if(null==test){
			test = "";
		}
		executePost(method, url, params, test.getBytes(), handler, sucCode, failedCode);
	}

	/**请求网络的基本方法
	 * @param method
	 * @param url
	 * @param params
	 * @param b
	 * @param handler
	 * @param sucCode
	 * @param failedCode
	 */
	@SuppressWarnings("deprecation")
	public void executePost(HttpMethod method, final String url, final RequestParams params, byte[] b, final Handler handler,
							final int sucCode, final int failedCode) {

		// -------------start上传body-----------------
		ByteArrayEntity entity = new ByteArrayEntity(b);
		params.setBodyEntity(entity);

		if(!TextUtils.isEmpty(SpUtils.getCookie(IRISApplication.getApplication()))){
			params.addHeader("Cookie",SpUtils.getCookie(IRISApplication.getApplication()));
		}
		// -------------end上传body-----------------
		IRISApplication.hu.send(method, url, params, new RequestCallBack<HttpUtils>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Message msg = new Message();
				msg.what = IRISApplication.SERVER_FAILED;
				handler.handleMessage(msg);

				LogIRISUtilS.e("tt", arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<HttpUtils> arg0) {
				Message msg = handler.obtainMessage();
				String result = arg0.result + "";
				if (TextUtils.isEmpty(result) || "[]".equals(result)||"{}".equals(result)) {
					msg.what = failedCode;
					handler.handleMessage(msg);
				} else {
					msg.what = sucCode;
					msg.obj = result;
					handler.handleMessage(msg);
				}

			}
		});
	}


    /*以下是josn传参的请求*/
	/**
	 * Post 请求数据（json传参）
	 *
	 * @param url
	 * @param params
	 * @param handler
	 */
	public void executePostForjson(String url, RequestParams params, Object bodys, final Handler handler) {
		executeForjson(HttpMethod.POST, url, params, bodys, handler, IRISApplication.DATA_SUC, IRISApplication.DATA_FAILED);
	}


	/**将含body的Object转换为二进制(json传参)
	 * @param method
	 * @param url
	 * @param params
	 * @param bodys
	 * @param handler
	 * @param sucCode
	 * @param failedCode
	 */
	public void executeForjson(HttpMethod method, String url, RequestParams params, Object bodys, final Handler handler,
						final int sucCode, final int failedCode) {
		// -------------start上传body-----------------
		String test = "";
		Gson g = new Gson();
		test = g.toJson(bodys);
		if(null==test){
			test = "";
		}
		executePostForjson(method, url, params, test.getBytes(), handler, sucCode, failedCode);
	}

	/**请求网络的基本方法(json传参)
	 * @param method
	 * @param url
	 * @param params
	 * @param b
	 * @param handler
	 * @param sucCode
	 * @param failedCode
	 */
	@SuppressWarnings("deprecation")
	public void executePostForjson(HttpMethod method, final String url, final RequestParams params, byte[] b, final Handler handler,
								   final int sucCode, final int failedCode) {

		// -------------start上传body-----------------
		mDelivery = new Handler(Looper.getMainLooper());
		mgson = new Gson();
		ByteArrayEntity entity = new ByteArrayEntity(b);
		params.setBodyEntity(entity);

//		if(!TextUtils.isEmpty(SpUtils.getCookie(IRISApplication.getApplication()))){
//			params.addHeader("Cookie",SpUtils.getCookie(IRISApplication.getApplication()));
//		}
		// -------------end上传body-----------------
		IRISApplication.hu.send(method, url, params, new RequestCallBack<HttpUtils>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Message msg = new Message();
				msg.what = IRISApplication.SERVER_FAILED;
				handler.handleMessage(msg);
				LogIRISUtilS.e("tt", arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<HttpUtils> arg0) {
				String head=arg0.getHeaders("Set-Cookie").toString();
				SpUtils.setCooike(IRISApplication.getApplication(),"");
				SpUtils.setCooike(IRISApplication.getApplication(),head);//保存cookier
				Message msg = handler.obtainMessage();
				String result = arg0.result + "";
				if (TextUtils.isEmpty(result) || "[]".equals(result)||"{}".equals(result)) {
					msg.what = 203;//暂无数据
					handler.handleMessage(msg);
				} else {
					try {
						JSONObject jsonObject=new JSONObject(result.toString());
						String code=jsonObject.getString("code");
						String message=jsonObject.getString("message");
						if(code.equals("success")){
							msg.what = sucCode;//获取成功
							msg.obj = result;
							handler.handleMessage(msg);
						}else {
							msg.what = failedCode;//获取失败
                            msg.obj=message;
							handler.handleMessage(msg);
						}


					} catch (JSONException e) {
						msg.what = 101;//解析异常
						handler.handleMessage(msg);
						e.printStackTrace();
					}

				}

			}
		});
	}



}

