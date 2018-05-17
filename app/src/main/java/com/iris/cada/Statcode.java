package com.iris.cada;

public class Statcode {
	
	public static final int SHSUC=201;//首页售后成功
	public static final int XSSUC=202;//销售成功
	
	public static final int SHFAILED=401;//售后数据失败
	public static final int XS_FAILED=402;//销售数据失败
	
	public static int Messnum = 0;//用来表示收到几条推送默认为0条
	
	public static int type; //0表示在分割界面，1表示在销售界面，2表示在售后界面
	
	public static int inactivity=0;//用来表示在哪个界面,1表示在首页

}
