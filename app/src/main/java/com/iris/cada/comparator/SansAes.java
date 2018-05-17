package com.iris.cada.comparator;

import java.util.Comparator;

import com.iris.cada.entity.BbkcsBean;
import com.iris.cada.utils.StringUtils;

public class SansAes implements Comparator<BbkcsBean> {

	@Override
	public int compare(BbkcsBean lhs, BbkcsBean rhs) {
		// TODO Auto-generated method stub
		return StringUtils.intStringComparator(lhs.getCoty030()+"", rhs.getCoty030()+"", true);
	}

}
