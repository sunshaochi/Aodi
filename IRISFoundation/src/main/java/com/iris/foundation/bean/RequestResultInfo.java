package com.iris.foundation.bean;

import java.io.Serializable;
import java.lang.reflect.Type;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.iris.foundation.tools.StringUtils;
import com.iris.foundation.utils.LogIRISUtilS;

/**
 * 类的说明：网络请求返回JavaBean针对统一的返回格式：{"data":[],"error_code":"0000","error_text":null}
 * 作者：chenlipeng
 * 创建时间：2017/6/16 11:22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestResultInfo<T>  implements Serializable {
    private static final long serialVersionUID = -5173718782598185482L;
    public String error_code = "0000";
    private String error_text = "";//请求结果提示语
    private T data;// 返回数据,字符串格式
    private String result;//返回数据字符串

    /**
     * 初始化ResultInfo
     * @param result
     * @param dataType data数据类型
     */
	public RequestResultInfo(String result, Type dataType) {
		this.result = result;
		Gson gson = new Gson();
		JSONObject json = null;
		try {
			json = new JSONObject(result);
		} catch (JSONException e1) {
			LogIRISUtilS.logException(e1);
		}
		try {
			this.error_text = json.getString("error_text");
		} catch (Exception e) {
			LogIRISUtilS.logException(e);
		}
		try {
			this.error_code = json.getString("error_code");
		} catch (Exception e) {
			LogIRISUtilS.logException(e);
		}
		try {
			Object data = json.get("data");
			if (data != null && !StringUtils.isEmpty(data.toString())) {
				this.data = gson.fromJson(data.toString(), dataType);
			}
		} catch (Exception e) {
			LogIRISUtilS.logException(e);
		}

	}

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
    public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_text() {
		return error_text;
	}

	public void setError_text(String error_text) {
		this.error_text = error_text;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	/**
     * 是否请求成功
     *
     * @return
     */
    public boolean isRequestSuccessed() {
        return "0000".equals(error_code);
    }

    @Override
    public String toString() {
        return "[RequestResultInfo : error_code = " + error_code + ", error_text = " + error_text + ", data = " + data;
    }
}
