package com.iris.cada.utils;

import java.util.Comparator;

import com.iris.cada.entity.Check;
import com.iris.cada.entity.ShzdBean;

public class ZdCheckJieDaiComparatorDesc implements Comparator<ShzdBean> {

	@Override
	public int compare(ShzdBean lhs, ShzdBean rhs) {
		return StringUtils.intStringComparator(lhs.get维修台次(), rhs.get维修台次(), false);
	}

}
