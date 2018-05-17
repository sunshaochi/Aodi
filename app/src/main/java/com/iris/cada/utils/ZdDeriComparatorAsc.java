package com.iris.cada.utils;

import java.util.Comparator;

import com.iris.cada.entity.Check;
import com.iris.cada.entity.ShzdBean;

public class ZdDeriComparatorAsc implements Comparator<ShzdBean> {

	@Override
	public int compare(ShzdBean lhs, ShzdBean rhs) {
		return StringUtils.intStringComparator(lhs.get平均单车收入(), rhs.get平均单车收入(), true);
//		return lhs.getDerivativesBusinessAverageCarProfits().compareTo(rhs.getDerivativesBusinessAverageCarProfits());
	}

}
