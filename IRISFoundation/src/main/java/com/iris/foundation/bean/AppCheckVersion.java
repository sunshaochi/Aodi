package com.iris.foundation.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 检查版本更新
 * @author Chenlp
 * @date 2017年7月19日
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppCheckVersion {
	/**版本号*/
	private String version;

	/**是否强制更新-0-否/1-是*/
	private String forceState;

	/**平台状态*/
	private String platformState;

	/**apk下载地址*/
	private String url;

	/**信息*/
	private String info;

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return this.version;
	}

	public void setForceState(String forceState) {
		this.forceState = forceState;
	}

	public String getForceState() {
		return this.forceState;
	}

	public void setPlatformState(String platformState) {
		this.platformState = platformState;
	}

	public String getPlatformState() {
		return this.platformState;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInfo() {
		return this.info;
	}
}
