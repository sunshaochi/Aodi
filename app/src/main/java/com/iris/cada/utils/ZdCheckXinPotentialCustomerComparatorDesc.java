package com.iris.cada.utils;

import java.util.Comparator;

import com.iris.cada.entity.Check;
import com.iris.cada.entity.ShzdBean;

public class ZdCheckXinPotentialCustomerComparatorDesc implements Comparator< ShzdBean> {

	@Override
	public int compare(ShzdBean lhs, ShzdBean rhs) {
		return StringUtils.intStringComparator(lhs.get合计收入(), rhs.get合计收入(), false);

//		return rhs.getXinPotentialCustomer().compareTo(lhs.getXinPotentialCustomer());
	}

}
