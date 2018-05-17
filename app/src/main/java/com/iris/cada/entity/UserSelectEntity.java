package com.iris.cada.entity;

/**
 * userSelect的实体类
 * 
 * @author jiahaoGuo
 *
 */
public class UserSelectEntity {
	public String carModel;// 是或者否(是否车型模式) 默认是否

	/**
	 * 本地有保存数据时
	 * 
	 * @param selName
	 * @param selRole
	 * @param compare
	 * @param compareModel
	 * @param carModel
	 * @param car
	 */
	public UserSelectEntity(String carModel) {
		super();
		this.carModel = carModel;
	}

	/**
	 * 第一次登录时
	 * 
	 * @param orgName
	 * @param orgRole
	 */
	public UserSelectEntity() {
		super();
		this.carModel = "否";
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	@Override
	public String toString() {
		return "UserSelectEntity [carModel=" + carModel + "]";
	}
}
