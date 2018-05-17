package com.iris.cada.utils;

import java.util.Comparator;

import com.iris.cada.entity.Check;
import com.iris.cada.entity.ShzdBean;

public class ZdCheckXinPotentialCustomerComparatorAsc implements Comparator< ShzdBean> {

	@Override
	public int compare(ShzdBean lhs, ShzdBean rhs) {
		return StringUtils.intStringComparator(lhs.get合计收入(), rhs.get合计收入(), true);

//		return lhs.getXinPotentialCustomer().compareTo(rhs.getXinPotentialCustomer());
	}

}
