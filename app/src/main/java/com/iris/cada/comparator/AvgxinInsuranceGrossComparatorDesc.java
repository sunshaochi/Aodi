package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.IViewProfitReport;

/**
 * 平均单车新保毛利    降序   比较器
 * 
 * @author ChuanJing
 * @date 2016年10月21日 上午10:22:22
 */
public class AvgxinInsuranceGrossComparatorDesc implements Comparator<IViewProfitReport> {

	@Override
	public int compare(IViewProfitReport lhs, IViewProfitReport rhs) {
		
		return rhs.getAvgxinInsuranceGross().compareTo(lhs.getAvgxinInsuranceGross());
	}

}