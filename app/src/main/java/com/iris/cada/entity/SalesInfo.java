package com.iris.cada.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class SalesInfo implements Serializable{

	/**
	 * 品牌
	 * 许可
	 * 角色
	 * 电话
	 * 经销商代码
	 * 经销商名
	 * 集团
	 * 上级名称
	 * 上级级别
	 * 名字
	 * 反馈信息
	 * 版本
	 */
	private static final long serialVersionUID = 1L;
	private String Brand;
	private String License;
	private String Role;
	private String Tel;
	private String JXSCode;
	private String JXSName;
	private String Group;
	private String UpName;
	private String UpLevel;
	private String Name;
	private String Info;
	private String Ver;
	private ArrayList<String> Models;//车型
	private ArrayList<String> Managers;//经销商或者销售顾问名字

	public SalesInfo() {
		super();
	}




	public SalesInfo(String brand, String license, String role, String tel,
			String jXSCode, String jXSName, String group, String upName,
			String upLevel, String name, String info, String ver,
			ArrayList<String> models, ArrayList<String> managers) {
		super();
		Brand = brand;
		License = license;
		Role = role;
		Tel = tel;
		JXSCode = jXSCode;
		JXSName = jXSName;
		Group = group;
		UpName = upName;
		UpLevel = upLevel;
		Name = name;
		Info = info;
		Ver = ver;
		Models = models;
		Managers = managers;
	}




	public String getVer() {
		return Ver;
	}




	public void setVer(String ver) {
		Ver = ver;
	}




	public String getInfo() {
		return Info;
	}




	public void setInfo(String info) {
		Info = info;
	}




	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getJXSCode() {
		return JXSCode;
	}

	public void setJXSCode(String jXSCode) {
		JXSCode = jXSCode;
	}

	public String getJXSName() {
		return JXSName;
	}

	public void setJXSName(String jXSName) {
		JXSName = jXSName;
	}

	public String getGroup() {
		return Group;
	}

	public void setGroup(String group) {
		Group = group;
	}

	public String getUpName() {
		return UpName;
	}

	public void setUpName(String upName) {
		UpName = upName;
	}

	public String getUpLevel() {
		return UpLevel;
	}

	public void setUpLevel(String upLevel) {
		UpLevel = upLevel;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public ArrayList<String> getModels() {
		return Models;
	}


	public void setModels(ArrayList<String> models) {
		Models = models;
	}




	public String getBrand() {
		return Brand;
	}



	public void setBrand(String brand) {
		Brand = brand;
	}



	public String getLicense() {
		return License;
	}



	public void setLicense(String license) {
		License = license;
	}



	public ArrayList<String> getManagers() {
		return Managers;
	}



	public void setManagers(ArrayList<String> managers) {
		Managers = managers;
	}



	@Override
	public String toString() {
		return "SalesInfo [Brand=" + Brand + ", License=" + License + ", Role="
				+ Role + ", Tel=" + Tel + ", JXSCode=" + JXSCode + ", JXSName="
				+ JXSName + ", Group=" + Group + ", UpName=" + UpName
				+ ", UpLevel=" + UpLevel + ", Name=" + Name + ", Info=" + Info
				+ ", Ver=" + Ver + ", Models=" + Models + ", Managers="
				+ Managers + "]";
	}




}
