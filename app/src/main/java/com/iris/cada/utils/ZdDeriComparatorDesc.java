package com.iris.cada.utils;

import java.util.Comparator;

import com.iris.cada.entity.ShzdBean;

public class ZdDeriComparatorDesc implements Comparator<ShzdBean> {
	@Override
	public int compare(ShzdBean lhs, ShzdBean rhs) {
		return StringUtils.intStringComparator(lhs.get平均单车收入(), rhs.get平均单车收入(), false);
//		return lhs.getDerivativesBusinessAverageCarProfits().compareTo(rhs.getDerivativesBusinessAverageCarProfits());
	}
}
