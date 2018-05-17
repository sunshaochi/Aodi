package com.iris.cada.utils;

import java.util.Comparator;

import com.iris.cada.entity.ShzdBean;

public class ZdCheckLookToBuyRateComparatorDes implements Comparator<ShzdBean> {

	@Override
	public int compare(ShzdBean lhs, ShzdBean rhs) {
		return StringUtils.intStringComparator(lhs.get合计毛利(), rhs.get合计毛利(), false);

//		return lhs.getLookToBuyRate().compareTo(rhs.getLookToBuyRate());
	}

}
