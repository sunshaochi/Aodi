package com.iris.cada.entity;

import java.util.List;

public class LoginBackInfo {
	// 品牌
	private String Brand = null;
	// "License":"ZSJ"
	private String License = null;//许可
	// 角色
	private String Role = null;
	private String Tel = null;
	private String JXSCode = null;
	private String JXSName = null;
	private String Group = null;
	private String UpName = null;
	private String UpLevel = null;
	private String Name = null;
	private String Info = null;
	// 版本号
	private String Ver = null;
	// 车型列表
	private List<String> Models = null;
	// 管理顾问姓名列表
	private List<String> Managers = null;


	private String id=null;
	private String userName=null;
	private String password=null;
	private String roleId=null;
	private String roleCode=null;
	private String roleName=null;
	private String mobileNo=null;
	private String dealerId=null;
	private String dealerCode=null;
	private String dealerFullCode=null;
	private String dealerType=null;
	private String dealerName=null;
	private String groupName=null;
	private String personName=null;
	private String isLogin=null;
	private String isLock=null;
	private String lockTime=null;
	private String loginTime=null;
	private String loginVerson=null;
	private String admin=null;
	private String editData=null;
	private String isDelete=null;
	private String deleteTime=null;
	private String createTime=null;
	private String createUser=null;

	private String lastUpdateTime=null;
	private String lastUpdateUser=null;

	private List<CarbrandBean>carBrandList;//品牌
	private List<CarbrandBean>carModelList;//车型
	private List<String>carCodeList;//车型编码

	private String state;
	
	//增加权限字段,用于判断显示哪一些页面
	private String  permission;
	
	private String isShowQa;//用来区分是否显示常见问题0不显示，1显示
	
	

	public String getIsShowQa() {
		return isShowQa;
	}

	public void setIsShowQa(String isShowQa) {
		this.isShowQa = isShowQa;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
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

	public String getInfo() {
		return Info;
	}

	public void setInfo(String info) {
		Info = info;
	}

	public String getVer() {
		return Ver;
	}

	public void setVer(String ver) {
		Ver = ver;
	}

	public List<String> getModels() {
		return Models;
	}

	public void setModels(List<String> models) {
		Models = models;
	}


	public List<String> getCarCodeList() {
		return carCodeList;
	}

	public void setCarCodeList(List<String> carCodeList) {
		this.carCodeList = carCodeList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerFullCode() {
		return dealerFullCode;
	}

	public void setDealerFullCode(String dealerFullCode) {
		this.dealerFullCode = dealerFullCode;
	}

	public String getDealerType() {
		return dealerType;
	}

	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public String getLockTime() {
		return lockTime;
	}

	public void setLockTime(String lockTime) {
		this.lockTime = lockTime;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginVerson() {
		return loginVerson;
	}

	public void setLoginVerson(String loginVerson) {
		this.loginVerson = loginVerson;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getEditData() {
		return editData;
	}

	public void setEditData(String editData) {
		this.editData = editData;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(String deleteTime) {
		this.deleteTime = deleteTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<CarbrandBean> getCarBrandList() {
		return carBrandList;
	}

	public void setCarBrandList(List<CarbrandBean> carBrandList) {
		this.carBrandList = carBrandList;
	}

	public List<CarbrandBean> getCarModelList() {
		return carModelList;
	}

	public void setCarModelList(List<CarbrandBean> carModelList) {
		this.carModelList = carModelList;
	}

	public List<String> getManagers() {
		return Managers;
	}

	public void setManagers(List<String> managers) {
		Managers = managers;
	}

	@Override
	public String toString() {
		return "LoginBackInfo [Brand=" + Brand + ", License=" + License + ", Role=" + Role + ", Tel=" + Tel
				+ ", JXSCode=" + JXSCode + ", JXSName=" + JXSName + ", Group=" + Group + ", UpName=" + UpName
				+ ", UpLevel=" + UpLevel + ", Name=" + Name + ", Info=" + Info + ", Ver=" + Ver + ", Models=" + Models
				+ ", Managers=" + Managers + ", permission=" + permission + ", isShowQa=" + isShowQa + "]";
	}

	
}
